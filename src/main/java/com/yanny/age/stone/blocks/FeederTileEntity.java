package com.yanny.age.stone.blocks;

import com.yanny.age.stone.config.Config;
import com.yanny.age.stone.subscribers.TileEntitySubscriber;
import com.yanny.ages.api.utils.ItemStackUtils;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class FeederTileEntity extends TileEntity implements IInventoryInterface, ITickableTileEntity, INamedContainerProvider {
    private static final Set<Item> VALID_ITEMS = new HashSet<>();
    static {
        VALID_ITEMS.addAll(Tags.Items.SEEDS.getAllElements());
        VALID_ITEMS.addAll(Tags.Items.CROPS.getAllElements());
    }

    public static final int ITEMS = 4;

    private final NonNullList<ItemStack> stacks = NonNullList.withSize(ITEMS, ItemStack.EMPTY);
    private final IItemHandlerModifiable nonSidedItemHandler = createNonSidedInventoryHandler(stacks);
    private final LazyOptional<IItemHandlerModifiable> sidedInventoryHandler = LazyOptional.of(() -> createSidedInventoryHandler(stacks));
    private final LazyOptional<IItemHandlerModifiable> nonSidedInventoryHandler = LazyOptional.of(() -> nonSidedItemHandler);
    private final RecipeWrapper inventoryWrapper = new RecipeWrapper(nonSidedItemHandler);

    private AxisAlignedBB boundingBox = new AxisAlignedBB(getPos());

    public FeederTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.feeder);
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote) {
            if (world.rand.nextInt(Config.feederTickChanceBreedAnimalEffect) == 0 && getItem().isPresent()) {
                useOnEntity();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity entity) {
        assert world != null;
        return new FeederContainer(id, pos, world, inventory, entity);
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        assert getType().getRegistryName() != null;
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nonnull
    @Override
    public IInventory getInventory() {
        return inventoryWrapper;
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        ItemStackUtils.deserializeStacks(invTag, stacks);
        super.read(tag);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", ItemStackUtils.serializeStacks(stacks));
        return super.write(tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), getType().hashCode(), getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        read(pkt.getNbtCompound());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side != null) {
                if (side != Direction.UP) {
                    return sidedInventoryHandler.cast();
                }
            } else {
                return nonSidedInventoryHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void validate() {
        super.validate();
        boundingBox = new AxisAlignedBB(pos.getX() - Config.feederEffectRange, pos.getY() - 1, pos.getZ() - Config.feederEffectRange,
                pos.getX() + Config.feederEffectRange + 1, pos.getY() + 2, pos.getZ() + Config.feederEffectRange + 1);
    }

    @Override
    public void remove() {
        sidedInventoryHandler.invalidate();
        nonSidedInventoryHandler.invalidate();
        super.remove();
    }

    boolean isItemValid(@Nonnull ItemStack itemStack) {
        return VALID_ITEMS.contains(itemStack.getItem());
    }

    @Nonnull
    private IItemHandlerModifiable createNonSidedInventoryHandler(@Nonnull NonNullList<ItemStack> stacks) {
        return new ItemStackHandler(stacks) {

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (FeederTileEntity.this.isItemValid(stack)) {
                    return super.insertItem(slot, stack, simulate);
                }

                return stack;
            }

            @Override
            protected void onContentsChanged(int slot) {
                assert world != null;
                markDirty();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    @Nonnull
    private IItemHandlerModifiable createSidedInventoryHandler(@Nonnull NonNullList<ItemStack> stacks) {
        return new ItemStackHandler(stacks) {
            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (FeederTileEntity.this.isItemValid(stack)) {
                    return super.insertItem(slot, stack, simulate);
                }

                return stack;
            }

            @Override
            protected void onContentsChanged(int slot) {
                assert world != null;
                markDirty();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    @Nonnull
    private Optional<ItemStack> getItem() {
        return stacks.stream().filter(itemStack -> !itemStack.isEmpty()).findFirst();
    }

    private void useOnEntity() {
        assert world != null;
        List<AnimalEntity> entities = world.getEntitiesWithinAABB(AnimalEntity.class, boundingBox, AnimalEntity::canBreed);
        Collections.shuffle(entities);

        if (!entities.isEmpty()) {
            getItem().ifPresent(itemStack -> {
                AnimalEntity winner = entities.get(0);

                if (winner.isChild()) {
                    winner.ageUp((int)((float)(-winner.getGrowingAge() / 20) * 0.1F), true);
                    itemStack.shrink(1);
                } else {
                    List<AnimalEntity> entities1 = world.getEntitiesWithinAABB(winner.getClass(), boundingBox,
                            livingEntity -> !livingEntity.isEntityEqual(winner) && !livingEntity.isChild() && livingEntity.canBreed());

                    if (winner.getGrowingAge() == 0 && entities1.size() < 30) {
                        winner.setInLove(null);
                        itemStack.shrink(1);

                        getItem().ifPresent(itemStack1 -> {
                            if (!entities1.isEmpty()) {
                                Collections.shuffle(entities1);
                                AnimalEntity winner1 = entities1.get(0);

                                if (winner1.getGrowingAge() == 0 && winner1.canBreed()) {
                                    winner1.setInLove(null);
                                    itemStack1.shrink(1);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}

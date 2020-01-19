package com.yanny.age.stone.blocks;

import com.yanny.age.stone.config.Config;
import com.yanny.age.stone.subscribers.ItemSubscriber;
import com.yanny.age.stone.subscribers.TileEntitySubscriber;
import com.yanny.age.stone.utils.ItemStackUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FishingNetTileEntity extends TileEntity implements IInventoryInterface, ITickableTileEntity, INamedContainerProvider {

    static final int INVENTORY_WIDTH = 5;
    static final int INVENTORY_HEIGHT = 3;
    static final int ITEMS = 1 + INVENTORY_WIDTH * INVENTORY_HEIGHT;

    private final NonNullList<ItemStack> stacks = NonNullList.withSize(ITEMS, ItemStack.EMPTY);
    private final IItemHandlerModifiable nonSidedItemHandler = createInventoryHandler(stacks);
    private final LazyOptional<IItemHandlerModifiable> sidedInventoryHandler = LazyOptional.of(() -> createInventoryHandler(stacks));
    private final LazyOptional<IItemHandlerModifiable> nonSidedInventoryHandler = LazyOptional.of(() -> nonSidedItemHandler);
    private final RecipeWrapper inventoryWrapper = new RecipeWrapper(nonSidedItemHandler);

    public FishingNetTileEntity() {
        //noinspection ConstantConditions
        super(TileEntitySubscriber.fishing_net);
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote) {
            if (!stacks.get(0).isEmpty() && world.rand.nextInt(Config.fishingNetChance) == 0 && hasWaterAround()) {
                if (stacks.get(0).attemptDamageItem(1, world.rand, null)) {
                    stacks.set(0, ItemStack.EMPTY);
                    world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.ATTACHED, false));
                }

                generateLoot();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity entity) {
        assert world != null;
        return new FishingNetContainer(id, pos, world, inventory, entity);
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
                if (side == Direction.DOWN) {
                    return sidedInventoryHandler.cast();
                }
            } else {
                return nonSidedInventoryHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
        sidedInventoryHandler.invalidate();
        nonSidedInventoryHandler.invalidate();
        super.remove();
    }

    public void updateState() {
        assert world != null;
        world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.ATTACHED, !stacks.get(0).isEmpty()));
    }

    private void generateLoot() {
        assert world != null;
        LootTable lootTable = ((ServerWorld) world).getServer().getLootTableManager().getLootTableFromLocation(LootTables.GAMEPLAY_FISHING);
        LootContext lootContext = new LootContext.Builder((ServerWorld) world)
                .withParameter(LootParameters.POSITION, getPos())
                .withParameter(LootParameters.TOOL, stacks.get(0))
                .build(LootParameterSets.FISHING);

        List<ItemStack> loot = lootTable.generate(lootContext);
        ItemStackUtils.insertItems(loot, stacks, 1, ITEMS);
    }

    private IItemHandlerModifiable createInventoryHandler(@Nonnull NonNullList<ItemStack> stacks) {
        return new ItemStackHandler(stacks) {

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot == 0) {
                    assert world != null;
                    world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.ATTACHED, true));
                }

                return super.insertItem(slot, stack, simulate);
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (slot == 0) {
                    assert world != null;
                    world.setBlockState(getPos(), getBlockState().with(BlockStateProperties.ATTACHED, false));
                }

                return super.extractItem(slot, amount, simulate);
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return (slot == 0) && (stack.getItem() == ItemSubscriber.grass_mesh);
            }

            @Override
            protected void onContentsChanged(int slot) {
                assert world != null;
                markDirty();
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    private boolean hasWaterAround() {
        assert world != null;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            IFluidState fluidState = world.getFluidState(pos.offset(direction));

            if (!fluidState.isSource() || fluidState.getFluid() != Fluids.WATER) {
                return false;
            }
        }

        return true;
    }
}

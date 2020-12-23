package com.yanny.age.stone.compatibility.crt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.exceptions.ScriptException;
import com.blamejared.crafttweaker.impl.blocks.MCBlock;
import com.yanny.age.stone.config.Config;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ZenRegister
@ZenCodeType.Name("mods.stone_age.ConfigManager")
public class ConfigManager {

    @ZenCodeType.Method
    public static MCBlock[] getDisabledBlocksInStoneAge() {
        return Config.disabledUseInStoneAgeList.stream().map(MCBlock::new).toArray(MCBlock[]::new);
    }

    @ZenCodeType.Method
    public static void addDisabledBlockInStoneAge(String ...resource) {
        Set<Block> blocks = new HashSet<>();
        Arrays.stream(resource).forEach(resourceLocation -> {
            ResourceLocation res = new ResourceLocation(resourceLocation);
            Block block = ForgeRegistries.BLOCKS.getValue(res);

            if (block != null) {
                blocks.add(block);
            } else {
                throw new ScriptException("Block resource " + resourceLocation + " does not exists!");
            }
        });

        Config.disabledUseInStoneAgeList.addAll(blocks);
    }

    @ZenCodeType.Method
    public static void removeDisabledBlockInStoneAge(String resource) {
        ResourceLocation res = new ResourceLocation(resource);
        Block block = ForgeRegistries.BLOCKS.getValue(res);

        if (block != null) {
            Config.disabledUseInStoneAgeList.remove(block);
        } else {
            throw new ScriptException("Block resource " + resource + " does not exists!");
        }
    }
}

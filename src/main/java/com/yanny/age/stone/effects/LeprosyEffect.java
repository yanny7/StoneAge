package com.yanny.age.stone.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class LeprosyEffect extends Effect {
    public LeprosyEffect() {
        super(EffectType.HARMFUL, 0x0b9614);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}

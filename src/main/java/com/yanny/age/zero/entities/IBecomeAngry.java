package com.yanny.age.zero.entities;

import net.minecraft.entity.Entity;

interface IBecomeAngry {
    boolean becomeAngryAt(Entity entity);
    boolean isAngry();
}

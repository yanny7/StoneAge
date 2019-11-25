package com.yanny.age.stone.entities;

import net.minecraft.entity.Entity;

interface IBecomeAngry {
    boolean becomeAngryAt(Entity entity);
    boolean isAngry();
}

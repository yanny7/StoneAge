package com.yanny.age.zero.entities;

import net.minecraft.entity.Entity;

public interface IBecomeAngry {
    boolean becomeAngryAt(Entity entity);
    boolean isAngry();
}

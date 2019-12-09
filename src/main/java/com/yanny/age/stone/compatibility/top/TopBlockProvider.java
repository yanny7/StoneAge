package com.yanny.age.stone.compatibility.top;

import mcjty.theoneprobe.api.IProbeInfoProvider;

public interface TopBlockProvider extends IProbeInfoProvider {
    @Override
    default String getID() {
        return TopCompatibility.ID;
    }
}

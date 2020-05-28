package com.yanny.age.stone.compatibility.top;

import com.yanny.age.stone.Reference;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;

public class TopCompatibility {
    public static final String ID = Reference.MODID + ":default";
    public static final String TOP_MOD_ID = "theoneprobe";
    private static boolean registered = false;

    public static void register() {
        if (registered) {
            return;
        }

        if (ModList.get().isLoaded(TOP_MOD_ID)) {
            registered = true;
            InterModComms.sendTo(TOP_MOD_ID, "getTheOneProbe", TopRegistration::new);
        }
    }
}

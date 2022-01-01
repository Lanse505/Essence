package com.teamacronymcoders.essence.common.util.proxy;

import net.minecraftforge.fml.DistExecutor.SafeSupplier;

import java.util.function.Supplier;

public class EssenceSafeSuppliers {

    public static Supplier<SafeSupplier<EssenceCommonProxy>> getClientProxy() {
        return EssenceClientProvider::new;
    }

    public static Supplier<SafeSupplier<EssenceCommonProxy>> getServerProxy() {
        return EssenceServerProvider::new;
    }
}

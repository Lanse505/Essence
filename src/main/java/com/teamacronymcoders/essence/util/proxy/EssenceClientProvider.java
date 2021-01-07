package com.teamacronymcoders.essence.util.proxy;

import net.minecraftforge.fml.DistExecutor.SafeSupplier;

public class EssenceClientProvider implements SafeSupplier<EssenceCommonProxy> {
    @Override
    public EssenceCommonProxy get() {
        return new EssenceClientProxy();
    }
}

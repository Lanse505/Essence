package com.teamacronymcoders.essence.common.util.proxy;

import net.minecraftforge.fml.DistExecutor.SafeSupplier;

public class EssenceServerProvider implements SafeSupplier<EssenceCommonProxy> {
    @Override
    public EssenceCommonProxy get() {
        return new EssenceServerProxy();
    }
}

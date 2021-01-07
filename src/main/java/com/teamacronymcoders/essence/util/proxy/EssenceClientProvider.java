package com.teamacronymcoders.essence.util.proxy;

import com.teamacronymcoders.essence.client.EssenceClientProxy;
import com.teamacronymcoders.essence.util.EssenceCommonProxy;
import net.minecraftforge.fml.DistExecutor.SafeSupplier;

public class EssenceClientProvider implements SafeSupplier<EssenceCommonProxy> {
    @Override
    public EssenceCommonProxy get() {
        return new EssenceClientProxy();
    }
}

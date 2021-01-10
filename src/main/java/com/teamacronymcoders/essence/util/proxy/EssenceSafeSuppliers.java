package com.teamacronymcoders.essence.util.proxy;

import java.util.function.Supplier;
import net.minecraftforge.fml.DistExecutor.SafeSupplier;

public class EssenceSafeSuppliers {

  public static Supplier<SafeSupplier<EssenceCommonProxy>> getClientProxy () {
    return EssenceClientProvider::new;
  }

  public static Supplier<SafeSupplier<EssenceCommonProxy>> getServerProxy () {
    return EssenceServerProvider::new;
  }
}

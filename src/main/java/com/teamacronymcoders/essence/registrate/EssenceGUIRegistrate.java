package com.teamacronymcoders.essence.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.client.container.PortableWorkbenchContainer;
import com.teamacronymcoders.essence.client.screen.PortableWorkbenchScreen;
import com.tterrag.registrate.builders.ContainerBuilder;
import com.tterrag.registrate.util.entry.ContainerEntry;

public class EssenceGUIRegistrate {

  public static ContainerEntry<PortableWorkbenchContainer> PORTABLE_WORKBENCH = Essence.ESSENCE_REGISTRATE.container("portable_workbench", (ContainerBuilder.ForgeContainerFactory<PortableWorkbenchContainer>) PortableWorkbenchContainer::new, () -> PortableWorkbenchScreen::new).register();

  public static void init() {}

}

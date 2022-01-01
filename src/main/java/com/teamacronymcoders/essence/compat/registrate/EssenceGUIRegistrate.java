package com.teamacronymcoders.essence.compat.registrate;

import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.client.container.PortableWorkbenchMenu;
import com.teamacronymcoders.essence.client.screen.PortableWorkbenchScreen;
import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;

public class EssenceGUIRegistrate {

    public static MenuEntry<PortableWorkbenchMenu> PORTABLE_WORKBENCH = Essence.ESSENCE_REGISTRATE.menu("portable_workbench", ((MenuBuilder.ForgeMenuFactory<PortableWorkbenchMenu>) (type, windowId, inv, buf) -> new PortableWorkbenchMenu(windowId, inv)), () -> (PortableWorkbenchScreen::new)).register();

    public static void init() {
    }

}

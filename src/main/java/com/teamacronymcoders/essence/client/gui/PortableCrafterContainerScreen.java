package com.teamacronymcoders.essence.client.gui;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.client.screen.container.BasicContainerScreen;
import com.teamacronymcoders.essence.container.PortableCrafterContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import java.util.stream.Collectors;

public class PortableCrafterContainerScreen extends BasicContainerScreen<PortableCrafterContainer> implements IHasContainer<PortableCrafterContainer> {
    public PortableCrafterContainerScreen(PortableCrafterContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.setAddons(container.getPortableCrafter().getScreenAddons().stream().map(IFactory::create).collect(Collectors.toList()));
    }
}

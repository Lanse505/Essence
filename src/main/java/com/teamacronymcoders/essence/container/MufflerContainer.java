package com.teamacronymcoders.essence.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

import java.util.Comparator;
import java.util.List;

public class MufflerContainer extends ContainerScreen<Container> implements Slider.ISlider {

    private static final int[] ranges = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 32, 64, 128, 256 };

    private Button modeButton;
    private Button addSoundButton;
    private Button removeSoundButton;
//    private GuiSlider rangeSlider; // TODO: Figure out this Implementation
//    private GuiSoundList soundList; // TODO: Figure out this Implementation

    public MufflerContainer(Container screenContainer, PlayerInventory inv) {
        super(screenContainer, inv, new TranslationTextComponent("gui.sound_muffler.title"));
    }

    @Override
    protected void init() {
        super.init();
//        String key = muffler.isWhiteList() ? "tile.sound_muffler.gui.button.mode.white_list" : "tile.sound_muffler.gui.button.mode.black_list";
//        modeButton = new Button(0, guiTop + 5, guiLeft + 159, 90, I18n.format(key), pressed -> {
//
//        });
        buttons.add(modeButton);

        addSoundButton = new Button(1, guiTop + 151, guiLeft + 159, 44, new TranslationTextComponent("tile.sound_muffler.gui.button.add"), pressed -> {

        });
        buttons.add(addSoundButton);

        removeSoundButton = new Button(2, guiTop + 151, guiLeft + 205,44, new TranslationTextComponent("tile.sound_muffler.gui.button.remove"), pressed -> {

        });
        removeSoundButton.active = false;
        buttons.add(removeSoundButton);

//        if(muffler.isRanged()) {
//            rangeSlider = new Button(3, guiTop + 151, guiLeft + 7, 128, 14, I18n.format("tile.sound_muffler.gui.slider.range"), "", 0f, 19f, muffler.getRangeIndex(), false, false, this);
//            b.add(rangeSlider);
//        }
//
//        soundList = new GuiSoundList(240, 126, guiTop + 22, guiTop + 148, guiLeft + 8, 14);
//        List<ResourceLocation> sounds = muffler.getMuffledSounds();
//        sounds.sort(Comparator.comparing(ResourceLocation::toString));
//        soundList.setSounds(sounds);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {}


    @Override
    public void onChangeSliderValue(Slider slider) {}
}

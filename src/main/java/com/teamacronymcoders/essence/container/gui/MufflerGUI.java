package com.teamacronymcoders.essence.container.gui;

import com.mojang.datafixers.types.Func;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.ModListWidget;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class MufflerGUI extends Screen {
    protected MufflerGUI(ITextComponent titleIn) {
        super(titleIn);
    }
//
//    /** The X size of the inventory window in pixels. */
//    protected int xSize = 176;
//    /** The Y size of the inventory window in pixels. */
//    protected int ySize = 166;
//    protected int guiLeft;
//    protected int guiTop;
//    private static final int[] ranges = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 32, 64, 128, 256 };
//
//    private Button modeButton;
//    private Button addSoundButton;
//    private Button removeSoundButton;
//    private GuiSlider rangeSlider; // TODO: Figure out this Implementation
//    private GuiSoundListWidget soundList; // TODO: Figure out this Implementation
//
//    private List<SoundEvent> sounds;
//
//    protected MufflerGUI() {
//        super(new TranslationTextComponent("gui.muffler.screen"));
//    }
//
//    @Override
//    protected void init() {
//        super.init();
//        this.guiLeft = (width - xSize) / 2;
//        this.guiTop = (height - ySize) / 2;
//        String key = muffler.isWhiteList() ? "tile.sound_muffler.gui.button.mode.white_list" : "tile.sound_muffler.gui.button.mode.black_list";
//        modeButton = new Button(0, guiTop + 5, guiLeft + 159, 90, I18n.format(key), pressed -> {
//
//        });
//        buttons.add(modeButton);
//
//        addSoundButton = new Button(1, guiTop + 151, guiLeft + 159, 44, I18n.format("tile.sound_muffler.gui.button.add"), pressed -> {
//
//        });
//        buttons.add(addSoundButton);
//
//        removeSoundButton = new Button(2, guiTop + 151, guiLeft + 205,44, I18n.format("tile.sound_muffler.gui.button.remove"), pressed -> {
//            List<ResourceLocation> selectedSounds = soundList.getSelected()
//        });
//        removeSoundButton.active = false;
//        buttons.add(removeSoundButton);
//
//        if(muffler.isRanged()) {
//            rangeSlider = new Button(3, guiTop + 151, guiLeft + 7, 128, 14, I18n.format("tile.sound_muffler.gui.slider.range"), "", 0f, 19f, muffler.getRangeIndex(), false, false, this);
//            buttons.add(rangeSlider);
//        }
//
//        soundList = new GuiSoundListWidget(240, 126, guiTop + 22, guiTop + 148, guiLeft + 8, 14);
//        List<ResourceLocation> sounds = muffler.getMuffledSounds();
//        sounds.sort(Comparator.comparing(ResourceLocation::toString));
//        soundList.setSounds(sounds);
//    }
//
//    @Override
//    public void render(int mouseX, int mouseY, float partialTicks) {
//        soundList.render(mouseX, mouseY, partialTicks);
//        if (soundList != null)
//            soundList.render(mouseX, mouseY, partialTicks);
//
//        String text = I18n.format("fml.menu.mods.search");
//        int x = soundList.getLeft() + ((soundList.getRight() - soundList.getLeft()) / 2) - (getFontRenderer().getStringWidth(text) / 2);
//        getFontRenderer().drawString(text, x, soundList. - getFontRenderer().FONT_HEIGHT, 0xFFFFFF);
//        soundList.render(mouseX, mouseY, partialTicks);
//        super.render(mouseX, mouseY, partialTicks);
//    }
//
//    public <T extends ExtendedList.AbstractListEntry<T>> void buildSoundList(Consumer<T> soundListViewConsumer, Function<SoundEvent, T> newEntry) {
//        sounds.forEach(sound -> soundListViewConsumer.accept(newEntry.apply(sound)));
//    }
//
//    public void setSelected(ModListWidget.ModEntry entry) {
//        this.selected = entry == this.selected ? null : entry;
//        updateCache();
//    }
//
//    public Minecraft getMinecraftInstance()
//    {
//        return minecraft;
//    }
//
//    public FontRenderer getFontRenderer() {
//        return font;
//    }
}

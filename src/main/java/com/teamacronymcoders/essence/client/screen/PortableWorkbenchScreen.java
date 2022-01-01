package com.teamacronymcoders.essence.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamacronymcoders.essence.client.container.PortableWorkbenchMenu;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public class PortableWorkbenchScreen extends AbstractContainerScreen<PortableWorkbenchMenu> implements RecipeUpdateListener, MenuAccess<PortableWorkbenchMenu> {
    private static final ResourceLocation CRAFTING_TABLE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
    private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
    private final RecipeBookComponent recipeBookGui = new RecipeBookComponent();
    private boolean widthTooNarrow;

    public PortableWorkbenchScreen(PortableWorkbenchMenu screenContainer, Inventory inventory, Component titleIn) {
        super(screenContainer, inventory, titleIn);
    }

    protected void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        this.recipeBookGui.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookGui.updateScreenPosition(this.width, this.getXSize());
        this.addWidget(this.recipeBookGui);
        this.setInitialFocus(this.recipeBookGui);
        this.addWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (button) -> {
            this.recipeBookGui.changeFocus(this.widthTooNarrow);
            this.recipeBookGui.toggleVisibility();
            this.leftPos = this.recipeBookGui.updateScreenPosition(this.width, this.getXSize());
            ((ImageButton) button).setPosition(this.leftPos + 5, this.height / 2 - 49);
        }));
        this.titleLabelX = 43;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.recipeBookGui.tick();
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
            this.renderBg(poseStack, partialTicks, mouseX, mouseY);
            this.recipeBookGui.render(poseStack, mouseX, mouseY, partialTicks);
        } else {
            this.recipeBookGui.render(poseStack, mouseX, mouseY, partialTicks);
            super.render(poseStack, mouseX, mouseY, partialTicks);
            this.recipeBookGui.renderGhostRecipe(poseStack, this.leftPos, this.topPos, true, partialTicks);
        }

        this.renderTooltip(poseStack, mouseX, mouseY);
        this.recipeBookGui.renderTooltip(poseStack, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem._setShaderTexture(0, CRAFTING_TABLE_GUI_TEXTURES);
        int i = this.leftPos;
        int j = (this.height - this.getYSize()) / 2;
        this.blit(poseStack, i, j, 0, 0, this.getXSize(), this.getYSize());
    }

    @Override
    protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return (!this.widthTooNarrow || !this.recipeBookGui.isVisible()) && super.isHovering(x, y, width, height, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBookGui.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBookGui);
            return true;
        } else {
            return this.widthTooNarrow && this.recipeBookGui.isVisible() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
        boolean flag = mouseX < (double) guiLeftIn || mouseY < (double) guiTopIn || mouseX >= (double) (guiLeftIn + this.getXSize()) || mouseY >= (double) (guiTopIn + this.getYSize());
        return this.recipeBookGui.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.getXSize(), this.getYSize(), mouseButton) && flag;
    }

    /**
     * Called when the mouse is clicked over a slot or outside the gui.
     */
    @Override
    protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slotIn, slotId, mouseButton, type);
        this.recipeBookGui.slotClicked(slotIn);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookGui.recipesUpdated();
    }

    @Override
    public void onClose() {
        this.recipeBookGui.removed();
        super.onClose();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookGui;
    }
}

package com.teamacronymcoders.essence.client.render.tesr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.common.block.infusion.tile.InfusionTableBlockEntity;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;

public class InfusionTableTESR implements BlockEntityRenderer<InfusionTableBlockEntity> {

    public static final ResourceLocation BOOK_TEXTURE = new ResourceLocation(Essence.MOD_ID, "entity/tome_of_knowledge");
    public static final Material MATERIAL_TEXTURE_BOOK = new Material(InventoryMenu.BLOCK_ATLAS, BOOK_TEXTURE);
    private final BookModel model;

    public InfusionTableTESR(BlockEntityRendererProvider.Context pContext) {
        this.model = new BookModel(pContext.bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void render(InfusionTableBlockEntity infusionTableTile, float partial, PoseStack poseStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        if (infusionTableTile.hasTome()) {
            renderBook(infusionTableTile, poseStack, partial, buffer, combinedLightIn, combinedOverlayIn);
        }
        if (!infusionTableTile.getInfusable().getStackInSlot(0).isEmpty()) {
            ItemStack stack = infusionTableTile.getInfusable().getStackInSlot(0);
            EssenceRenderHelper.renderItemStack(stack, infusionTableTile.getLevel(), infusionTableTile.getBlockPos(), infusionTableTile.getTicksExisted(), 1.5f, partial, poseStack, buffer);
        }
    }

    public void renderBook(InfusionTableBlockEntity infusionTableTile, PoseStack poseStack, float partial, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.75D, 0.5D);
        float f = (float) infusionTableTile.ticks + partial;
        poseStack.translate(0.0D, 0.1F + Mth.sin(f * 0.1F) * 0.01F, 0.0D);

        float f1;
        f1 = infusionTableTile.rot - infusionTableTile.oldRot;
        while (f1 >= (float) Math.PI) {
            f1 -= ((float) Math.PI * 2F);
        }

        while (f1 < -(float) Math.PI) {
            f1 += ((float) Math.PI * 2F);
        }

        float f2 = infusionTableTile.oldRot + f1 * partial;
        poseStack.mulPose(Vector3f.YP.rotation(-f2));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(80.0F));
        float f3 = Mth.lerp(partial, infusionTableTile.oldFlip, infusionTableTile.flip);
        float f4 = Mth.frac(f3 + 0.25F) * 1.6F - 0.3F;
        float f5 = Mth.frac(f3 + 0.75F) * 1.6F - 0.3F;
        float f6 = Mth.lerp(partial, infusionTableTile.oldOpen, infusionTableTile.open);
        this.model.setupAnim(f, Mth.clamp(f4, 0.0F, 1.0F), Mth.clamp(f5, 0.0F, 1.0F), f6);
        VertexConsumer vertexConsumer = MATERIAL_TEXTURE_BOOK.buffer(buffer, RenderType::entitySolid);
        this.model.render(poseStack, vertexConsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}

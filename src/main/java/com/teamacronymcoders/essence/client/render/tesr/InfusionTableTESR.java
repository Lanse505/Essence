package com.teamacronymcoders.essence.client.render.tesr;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.block.infusion.tile.InfusionTableTile;
import com.teamacronymcoders.essence.util.helper.EssenceRenderHelper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class InfusionTableTESR extends TileEntityRenderer<InfusionTableTile> {

  public static final ResourceLocation BOOK_TEXTURE = new ResourceLocation(Essence.MOD_ID, "entity/tome_of_knowledge");
  public static final RenderMaterial MATERIAL_TEXTURE_BOOK = new RenderMaterial(PlayerContainer.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(Essence.MOD_ID, "entity/tome_of_knowledge"));
  private final BookModel model = new BookModel();

  public InfusionTableTESR(TileEntityRendererDispatcher dispatcher) {
    super(dispatcher);
  }

  @Override
  public void render(InfusionTableTile infusionTableTile, float partial, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
    if (infusionTableTile.hasTome()) {
      renderBook(infusionTableTile, matrix, partial, buffer, combinedLightIn, combinedOverlayIn);
    }
    if (!infusionTableTile.getInfusable().getStackInSlot(0).isEmpty()) {
      ItemStack stack = infusionTableTile.getInfusable().getStackInSlot(0);
      EssenceRenderHelper.renderItemStack(stack, infusionTableTile.getWorld(), infusionTableTile.getPos(), infusionTableTile.getTicksExisted(), 1.5f, partial, matrix, buffer);
    }
  }

  public void renderBook(InfusionTableTile infusionTableTile, MatrixStack matrix, float partial, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
    matrix.push();
    matrix.translate(0.5D, 0.75D, 0.5D);
    float f = (float) infusionTableTile.ticks + partial;
    matrix.translate(0.0D, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0D);

    float f1;
    f1 = infusionTableTile.nextPageAngle - infusionTableTile.pageAngle;
    while (f1 >= (float) Math.PI) {
      f1 -= ((float) Math.PI * 2F);
    }

    while (f1 < -(float) Math.PI) {
      f1 += ((float) Math.PI * 2F);
    }

    float f2 = infusionTableTile.pageAngle + f1 * partial;
    matrix.rotate(Vector3f.YP.rotation(-f2));
    matrix.rotate(Vector3f.ZP.rotationDegrees(80.0F));
    float f3 = MathHelper.lerp(partial, infusionTableTile.field_195524_g, infusionTableTile.field_195523_f);
    float f4 = MathHelper.frac(f3 + 0.25F) * 1.6F - 0.3F;
    float f5 = MathHelper.frac(f3 + 0.75F) * 1.6F - 0.3F;
    float f6 = MathHelper.lerp(partial, infusionTableTile.pageTurningSpeed, infusionTableTile.nextPageTurningSpeed);
    this.model.setBookState(f, MathHelper.clamp(f4, 0.0F, 1.0F), MathHelper.clamp(f5, 0.0F, 1.0F), f6);
    IVertexBuilder ivertexbuilder = MATERIAL_TEXTURE_BOOK.getBuffer(buffer, RenderType::getEntitySolid);
    this.model.renderAll(matrix, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
    matrix.pop();
  }
}

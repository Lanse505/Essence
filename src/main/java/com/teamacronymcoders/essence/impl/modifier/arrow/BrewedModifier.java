package com.teamacronymcoders.essence.impl.modifier.arrow;

import com.teamacronymcoders.essence.api.modifier.ArrowCoreModifier;
import com.teamacronymcoders.essence.api.modifier.core.Modifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.ITextComponent;

public class BrewedModifier extends ArrowCoreModifier {
    private Potion potion;

    public BrewedModifier(Potion potion) {
        super(1);
        this.potion = potion;
    }

    @Override
    public void alterArrowEntity(AbstractArrowEntity abstractArrowEntity, PlayerEntity shooter, float velocity, int level) {
        if (abstractArrowEntity instanceof ArrowEntity) {
            ArrowEntity arrowEntity = (ArrowEntity) abstractArrowEntity;
            arrowEntity.setPotionEffect(PotionUtils.addPotionToItemStack(new ItemStack(Items.TIPPED_ARROW), this.potion));
        }
    }

    @Override
    public boolean canApplyOnItemStack(ItemStack stack) {
        return super.canApplyOnItemStack(stack);
    }

    @Override
    public boolean canApplyTogether(Modifier modifier) {
        return super.canApplyTogether(modifier);
    }

    @Override
    public String getTranslationName() {
        return super.getTranslationName();
    }

    @Override
    public ITextComponent getRenderedText(int level) {
        return super.getRenderedText(level);
    }
}

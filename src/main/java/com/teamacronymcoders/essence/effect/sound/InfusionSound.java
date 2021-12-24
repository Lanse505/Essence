package com.teamacronymcoders.essence.effect.sound;

import com.teamacronymcoders.essence.block.infusion.tile.InfusionTableBlockEntity;
import com.teamacronymcoders.essence.registrate.EssenceSoundRegistrate;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InfusionSound extends AbstractTickableSoundInstance {

  private final InfusionTableBlockEntity tableTile;

  public InfusionSound(InfusionTableBlockEntity tableTile) {
    this(EssenceSoundRegistrate.INFUSION_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f, tableTile);
  }

  public InfusionSound(SoundEvent soundIn, SoundSource categoryIn, float volume, float pitch, InfusionTableBlockEntity tableTile) {
    super(soundIn, categoryIn);
    this.volume = volume;
    this.pitch = pitch;
    this.tableTile = tableTile;
    this.x = tableTile.getBlockPos().getX();
    this.y = tableTile.getBlockPos().getY();
    this.z = tableTile.getBlockPos().getZ();
    this.looping = true;
    this.delay = 1020;
  }

  @Override
  public void tick() {
    if (!tableTile.getWorking()) {
      this.stop();
    }
  }

}

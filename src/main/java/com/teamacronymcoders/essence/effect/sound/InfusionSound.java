package com.teamacronymcoders.essence.effect.sound;

import com.teamacronymcoders.essence.block.infusion.tile.InfusionTableTile;
import com.teamacronymcoders.essence.registrate.EssenceSoundRegistrate;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InfusionSound extends TickableSound {

  private final InfusionTableTile tableTile;

  public InfusionSound(InfusionTableTile tableTile) {
    this(EssenceSoundRegistrate.INFUSION_SOUND.get(), SoundCategory.BLOCKS, 1.0f, 1.0f, tableTile);
  }

  public InfusionSound(SoundEvent soundIn, SoundCategory categoryIn, float volume, float pitch, InfusionTableTile tableTile) {
    super(soundIn, categoryIn);
    this.volume = volume;
    this.pitch = pitch;
    this.tableTile = tableTile;
    this.x = tableTile.getPos().getX();
    this.y = tableTile.getPos().getY();
    this.z = tableTile.getPos().getZ();
    this.repeat = true;
    this.repeatDelay = 1020;
  }

  @Override
  public void tick() {
    if (!tableTile.getWorking()) {
      this.finishPlaying();
    }
  }

}

package com.teamacronymcoders.essence.effect.sound;

import com.teamacronymcoders.essence.block.infusion.tile.InfusionTableTile;
import com.teamacronymcoders.essence.registrate.EssenceSoundRegistrate;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class InfusionBookSound extends TickableSound {

  private final InfusionTableTile tableTile;

  public InfusionBookSound(InfusionTableTile tableTile) {
    this(EssenceSoundRegistrate.INFUSION_BOOK_SOUND.get(), SoundCategory.BLOCKS, 1.0f, 1.0f, tableTile);
  }

  protected InfusionBookSound(SoundEvent soundIn, SoundCategory categoryIn, float volume, float pitch, InfusionTableTile tableTile) {
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
    // TODO: Figure out how to play my sound without making it obnoxious and horrible
    if (!tableTile.getWorld().isPlayerWithin(tableTile.getPos().getX(), tableTile.getPos().getY(), tableTile.getPos().getZ(), 3.0D) || tableTile.getWorld().getGameTime() - tableTile.getPageSoundLastPlayed() > 60) {
      this.finishPlaying();
    }
  }
}

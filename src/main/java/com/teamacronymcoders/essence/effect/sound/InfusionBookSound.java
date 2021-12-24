package com.teamacronymcoders.essence.effect.sound;

import com.teamacronymcoders.essence.block.infusion.tile.InfusionTableBlockEntity;
import com.teamacronymcoders.essence.registrate.EssenceSoundRegistrate;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class InfusionBookSound extends AbstractTickableSoundInstance {

  private final InfusionTableBlockEntity tableTile;

  public InfusionBookSound(InfusionTableBlockEntity tableTile) {
    this(EssenceSoundRegistrate.INFUSION_BOOK_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f, tableTile);
  }

  protected InfusionBookSound(SoundEvent soundIn, SoundSource categoryIn, float volume, float pitch, InfusionTableBlockEntity tableTile) {
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
    // TODO: Figure out how to play my sound without making it obnoxious and horrible

    if (tableTile.getLevel() != null && (!tableTile.getLevel().hasNearbyAlivePlayer(tableTile.getBlockPos().getX(), tableTile.getBlockPos().getY(), tableTile.getBlockPos().getZ(), 3.0D) || tableTile.getLevel().getGameTime() - tableTile.getPageSoundLastPlayed() > 60)) {
      this.stop();
    }
  }
}

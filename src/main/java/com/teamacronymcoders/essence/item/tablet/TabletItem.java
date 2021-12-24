package com.teamacronymcoders.essence.item.tablet;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.world.item.Item;

public class TabletItem extends Item {

  public TabletItem() {
    super(new Item.Properties().tab(Essence.CORE_TAB).stacksTo(1).defaultDurability(0));
  }


}

package com.teamacronymcoders.essence.item.tome;

import com.teamacronymcoders.essence.Essence;
import net.minecraft.world.item.Item;

public class TomeItem extends Item {

  public TomeItem(Properties properties) {
    super(properties);
  }

  public TomeItem() {
    super(new Item.Properties().tab(Essence.CORE_TAB).stacksTo(1).defaultDurability(0));
  }


}

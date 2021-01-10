package com.teamacronymcoders.essence.util.tab;

import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;

public class EssenceCoreTab extends AdvancedTitaniumTab {

  public EssenceCoreTab () {
    super("essence_core", true);
  }

  @Override
  public boolean hasScrollbar () {
    return true;
  }


}

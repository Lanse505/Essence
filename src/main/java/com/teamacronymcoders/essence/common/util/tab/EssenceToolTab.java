package com.teamacronymcoders.essence.common.util.tab;

import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;

public class EssenceToolTab extends AdvancedTitaniumTab {

    public EssenceToolTab() {
        super("essence_tools", true);
    }

    @Override
    public boolean canScroll() {
        return true;
    }

}

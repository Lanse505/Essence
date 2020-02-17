package com.teamacronymcoders.essence.utils;

import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;

public class EssenceReferences {
    public static final String MODID = "essence";

    public static final AdvancedTitaniumTab CORE_TAB = (AdvancedTitaniumTab) new AdvancedTitaniumTab("essence_core", true) {
        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImageName("item_search.png");

    public static final AdvancedTitaniumTab TOOL_TAB = (AdvancedTitaniumTab) new AdvancedTitaniumTab("essence_tools", true) {
        @Override
        public boolean hasSearchBar() {
            return true;
        }
    }.setBackgroundImageName("item_search.png");

}

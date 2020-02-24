package com.teamacronymcoders.essence.utils;

import com.hrznstudio.titanium.tab.AdvancedTitaniumTab;

import java.util.Random;
import java.util.UUID;

public class EssenceReferences {
    public static final String MODID = "essence";
    public static final Random random = new Random();

    public static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("dc3a5e97-4bbb-4b0f-9698-43fe2babb628");
    public static final UUID ARMOR_UUID = UUID.fromString("fbe6f3d9-80bc-4160-b54c-5020b5a914bc");
    public static final UUID MAX_HEALTH_UUID = UUID.fromString("baa36be4-749d-42f0-8e4f-a89959a36edf");
    public static final UUID MOVEMENT_SPEED_UUID = UUID.fromString("90742179-0f40-4ab8-8254-70ea451c9afb");
    public static final UUID ARMOR_TOUGHNESS_UUID = UUID.fromString("56a1c8d0-f074-4c7c-bab1-381d35939bbf");

    public static final AdvancedTitaniumTab CORE_TAB = new AdvancedTitaniumTab("essence_core", true) {
        @Override
        public boolean hasScrollbar() {
            return true;
        }
    };
    public static final AdvancedTitaniumTab TOOL_TAB = new AdvancedTitaniumTab("essence_tools", true) {
        @Override
        public boolean hasScrollbar() {
            return true;
        }
    };
}

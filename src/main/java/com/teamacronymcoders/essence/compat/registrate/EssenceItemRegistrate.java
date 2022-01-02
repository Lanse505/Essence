package com.teamacronymcoders.essence.compat.registrate;

import com.hrznstudio.titanium.item.BasicItem;
import com.teamacronymcoders.essence.Essence;
import com.teamacronymcoders.essence.common.item.CraftingCookieItem;
import com.teamacronymcoders.essence.common.item.essence.EssenceIngotItem;
import com.teamacronymcoders.essence.common.item.essence.EssenceNuggetItem;
import com.teamacronymcoders.essence.common.item.essence.EssenceCrystalItem;
import com.teamacronymcoders.essence.common.item.misc.DecoderSlingshot;
import com.teamacronymcoders.essence.common.item.misc.GlueBallItem;
import com.teamacronymcoders.essence.common.item.tome.TomeOfKnowledgeItem;
import com.teamacronymcoders.essence.common.item.tome.experience.TomeOfExperienceItem;
import com.teamacronymcoders.essence.common.item.tool.*;
import com.teamacronymcoders.essence.common.item.wrench.EssenceWrench;
import com.teamacronymcoders.essence.common.item.wrench.SerializedEntityItem;
import com.teamacronymcoders.essence.common.util.EssenceTags;
import com.teamacronymcoders.essence.common.util.tier.EssenceItemTiers;
import com.teamacronymcoders.essence.common.util.tier.EssenceToolTiers;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class EssenceItemRegistrate {

    public static final Map<Class<?>, BiFunction<Item.Properties, EssenceToolTiers, Item>> constructorMap = new HashMap<>();
    public static final Map<Class<?>, ResourceLocation> textureMap = new HashMap<>();

    static {
        // Constructors
        constructorMap.put(EssenceAxe.class, EssenceAxe::new);
        constructorMap.put(EssencePickaxe.class, EssencePickaxe::new);
        constructorMap.put(EssenceShovel.class, EssenceShovel::new);
        constructorMap.put(EssenceSword.class, EssenceSword::new);
        constructorMap.put(EssenceHoe.class, EssenceHoe::new);
        constructorMap.put(EssenceOmniTool.class, EssenceOmniTool::new);
        constructorMap.put(EssenceShear.class, EssenceShear::new);
        constructorMap.put(EssenceBow.class, EssenceBow::new);

        // Textures
        textureMap.put(EssenceAxe.class, new ResourceLocation(Essence.MOD_ID, "item/essence_axe"));
        textureMap.put(EssencePickaxe.class, new ResourceLocation(Essence.MOD_ID, "item/essence_pickaxe"));
        textureMap.put(EssenceShovel.class, new ResourceLocation(Essence.MOD_ID, "item/essence_shovel"));
        textureMap.put(EssenceSword.class, new ResourceLocation(Essence.MOD_ID, "item/essence_sword"));
        textureMap.put(EssenceHoe.class, new ResourceLocation(Essence.MOD_ID, "item/essence_hoe"));
        textureMap.put(EssenceOmniTool.class, new ResourceLocation(Essence.MOD_ID, "item/essence_omnitool"));
        textureMap.put(EssenceShear.class, new ResourceLocation(Essence.MOD_ID, "item/essence_shear"));
        textureMap.put(EssenceBow.class, new ResourceLocation(Essence.MOD_ID, "item/essence_bow"));
    }

    public static void init() {
    }

    // CORE
    public static ItemEntry<Item> RAW_ESSENCE = Essence.ESSENCE_REGISTRATE.object("raw_essence").item(Item::new).properties(Item.Properties::fireResistant).tab(() -> Essence.CORE_TAB).lang("Raw Essence").model((context, provider) -> provider.withExistingParent(context.getId().getPath(), new ResourceLocation(Essence.MOD_ID, "item/item")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/raw_essence"))).register();

    public static ItemEntry<EssenceCrystalItem> ESSENCE_CRYSTAL = Essence.ESSENCE_REGISTRATE.object("essence_crystal").item(properties -> new EssenceCrystalItem(properties, EssenceItemTiers.BASIC)).properties(properties -> properties.tab(Essence.CORE_TAB)).lang("Essence-Infused Crystal").model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/essence_crystal"))).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL).register();
    public static ItemEntry<EssenceCrystalItem> ESSENCE_CRYSTAL_EMPOWERED = Essence.ESSENCE_REGISTRATE.object("empowered_essence_crystal").item(properties -> new EssenceCrystalItem(properties, EssenceItemTiers.EMPOWERED)).properties(properties -> properties.tab(Essence.CORE_TAB)).lang("Essence-Infused Crystal").model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/empowered_essence_crystal"))).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_EMPOWERED).register();
    public static ItemEntry<EssenceCrystalItem> ESSENCE_CRYSTAL_SUPREME = Essence.ESSENCE_REGISTRATE.object("supreme_essence_crystal").item(properties -> new EssenceCrystalItem(properties, EssenceItemTiers.SUPREME)).properties(properties -> properties.tab(Essence.CORE_TAB)).lang("Essence-Infused Crystal").model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/supreme_essence_crystal"))).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_SUPREME).register();
    public static ItemEntry<EssenceCrystalItem> ESSENCE_CRYSTAL_DIVINE = Essence.ESSENCE_REGISTRATE.object("divine_essence_crystal").item(properties -> new EssenceCrystalItem(properties, EssenceItemTiers.DIVINE)).properties(properties -> properties.tab(Essence.CORE_TAB)).lang("Essence-Infused Crystal").model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/divine_essence_crystal"))).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_CRYSTAL_DIVINE).register();

    public static ItemEntry<BasicItem> ESSENCE_STICK = Essence.ESSENCE_REGISTRATE.object("essence_stick").item(BasicItem::new).properties(properties -> properties.tab(Essence.CORE_TAB)).lang("Essence-Wood Sticks").model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/essence_stick"))).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).register();
    public static ItemEntry<BasicItem> ENDERITE_SCRAP = Essence.ESSENCE_REGISTRATE.object("enderite_scrap").item(BasicItem::new).properties(properties -> properties.tab(Essence.CORE_TAB)).lang("Enderite Scrap").model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/enderite_scrap"))).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).register();

    // Tiered Materials
    public static ItemEntry<EssenceIngotItem> ESSENCE_INGOT = Essence.ESSENCE_REGISTRATE.object("essence_ingot").item(properties -> new EssenceIngotItem(properties, EssenceItemTiers.BASIC)).properties(properties -> properties.tab(Essence.CORE_TAB)).model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/essence_ingot"))).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL).register();
    public static ItemEntry<EssenceIngotItem> ESSENCE_INGOT_EMPOWERED = Essence.ESSENCE_REGISTRATE.object("empowered_essence_ingot").item(properties -> new EssenceIngotItem(properties, EssenceItemTiers.EMPOWERED)).properties(properties -> properties.tab(Essence.CORE_TAB)).model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/empowered_essence_ingot"))).lang("Essence Ingot").tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED).register();
    public static ItemEntry<EssenceIngotItem> ESSENCE_INGOT_SUPREME = Essence.ESSENCE_REGISTRATE.object("supreme_essence_ingot").item(properties -> new EssenceIngotItem(properties, EssenceItemTiers.SUPREME)).properties(properties -> properties.tab(Essence.CORE_TAB)).model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/supreme_essence_ingot"))).lang("Essence Ingot").tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_SUPREME).register();
    public static ItemEntry<EssenceIngotItem> ESSENCE_INGOT_DIVINE = Essence.ESSENCE_REGISTRATE.object("divine_essence_ingot").item(properties -> new EssenceIngotItem(properties, EssenceItemTiers.DIVINE)).properties(properties -> properties.tab(Essence.CORE_TAB)).model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/divine_essence_ingot"))).lang("Essence Ingot").tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_DIVINE).register();

    public static ItemEntry<EssenceNuggetItem> ESSENCE_NUGGET = Essence.ESSENCE_REGISTRATE.object("essence_nugget").item(properties -> new EssenceNuggetItem(properties, EssenceItemTiers.BASIC)).properties(properties -> properties.tab(Essence.CORE_TAB)).model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/essence_nugget"))).tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET).register();
    public static ItemEntry<EssenceNuggetItem> ESSENCE_NUGGET_EMPOWERED = Essence.ESSENCE_REGISTRATE.object("empowered_essence_nugget").item(properties -> new EssenceNuggetItem(properties, EssenceItemTiers.EMPOWERED)).properties(properties -> properties.tab(Essence.CORE_TAB)).model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/empowered_essence_nugget"))).lang("Essence Nugget").tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_EMPOWERED).register();
    public static ItemEntry<EssenceNuggetItem> ESSENCE_NUGGET_SUPREME = Essence.ESSENCE_REGISTRATE.object("supreme_essence_nugget").item(properties -> new EssenceNuggetItem(properties, EssenceItemTiers.SUPREME)).properties(properties -> properties.tab(Essence.CORE_TAB)).model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/supreme_essence_nugget"))).lang("Essence Nugget").tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_SUPREME).register();
    public static ItemEntry<EssenceNuggetItem> ESSENCE_NUGGET_DIVINE = Essence.ESSENCE_REGISTRATE.object("divine_essence_nugget").item(properties -> new EssenceNuggetItem(properties, EssenceItemTiers.DIVINE)).properties(properties -> properties.tab(Essence.CORE_TAB)).model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/divine_essence_nugget"))).lang("Essence Nugget").tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_DIVINE).register();

    // Tomes
    public static ItemEntry<TomeOfKnowledgeItem> TOME_OF_KNOWLEDGE = Essence.ESSENCE_REGISTRATE.object("tome_of_knowledge")
            .item(TomeOfKnowledgeItem::new).properties(properties -> properties.tab(Essence.CORE_TAB).stacksTo(1).defaultDurability(0))
            .lang("Tome of Bound-Knowledge")
            .model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/tome_of_knowledge")))
            .register();

    public static ItemEntry<TomeOfExperienceItem> TOME_OF_EXPERIENCE = Essence.ESSENCE_REGISTRATE.object("tome_of_experience")
            .item(TomeOfExperienceItem::new).properties(properties -> properties.tab(Essence.CORE_TAB).stacksTo(1).defaultDurability(0))
            .lang("Tome of Experience")
            .model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/tome_of_experience")))
            .register();

    // Misc
    public static ItemEntry<Item> FELINIUM_JAMINITE_INGOT = Essence.ESSENCE_REGISTRATE.object("felinium_jaminite_ingot")
            .item(Item::new).properties(properties -> properties.fireResistant().rarity(Rarity.EPIC))
            .lang("Felinium Jaminite Ingot")
            .model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/felinium_jaminite_ingot")))
            .tab(() -> Essence.CORE_TAB)
            .register();

    // Tools
    // MISC
    public static FoodProperties CRAFTING_COOKIE_FOOD = new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).alwaysEat().build();
    public static ItemEntry<CraftingCookieItem> CRAFTING_COOKIE = Essence.ESSENCE_REGISTRATE.object("crafting_cookie")
            .item(CraftingCookieItem::new).properties(properties -> properties.stacksTo(64).tab(Essence.TOOL_TAB).rarity(Rarity.RARE).food(CRAFTING_COOKIE_FOOD))
            .lang("Crafting Cookie")
            .model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/crafting_cookie")))
            .register();
    public static ItemEntry<EssenceWrench> ESSENCE_WRENCH = Essence.ESSENCE_REGISTRATE.object("essence_wrench")
            .item(EssenceWrench::new).properties(properties -> properties.tab(Essence.TOOL_TAB).stacksTo(1).defaultDurability(2048).rarity(Rarity.RARE))
            .model((context, provider) -> provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/essence_wrench")))
            .lang("Essence-Infused Wrench").register();
    public static ItemEntry<SerializedEntityItem> SERIALIZED_ENTITY = Essence.ESSENCE_REGISTRATE.object("serialized_entity")
            .item(SerializedEntityItem::new).properties(properties -> properties.defaultDurability(1))
            .lang("Serialized Entity")
            .model((context, provider) -> provider.getBuilder(context.getId().getPath()).parent(new ModelFile.UncheckedModelFile(new ResourceLocation("builtin/entity"))))
            .register();
    public static ItemEntry<GlueBallItem> GLUE_BALL_ITEM = Essence.ESSENCE_REGISTRATE.object("glue_ball").item(GlueBallItem::new).properties(properties -> properties.tab(Essence.CORE_TAB))
            .lang("Ball of Glue")
            .model((context, provider) -> provider.handheld(context))
            .register();
    public static ItemEntry<DecoderSlingshot> DECODER_SLINGSHOT = Essence.ESSENCE_REGISTRATE.object("decoder_slingshot").item(DecoderSlingshot::new).properties(properties -> properties.tab(Essence.TOOL_TAB))
            .lang("Decoder Slingshot")
            .model((context, provider) -> {
                provider.generated(context)
                        .texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot"))
                        .transforms()
                        .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).rotation(-80, 260, -40).translation(-1, -2, 2.5f).scale(0.9f).end()
                        .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).rotation(-80, -280, 40).translation(-1, -2, 2.5f).scale(0.9f).end()
                        .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).rotation(0, -90, 25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                        .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).rotation(0, 90, -25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                        .end()
                        .override()
                        .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                        .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot_pulling_0")))
                        .end()
                        .override()
                        .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                        .predicate(new ResourceLocation(Essence.MOD_ID, "pull"), 0.65f)
                        .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot_pulling_1")))
                        .end()
                        .override()
                        .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                        .predicate(new ResourceLocation(Essence.MOD_ID, "pull"), 0.9f)
                        .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot_pulling_2")))
                        .end();
                provider.withExistingParent("decoder_slingshot_pulling_0", new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot_pulling_0"));
                provider.withExistingParent("decoder_slingshot_pulling_1", new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot_pulling_1"));
                provider.withExistingParent("decoder_slingshot_pulling_2", new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/decoder_slingshot_pulling_2"));
            })
            .register();


    // Tier 1
    public static ItemEntry<EssenceAxe> ESSENCE_AXE = getTool("_essence_axe", "Essence Axe", EssenceAxe.class, EssenceToolTiers.ESSENCE, axeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), new ResourceLocation(Essence.MOD_ID, "essence_axe")), EssenceTags.EssenceItemTags.ESSENCE_AXE);
    public static ItemEntry<EssencePickaxe> ESSENCE_PICKAXE = getTool("_essence_pickaxe", "Essence Pickaxe", EssencePickaxe.class, EssenceToolTiers.ESSENCE, pickaxeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), new ResourceLocation(Essence.MOD_ID, "essence_pickaxe")), EssenceTags.EssenceItemTags.ESSENCE_PICKAXE);
    public static ItemEntry<EssenceShovel> ESSENCE_SHOVEL = getTool("_essence_shovel", "Essence Shovel", EssenceShovel.class, EssenceToolTiers.ESSENCE, shovelRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), new ResourceLocation(Essence.MOD_ID, "essence_shovel")), EssenceTags.EssenceItemTags.ESSENCE_SHOVEL);
    public static ItemEntry<EssenceSword> ESSENCE_SWORD = getTool("_essence_sword", "Essence Sword", EssenceSword.class, EssenceToolTiers.ESSENCE, swordRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), new ResourceLocation(Essence.MOD_ID, "essence_sword")), EssenceTags.EssenceItemTags.ESSENCE_SWORD);
    public static ItemEntry<EssenceHoe> ESSENCE_HOE = getTool("_essence_hoe", "Essence Hoe", EssenceHoe.class, EssenceToolTiers.ESSENCE, hoeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), new ResourceLocation(Essence.MOD_ID, "essence_hoe")), EssenceTags.EssenceItemTags.ESSENCE_HOE);
    public static ItemEntry<EssenceOmniTool> ESSENCE_OMNITOOL = getTool("_essence_omnitool", "Essence Omni-Tool", EssenceOmniTool.class, EssenceToolTiers.ESSENCE, omnitoolRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_AXE), () -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_SHOVEL), () -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_PICKAXE), new ResourceLocation(Essence.MOD_ID, "essence_omnitool")), EssenceTags.EssenceItemTags.ESSENCE_OMNITOOL);
    public static ItemEntry<EssenceShear> ESSENCE_SHEAR = getTool("_essence_shear", "Essence Shear", EssenceShear.class, EssenceToolTiers.ESSENCE, shearRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL), new ResourceLocation(Essence.MOD_ID, "essence_shear")), EssenceTags.EssenceItemTags.ESSENCE_SHEAR);
    public static ItemEntry<EssenceBow> ESSENCE_BOW = getTool("_essence_bow", "Essence Bow", EssenceBow.class, EssenceToolTiers.ESSENCE, bowRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET), new ResourceLocation(Essence.MOD_ID, "essence_bow")), EssenceTags.EssenceItemTags.ESSENCE_BOW);

    // Tier 2
    public static ItemEntry<EssenceAxe> ESSENCE_AXE_EMPOWERED = getTool("_essence_axe", "Essence Axe", EssenceAxe.class, EssenceToolTiers.EMPOWERED, axeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), new ResourceLocation(Essence.MOD_ID, "essence_axe_empowered")), EssenceTags.EssenceItemTags.ESSENCE_AXE_EMPOWERED);
    public static ItemEntry<EssencePickaxe> ESSENCE_PICKAXE_EMPOWERED = getTool("_essence_pickaxe", "Essence Pickaxe", EssencePickaxe.class, EssenceToolTiers.EMPOWERED, pickaxeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), new ResourceLocation(Essence.MOD_ID, "essence_pickaxe_empowered")), EssenceTags.EssenceItemTags.ESSENCE_PICKAXE_EMPOWERED);
    public static ItemEntry<EssenceShovel> ESSENCE_SHOVEL_EMPOWERED = getTool("_essence_shovel", "Essence Shovel", EssenceShovel.class, EssenceToolTiers.EMPOWERED, shovelRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), new ResourceLocation(Essence.MOD_ID, "essence_shovel_empowered")), EssenceTags.EssenceItemTags.ESSENCE_SHOVEL_EMPOWERED);
    public static ItemEntry<EssenceSword> ESSENCE_SWORD_EMPOWERED = getTool("_essence_sword", "Essence Sword", EssenceSword.class, EssenceToolTiers.EMPOWERED, swordRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), new ResourceLocation(Essence.MOD_ID, "essence_sword_empowered")), EssenceTags.EssenceItemTags.ESSENCE_SWORD_EMPOWERED);
    public static ItemEntry<EssenceHoe> ESSENCE_HOE_EMPOWERED = getTool("_essence_hoe", "Essence Hoe", EssenceHoe.class, EssenceToolTiers.EMPOWERED, hoeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), new ResourceLocation(Essence.MOD_ID, "essence_hoe_empowered")), EssenceTags.EssenceItemTags.ESSENCE_HOE_EMPOWERED);
    public static ItemEntry<EssenceOmniTool> ESSENCE_OMNITOOL_EMPOWERED = getTool("_essence_omnitool", "Essence Omni-Tool", EssenceOmniTool.class, EssenceToolTiers.EMPOWERED, omnitoolRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_AXE_EMPOWERED), () -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_SHOVEL_EMPOWERED), () -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_PICKAXE_EMPOWERED), new ResourceLocation(Essence.MOD_ID, "essence_omnitool_empowered")), EssenceTags.EssenceItemTags.ESSENCE_OMNITOOL_EMPOWERED);
    public static ItemEntry<EssenceShear> ESSENCE_SHEAR_EMPOWERED = getTool("_essence_shear", "Essence Shear", EssenceShear.class, EssenceToolTiers.EMPOWERED, shearRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_EMPOWERED), new ResourceLocation(Essence.MOD_ID, "essence_shear_empowered")), EssenceTags.EssenceItemTags.ESSENCE_SHEAR_EMPOWERED);
    public static ItemEntry<EssenceBow> ESSENCE_BOW_EMPOWERED = getTool("_essence_bow", "Essence Bow", EssenceBow.class, EssenceToolTiers.EMPOWERED, bowRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_EMPOWERED), new ResourceLocation(Essence.MOD_ID, "essence_bow_empowered")), EssenceTags.EssenceItemTags.ESSENCE_BOW_EMPOWERED);
    // Tier 3
    public static ItemEntry<EssenceAxe> ESSENCE_AXE_SUPREME = getTool("_essence_axe", "Essence Axe", EssenceAxe.class, EssenceToolTiers.SUPREME, axeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_SUPREME), new ResourceLocation(Essence.MOD_ID, "essence_axe_supreme")), EssenceTags.EssenceItemTags.ESSENCE_AXE_SUPREME);
    public static ItemEntry<EssencePickaxe> ESSENCE_PICKAXE_SUPREME = getTool("_essence_pickaxe", "Essence Pickaxe", EssencePickaxe.class, EssenceToolTiers.SUPREME, pickaxeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_SUPREME), new ResourceLocation(Essence.MOD_ID, "essence_pickaxe_supreme")), EssenceTags.EssenceItemTags.ESSENCE_PICKAXE_SUPREME);
    public static ItemEntry<EssenceShovel> ESSENCE_SHOVEL_SUPREME = getTool("_essence_shovel", "Essence Shovel", EssenceShovel.class, EssenceToolTiers.SUPREME, shovelRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_SUPREME), new ResourceLocation(Essence.MOD_ID, "essence_shovel_supreme")), EssenceTags.EssenceItemTags.ESSENCE_SHOVEL_SUPREME);
    public static ItemEntry<EssenceSword> ESSENCE_SWORD_SUPREME = getTool("_essence_sword", "Essence Sword", EssenceSword.class, EssenceToolTiers.SUPREME, swordRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_SUPREME), new ResourceLocation(Essence.MOD_ID, "essence_sword_supreme")), EssenceTags.EssenceItemTags.ESSENCE_SWORD_SUPREME);
    public static ItemEntry<EssenceHoe> ESSENCE_HOE_SUPREME = getTool("_essence_hoe", "Essence Hoe", EssenceHoe.class, EssenceToolTiers.SUPREME, hoeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_SUPREME), new ResourceLocation(Essence.MOD_ID, "essence_hoe_supreme")), EssenceTags.EssenceItemTags.ESSENCE_HOE_SUPREME);
    public static ItemEntry<EssenceOmniTool> ESSENCE_OMNITOOL_SUPREME = getTool("_essence_omnitool", "Essence Omni-Tool", EssenceOmniTool.class, EssenceToolTiers.SUPREME, omnitoolRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_AXE_SUPREME), () -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_SHOVEL_SUPREME), () -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_PICKAXE_SUPREME), new ResourceLocation(Essence.MOD_ID, "essence_omnitool_supreme")), EssenceTags.EssenceItemTags.ESSENCE_OMNITOOL_SUPREME);
    public static ItemEntry<EssenceShear> ESSENCE_SHEAR_SUPREME = getTool("_essence_shear", "Essence Shear", EssenceShear.class, EssenceToolTiers.SUPREME, shearRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_SUPREME), new ResourceLocation(Essence.MOD_ID, "essence_shear_supreme")), EssenceTags.EssenceItemTags.ESSENCE_SHEAR_SUPREME);
    public static ItemEntry<EssenceBow> ESSENCE_BOW_SUPREME = getTool("_essence_bow", "Essence Bow", EssenceBow.class, EssenceToolTiers.SUPREME, bowRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_SUPREME), new ResourceLocation(Essence.MOD_ID, "essence_bow_supreme")), EssenceTags.EssenceItemTags.ESSENCE_BOW_SUPREME);
    // Tier 4
    public static ItemEntry<EssenceAxe> ESSENCE_AXE_DIVINE = getTool("_essence_axe", "Essence Axe", EssenceAxe.class, EssenceToolTiers.DIVINE, axeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_DIVINE), new ResourceLocation(Essence.MOD_ID, "essence_axe_divine")), EssenceTags.EssenceItemTags.ESSENCE_AXE_DIVINE);
    public static ItemEntry<EssencePickaxe> ESSENCE_PICKAXE_DIVINE = getTool("_essence_pickaxe", "Essence Pickaxe", EssencePickaxe.class, EssenceToolTiers.DIVINE, pickaxeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_DIVINE), new ResourceLocation(Essence.MOD_ID, "essence_pickaxe_divine")), EssenceTags.EssenceItemTags.ESSENCE_PICKAXE_DIVINE);
    public static ItemEntry<EssenceShovel> ESSENCE_SHOVEL_DIVINE = getTool("_essence_shovel", "Essence Shovel", EssenceShovel.class, EssenceToolTiers.DIVINE, shovelRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_DIVINE), new ResourceLocation(Essence.MOD_ID, "essence_shovel_divine")), EssenceTags.EssenceItemTags.ESSENCE_SHOVEL_DIVINE);
    public static ItemEntry<EssenceSword> ESSENCE_SWORD_DIVINE = getTool("_essence_sword", "Essence Sword", EssenceSword.class, EssenceToolTiers.DIVINE, swordRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_DIVINE), new ResourceLocation(Essence.MOD_ID, "essence_sword_divine")), EssenceTags.EssenceItemTags.ESSENCE_SWORD_DIVINE);
    public static ItemEntry<EssenceHoe> ESSENCE_HOE_DIVINE = getTool("_essence_hoe", "Essence Hoe", EssenceHoe.class, EssenceToolTiers.DIVINE, hoeRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_DIVINE), new ResourceLocation(Essence.MOD_ID, "essence_hoe_divine")), EssenceTags.EssenceItemTags.ESSENCE_HOE_DIVINE);
    public static ItemEntry<EssenceOmniTool> ESSENCE_OMNITOOL_DIVINE = getTool("_essence_omnitool", "Essence Omni-Tool", EssenceOmniTool.class, EssenceToolTiers.DIVINE, omnitoolRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_AXE_DIVINE), () -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_SHOVEL_DIVINE), () -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_PICKAXE_DIVINE), new ResourceLocation(Essence.MOD_ID, "essence_omnitool_divine")), EssenceTags.EssenceItemTags.ESSENCE_OMNITOOL_DIVINE);
    public static ItemEntry<EssenceShear> ESSENCE_SHEAR_DIVINE = getTool("_essence_shear", "Essence Shear", EssenceShear.class, EssenceToolTiers.DIVINE, shearRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_DIVINE), new ResourceLocation(Essence.MOD_ID, "essence_shear_divine")), EssenceTags.EssenceItemTags.ESSENCE_SHEAR_DIVINE);
    public static ItemEntry<EssenceBow> ESSENCE_BOW_DIVINE = getTool("_essence_bow", "Essence Bow", EssenceBow.class, EssenceToolTiers.DIVINE, bowRecipe(() -> DataIngredient.tag(EssenceTags.EssenceItemTags.ESSENCE_INFUSED_METAL_NUGGET_DIVINE), new ResourceLocation(Essence.MOD_ID, "essence_bow_divine")), EssenceTags.EssenceItemTags.ESSENCE_BOW_DIVINE);

    @SuppressWarnings("unchecked")
    public static <T extends Item> ItemEntry<T> getTool(String name, String lang, Class<T> tool, EssenceToolTiers tier, NonNullBiConsumer<DataGenContext<Item, Item>, RegistrateRecipeProvider> recipeConsumer, Tag.Named<Item>... tags) {
        String tierType = tier == EssenceToolTiers.ESSENCE ? "" : tier.toString().toLowerCase();
        String entryName = tierType.equals("") ? name.substring(1) : tierType + name;
        return (ItemEntry<T>) Essence.ESSENCE_REGISTRATE.object(entryName)
                .item(properties -> constructorMap.get(tool).apply(properties, tier))
                .model((context, provider) -> {
                    String id = name.substring(1);
                    if (!tool.equals(EssenceBow.class)) {
                        if (tier == EssenceToolTiers.ESSENCE) {
                            provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/" + id));
                        } else {
                            provider.handheld(context, new ResourceLocation(Essence.MOD_ID, "item/" + tierType + "_" + id));
                        }
                    } else {
                        if (tier == EssenceToolTiers.ESSENCE) {
                            provider.generated(context)
                                    .texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/essence_bow"))
                                    .transforms()
                                    .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).rotation(-80, 260, -40).translation(-1, -2, 2.5f).scale(0.9f).end()
                                    .transform(ModelBuilder.Perspective.THIRDPERSON_LEFT).rotation(-80, -280, 40).translation(-1, -2, 2.5f).scale(0.9f).end()
                                    .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).rotation(0, -90, 25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                                    .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).rotation(0, 90, -25).translation(1.13f, 3.2f, 1.13f).scale(0.68f).end()
                                    .end()
                                    .override()
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                                    .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Essence.MOD_ID, "item/essence_bow_pulling_0")))
                                    .end()
                                    .override()
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pull"), 0.65f)
                                    .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Essence.MOD_ID, "item/essence_bow_pulling_1")))
                                    .end()
                                    .override()
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pull"), 0.9f)
                                    .model(new ModelFile.UncheckedModelFile(new ResourceLocation(Essence.MOD_ID, "item/essence_bow_pulling_2")))
                                    .end();
                            provider.withExistingParent("essence_bow_pulling_0", new ResourceLocation(Essence.MOD_ID, "item/essence_bow")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/essence_bow_pulling_0"));
                            provider.withExistingParent("essence_bow_pulling_1", new ResourceLocation(Essence.MOD_ID, "item/essence_bow")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/essence_bow_pulling_1"));
                            provider.withExistingParent("essence_bow_pulling_2", new ResourceLocation(Essence.MOD_ID, "item/essence_bow")).texture("layer0", new ResourceLocation(Essence.MOD_ID, "item/essence_bow_pulling_2"));
                        } else {
                            provider.generated(context, new ResourceLocation(Essence.MOD_ID, "item/" + tierType + "_" + "essence_bow"))
                                    .override()
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                                    .model(provider.getExistingFile(new ResourceLocation(Essence.MOD_ID, "item/" + tierType + "_" + "essence_bow_pulling_0")))
                                    .end()
                                    .override()
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pull"), 0.65f)
                                    .model(provider.getExistingFile(new ResourceLocation(Essence.MOD_ID, "item/" + tierType + "_" + "essence_bow_pulling_1")))
                                    .end()
                                    .override()
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pulling"), 1)
                                    .predicate(new ResourceLocation(Essence.MOD_ID, "pull"), 0.9f)
                                    .model(provider.getExistingFile(new ResourceLocation(Essence.MOD_ID, "item/" + tierType + "_" + "essence_bow_pulling_2")))
                                    .end();
                        }
                    }
                })
                .lang(lang).tag(tags)
                .recipe(recipeConsumer)
                .tab(() -> Essence.TOOL_TAB)
                .register();
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> axeRecipe(Supplier<DataIngredient> ingot, ResourceLocation rl) {
        return (context, provider) -> ShapedRecipeBuilder.shaped(context.get(), 1).pattern("ii ").pattern("is ").pattern(" s ").define('i', ingot.get()).define('s', EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).unlockedBy("has_" + provider.safeName(ingot.get()), ingot.get().getCritereon(provider)).save(provider, rl);
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> pickaxeRecipe(Supplier<DataIngredient> ingot, ResourceLocation rl) {
        return (context, provider) -> ShapedRecipeBuilder.shaped(context.get(), 1).pattern("iii").pattern(" s ").pattern(" s ").define('i', ingot.get()).define('s', EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).unlockedBy("has_" + provider.safeName(ingot.get()), ingot.get().getCritereon(provider)).save(provider, rl);
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> shovelRecipe(Supplier<DataIngredient> ingot, ResourceLocation rl) {
        return (context, provider) -> ShapedRecipeBuilder.shaped(context.get(), 1).pattern(" i ").pattern(" s ").pattern(" s ").define('i', ingot.get()).define('s', EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).unlockedBy("has_" + provider.safeName(ingot.get()), ingot.get().getCritereon(provider)).save(provider, rl);
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> swordRecipe(Supplier<DataIngredient> ingot, ResourceLocation rl) {
        return (context, provider) -> ShapedRecipeBuilder.shaped(context.get(), 1).pattern(" i ").pattern(" i ").pattern(" s ").define('i', ingot.get()).define('s', EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).unlockedBy("has_" + provider.safeName(ingot.get()), ingot.get().getCritereon(provider)).save(provider, rl);
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> shearRecipe(Supplier<DataIngredient> ingot, ResourceLocation rl) {
        return (context, provider) -> ShapedRecipeBuilder.shaped(context.get(), 1).pattern(" i").pattern("i ").define('i', ingot.get()).unlockedBy("has_" + provider.safeName(ingot.get()), ingot.get().getCritereon(provider)).save(provider, rl);
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> bowRecipe(Supplier<DataIngredient> nugget, ResourceLocation rl) {
        return (context, provider) -> ShapedRecipeBuilder.shaped(context.get(), 1).pattern("stn").pattern("s t").pattern("stn").define('t', EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).define('s', DataIngredient.tag(Tags.Items.STRING)).define('n', nugget.get()).unlockedBy("has_" + provider.safeName(nugget.get()), nugget.get().getCritereon(provider)).save(provider, rl);
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> omnitoolRecipe(Supplier<DataIngredient> axe, Supplier<DataIngredient> shovel, Supplier<DataIngredient> pickaxe, ResourceLocation rl) {
        return (context, provider) -> ShapedRecipeBuilder.shaped(context.get(), 1).pattern("asp").pattern(" t ").pattern(" t ").define('a', axe.get()).define('s', shovel.get()).define('p', pickaxe.get()).define('t', EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).unlockedBy("has_" + provider.safeName(axe.get()), axe.get().getCritereon(provider)).unlockedBy("has_" + provider.safeName(shovel.get()), shovel.get().getCritereon(provider)).unlockedBy("has_" + provider.safeName(pickaxe.get()), pickaxe.get().getCritereon(provider)).save(provider, rl);
    }

    public static <T extends Item> NonNullBiConsumer<DataGenContext<Item, T>, RegistrateRecipeProvider> hoeRecipe(Supplier<DataIngredient> ingot, ResourceLocation rl) {
        return (context, provider) -> ShapedRecipeBuilder.shaped(context.get(), 1).pattern("ii ").pattern(" s ").pattern(" s ").define('i', ingot.get()).define('s', EssenceTags.EssenceItemTags.ESSENCE_INFUSED_STICK).unlockedBy("has_" + provider.safeName(ingot.get()), ingot.get().getCritereon(provider)).save(provider, rl);
    }
}

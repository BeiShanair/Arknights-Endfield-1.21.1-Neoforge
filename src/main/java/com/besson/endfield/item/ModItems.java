package com.besson.endfield.item;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.block.ModBlocks;
import com.besson.endfield.item.custom.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(ArknightsEndField.MOD_ID);

    public static final DeferredItem<Item> PORTABLE_ORIGINIUM_RIG_ITEM = ITEMS.register("portable_originium_rig",
            () -> new PortableOriginiumRigItem(ModBlocks.PORTABLE_ORIGINIUM_RIG.get(), new Item.Properties()));
    public static final DeferredItem<Item> PROTOCOL_ANCHOR_CORE_ITEM = ITEMS.register("protocol_anchor_core",
            () -> new ProtocolAnchorCoreItem(ModBlocks.PROTOCOL_ANCHOR_CORE.get(), new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> THERMAL_BANK_ITEM = ITEMS.register("thermal_bank",
            () -> new ThermalBankItem(ModBlocks.THERMAL_BANK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> RELAY_TOWER_ITEM = ITEMS.register("relay_tower",
            () -> new RelayTowerItem(ModBlocks.RELAY_TOWER.get(), new Item.Properties()));
    public static final DeferredItem<Item> ELECTRIC_PYLON_ITEM = ITEMS.register("electric_pylon",
            () -> new ElectricPylonItem(ModBlocks.ELECTRIC_PYLON.get(), new Item.Properties()));
    public static final DeferredItem<Item> ELECTRIC_MINING_RIG_ITEM = ITEMS.register("electric_mining_rig",
            () -> new ElectricMiningRigItem(ModBlocks.ELECTRIC_MINING_RIG.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> ELECTRIC_MINING_RIG_MK_II_ITEM = ITEMS.register("electric_mining_rig_mk_ii",
            () -> new ElectricMiningRigMkIIItem(ModBlocks.ELECTRIC_MINING_RIG_MK_II.get(), new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> REFINING_UNIT_ITEM = ITEMS.register("refining_unit",
            () -> new RefiningUnitItem(ModBlocks.REFINING_UNIT.get(), new Item.Properties()));
    public static final DeferredItem<Item> FILLING_UNIT_ITEM = ITEMS.register("filling_unit",
            () -> new FillingUnitItem(ModBlocks.FILLING_UNIT.get(), new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> FITTING_UNIT_ITEM = ITEMS.register("fitting_unit",
            () -> new FittingUnitItem(ModBlocks.FITTING_UNIT.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SHREDDING_UNIT_ITEM = ITEMS.register("shredding_unit",
            () -> new ShreddingUnitItem(ModBlocks.SHREDDING_UNIT.get(), new Item.Properties()));
    public static final DeferredItem<Item> GEARING_UNIT_ITEM = ITEMS.register("gearing_unit",
            () -> new GearingUnitItem(ModBlocks.GEARING_UNIT.get(), new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> GRINDING_UNIT_ITEM = ITEMS.register("grinding_unit",
            () -> new GrindingUnitItem(ModBlocks.GRINDING_UNIT.get(), new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> MOULDING_UNIT_ITEM = ITEMS.register("moulding_unit",
            () -> new MouldingUnitItem(ModBlocks.MOULDING_UNIT.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> PACKAGING_UNIT_ITEM = ITEMS.register("packaging_unit",
            () -> new PackagingUnitItem(ModBlocks.PACKAGING_UNIT.get(), new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> PLANTING_UNIT_ITEM = ITEMS.register("planting_unit",
            () -> new PlantingUnitItem(ModBlocks.PLANTING_UNIT.get(), new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> SEED_PICKING_UNIT_ITEM = ITEMS.register("seed_picking_unit",
            () -> new SeedPickingUnitItem(ModBlocks.SEED_PICKING_UNIT.get(), new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> AGGAGRIT = ITEMS.register("aggagrit", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AGGAGRIT_BLOCK = ITEMS.register("aggagrit_block", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> AGGAGRIT_CLUSTER = ITEMS.register("aggagrit_cluster", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> AKETINE = ITEMS.register("aketine", () -> new BlockItem(ModBlocks.AKETINE_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> AKETINE_POWDER = ITEMS.register("aketine_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AKETINE_SEED = ITEMS.register("aketine_seed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AMBER_RICE = ITEMS.register("amber_rice", () -> new BlockItem(ModBlocks.AMBER_RICE_BLOCK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> AMBER_RICE_SEED = ITEMS.register("amber_rice_seed", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> AMETHYST_BOTTLE = ITEMS.register("amethyst_bottle", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> AMETHYST_COMPONENT = ITEMS.register("amethyst_component", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> AMETHYST_FIBER = ITEMS.register("amethyst_fiber", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> AMETHYST_ORE = ITEMS.register("amethyst_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AMETHYST_PART = ITEMS.register("amethyst_part", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> AMETHYST_POWDER = ITEMS.register("amethyst_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> ARTS_TUBE = ITEMS.register("arts_tube", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.ART_TUBE)));
    public static final DeferredItem<Item> ARTS_VIAL = ITEMS.register("arts_vial", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.ART_VIAL)));
    public static final DeferredItem<Item> ASHPIN_REMEDY = ITEMS.register("ashpin_remedy", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.ASHPIN_REMEDY)));
    public static final DeferredItem<Item> BIZARROTACK = ITEMS.register("bizarrotack", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(ModFoods.BIZARROTACK)));
    public static final DeferredItem<Item> BIZARRO_CHILI = ITEMS.register("bizarro_chili", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.BIZARRO_CHILI)));
    public static final DeferredItem<Item> BLANCHED_REMEDY = ITEMS.register("blanched_remedy", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.BLANCHED_REMEDY)));
    public static final DeferredItem<Item> BUCKFLOWER = ITEMS.register("buckflower", () -> new BlockItem(ModBlocks.BUCKFLOWER_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> BUCKFLOWER_POWDER = ITEMS.register("buckflower_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> BUCKFLOWER_SEED = ITEMS.register("buckflower_seed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BUCKPILL_L = ITEMS.register("buckpill_l", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.BUCKPILL_L)));
    public static final DeferredItem<Item> BUCKPILL_RF = ITEMS.register("buckpill_rf", () -> new ModEpicItem(new Item.Properties().food(ModFoods.BUCKPILL_RF)));
    public static final DeferredItem<Item> BUCKPILL_S = ITEMS.register("buckpill_s", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.BUCKPILL_S)));
    public static final DeferredItem<Item> BUCK_CAPSULE_A = ITEMS.register("buck_capsule_a", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.BUCK_CAPSULE_A)));
    public static final DeferredItem<Item> BUCK_CAPSULE_B = ITEMS.register("buck_capsule_b", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.BUCK_CAPSULE_B)));
    public static final DeferredItem<Item> BUCK_CAPSULE_C = ITEMS.register("buck_capsule_c", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.BUCK_CAPSULE_C)));
    public static final DeferredItem<Item> BUGTACK = ITEMS.register("bugtack", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.BUGTACK)));
    public static final DeferredItem<Item> CANNED_CITROME_A = ITEMS.register("canned_citrome_a", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.CANNED_CITROME_A)));
    public static final DeferredItem<Item> CANNED_CITROME_B = ITEMS.register("canned_citrome_b", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.CANNED_CITROME_B)));
    public static final DeferredItem<Item> CANNED_CITROME_C = ITEMS.register("canned_citrome_c", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.CANNED_CITROME_C)));
    public static final DeferredItem<Item> CARBON = ITEMS.register("carbon", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> CARBON_POWDER = ITEMS.register("carbon_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> CARTILAGE_BIT = ITEMS.register("cartilage_bit", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CARTILAGE_TACK = ITEMS.register("cartilage_tack", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.CARTILAGE_TACK)));
    public static final DeferredItem<Item> CHITIN_BIT = ITEMS.register("chitin_bit", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> CITROBUCKY_MIX = ITEMS.register("citrobucky_mix", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.CITROBUCKY_MIX)));
    public static final DeferredItem<Item> CITROME = ITEMS.register("citrome",() -> new BlockItem(ModBlocks.CITROME_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> CITROME_JAM = ITEMS.register("citrome_jam", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(ModFoods.CITROME_JAM)));
    public static final DeferredItem<Item> CITROME_JELLY = ITEMS.register("citrome_jelly", () -> new Item(new Item.Properties().food(ModFoods.CITROME_JELLY)));
    public static final DeferredItem<Item> CITROME_POWDER = ITEMS.register("citrome_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> CITROME_PUDDING = ITEMS.register("citrome_pudding", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.CITROME_PUDDING)));
    public static final DeferredItem<Item> CITROME_SEED = ITEMS.register("citrome_seed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CITROMIX = ITEMS.register("citromix", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CITROMIX_L = ITEMS.register("citromix_l", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.CITROMIX_L)));
    public static final DeferredItem<Item> CITROMIX_RF = ITEMS.register("citromix_rf", () -> new ModEpicItem(new Item.Properties().food(ModFoods.CITROMIX_RF)));
    public static final DeferredItem<Item> CITROMIX_S = ITEMS.register("citromix_ss", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.CITROMIX_S)));
    public static final DeferredItem<Item> COARSE_FLATBREAD = ITEMS.register("coarse_flatbread", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.COARSE_FLATBREAD)));
    public static final DeferredItem<Item> CORRECTIVE_REMEDY = ITEMS.register("corrective_remedy", () -> new ModEpicItem(new Item.Properties().food(ModFoods.CORRECTIVE_REMEDY)));
    public static final DeferredItem<Item> CRYSTON_BOTTLE = ITEMS.register("cryston_bottle", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CRYSTON_COMPONENT = ITEMS.register("cryston_component", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CRYSTON_FIBER = ITEMS.register("cryston_fiber", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CRYSTON_PART = ITEMS.register("cryston_part", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CRYSTON_POWDER = ITEMS.register("cryston_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CUPRIUM = ITEMS.register("cuprium", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> CUPRIUM_COMPONENT = ITEMS.register("cuprium_component", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CUPRIUM_JAR = ITEMS.register("cuprium_jar", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CUPRIUM_ORE = ITEMS.register("cuprium_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CUPRIUM_PART = ITEMS.register("cuprium_part", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> CUPRIUM_POWDER = ITEMS.register("cuprium_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> DENSE_CARBON_POWDER = ITEMS.register("dense_carbon_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> DENSE_FERRIUM_POWDER = ITEMS.register("dense_ferrium_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> DENSE_ORIGINIUM_POWDER = ITEMS.register("dense_originium_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> DENSE_ORIGOCRUST_POWDER = ITEMS.register("dense_origocrust_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> FERRIUM = ITEMS.register("ferrium", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FERRIUM_BOTTLE = ITEMS.register("ferrium_bottle", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FERRIUM_COMPONENT = ITEMS.register("ferrium_component", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FERRIUM_ORE = ITEMS.register("ferrium_ore", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FERRIUM_PART = ITEMS.register("ferrium_part", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FERRIUM_POWDER = ITEMS.register("ferrium_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FILLET = ITEMS.register("fillet", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FILLET_CONFIT = ITEMS.register("fillet_confit", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.FILLET_CONFIT)));
    public static final DeferredItem<Item> FIREBUCKLE = ITEMS.register("firebuckle",() -> new BlockItem(ModBlocks.FIREBUCKLE_BLOCK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FIREBUCKLE_POWDER = ITEMS.register("firebuckle_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> FIRESTOVE_RICE = ITEMS.register("firestove_rice", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.FIRESTOVE_RICE)));
    public static final DeferredItem<Item> FIRETACK = ITEMS.register("firetack", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.FIRETACK)));
    public static final DeferredItem<Item> FLUFFED_JINCAO = ITEMS.register("fluffed_jincao", () -> new BlockItem(ModBlocks.FLUFFED_JINCAO_BLOCK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> FLUFFED_JINCAO_POWDER = ITEMS.register("fluffed_jincao_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> FORTIFYING_INFUSION = ITEMS.register("fortifying_infusion", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.FORTIFYING_INFUSION)));
    public static final DeferredItem<Item> GARDEN_FRIED_RICE = ITEMS.register("garden_fried_rice", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.GARDEN_FRIED_RICE)));
    public static final DeferredItem<Item> GARDEN_STIR_FRY = ITEMS.register("garden_stir_fry", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.GARDEN_STIR_FRY)));
    public static final DeferredItem<Item> GINSENG_MEAT_STEW = ITEMS.register("ginseng_meat_stew", () -> new ModEpicItem(new Item.Properties().food(ModFoods.GINSENG_MEAT_STEW)));
    public static final DeferredItem<Item> GLOWBUG = ITEMS.register("glowbug", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> GRASS_CHAFF = ITEMS.register("grass_chaff", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GROUND_BUCKFLOWER_POWDER = ITEMS.register("ground_buckflower_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> GROUND_CITROME_POWDER = ITEMS.register("ground_citrome_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> HC_BATTERY = ITEMS.register("hc_battery", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> HOLLOW_BONE = ITEMS.register("hollow_bone", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> HOLLOW_BONECHIP = ITEMS.register("hollow_bonechip", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> HOT_CRUNCHY_RIBS = ITEMS.register("hot_crunchy_ribs", () -> new ModEpicItem(new Item.Properties().food(ModFoods.HOT_CRUNCHY_RIBS)));
    public static final DeferredItem<Item> INDUSTRIAL_EXPLOSIVE = ITEMS.register("industrial_explosive",() -> new IndustrialExplosiveItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> JINCAO = ITEMS.register("jincao", () -> new BlockItem(ModBlocks.JINCAO_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> JINCAO_DRINK = ITEMS.register("jincao_drink", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> JINCAO_INFUSION = ITEMS.register("jincao_infusion", () -> new ModEpicItem(new Item.Properties().food(ModFoods.JINCAO_INFUSION)));
    public static final DeferredItem<Item> JINCAO_POWDER = ITEMS.register("jincao_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> JINCAO_SEED = ITEMS.register("jincao_seed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> JINCAO_TEA = ITEMS.register("jincao_tea", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> JINCAO_TISANE = ITEMS.register("jincao_tisane", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.JINCAO_TISANE)));
    public static final DeferredItem<Item> KUNST_TUBE = ITEMS.register("kunst_tube", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.KUBST_TUBE)));
    public static final DeferredItem<Item> KUNST_VIAL = ITEMS.register("kunst_vial", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.KUNST_VIAL)));
    public static final DeferredItem<Item> LC_BATTERY = ITEMS.register("lc_battery", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> MEAT_STIR_FRY = ITEMS.register("meat_stir_fry", () -> new ModEpicItem(new Item.Properties().food(ModFoods.MEAT_STIR_FRY)));
    public static final DeferredItem<Item> MOSSFIELD_PIE = ITEMS.register("mossfield_pie", () -> new ModEpicItem(new Item.Properties().food(ModFoods.MOSSFIELD_PIE)));
    public static final DeferredItem<Item> ORIGINIUM_ORE = ITEMS.register("originium_ore", () -> new ModFuelItem(new Item.Properties(), 1600));
    public static final DeferredItem<Item> ORIGINIUM_POWDER = ITEMS.register("originium_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> ORIGOCRUST = ITEMS.register("origocrust", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> ORIGOCRUST_POWDER = ITEMS.register("origocrust_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> PACKED_ORIGOCRUST = ITEMS.register("packed_origocrust", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> PERPLEXING_MEDICATION = ITEMS.register("perplexing_medication", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.PERPLEXING_MEDICATION)));
    public static final DeferredItem<Item> PRESERVE_STEW = ITEMS.register("preserve_stew", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.PRESERVE_STEW)));
    public static final DeferredItem<Item> REDJADE_GINSENG = ITEMS.register("redjade_ginseng", () -> new BlockItem(ModBlocks.REDJADE_GINSENG_BLOCK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> REDJADE_GINSENG_SEED = ITEMS.register("redjade_ginseng_seed", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> REED_RYE = ITEMS.register("reed_rye", () -> new BlockItem(ModBlocks.REED_RYE_BLOCK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> REED_RYE_SEED = ITEMS.register("reed_rye_seed", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SANDLEAF = ITEMS.register("sandleaf", () -> new BlockItem(ModBlocks.SANDLEAF_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> SANDLEAF_POWDER = ITEMS.register("sandleaf_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SANDLEAF_SEED = ITEMS.register("sandleaf_seed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SAVORY_FILLET = ITEMS.register("savory_fillet", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(ModFoods.SAVORY_FILLET)));
    public static final DeferredItem<Item> SAVORY_TANGBAO = ITEMS.register("savory_tangbao", () -> new ModEpicItem(new Item.Properties().food(ModFoods.SAVORY_TANGBAO)));
    public static final DeferredItem<Item> SAVORY_TANGMIAN = ITEMS.register("savory_tangmian", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(ModFoods.SAVORY_TANGMIAN)));
    public static final DeferredItem<Item> SCORCHBUG = ITEMS.register("scorchbug", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> SC_BATTERY = ITEMS.register("sc_battery", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> SENSORY_REMEDY = ITEMS.register("sensory_remedy", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.SENSORY_REMEDY)));
    public static final DeferredItem<Item> SMOKED_RICEBALL = ITEMS.register("smoked_riceball", () -> new ModEpicItem(new Item.Properties().food(ModFoods.SMOKED_RICEBALL)));
    public static final DeferredItem<Item> SOAKED_WOOD = ITEMS.register("soaked_wood", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> SOAKED_WOODCHIP = ITEMS.register("soaked_woodchip", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> STABILIZED_CARBON = ITEMS.register("stabilized_carbon", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> STEEL = ITEMS.register("steel", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> STEEL_BOTTLE = ITEMS.register("steel_bottle", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> STEEL_JAR = ITEMS.register("steel_jar", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> STEEL_PART = ITEMS.register("steel_part", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> TARTPEPPER = ITEMS.register("tartpepper", () -> new BlockItem(ModBlocks.TARTPEPPER_BLOCK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> TARTPEPPER_PICKLE = ITEMS.register("tartpepper_pickle", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModFoods.TARTPEPPER_PICKLE)));
    public static final DeferredItem<Item> TARTPEPPER_SALAD = ITEMS.register("tartpepper_salad", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.TARTPEPPER_SALAD)));
    public static final DeferredItem<Item> TARTPEPPER_SEED = ITEMS.register("tartpepper_seed", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> THORNY_YAZHEN = ITEMS.register("thorny_yazhen", () -> new BlockItem(ModBlocks.THORNY_YAZHEN_BLOCK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> THORNY_YAZHEN_POWDER = ITEMS.register("thorny_yazhen_powder", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> UMBRALINE = ITEMS.register("umbraline", () -> new BlockItem(ModBlocks.UMBRALINE_BLOCK.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> VALLEY_GRAYBREAD = ITEMS.register("valley_graybread", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.VALLEY_GRAYBREAD)));
    public static final DeferredItem<Item> WOOD = ITEMS.register("wood", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WULING_FRIED_RICE = ITEMS.register("wuling_fried_rice", () -> new ModEpicItem(new Item.Properties().food(ModFoods.WULING_FRIED_RICE)));
    public static final DeferredItem<Item> YAZHEN = ITEMS.register("yazhen", () -> new BlockItem(ModBlocks.YAZHEN_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<Item> YAZHEN_POWDER = ITEMS.register("yazhen_powder", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> YAZHEN_SEED = ITEMS.register("yazhen_seed", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> YAZHEN_SPRAY_L = ITEMS.register("yazhen_spray_l", () -> new ModEpicItem(new Item.Properties().food(ModFoods.YAZHEN_SPRAY_S)));
    public static final DeferredItem<Item> YAZHEN_SPRAY_S = ITEMS.register("yazhen_spray_s", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ModFoods.YAZHEN_SPRAY_L)));
    public static final DeferredItem<Item> YAZHEN_SYRINGE_A = ITEMS.register("yazhen_syringe_a", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> YAZHEN_SYRINGE_C = ITEMS.register("yazhen_syringe_c", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

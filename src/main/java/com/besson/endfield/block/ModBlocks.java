package com.besson.endfield.block;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.block.custom.*;
import com.besson.endfield.item.ModItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(ArknightsEndField.MOD_ID);

    public static final DeferredBlock<Block> CRAFTER = registerBlocks("crafter",
            () -> new CrafterBlock(BlockBehaviour.Properties.of().strength(3f).noOcclusion()));
    public static final DeferredBlock<Block> PORTABLE_ORIGINIUM_RIG = registerBlocksWithoutItem("portable_originium_rig",
            () -> new PortableOriginiumRigBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> PROTOCOL_ANCHOR_CORE = registerBlocksWithoutItem("protocol_anchor_core",
            () -> new ProtocolAnchorCoreBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> PROTOCOL_ANCHOR_CORE_PORT = registerBlocks("protocol_anchor_core_port",
            () -> new ProtocolAnchorCorePortBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> PROTOCOL_ANCHOR_CORE_SIDE = registerBlocks("protocol_anchor_core_side",
            () -> new ProtocolAnchorCoreSideBlock(BlockBehaviour.Properties.of().strength(1f, 1f).noOcclusion()));
    public static final DeferredBlock<Block> RELAY_TOWER = registerBlocksWithoutItem("relay_tower",
            () -> new RelayTowerBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> ELECTRIC_PYLON = registerBlocksWithoutItem("electric_pylon",
            () -> new ElectricPylonBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> ELECTRIC_MINING_RIG = registerBlocksWithoutItem("electric_mining_rig",
            () -> new ElectricMiningRigBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> ELECTRIC_MINING_RIG_MK_II = registerBlocksWithoutItem("electric_mining_rig_mk_ii",
            () -> new ElectricMiningRigMkIIBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> REFINING_UNIT = registerBlocksWithoutItem("refining_unit",
            () -> new RefiningUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> REFINING_UNIT_SIDE = registerBlocks("refining_unit_side",
            () -> new RefiningUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> FILLING_UNIT = registerBlocksWithoutItem("filling_unit",
            () -> new FillingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> FILLING_UNIT_SIDE = registerBlocks("filling_unit_side",
            () -> new FillingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> FITTING_UNIT = registerBlocksWithoutItem("fitting_unit",
            () -> new FittingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> FITTING_UNIT_SIDE = registerBlocks("fitting_unit_side",
            () -> new FittingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> SHREDDING_UNIT = registerBlocksWithoutItem("shredding_unit",
            () -> new ShreddingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> SHREDDING_UNIT_SIDE = registerBlocks("shredding_unit_side",
            () -> new ShreddingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> GEARING_UNIT = registerBlocksWithoutItem("gearing_unit",
            () -> new GearingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> GEARING_UNIT_SIDE = registerBlocks("gearing_unit_side",
            () -> new GearingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> GRINDING_UNIT = registerBlocksWithoutItem("grinding_unit",
            () -> new GrindingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> GRINDING_UNIT_SIDE = registerBlocks("grinding_unit_side",
            () -> new GrindingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> MOULDING_UNIT = registerBlocksWithoutItem("moulding_unit",
            () -> new MouldingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> MOULDING_UNIT_SIDE = registerBlocksWithoutItem("moulding_unit_side",
            () -> new MouldingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> PACKAGING_UNIT = registerBlocksWithoutItem("packaging_unit",
            () -> new PackagingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> PACKAGING_UNIT_SIDE = registerBlocks("packaging_unit_side",
            () -> new PackagingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> PLANTING_UNIT = registerBlocksWithoutItem("planting_unit",
            () -> new PlantingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> PLANTING_UNIT_SIDE = registerBlocks("planting_unit_side",
            () -> new PlantingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> SEED_PICKING_UNIT = registerBlocksWithoutItem("seed_picking_unit",
            () -> new SeedPickingUnitBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> SEED_PICKING_UNIT_SIDE = registerBlocks("seed_picking_unit_side",
            () -> new SeedPickingUnitSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> THERMAL_BANK = registerBlocksWithoutItem("thermal_bank",
            () -> new ThermalBankBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));
    public static final DeferredBlock<Block> THERMAL_BANK_SIDE = registerBlocks("thermal_bank_side",
            () -> new ThermalBankSideBlock(BlockBehaviour.Properties.of().strength(3f, 5f).noOcclusion()));

    public static final DeferredBlock<Block> AMETHYST_MINERAL_VEIN_BLOCK = registerBlocks("amethyst_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> COAL_MINERAL_VEIN_BLOCK = registerBlocks("coal_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> COPPER_MINERAL_VEIN_BLOCK = registerBlocks("copper_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DIAMOND_MINERAL_VEIN_BLOCK = registerBlocks("diamond_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> GOLD_MINERAL_VEIN_BLOCK = registerBlocks("gold_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> IRON_MINERAL_VEIN_BLOCK = registerBlocks("iron_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LAPIS_MINERAL_VEIN_BLOCK = registerBlocks("lapis_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> REDSTONE_MINERAL_VEIN_BLOCK = registerBlocks("redstone_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> EMERALD_MINERAL_VEIN_BLOCK = registerBlocks("emerald_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ORIGINIUM_MINERAL_VEIN_BLOCK = registerBlocks("originium_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> AMETHYST_ORE_BLOCK = registerBlocks("amethyst_ore_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> CUPRIUM_MINERAL_VEIN_BLOCK = registerBlocks("cuprium_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> CUPRIUM_ORE_BLOCK = registerBlocks("cuprium_ore_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_AMETHYST_ORE = registerBlocks("deepslate_amethyst_ore",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_CUPRIUM_ORE = registerBlocks("deepslate_cuprium_ore",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_FERRIUM_ORE = registerBlocks("deepslate_ferrium_ore",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> DEEPSLATE_ORIGINIUM_ORE = registerBlocks("deepslate_originium_ore",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> FERRIUM_MINERAL_VEIN_BLOCK = registerBlocks("ferrium_mineral_vein_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> FERRIUM_ORE_BLOCK = registerBlocks("ferrium_ore_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> ORIGINIUM_ORE_BLOCK = registerBlocks("originium_ore_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(5f, 5f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> AKETINE_BLOCK = registerBlocksWithoutItem("aketine_block",
            () -> new FlowerBlock(MobEffects.JUMP, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_AKETINE_BLOCK = registerBlocksWithoutItem("potted_aketine_block",
            () -> new FlowerPotBlock(null, ModBlocks.AKETINE_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> AMBER_RICE_BLOCK = registerBlocksWithoutItem("amber_rice_block",
            () -> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_AMBER_RICE_BLOCK = registerBlocksWithoutItem("potted_amber_rice_block",
            () -> new FlowerPotBlock(null, ModBlocks.AMBER_RICE_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> BUCKFLOWER_BLOCK = registerBlocksWithoutItem("buckflower_block",
            () -> new FlowerBlock(MobEffects.REGENERATION, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_BUCKFLOWER_BLOCK = registerBlocksWithoutItem("potted_buckflower_block",
            () -> new FlowerPotBlock(null, ModBlocks.BUCKFLOWER_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> CITROME_BLOCK = registerBlocksWithoutItem("citrome_block",
            () -> new FlowerBlock(MobEffects.SATURATION, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_CITROME_BLOCK = registerBlocksWithoutItem("potted_citrome_block",
            () -> new FlowerPotBlock(null, ModBlocks.CITROME_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> FIREBUCKLE_BLOCK = registerBlocksWithoutItem("firebuckle_block",
            () -> new FlowerBlock(MobEffects.FIRE_RESISTANCE, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_FIREBUCKLE_BLOCK = registerBlocksWithoutItem("potted_firebuckle_block",
            () -> new FlowerPotBlock(null, ModBlocks.FIREBUCKLE_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> FLUFFED_JINCAO_BLOCK = registerBlocksWithoutItem("fluffed_jincao_block",
            () -> new FlowerBlock(MobEffects.MOVEMENT_SLOWDOWN, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_FLUFFED_JINCAO_BLOCK = registerBlocksWithoutItem("potted_fluffed_jincao_block",
            () -> new FlowerPotBlock(null, ModBlocks.FLUFFED_JINCAO_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> JINCAO_BLOCK = registerBlocksWithoutItem("jincao_block",
            () -> new FlowerBlock(MobEffects.HEAL, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_JINCAO_BLOCK = registerBlocksWithoutItem("potted_jincao_block",
            () -> new FlowerPotBlock(null, ModBlocks.JINCAO_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> REDJADE_GINSENG_BLOCK = registerBlocksWithoutItem("redjade_ginseng_block",
            () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_REDJADE_GINSENG_BLOCK = registerBlocksWithoutItem("potted_redjade_ginseng_block",
            () -> new FlowerPotBlock(null, ModBlocks.REDJADE_GINSENG_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> REED_RYE_BLOCK = registerBlocksWithoutItem("reed_rye_block",
            () -> new FlowerBlock(MobEffects.DIG_SPEED, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_REED_RYE_BLOCK = registerBlocksWithoutItem("potted_reed_rye_block",
            () -> new FlowerPotBlock(null, ModBlocks.REED_RYE_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> SANDLEAF_BLOCK = registerBlocksWithoutItem("sandleaf_block",
            () -> new FlowerBlock(MobEffects.WATER_BREATHING, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_SANDLEAF_BLOCK = registerBlocksWithoutItem("potted_sandleaf_block",
            () -> new FlowerPotBlock(null, ModBlocks.SANDLEAF_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> TARTPEPPER_BLOCK = registerBlocksWithoutItem("tartpepper_block",
            () -> new FlowerBlock(MobEffects.CONFUSION, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_TARTPEPPER_BLOCK = registerBlocksWithoutItem("potted_tartpepper_block",
            () -> new FlowerPotBlock(null, ModBlocks.TARTPEPPER_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> THORNY_YAZHEN_BLOCK = registerBlocksWithoutItem("thorny_yazhen_block",
            () -> new FlowerBlock(MobEffects.POISON, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_THORNY_YAZHEN_BLOCK = registerBlocksWithoutItem("potted_thorny_yazhen_block",
            () -> new FlowerPotBlock(null, ModBlocks.THORNY_YAZHEN_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> UMBRALINE_BLOCK = registerBlocksWithoutItem("umbraline_block",
            () -> new FlowerBlock(MobEffects.INVISIBILITY, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_UMBRALINE_BLOCK = registerBlocksWithoutItem("potted_umbraline_block",
            () -> new FlowerPotBlock(null, ModBlocks.UMBRALINE_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));
    public static final DeferredBlock<Block> YAZHEN_BLOCK = registerBlocksWithoutItem("yazhen_block",
            () -> new FlowerBlock(MobEffects.REGENERATION, 100, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion().instabreak().noCollission()));
    public static final DeferredBlock<Block> POTTED_YAZHEN_BLOCK = registerBlocksWithoutItem("potted_yazhen_block",
            () -> new FlowerPotBlock(null, ModBlocks.YAZHEN_BLOCK, BlockBehaviour.Properties.of().strength(0.5f).noOcclusion()));

    public static final DeferredBlock<Block> FE_CONVERTER_BLOCK = registerBlocks("fe_converter_block",
            () -> new FEConverterBlock(BlockBehaviour.Properties.of().strength(3f).noOcclusion()));

    private static <T extends Block>DeferredBlock<T> registerBlocksWithoutItem(String name, Supplier<T> blocks) {
        return BLOCKS.register(name, blocks);
    }

    private static <T extends Block>DeferredBlock<T> registerBlocks(String name, Supplier<T> blocks){
        DeferredBlock<T> block = BLOCKS.register(name, blocks);
        registerBlockItems(name, block);
        return block;
    }

    private static <T extends Block> void registerBlockItems(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}

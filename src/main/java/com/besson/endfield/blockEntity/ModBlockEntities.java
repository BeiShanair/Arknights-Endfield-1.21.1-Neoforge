package com.besson.endfield.blockEntity;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.block.ModBlocks;
import com.besson.endfield.blockEntity.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ArknightsEndField.MOD_ID);

    public static final Supplier<BlockEntityType<CrafterBlockEntity>> CRAFTER =
            BLOCK_ENTITIES.register("crafter", () -> BlockEntityType.Builder.of(
                    CrafterBlockEntity::new, ModBlocks.CRAFTER.get()).build(null));
    public static final Supplier<BlockEntityType<PortableOriginiumRigBlockEntity>> PORTABLE_ORIGINIUM_RIG =
            BLOCK_ENTITIES.register("portable_originium_rig", () -> BlockEntityType.Builder.of(
                    PortableOriginiumRigBlockEntity::new, ModBlocks.PORTABLE_ORIGINIUM_RIG.get()).build(null));
    public static final Supplier<BlockEntityType<ProtocolAnchorCoreBlockEntity>> PROTOCOL_ANCHOR_CORE =
            BLOCK_ENTITIES.register("protocol_anchor_core", () -> BlockEntityType.Builder.of(
                    ProtocolAnchorCoreBlockEntity::new, ModBlocks.PROTOCOL_ANCHOR_CORE.get()).build(null));
    public static final Supplier<BlockEntityType<ProtocolAnchorCorePortBlockEntity>> PROTOCOL_ANCHOR_CORE_PORT =
            BLOCK_ENTITIES.register("protocol_anchor_core_port", () -> BlockEntityType.Builder.of(
                    ProtocolAnchorCorePortBlockEntity::new, ModBlocks.PROTOCOL_ANCHOR_CORE_PORT.get()).build(null));
    public static final Supplier<BlockEntityType<ThermalBankBlockEntity>> THERMAL_BANK =
            BLOCK_ENTITIES.register("thermal_bank", () -> BlockEntityType.Builder.of(
                    ThermalBankBlockEntity::new, ModBlocks.THERMAL_BANK.get()).build(null));
    public static final Supplier<BlockEntityType<ThermalBankSideBlockEntity>> THERMAL_BANK_SIDE =
            BLOCK_ENTITIES.register("thermal_bank_side", () -> BlockEntityType.Builder.of(
                    ThermalBankSideBlockEntity::new, ModBlocks.THERMAL_BANK_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<RelayTowerBlockEntity>> RELAY_TOWER =
            BLOCK_ENTITIES.register("relay_tower", () -> BlockEntityType.Builder.of(
                    RelayTowerBlockEntity::new, ModBlocks.RELAY_TOWER.get()).build(null));
    public static final Supplier<BlockEntityType<ElectricPylonBlockEntity>> ELECTRIC_PYLON =
            BLOCK_ENTITIES.register("electric_pylon", () -> BlockEntityType.Builder.of(
                    ElectricPylonBlockEntity::new, ModBlocks.ELECTRIC_PYLON.get()).build(null));
    public static final Supplier<BlockEntityType<ElectricMiningRigBlockEntity>> ELECTRIC_MINING_RIG =
            BLOCK_ENTITIES.register("electric_mining_rig", () -> BlockEntityType.Builder.of(
                    ElectricMiningRigBlockEntity::new, ModBlocks.ELECTRIC_MINING_RIG.get()).build(null));
    public static final Supplier<BlockEntityType<ElectricMiningRigMkIIBlockEntity>> ELECTRIC_MINING_RIG_MK_II =
            BLOCK_ENTITIES.register("electric_mining_rig_mk2", () -> BlockEntityType.Builder.of(
                    ElectricMiningRigMkIIBlockEntity::new, ModBlocks.ELECTRIC_MINING_RIG_MK_II.get()).build(null));
    public static final Supplier<BlockEntityType<RefiningUnitBlockEntity>> REFINING_UNIT =
            BLOCK_ENTITIES.register("refining_unit", () -> BlockEntityType.Builder.of(
                    RefiningUnitBlockEntity::new, ModBlocks.REFINING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<RefiningUnitSideBlockEntity>> REFINING_UNIT_SIDE =
            BLOCK_ENTITIES.register("refining_unit_side", () -> BlockEntityType.Builder.of(
                    RefiningUnitSideBlockEntity::new, ModBlocks.REFINING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<FillingUnitBlockEntity>> FILLING_UNIT =
            BLOCK_ENTITIES.register("filling_unit", () -> BlockEntityType.Builder.of(
                    FillingUnitBlockEntity::new, ModBlocks.FILLING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<FillingUnitSideBlockEntity>> FILLING_UNIT_SIDE =
            BLOCK_ENTITIES.register("filling_unit_side", () -> BlockEntityType.Builder.of(
                    FillingUnitSideBlockEntity::new, ModBlocks.FILLING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<FittingUnitBlockEntity>> FITTING_UNIT =
            BLOCK_ENTITIES.register("fitting_unit", () -> BlockEntityType.Builder.of(
                    FittingUnitBlockEntity::new, ModBlocks.FITTING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<FittingUnitSideBlockEntity>> FITTING_UNIT_SIDE =
            BLOCK_ENTITIES.register("fitting_unit_side", () -> BlockEntityType.Builder.of(
                    FittingUnitSideBlockEntity::new, ModBlocks.FITTING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<GearingUnitBlockEntity>> GEARING_UNIT =
            BLOCK_ENTITIES.register("gearing_unit", () -> BlockEntityType.Builder.of(
                    GearingUnitBlockEntity::new, ModBlocks.GEARING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<GearingUnitSideBlockEntity>> GEARING_UNIT_SIDE =
            BLOCK_ENTITIES.register("gearing_unit_side", () -> BlockEntityType.Builder.of(
                    GearingUnitSideBlockEntity::new, ModBlocks.GEARING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<GrindingUnitBlockEntity>> GRINDING_UNIT =
            BLOCK_ENTITIES.register("grinding_unit", () -> BlockEntityType.Builder.of(
                    GrindingUnitBlockEntity::new, ModBlocks.GRINDING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<GrindingUnitSideBlockEntity>> GRINDING_UNIT_SIDE =
            BLOCK_ENTITIES.register("grinding_unit_side", () -> BlockEntityType.Builder.of(
                    GrindingUnitSideBlockEntity::new, ModBlocks.GRINDING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<MouldingUnitBlockEntity>> MOULDING_UNIT =
            BLOCK_ENTITIES.register("moulding_unit", () -> BlockEntityType.Builder.of(
                    MouldingUnitBlockEntity::new, ModBlocks.MOULDING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<MouldingUnitSideBlockEntity>> MOULDING_UNIT_SIDE =
            BLOCK_ENTITIES.register("moulding_unit_side", () -> BlockEntityType.Builder.of(
                    MouldingUnitSideBlockEntity::new, ModBlocks.MOULDING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<PackagingUnitBlockEntity>> PACKAGING_UNIT =
            BLOCK_ENTITIES.register("packaging_unit", () -> BlockEntityType.Builder.of(
                    PackagingUnitBlockEntity::new, ModBlocks.PACKAGING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<PackagingUnitSideBlockEntity>> PACKAGING_UNIT_SIDE =
            BLOCK_ENTITIES.register("packaging_unit_side", () -> BlockEntityType.Builder.of(
                    PackagingUnitSideBlockEntity::new, ModBlocks.PACKAGING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<PlantingUnitBlockEntity>> PLANTING_UNIT =
            BLOCK_ENTITIES.register("planting_unit", () -> BlockEntityType.Builder.of(
                    PlantingUnitBlockEntity::new, ModBlocks.PLANTING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<PlantingUnitSideBlockEntity>> PLANTING_UNIT_SIDE =
            BLOCK_ENTITIES.register("planting_unit_side", () -> BlockEntityType.Builder.of(
                    PlantingUnitSideBlockEntity::new, ModBlocks.PLANTING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<SeedPickingUnitBlockEntity>> SEED_PICKING_UNIT =
            BLOCK_ENTITIES.register("seed_picking_unit", () -> BlockEntityType.Builder.of(
                    SeedPickingUnitBlockEntity::new, ModBlocks.SEED_PICKING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<SeedPickingUnitSideBlockEntity>> SEED_PICKING_UNIT_SIDE =
            BLOCK_ENTITIES.register("seed_picking_unit_side", () -> BlockEntityType.Builder.of(
                    SeedPickingUnitSideBlockEntity::new, ModBlocks.SEED_PICKING_UNIT_SIDE.get()).build(null));
    public static final Supplier<BlockEntityType<ShreddingUnitBlockEntity>> SHREDDING_UNIT =
            BLOCK_ENTITIES.register("shredding_unit", () -> BlockEntityType.Builder.of(
                    ShreddingUnitBlockEntity::new, ModBlocks.SHREDDING_UNIT.get()).build(null));
    public static final Supplier<BlockEntityType<ShreddingUnitSideBlockEntity>> SHREDDING_UNIT_SIDE =
            BLOCK_ENTITIES.register("shredding_unit_side", () -> BlockEntityType.Builder.of(
                    ShreddingUnitSideBlockEntity::new, ModBlocks.SHREDDING_UNIT_SIDE.get()).build(null));

    public static final Supplier<BlockEntityType<FEConverterBlockEntity>> FE_CONVERTER =
            BLOCK_ENTITIES.register("fe_converter", () -> BlockEntityType.Builder.of(
                    FEConverterBlockEntity::new, ModBlocks.FE_CONVERTER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}

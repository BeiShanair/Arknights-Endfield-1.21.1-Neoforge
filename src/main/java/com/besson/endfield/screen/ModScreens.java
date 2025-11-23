package com.besson.endfield.screen;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.screen.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModScreens {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, ArknightsEndField.MOD_ID);

    public static final Supplier<MenuType<CrafterScreenHandler>> CRAFTER_SCREEN =
            register("crafter", CrafterScreenHandler::new);
    public static final Supplier<MenuType<PortableOriginiumRigScreenHandler>> PORTABLE_ORIGINIUM_RIG_SCREEN =
            register("portable_originium_rig", PortableOriginiumRigScreenHandler::new);
    public static final Supplier<MenuType<ProtocolAnchorCoreScreenHandler>> PROTOCOL_ANCHOR_CORE_SCREEN =
            register("protocol_anchor_core", ProtocolAnchorCoreScreenHandler::new);
    public static final Supplier<MenuType<ThermalBankScreenHandler>> THERMAL_BANK_SCREEN =
            register("thermal_bank", ThermalBankScreenHandler::new);
    public static final Supplier<MenuType<ElectricMiningRigScreenHandler>> ELECTRIC_MINING_RIG_SCREEN =
            register("electric_mining_rig", ElectricMiningRigScreenHandler::new);
    public static final Supplier<MenuType<ElectricMiningRigMkIIScreenHandler>> ELECTRIC_MINING_RIG_MK_II_SCREEN =
            register("electric_mining_rig_mk_ii", ElectricMiningRigMkIIScreenHandler::new);
    public static final Supplier<MenuType<RefiningUnitScreenHandler>> REFINING_UNIT_SCREEN =
            register("refining_unit", RefiningUnitScreenHandler::new);
    public static final Supplier<MenuType<FillingUnitScreenHandler>> FILLING_UNIT_SCREEN =
            register("filling_unit", FillingUnitScreenHandler::new);
    public static final Supplier<MenuType<FittingUnitScreenHandler>> FITTING_UNIT_SCREEN =
            register("fitting_unit", FittingUnitScreenHandler::new);
    public static final Supplier<MenuType<GearingUnitScreenHandler>> GEARING_UNIT_SCREEN =
            register("gearing_unit", GearingUnitScreenHandler::new);
    public static final Supplier<MenuType<GrindingUnitScreenHandler>> GRINDING_UNIT_SCREEN =
            register("grinding_unit", GrindingUnitScreenHandler::new);
    public static final Supplier<MenuType<MouldingUnitScreenHandler>> MOULDING_UNIT_SCREEN =
            register("moulding_unit", MouldingUnitScreenHandler::new);
    public static final Supplier<MenuType<PackagingUnitScreenHandler>> PACKAGING_UNIT_SCREEN =
            register("packaging_unit", PackagingUnitScreenHandler::new);
    public static final Supplier<MenuType<PlantingUnitScreenHandler>> PLANTING_UNIT_SCREEN =
            register("planting_unit", PlantingUnitScreenHandler::new);
    public static final Supplier<MenuType<SeedPickingUnitScreenHandler>> SEED_PICKING_UNIT_SCREEN =
            register("seed_picking_unit", SeedPickingUnitScreenHandler::new);
    public static final Supplier<MenuType<ShreddingUnitScreenHandler>> SHREDDING_UNIT_SCREEN =
            register("shredding_unit", ShreddingUnitScreenHandler::new);

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> register(String name, IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}

package com.besson.endfield;

import com.besson.endfield.entity.ModItemEntity;
import com.besson.endfield.screen.ModScreens;
import com.besson.endfield.screen.custom.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = ArknightsEndField.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = ArknightsEndField.MOD_ID, value = Dist.CLIENT)
public class ArknightsEndFieldClient {
    public ArknightsEndFieldClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        ArknightsEndField.LOGGER.info("HELLO FROM CLIENT SETUP");
        ArknightsEndField.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModScreens.CRAFTER_SCREEN.get(), CrafterScreen::new);
        event.register(ModScreens.PORTABLE_ORIGINIUM_RIG_SCREEN.get(), PortableOriginiumRigScreen::new);
        event.register(ModScreens.PROTOCOL_ANCHOR_CORE_SCREEN.get(), ProtocolAnchorCoreScreen::new);
        event.register(ModScreens.THERMAL_BANK_SCREEN.get(), ThermalBankScreen::new);
        event.register(ModScreens.ELECTRIC_MINING_RIG_SCREEN.get(), ElectricMiningRigScreen::new);
        event.register(ModScreens.ELECTRIC_MINING_RIG_MK_II_SCREEN.get(), ElectricMiningRigMkIIScreen::new);
        event.register(ModScreens.REFINING_UNIT_SCREEN.get(), RefiningUnitScreen::new);
        event.register(ModScreens.FILLING_UNIT_SCREEN.get(), FillingUnitScreen::new);
        event.register(ModScreens.FITTING_UNIT_SCREEN.get(), FittingUnitScreen::new);
        event.register(ModScreens.GEARING_UNIT_SCREEN.get(), GearingUnitScreen::new);
        event.register(ModScreens.GRINDING_UNIT_SCREEN.get(), GrindingUnitScreen::new);
        event.register(ModScreens.MOULDING_UNIT_SCREEN.get(), MouldingUnitScreen::new);
        event.register(ModScreens.PACKAGING_UNIT_SCREEN.get(), PackagingUnitScreen::new);
        event.register(ModScreens.PLANTING_UNIT_SCREEN.get(), PlantingUnitScreen::new);
        event.register(ModScreens.SEED_PICKING_UNIT_SCREEN.get(), SeedPickingUnitScreen::new);
        event.register(ModScreens.SHREDDING_UNIT_SCREEN.get(), ShreddingUnitScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModItemEntity.INDUSTRIAL_EXPLOSIVE.get(), ThrownItemRenderer::new);
    }
}

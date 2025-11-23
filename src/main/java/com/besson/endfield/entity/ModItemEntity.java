package com.besson.endfield.entity;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.item.custom.IndustrialExplosiveEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItemEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ArknightsEndField.MOD_ID);

    public static final Supplier<EntityType<IndustrialExplosiveEntity>> INDUSTRIAL_EXPLOSIVE =
            ENTITY_TYPES.register("industrial_explosive", () ->
                    EntityType.Builder.<IndustrialExplosiveEntity>of(
                            IndustrialExplosiveEntity::new, MobCategory.MISC
                            )
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("industrial_explosive"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

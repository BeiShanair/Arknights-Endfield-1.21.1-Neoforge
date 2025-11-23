package com.besson.endfield.recipe;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.recipe.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ArknightsEndField.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, ArknightsEndField.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrafterRecipe>> CRAFTER_SERIALIZER =
            RECIPE_SERIALIZERS.register("crafter", CrafterRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<CrafterRecipe>> CRAFTER_TYPE =
            RECIPE_TYPES.register("crafter", () -> new RecipeType<CrafterRecipe>() {
                @Override
                public String toString() {
                    return "crafter";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<OreRigRecipe>> ORE_RIG_SERIALIZER =
            RECIPE_SERIALIZERS.register("ore_rig", OreRigRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<OreRigRecipe>> ORE_RIG_TYPE =
            RECIPE_TYPES.register("ore_rig", () -> new RecipeType<OreRigRecipe>() {
                @Override
                public String toString() {
                    return "ore_rig";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RefiningUnitRecipe>> REFINING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("refining_unit", RefiningUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<RefiningUnitRecipe>> REFINING_UNIT_TYPE =
            RECIPE_TYPES.register("refining_unit", () -> new RecipeType<RefiningUnitRecipe>() {
                @Override
                public String toString() {
                    return "refining_unit";
                }
            });


    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FillingUnitRecipe>> FILLING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("filling_unit", FillingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FillingUnitRecipe>> FILLING_UNIT_TYPE =
            RECIPE_TYPES.register("filling_unit", () -> new RecipeType<FillingUnitRecipe>() {
                @Override
                public String toString() {
                    return "filling_unit";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FittingUnitRecipe>> FITTING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("fitting_unit", FittingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FittingUnitRecipe>> FITTING_UNIT_TYPE =
            RECIPE_TYPES.register("fitting_unit", () -> new RecipeType<FittingUnitRecipe>() {
                @Override
                public String toString() {
                    return "fitting_unit";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GearingUnitRecipe>> GEARING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("gearing_unit", GearingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<GearingUnitRecipe>> GEARING_UNIT_TYPE =
            RECIPE_TYPES.register("gearing_unit", () -> new RecipeType<GearingUnitRecipe>() {
                @Override
                public String toString() {
                    return "gearing_unit";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GrindingUnitRecipe>> GRINDING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("grinding_unit", GrindingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<GrindingUnitRecipe>> GRINDING_UNIT_TYPE =
            RECIPE_TYPES.register("grinding_unit", () -> new RecipeType<GrindingUnitRecipe>() {
                @Override
                public String toString() {
                    return "grinding_unit";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MouldingUnitRecipe>> MOULDING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("moulding_unit", MouldingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MouldingUnitRecipe>> MOULDING_UNIT_TYPE =
            RECIPE_TYPES.register("moulding_unit", () -> new RecipeType<MouldingUnitRecipe>() {
                @Override
                public String toString() {
                    return "moulding_unit";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PackagingUnitRecipe>> PACKAGING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("packaging_unit", PackagingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<PackagingUnitRecipe>> PACKAGING_UNIT_TYPE =
            RECIPE_TYPES.register("packaging_unit", () -> new RecipeType<PackagingUnitRecipe>() {
                @Override
                public String toString() {
                    return "packaging_unit";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<PlantingUnitRecipe>> PLANTING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("planting_unit", PlantingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<PlantingUnitRecipe>> PLANTING_UNIT_TYPE =
            RECIPE_TYPES.register("planting_unit", () -> new RecipeType<PlantingUnitRecipe>() {
                @Override
                public String toString() {
                    return "planting_unit";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SeedPickingUnitRecipe>> SEED_PICKING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("seed_picking_unit", SeedPickingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<SeedPickingUnitRecipe>> SEED_PICKING_UNIT_TYPE =
            RECIPE_TYPES.register("seed_picking_unit", () -> new RecipeType<SeedPickingUnitRecipe>() {
                @Override
                public String toString() {
                    return "seed_picking_unit";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ShreddingUnitRecipe>> SHREDDING_UNIT_SERIALIZER =
            RECIPE_SERIALIZERS.register("shredding_unit", ShreddingUnitRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ShreddingUnitRecipe>> SHREDDING_UNIT_TYPE =
            RECIPE_TYPES.register("shredding_unit", () -> new RecipeType<ShreddingUnitRecipe>() {
                @Override
                public String toString() {
                    return "shredding_unit";
                }
            });

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}

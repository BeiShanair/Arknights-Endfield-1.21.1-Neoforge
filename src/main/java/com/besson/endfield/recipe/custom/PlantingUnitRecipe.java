package com.besson.endfield.recipe.custom;

import com.besson.endfield.recipe.ModRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record PlantingUnitRecipe(Ingredient input, ItemStack output) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput inventory, Level world) {
        if (world.isClientSide()) return false;
        return input.test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(input);
        return ingredients;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.PLANTING_UNIT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.PLANTING_UNIT_TYPE.get();
    }

    public Ingredient getInput() {
        return input;
    }

    public static class Serializer implements RecipeSerializer<PlantingUnitRecipe> {
        public static final MapCodec<PlantingUnitRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter(PlantingUnitRecipe::input),
                ItemStack.CODEC.fieldOf("output").forGetter(PlantingUnitRecipe::output)
        ).apply(instance, PlantingUnitRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, PlantingUnitRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, PlantingUnitRecipe::input,
                        ItemStack.STREAM_CODEC, PlantingUnitRecipe::output,
                        PlantingUnitRecipe::new
                );

        @Override
        public MapCodec<PlantingUnitRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, PlantingUnitRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

package com.besson.endfield.recipe.custom;

import com.besson.endfield.recipe.ModRecipes;
import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record ShreddingUnitRecipe(Ingredient input, ItemStack output) implements Recipe<SingleRecipeInput> {

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
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SHREDDING_UNIT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SHREDDING_UNIT_TYPE.get();
    }

    public Ingredient getInput() {
        return input;
    }

    public static class Serializer implements RecipeSerializer<ShreddingUnitRecipe> {
        public static final MapCodec<ShreddingUnitRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter(ShreddingUnitRecipe::input),
                ItemStack.CODEC.fieldOf("output").forGetter(ShreddingUnitRecipe::output)
        ).apply(instance, ShreddingUnitRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ShreddingUnitRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, ShreddingUnitRecipe::input,
                        ItemStack.STREAM_CODEC, ShreddingUnitRecipe::output,
                        ShreddingUnitRecipe::new
                );

        @Override
        public MapCodec<ShreddingUnitRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShreddingUnitRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

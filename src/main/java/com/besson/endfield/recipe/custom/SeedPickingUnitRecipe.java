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

public record SeedPickingUnitRecipe(Ingredient input, ItemStack output) implements Recipe<SingleRecipeInput> {

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
        return ModRecipes.SEED_PICKING_UNIT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SEED_PICKING_UNIT_TYPE.get();
    }

    public Ingredient getInput() {
        return input;
    }


    public static class Serializer implements RecipeSerializer<SeedPickingUnitRecipe> {
        public static final MapCodec<SeedPickingUnitRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter(SeedPickingUnitRecipe::input),
                ItemStack.CODEC.fieldOf("output").forGetter(SeedPickingUnitRecipe::output)
        ).apply(instance, SeedPickingUnitRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, SeedPickingUnitRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, SeedPickingUnitRecipe::input,
                        ItemStack.STREAM_CODEC, SeedPickingUnitRecipe::output,
                        SeedPickingUnitRecipe::new
                );

        @Override
        public MapCodec<SeedPickingUnitRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SeedPickingUnitRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

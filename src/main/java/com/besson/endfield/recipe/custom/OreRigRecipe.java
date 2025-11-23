package com.besson.endfield.recipe.custom;

import com.besson.endfield.recipe.ModRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record OreRigRecipe(Ingredient input, ItemStack output) implements Recipe<SingleRecipeInput> {

    public Ingredient getInput() {
        return input;
    }

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
        return ModRecipes.ORE_RIG_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ORE_RIG_TYPE.get();
    }


    public static class Serializer implements RecipeSerializer<OreRigRecipe> {

        public static final MapCodec<OreRigRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter(OreRigRecipe::input),
                ItemStack.CODEC.fieldOf("output").forGetter(OreRigRecipe::output)
        ).apply(instance, OreRigRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, OreRigRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, OreRigRecipe::input,
                        ItemStack.STREAM_CODEC, OreRigRecipe::output,
                        OreRigRecipe::new
                );

        @Override
        public MapCodec<OreRigRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, OreRigRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

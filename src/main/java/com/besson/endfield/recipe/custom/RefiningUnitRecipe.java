package com.besson.endfield.recipe.custom;

import com.besson.endfield.recipe.InputEntry;
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

public record RefiningUnitRecipe(InputEntry input, ItemStack output) implements Recipe<SingleRecipeInput> {

    public InputEntry getInput() {
        return input;
    }

    @Override
    public boolean matches(SingleRecipeInput inventory, Level world) {
        if (world.isClientSide()) return false;
        ItemStack inputs = inventory.getItem(0);
        return input.ingredient().test(inputs);
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
        return ModRecipes.REFINING_UNIT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.REFINING_UNIT_TYPE.get();
    }


    public static class Serializer implements RecipeSerializer<RefiningUnitRecipe> {

        public static final MapCodec<RefiningUnitRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        InputEntry.CODEC.fieldOf("input").forGetter(RefiningUnitRecipe::input),
                        ItemStack.CODEC.fieldOf("output").forGetter(RefiningUnitRecipe::output)
                ).apply(instance, RefiningUnitRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, RefiningUnitRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        InputEntry.STREAM_CODEC, RefiningUnitRecipe::input,
                        ItemStack.STREAM_CODEC, RefiningUnitRecipe::output,
                        RefiningUnitRecipe::new
                );

        @Override
        public MapCodec<RefiningUnitRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RefiningUnitRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

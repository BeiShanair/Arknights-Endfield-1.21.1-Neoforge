package com.besson.endfield.recipe.custom;

import com.besson.endfield.recipe.InputEntry;
import com.besson.endfield.recipe.ModRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record GearingUnitRecipe(NonNullList<InputEntry> input, ItemStack output) implements Recipe<DoubleRecipeInput> {

    @Override
    public boolean matches(DoubleRecipeInput inventory, Level world) {
        if (world.isClientSide()) return false;

        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            inputs.add(inventory.getItem(i));
        }

        for (InputEntry inputEntry : input) {
            boolean matched = false;
            for (ItemStack stack : inputs) {
                if (inputEntry.ingredient().test(stack)) {
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(DoubleRecipeInput inputs, HolderLookup.Provider registries) {
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
        return ModRecipes.GEARING_UNIT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GEARING_UNIT_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (InputEntry entry : input) {
            ingredients.add(entry.ingredient());
        }
        return ingredients;
    }

    public static class Serializer implements RecipeSerializer<GearingUnitRecipe> {

        public static final MapCodec<GearingUnitRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(Codec.list(InputEntry.CODEC.codec()).fieldOf("input").forGetter(GearingUnitRecipe::input),
                        ItemStack.CODEC.fieldOf("output").forGetter(GearingUnitRecipe::output))
                        .apply(instance, (list, out) -> {
                            NonNullList<InputEntry> inputs = NonNullList.of(InputEntry.EMPTY, list.toArray(new InputEntry[0]));
                            return new GearingUnitRecipe(inputs, out);
                        }));

        public static final StreamCodec<RegistryFriendlyByteBuf, GearingUnitRecipe> STREAM_CODEC =
                StreamCodec.of((buf, recipe) -> {
                    buf.writeVarInt(recipe.input().size());
                    for (InputEntry entry : recipe.input()) {
                        InputEntry.STREAM_CODEC.encode(buf, entry);
                    }
                    ItemStack.STREAM_CODEC.encode(buf, recipe.output());
                }, buf -> {
                    int size = buf.readVarInt();
                    NonNullList<InputEntry> nn = NonNullList.create();
                    for (int i = 0; i < size; i++) {
                        nn.add(InputEntry.STREAM_CODEC.decode(buf));
                    }
                    ItemStack out = ItemStack.STREAM_CODEC.decode(buf);
                    return new GearingUnitRecipe(nn, out);
                });

        @Override
        public MapCodec<GearingUnitRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, GearingUnitRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

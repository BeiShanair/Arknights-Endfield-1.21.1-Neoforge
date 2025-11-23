package com.besson.endfield.recipe.custom;

import com.besson.endfield.recipe.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public record CrafterRecipe(Map<Item, Integer> required, ItemStack output) implements Recipe<CrafterRecipeInput> {

    @Override
    public boolean matches(CrafterRecipeInput input, Level level) {
        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            int count = 0;
            for (int i = 0; i < input.size(); i++) {
                ItemStack stack = input.getItem(i);
                if (stack.getItem().equals(entry.getKey().asItem())) {
                    count += stack.getCount();
                }
            }
            if (count < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(CrafterRecipeInput input, HolderLookup.Provider registries) {
        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            int need = entry.getValue();
            for (int i = 0; i < input.size(); i++) {
                ItemStack stack = input.getItem(i);
                if (stack.getItem().equals(entry.getKey().asItem())) {
                    int removed = Math.min(stack.getCount(), need);
                    stack.shrink(removed);
                    need -= removed;
                    if (need <= 0) break;
                }
            }
        }
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CRAFTER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CRAFTER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CrafterRecipe> {

        public static final MapCodec<CrafterRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(Codec.unboundedMap(BuiltInRegistries.ITEM.byNameCodec(), Codec.INT)
                                        .fieldOf("input").forGetter(CrafterRecipe::required),
                                ItemStack.CODEC.fieldOf("output").forGetter(CrafterRecipe::output))
                        .apply(instance, CrafterRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CrafterRecipe> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.map(HashMap::new, ByteBufCodecs.registry(Registries.ITEM), ByteBufCodecs.VAR_INT),
                CrafterRecipe::required, ItemStack.STREAM_CODEC, CrafterRecipe::output, CrafterRecipe::new);
        @Override
        public MapCodec<CrafterRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrafterRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}

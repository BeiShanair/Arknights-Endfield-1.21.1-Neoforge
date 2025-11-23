package com.besson.endfield.recipe.custom;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CrafterRecipeInput(Container container) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return container.getItem(index);
    }

    @Override
    public int size() {
        return container.getContainerSize();
    }
}

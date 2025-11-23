package com.besson.endfield.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;

public record InputEntry(Ingredient ingredient, int count) {
    public static final InputEntry EMPTY = new InputEntry(Ingredient.EMPTY, 0);

    public static final MapCodec<InputEntry> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(InputEntry::ingredient),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(InputEntry::count)
            ).apply(instance, InputEntry::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, InputEntry> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, InputEntry::ingredient,
                    ByteBufCodecs.VAR_INT, InputEntry::count,
                    InputEntry::new
            );
}

package com.besson.endfield.power;


import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class PowerState extends SavedData {
    public int storedEnergy = 0;

    public PowerState() {}

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("StoredEnergy", storedEnergy);
        return tag;
    }

    public static PowerState fromNbt(CompoundTag nbt, HolderLookup.Provider registries) {
        PowerState state = new PowerState();
        state.storedEnergy = nbt.getInt("StoredEnergy");
        return state;
    }
}

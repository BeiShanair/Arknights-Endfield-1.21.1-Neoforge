package com.besson.endfield.util.power;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;

public class NodeState extends SavedData {
    public Set<NodeEntry> nodeEntries = new HashSet<>();
    
    public NodeState() {}

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        ListTag list = new ListTag();
        for (NodeEntry entry : nodeEntries) {
            list.add(entry.writeNbt());
        }
        compoundTag.put("nodes", list);
        return compoundTag;
    }
    
    public static NodeState fromNbt(CompoundTag nbt, HolderLookup.Provider provider) {
        NodeState state = new NodeState();
        ListTag list = nbt.getList("nodes", 10);
        for (int i = 0; i < list.size(); i++) {
            state.nodeEntries.add(NodeEntry.fromNbt(list.getCompound(i)));
        }
        return state;
    }
}


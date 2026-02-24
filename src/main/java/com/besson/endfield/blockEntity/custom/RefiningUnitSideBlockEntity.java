package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.blockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RefiningUnitSideBlockEntity extends BaseIOSideBlockEntity {
    public RefiningUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REFINING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable RefiningUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = level.getBlockEntity(parentPos);
        if (entity instanceof RefiningUnitBlockEntity entity1) {
            return entity1;
        }
        return null;
    }
}

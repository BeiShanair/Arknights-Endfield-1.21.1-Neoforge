package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.blockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GrindingUnitSideBlockEntity extends BaseIOSideBlockEntity {
    public GrindingUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GRINDING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable GrindingUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = this.level.getBlockEntity(parentPos);
        if (entity instanceof GrindingUnitBlockEntity parent) {
            return parent;
        }
        return null;
    }
}

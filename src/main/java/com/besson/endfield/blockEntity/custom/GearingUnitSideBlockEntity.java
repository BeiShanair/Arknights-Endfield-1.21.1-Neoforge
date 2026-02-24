package com.besson.endfield.blockEntity.custom;

import com.besson.endfield.blockEntity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GearingUnitSideBlockEntity extends BaseIOSideBlockEntity {
    public GearingUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEARING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable GearingUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = this.level.getBlockEntity(parentPos);
        if (entity instanceof GearingUnitBlockEntity parent) {
            return parent;
        }
        return null;
    }
}

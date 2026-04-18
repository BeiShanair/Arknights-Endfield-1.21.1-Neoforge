package com.besson.endfield.blockEntity.custom.production1;

import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.BaseIOSideBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FittingUnitSideBlockEntity extends BaseIOSideBlockEntity {
    public FittingUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FITTING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable FittingUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = this.level.getBlockEntity(parentPos);
        if (entity instanceof FittingUnitBlockEntity parent) {
            return parent;
        }
        return null;
    }
}

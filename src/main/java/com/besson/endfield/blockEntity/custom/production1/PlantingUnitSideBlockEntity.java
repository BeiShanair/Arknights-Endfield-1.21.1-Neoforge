package com.besson.endfield.blockEntity.custom.production1;

import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.BaseIOSideBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PlantingUnitSideBlockEntity extends BaseIOSideBlockEntity {
    public PlantingUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLANTING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable PlantingUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = this.level.getBlockEntity(parentPos);
        if (entity instanceof PlantingUnitBlockEntity parent) {
            return parent;
        }
        return null;
    }
}

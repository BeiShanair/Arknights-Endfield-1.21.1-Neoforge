package com.besson.endfield.blockEntity.custom.production1;

import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.BaseIOSideBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SeedPickingUnitSideBlockEntity extends BaseIOSideBlockEntity {
    public SeedPickingUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SEED_PICKING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable SeedPickingUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = this.level.getBlockEntity(parentPos);
        if (entity instanceof SeedPickingUnitBlockEntity parent) {
            return parent;
        }
        return null;
    }
}

package com.besson.endfield.blockEntity.custom.production1;

import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.BaseIOSideBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ShreddingUnitSideBlockEntity extends BaseIOSideBlockEntity {
    public ShreddingUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHREDDING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable ShreddingUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = level.getBlockEntity(parentPos);
        if (entity instanceof ShreddingUnitBlockEntity entity1) {
            return entity1;
        }
        return null;
    }
}

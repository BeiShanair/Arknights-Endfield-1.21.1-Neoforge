package com.besson.endfield.blockEntity.custom.production2;

import com.besson.endfield.blockEntity.ModBlockEntities;
import com.besson.endfield.blockEntity.custom.BaseIOSideBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PackagingUnitSideBlockEntity extends BaseIOSideBlockEntity {
    public PackagingUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PACKAGING_UNIT_SIDE.get(), pos, state);
    }

    public @Nullable PackagingUnitBlockEntity getParentBlock() {
        if (parentPos == null || level == null) return null;
        BlockEntity entity = level.getBlockEntity(parentPos);
        if (entity instanceof PackagingUnitBlockEntity entity1) {
            return entity1;
        }
        return null;
    }
}

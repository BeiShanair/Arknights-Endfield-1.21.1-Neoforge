package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.FittingUnitBlockEntity;
import com.besson.endfield.model.block.FittingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FittingUnitRenderer extends GeoBlockRenderer<FittingUnitBlockEntity> {
    public FittingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new FittingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(FittingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(FittingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.GrindingUnitBlockEntity;
import com.besson.endfield.model.block.GrindingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GrindingUnitRenderer extends GeoBlockRenderer<GrindingUnitBlockEntity> {
    public GrindingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new GrindingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(GrindingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(GrindingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

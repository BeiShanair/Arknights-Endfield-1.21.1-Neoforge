package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.SeedPickingUnitBlockEntity;
import com.besson.endfield.model.block.SeedPickingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SeedPickingUnitRenderer extends GeoBlockRenderer<SeedPickingUnitBlockEntity> {
    public SeedPickingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new SeedPickingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(SeedPickingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(SeedPickingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

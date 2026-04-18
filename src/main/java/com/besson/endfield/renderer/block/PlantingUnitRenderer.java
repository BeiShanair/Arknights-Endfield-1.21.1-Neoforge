package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.production1.PlantingUnitBlockEntity;
import com.besson.endfield.model.block.production1.PlantingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PlantingUnitRenderer extends GeoBlockRenderer<PlantingUnitBlockEntity> {
    public PlantingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new PlantingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(PlantingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(PlantingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.GearingUnitBlockEntity;
import com.besson.endfield.model.block.GearingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GearingUnitRenderer extends GeoBlockRenderer<GearingUnitBlockEntity> {
    public GearingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new GearingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(GearingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(GearingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

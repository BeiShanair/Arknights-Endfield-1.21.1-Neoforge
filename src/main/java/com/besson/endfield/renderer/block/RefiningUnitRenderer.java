package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.RefiningUnitBlockEntity;
import com.besson.endfield.model.block.RefiningUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RefiningUnitRenderer extends GeoBlockRenderer<RefiningUnitBlockEntity> {
    public RefiningUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new RefiningUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(RefiningUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(RefiningUnitBlockEntity pBlockEntity) {
        return true;
    }
}

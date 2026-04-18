package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.production1.ShreddingUnitBlockEntity;
import com.besson.endfield.model.block.production1.ShreddingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ShreddingUnitRenderer extends GeoBlockRenderer<ShreddingUnitBlockEntity> {
    public ShreddingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new ShreddingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(ShreddingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(ShreddingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

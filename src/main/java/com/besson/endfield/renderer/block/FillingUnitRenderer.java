package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.production2.FillingUnitBlockEntity;
import com.besson.endfield.model.block.production2.FillingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FillingUnitRenderer extends GeoBlockRenderer<FillingUnitBlockEntity> {
    public FillingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new FillingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(FillingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(FillingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

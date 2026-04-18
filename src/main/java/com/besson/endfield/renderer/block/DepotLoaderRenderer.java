package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.logicitis.DepotLoaderBlockEntity;
import com.besson.endfield.model.block.logicitis.DepotLoaderModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DepotLoaderRenderer extends GeoBlockRenderer<DepotLoaderBlockEntity> {
    public DepotLoaderRenderer(BlockEntityRendererProvider.Context context) {
        super(new DepotLoaderModel());
    }

    @Override
    public AABB getRenderBoundingBox(DepotLoaderBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 3, 0);
    }
}

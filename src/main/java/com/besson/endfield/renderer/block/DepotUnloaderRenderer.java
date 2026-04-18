package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.logicitis.DepotUnloaderBlockEntity;
import com.besson.endfield.model.block.logicitis.DepotUnloaderModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DepotUnloaderRenderer extends GeoBlockRenderer<DepotUnloaderBlockEntity> {
    public DepotUnloaderRenderer(BlockEntityRendererProvider.Context context) {
        super(new DepotUnloaderModel());
    }

    @Override
    public AABB getRenderBoundingBox(DepotUnloaderBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 3, 0);
    }
}

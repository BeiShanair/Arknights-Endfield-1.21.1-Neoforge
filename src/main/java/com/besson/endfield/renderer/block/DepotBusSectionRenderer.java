package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.logicitis.DepotBusSectionBlockEntity;
import com.besson.endfield.model.block.logicitis.DepotBusSectionModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DepotBusSectionRenderer extends GeoBlockRenderer<DepotBusSectionBlockEntity> {
    public DepotBusSectionRenderer(BlockEntityRendererProvider.Context context) {
        super(new DepotBusSectionModel());
    }

    @Override
    public AABB getRenderBoundingBox(DepotBusSectionBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 3, 0);
    }
}

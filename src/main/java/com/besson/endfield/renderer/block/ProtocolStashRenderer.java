package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.logicitis.ProtocolStashBlockEntity;
import com.besson.endfield.model.block.logicitis.ProtocolStashModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ProtocolStashRenderer extends GeoBlockRenderer<ProtocolStashBlockEntity> {
    public ProtocolStashRenderer(BlockEntityRendererProvider.Context context) {
        super(new ProtocolStashModel());
    }

    @Override
    public AABB getRenderBoundingBox(ProtocolStashBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }
}

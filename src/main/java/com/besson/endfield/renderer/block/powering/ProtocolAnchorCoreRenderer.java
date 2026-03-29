package com.besson.endfield.renderer.block.powering;

import com.besson.endfield.blockEntity.custom.powering.ProtocolAnchorCoreBlockEntity;
import com.besson.endfield.model.block.powering.ProtocolAnchorCoreModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ProtocolAnchorCoreRenderer extends GeoBlockRenderer<ProtocolAnchorCoreBlockEntity> {
    public ProtocolAnchorCoreRenderer(BlockEntityRendererProvider.Context context) {
        super(new ProtocolAnchorCoreModel());
    }

    @Override
    public AABB getRenderBoundingBox(ProtocolAnchorCoreBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 27, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(ProtocolAnchorCoreBlockEntity pBlockEntity) {
        return true;
    }
}

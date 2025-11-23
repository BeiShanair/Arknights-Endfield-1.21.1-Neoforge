package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.MouldingUnitBlockEntity;
import com.besson.endfield.model.block.MouldingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MouldingUnitRenderer extends GeoBlockRenderer<MouldingUnitBlockEntity> {
    public MouldingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new MouldingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(MouldingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(MouldingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

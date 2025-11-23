package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.ElectricMiningRigBlockEntity;
import com.besson.endfield.model.block.ElectricMiningRigModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ElectricMiningRigRenderer extends GeoBlockRenderer<ElectricMiningRigBlockEntity> {
    public ElectricMiningRigRenderer(BlockEntityRendererProvider.Context context) {
        super(new ElectricMiningRigModel());
    }

    @Override
    public AABB getRenderBoundingBox(ElectricMiningRigBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 3, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(ElectricMiningRigBlockEntity pBlockEntity) {
        return true;
    }
}

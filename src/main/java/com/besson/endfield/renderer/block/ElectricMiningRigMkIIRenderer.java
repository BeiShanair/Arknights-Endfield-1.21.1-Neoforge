package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.ElectricMiningRigMkIIBlockEntity;
import com.besson.endfield.model.block.ElectricMiningRigMkIIModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ElectricMiningRigMkIIRenderer extends GeoBlockRenderer<ElectricMiningRigMkIIBlockEntity> {
    public ElectricMiningRigMkIIRenderer(BlockEntityRendererProvider.Context context) {
        super(new ElectricMiningRigMkIIModel());
    }

    @Override
    public AABB getRenderBoundingBox(ElectricMiningRigMkIIBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 3, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(ElectricMiningRigMkIIBlockEntity pBlockEntity) {
        return true;
    }
}

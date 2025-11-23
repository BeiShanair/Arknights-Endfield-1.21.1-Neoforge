package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.ThermalBankBlockEntity;
import com.besson.endfield.model.block.ThermalBankModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ThermalBankRenderer extends GeoBlockRenderer<ThermalBankBlockEntity> {
    public ThermalBankRenderer(BlockEntityRendererProvider.Context context) {
        super(new ThermalBankModel());
    }

    @Override
    public AABB getRenderBoundingBox(ThermalBankBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(ThermalBankBlockEntity pBlockEntity) {
        return true;
    }
}

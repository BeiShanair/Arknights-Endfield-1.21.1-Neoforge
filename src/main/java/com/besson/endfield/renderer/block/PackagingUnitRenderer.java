package com.besson.endfield.renderer.block;

import com.besson.endfield.blockEntity.custom.PackagingUnitBlockEntity;
import com.besson.endfield.model.block.PackagingUnitModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PackagingUnitRenderer extends GeoBlockRenderer<PackagingUnitBlockEntity> {
    public PackagingUnitRenderer(BlockEntityRendererProvider.Context context) {
        super(new PackagingUnitModel());
    }

    @Override
    public AABB getRenderBoundingBox(PackagingUnitBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(0, 4, 0);
    }

    @Override
    public boolean shouldRenderOffScreen(PackagingUnitBlockEntity pBlockEntity) {
        return true;
    }
}

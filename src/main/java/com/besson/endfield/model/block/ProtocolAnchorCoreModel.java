package com.besson.endfield.model.block;

import com.besson.endfield.ArknightsEndField;
import com.besson.endfield.blockEntity.custom.ProtocolAnchorCoreBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ProtocolAnchorCoreModel extends GeoModel<ProtocolAnchorCoreBlockEntity> {
    @Override
    public ResourceLocation getModelResource(ProtocolAnchorCoreBlockEntity protocolAnchorCoreBlockEntity) {
        return ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "geo/protocol_anchor_core.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ProtocolAnchorCoreBlockEntity protocolAnchorCoreBlockEntity) {
        return ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "textures/block/protocol_anchor_core.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ProtocolAnchorCoreBlockEntity protocolAnchorCoreBlockEntity) {
        return ResourceLocation.fromNamespaceAndPath(ArknightsEndField.MOD_ID, "animations/protocol_anchor_core.animation.json");
    }
}

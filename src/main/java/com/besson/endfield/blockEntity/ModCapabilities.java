package com.besson.endfield.blockEntity;

import com.besson.endfield.block.ModBlocks;
import com.besson.endfield.block.custom.*;
import com.besson.endfield.blockEntity.custom.*;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof ElectricMiningRigBlockEntity be) {
                return be.getItemStackHandler();
            }
            return null;
        }, ModBlocks.ELECTRIC_MINING_RIG.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
                    if (blockEntity instanceof ElectricMiningRigMkIIBlockEntity be) {
                        return be.getItemStackHandler();
                    }
                    return null;
                }, ModBlocks.ELECTRIC_MINING_RIG_MK_II.get()
        );
        event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, pos, state, blockEntity, context) -> {
                    if (blockEntity instanceof FEConverterBlockEntity be) {
                        return be.getEnergyStorage();
                    }
                    return null;
                }, ModBlocks.FE_CONVERTER_BLOCK.get()
        );

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof FillingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(FillingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.FILLING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof FillingUnitSideBlockEntity be) {
                FillingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(FillingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.FILLING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof FittingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(FittingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.FITTING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof FittingUnitSideBlockEntity be) {
                FittingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(FittingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.FITTING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof GearingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(GearingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.GEARING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof GearingUnitSideBlockEntity be) {
                GearingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(GearingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.GEARING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof GrindingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(GrindingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.GRINDING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof GrindingUnitSideBlockEntity be) {
                GrindingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(GrindingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.GRINDING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof MouldingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(MouldingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.MOULDING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof MouldingUnitSideBlockEntity be) {
                MouldingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(MouldingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.MOULDING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof PackagingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(PackagingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.PACKAGING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof PackagingUnitSideBlockEntity be) {
                PackagingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(PackagingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.PACKAGING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof PlantingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(PlantingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.PLANTING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof PlantingUnitSideBlockEntity be) {
                PlantingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(PlantingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.PLANTING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof PortableOriginiumRigBlockEntity be) {
                return be.getItemStackHandler();
            }
            return null;
        }, ModBlocks.PORTABLE_ORIGINIUM_RIG.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof ProtocolAnchorCoreBlockEntity be) {
                return be.getItemStackHandler();
            }
            return null;
        }, ModBlocks.PROTOCOL_ANCHOR_CORE.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof ProtocolAnchorCorePortBlockEntity be) {
                ProtocolAnchorCoreBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = be.getBlockState().getValue(ProtocolAnchorCorePortBlock.FACING);
                    if (context == facing) {
                        return be.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return be.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.PROTOCOL_ANCHOR_CORE_PORT.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof RefiningUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(RefiningUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.REFINING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof RefiningUnitSideBlockEntity be) {
                RefiningUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(RefiningUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.REFINING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof SeedPickingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(SeedPickingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.SEED_PICKING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof SeedPickingUnitSideBlockEntity be) {
                SeedPickingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(SeedPickingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.SEED_PICKING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof ShreddingUnitBlockEntity be) {
                if (context == null) {
                    return be.getItemStackHandler();
                }
                Direction facing = blockEntity.getBlockState().getValue(ShreddingUnitBlock.FACING);
                if (context == facing) {
                    return be.getInputHandler();
                } else if (context == facing.getOpposite()) {
                    return be.getOutputHandler();
                }
            }
            return null;
        }, ModBlocks.SHREDDING_UNIT.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof ShreddingUnitSideBlockEntity be) {
                ShreddingUnitBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                    Direction facing = parent.getBlockState().getValue(ShreddingUnitBlock.FACING);
                    if (context == facing) {
                        return parent.getInputHandler();
                    } else if (context == facing.getOpposite()) {
                        return parent.getOutputHandler();
                    }
                }
            }
            return null;
        }, ModBlocks.SHREDDING_UNIT_SIDE.get());

        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof ThermalBankBlockEntity be) {
                return be.getItemStackHandler();
            }
            return null;
        }, ModBlocks.THERMAL_BANK.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof ThermalBankSideBlockEntity be) {
                ThermalBankBlockEntity parent = be.getParentBlock();
                if (parent != null) {
                    if (context == null) {
                        return parent.getItemStackHandler();
                    }
                }
            }
            return null;
         }, ModBlocks.THERMAL_BANK_SIDE.get()
        );
    }
}

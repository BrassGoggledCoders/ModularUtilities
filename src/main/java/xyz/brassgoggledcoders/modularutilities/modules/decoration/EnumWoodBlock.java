package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import net.minecraft.util.IStringSerializable;

public enum EnumWoodBlock implements IStringSerializable {
    OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, BIG_OAK;

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}

package xyz.brassgoggledcoders.modularutilities.modules.destruction;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.block.Block;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(ModularUtilities.MODID)
public class DestructionModule extends ModuleBase {
    public static Block splitter_tnt;

    @Override
    public String getName() {
        return "Destruction";
    }

    @Override
    public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
        getBlockRegistry().register(splitter_tnt = new BlockSplitterTNT("splitter_tnt"));
    }
}

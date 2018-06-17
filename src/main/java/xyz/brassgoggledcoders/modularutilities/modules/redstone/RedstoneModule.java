package xyz.brassgoggledcoders.modularutilities.modules.redstone;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(ModularUtilities.MODID)
public class RedstoneModule extends ModuleBase {

    public static Block fused_quartz, redstone_sand;

    @Override
    public String getName() {
        return "Redstone";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        fused_quartz = new BlockFusedQuartz();
        this.getBlockRegistry().register(fused_quartz);
        redstone_sand = new BlockRedstoneSand();
        this.getBlockRegistry().register(redstone_sand);
        /*
		 * TODO:
		 * - Resistor: opposite of repeater, changing settings changes resistance rather than delay
		 * - Redstone cable that's waterproof and freestanding
		 * - Second colour of redstone. Wire only.
		 * - Very basic Logic Gates
		 * - Variable strength emmitter
		 */
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        GameRegistry.addSmelting(Blocks.QUARTZ_BLOCK, new ItemStack(fused_quartz), 0.2F);
    }
}

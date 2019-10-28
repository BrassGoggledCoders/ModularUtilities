package xyz.brassgoggledcoders.modularutilities.modules.construction;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(ModularUtilities.MODID)
public class ConstructionModule extends ModuleBase {
    public static Block blast_glass, filler_fluid_block, concrete_fluid_block, concrete;
    public static Fluid filler_fluid, concrete_fluid;

    @Override
    public String getName() {
        return "Construction";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        blast_glass = new BlockBlastGlass().setHardness(25F).setResistance(500F);
        getBlockRegistry().register(blast_glass);

        filler_fluid = new Fluid("dirt", new ResourceLocation(ModularUtilities.MODID, "blocks/filler_fluid"),
                new ResourceLocation(ModularUtilities.MODID, "blocks/filler_fluid_flow")).setUnlocalizedName("dirt");
        FluidRegistry.registerFluid(filler_fluid);
        FluidRegistry.addBucketForFluid(filler_fluid);

        filler_fluid_block = new BlockFillerFluid(Material.WATER, "filler_fluid", filler_fluid);
        getBlockRegistry().register(filler_fluid_block);

        concrete_fluid = new Fluid("concrete", new ResourceLocation(ModularUtilities.MODID, "blocks/concrete_fluid"),
                new ResourceLocation(ModularUtilities.MODID, "blocks/concrete_fluid_flow")).setDensity(2000)
                        .setViscosity(2000).setUnlocalizedName("concrete");
        FluidRegistry.registerFluid(concrete_fluid);
        FluidRegistry.addBucketForFluid(concrete_fluid);

        concrete_fluid_block = new BlockConcreteFluid(new MaterialConcrete(), "concrete_fluid", concrete_fluid);
        getBlockRegistry().register(concrete_fluid_block);

        concrete = new BlockBase(Material.ROCK, "concrete").setHardness(3).setResistance(18);
        getBlockRegistry().register(concrete);

        /*
         * TODO: - Quickdry Concrete - Rebar: Makes solid concrete harder - 'polished'
         * concrete - Paintable concrete - Tar? - Laser level - Scaffolding - Blast
         * resistant door, pushable blast blocks.
         */
    }

    public class MaterialConcrete extends MaterialLiquid {

        public MaterialConcrete() {
            super(MapColor.CLAY);
        }

        @Override
        public boolean blocksMovement() {
            return true;
        }
    }
}

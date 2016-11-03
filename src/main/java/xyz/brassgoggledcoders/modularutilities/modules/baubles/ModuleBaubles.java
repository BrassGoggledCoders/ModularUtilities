package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModDependency;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

import java.util.List;

@Module(ModularUtilities.MODID)
public class ModuleBaubles extends ModuleBase {

	public static Item fire_charm, deflection_belt, bloodbound_ring;

	@Override
	public String getName() {
		return "Baubles";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		fire_charm = new ItemFireCharm();
		this.getItemRegistry().register(fire_charm);
		deflection_belt = new ItemDeflectionBelt();
		this.getItemRegistry().register(deflection_belt);
		bloodbound_ring = new ItemBloodboundRing();
		this.getItemRegistry().register(bloodbound_ring);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		GameRegistry.addRecipe(new ItemStack(fire_charm), "SSS", "MDM", "OOO", 'S', Items.STRING, 'M',
				Blocks.field_189877_df, 'D', Items.DIAMOND, 'O', Blocks.OBSIDIAN);
		GameRegistry.addRecipe(new ItemStack(deflection_belt), "LLL", "LDL", "LLL", 'L', Items.LEATHER, 'D',
				Items.DRAGON_BREATH);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bloodbound_ring), "IBI", "I I", "III", 'I',
				"ingotIron", 'B', Items.BLAZE_POWDER));
	}

	@Override
	public List<IDependency> getDependencies(List<IDependency> dependencies) {
		dependencies.add(new ModDependency("Baubles"));
		return dependencies;
	}

}

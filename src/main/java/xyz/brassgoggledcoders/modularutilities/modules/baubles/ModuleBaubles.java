package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.boilerplate.module.dependencies.IDependency;
import xyz.brassgoggledcoders.boilerplate.module.dependencies.ModDependency;

// @Module(mod = ModularUtilities.MODID)
public class ModuleBaubles extends ModuleBase {

	public static Item fire_charm;

	@Override
	public String getName() {
		return "Baubles";
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		fire_charm = new ItemFireCharm();
		this.getItemRegistry().registerItem(fire_charm);
	}

	@Override
	public List<IDependency> getDependencies(List<IDependency> dependencies) {
		dependencies.add(new ModDependency("Baubles"));
		return dependencies;
	}

}

package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModDependency;
import com.teamacronymcoders.base.registry.ItemRegistry;
import com.teamacronymcoders.base.registry.config.ConfigRegistry;
import net.minecraft.item.Item;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

import java.util.List;

@Module(ModularUtilities.MODID)
public class ModuleBaubles extends ModuleBase {

	public static Item fireCharm, deflectionBelt, bloodboundRing;

	@Override
	public String getName() {
		return "Baubles";
	}

	public void registerItems(ConfigRegistry configRegistry, ItemRegistry itemRegistry) {
		itemRegistry.register(bloodboundRing = new ItemBloodboundRing());
		itemRegistry.register(fireCharm = new ItemFireCharm());
		itemRegistry.register(deflectionBelt = new ItemDeflectionBelt());
	}

	@Override
	public List<IDependency> getDependencies(List<IDependency> dependencies) {
		dependencies.add(new ModDependency("Baubles"));
		return dependencies;
	}

}

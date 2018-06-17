package xyz.brassgoggledcoders.modularutilities.modules.baubles;

import java.util.List;

import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.modulesystem.dependencies.IDependency;
import com.teamacronymcoders.base.modulesystem.dependencies.ModDependency;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;

import net.minecraft.item.Item;

// @Module(ModularUtilities.MODID)
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
        dependencies.add(new ModDependency("baubles"));
        return dependencies;
    }

}

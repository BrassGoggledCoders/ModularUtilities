package xyz.brassgoggledcoders.modularutilities.modules.villages;

import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.util.VillagerUtils;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.GameData;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

// @Module(mod = ModularUtilities.MODID)
public class VillagesModule extends ModuleBase {

    @Override
    public String getName() {
        return "Villages";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        VillagerProfession explorer = new VillagerProfession("modularutilities:explorer",
                "modularutilities:textures/entity/explorer.png",
                "minecraft:textures/entity/zombie_villager/zombie_villager.png");
        VillagerCareer explorer_career = new VillagerCareer(explorer, "modularutilities:explorer");
        VillagerUtils.addSellTrade(explorer_career, new ItemStack(Blocks.SAND, 6, 1), 2, 4);
        VillagerUtils.addSellTrade(explorer_career, new ItemStack(Blocks.DIRT, 3, 1), 1, 2);
        VillagerUtils.addSellTrade(explorer_career, new ItemStack(Blocks.STONEBRICK, 3, 1), 1, 2);
        VillagerUtils.addSellTrade(explorer_career, new ItemStack(Blocks.STONEBRICK, 3, 2), 1, 2);
        VillagerUtils.addSellTrade(explorer_career, new ItemStack(Blocks.STONEBRICK, 3, 3), 1, 2);
        VillagerUtils.addSellTrade(2, explorer_career, new ItemStack(Blocks.PACKED_ICE, 2), 3, 6);
        VillagerUtils.addSellTrade(2, explorer_career, new ItemStack(Blocks.DIRT, 1, 2), 1, 2);
        VillagerUtils.addSellTrade(2, explorer_career, new ItemStack(Blocks.PACKED_ICE, 2), 3, 6);
        VillagerUtils.addSellTrade(2, explorer_career, new ItemStack(Blocks.DIRT, 1, 2), 1, 2);
        VillagerUtils.addSellTrade(3, explorer_career, new ItemStack(Blocks.MYCELIUM), 3, 4);

        GameData.getWrapper(VillagerProfession.class).register(230,
                new ResourceLocation(ModularUtilities.MODID, "explorer"), explorer);
    }

}

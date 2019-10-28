package xyz.brassgoggledcoders.modularutilities.modules.miscellaneous;

import java.util.Arrays;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(ModularUtilities.MODID)
public class MiscellaneousModule extends ModuleBase {
    public static BlockBase feathers, magmagold;
    public static Item goldpetal;

    @Override
    public String getName() {
        return "Miscellaneous";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        feathers = new BlockFeathers();
        getBlockRegistry().register(feathers);

        goldpetal = new ItemGoldPetal("goldpetal");
        getItemRegistry().register(goldpetal);

        magmagold = new BlockMagmagold(Material.ROCK, "magmagold");
        getBlockRegistry().register(magmagold);

        MinecraftForge.EVENT_BUS.register(this);
        GameRegistry.registerWorldGenerator(new WorldGeneratorModularUtils(), 2);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        OreDictionary.registerOre("wool", feathers);
        OreDictionary.registerOre("wool", Blocks.WOOL);

        Blocks.DRAGON_EGG.setCreativeTab(CreativeTabs.DECORATIONS);
        Blocks.FARMLAND.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @SubscribeEvent
    public void onItemExpire(ItemExpireEvent event) {
        ItemStack[] rareStuff = new ItemStack[] { new ItemStack(Items.ELYTRA) };
        if(event.getEntityItem().getItem().getRarity() == EnumRarity.EPIC
                || Arrays.asList(rareStuff).contains(event.getEntityItem().getItem())) {
            event.setExtraLife(600);
            event.setCanceled(true);
        }
    }
}

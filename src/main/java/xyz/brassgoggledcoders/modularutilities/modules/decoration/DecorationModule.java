package xyz.brassgoggledcoders.modularutilities.modules.decoration;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.modulesystem.Module;
import com.teamacronymcoders.base.modulesystem.ModuleBase;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.base.registrysystem.config.ConfigRegistry;
import com.teamacronymcoders.base.util.ItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(ModularUtilities.MODID)
public class DecorationModule extends ModuleBase {

    public static Block turf, leaf_cover, leaf_cover_opaque, stone_decor, smooth_glowstone, hedge, hedge_opaque,
            soul_glass, elder_sea_lantern;

    @Override
    public String getName() {
        return "Decoration";
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void registerBlocks(ConfigRegistry configRegistry, BlockRegistry blockRegistry) {
        blockRegistry.register(turf = new BlockTurf());
        blockRegistry.register(leaf_cover = new BlockLeafCover("leaf_cover", false));
        blockRegistry.register(leaf_cover_opaque = new BlockLeafCover("leaf_cover_opaque", true));
        blockRegistry.register(hedge = new BlockHedge("hedge", false));
        blockRegistry.register(hedge_opaque = new BlockHedge("hedge_opaque", true));
        blockRegistry.register(stone_decor = new BlockStoneDecor());
        blockRegistry
                .register(elder_sea_lantern = new BlockBase(Material.GLASS, "elder_sea_lantern").setLightLevel(15));
        blockRegistry.register(smooth_glowstone = new BlockBase(Material.GLASS, "smooth_glowstone").setLightLevel(1F));
        blockRegistry.register(soul_glass = new BlockSoulGlass(Material.GLASS, "soul_glass"));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        GameRegistry.addSmelting(Blocks.GLOWSTONE, new ItemStack(smooth_glowstone), 0);
        GameRegistry.addSmelting(Blocks.SOUL_SAND, new ItemStack(soul_glass), 0.3F);
        GameRegistry.addSmelting(new ItemStack(Blocks.BRICK_BLOCK), new ItemStack(stone_decor, 1, 5), 0.05F);
    }

    @Override
    public String getClientProxyPath() {
        return "xyz.brassgoggledcoders.modularutilities.modules.decoration.proxy.DecorationClient";
    }

    @SubscribeEvent
    public void onBlockRightClicked(RightClickBlock event) {
        if(event.getWorld().isRemote) {
            return;
        }

        if(ItemStackUtils.doItemsMatch(event.getEntityPlayer().getHeldItemMainhand(), Items.SHEARS)) {
            Block bl = event.getWorld().getBlockState(event.getPos()).getBlock();
            InventoryPlayer inv = event.getEntityPlayer().inventory;
            int meta = 0;
            boolean flag = false;

            if(bl == Blocks.GRASS) {
                Biome b = event.getWorld().getBiome(event.getPos());

                if(BiomeDictionary.hasType(b, BiomeDictionary.Type.SANDY)) {
                    meta = 1;
                }
                else if(BiomeDictionary.hasType(b, BiomeDictionary.Type.COLD)) {
                    meta = 2;
                }
                else if(BiomeDictionary.hasType(b, BiomeDictionary.Type.JUNGLE)) {
                    meta = 3;
                }
                else if(BiomeDictionary.hasType(b, BiomeDictionary.Type.SWAMP)) {
                    meta = 4;
                }

                flag = true;
            }
            else if(bl == Blocks.DIRT
                    && bl.getMetaFromState(event.getWorld().getBlockState(event.getPos())) == BlockDirt.DirtType.PODZOL
                            .getMetadata()) {
                meta = 5;
                flag = true;
            }
            else if(bl == Blocks.MYCELIUM) {
                meta = 6;
                flag = true;
            }

            if(flag && inv.addItemStackToInventory(new ItemStack(turf, 1, meta))) {
                event.getWorld().setBlockState(event.getPos(), Blocks.DIRT.getDefaultState());
                event.setUseItem(Result.ALLOW);
            }
        }
    }
}

package net.crazysnailboy.mods.compot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.crazysnailboy.mods.compot.common.config.ModConfiguration;
import net.crazysnailboy.mods.compot.item.crafting.RecipeCombinedPotions;
import net.crazysnailboy.mods.compot.item.crafting.RecipeCombinedPotions2;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.RecipeSorter;


@SuppressWarnings("deprecation")
@Mod(modid = CombinedPotions.MODID, name = CombinedPotions.NAME, version = CombinedPotions.VERSION, updateJSON = CombinedPotions.UPDATEJSON, dependencies = CombinedPotions.DEPENDENCIES)
public class CombinedPotions
{

	public static final String MODID = "compot";
	public static final String NAME = "Combined Potions";
	public static final String VERSION = "${version}";
	public static final String UPDATEJSON = "https://raw.githubusercontent.com/crazysnailboy/CombinedPotions/master/update.json";
	public static final String DEPENDENCIES = "required-after:forge@[14.21.0.2363,);after:potioncore;";

	public static final Logger LOGGER = LogManager.getLogger(MODID);


	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModConfiguration.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (!Loader.isModLoaded("potioncore"))
		{
			ForgeRegistries.RECIPES.register(new RecipeCombinedPotions().setRegistryName("combined_potions"));
			RecipeSorter.register(MODID + ":" + RecipeCombinedPotions.class.getSimpleName().toLowerCase(), RecipeCombinedPotions.class, RecipeSorter.Category.SHAPELESS, "after:*");
		}
		else
		{
			ForgeRegistries.RECIPES.register(new RecipeCombinedPotions2().setRegistryName("combined_potions"));
			RecipeSorter.register(MODID + ":" + RecipeCombinedPotions2.class.getSimpleName().toLowerCase(), RecipeCombinedPotions2.class, RecipeSorter.Category.SHAPELESS, "after:*");
		}
	}

}
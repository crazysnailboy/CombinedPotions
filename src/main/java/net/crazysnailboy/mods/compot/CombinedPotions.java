package net.crazysnailboy.mods.compot;

import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.crazysnailboy.mods.compot.common.config.ModConfiguration;
import net.crazysnailboy.mods.compot.item.crafting.RecipeCombinedPotions;
import net.crazysnailboy.mods.compot.item.crafting.RecipeCombinedPotions2;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.RecipeSorter;

@Mod(modid = CombinedPotions.MODID, name = CombinedPotions.NAME, version = CombinedPotions.VERSION, updateJSON = CombinedPotions.UPDATEJSON, dependencies = "after:potioncore")
public class CombinedPotions
{

	public static final String MODID = "compot";
	public static final String NAME = "Combined Potions";
	public static final String VERSION = "${version}";
	public static final String UPDATEJSON = "https://raw.githubusercontent.com/crazysnailboy/CombinedPotions/master/update.json";

	public static final Logger LOGGER = LogManager.getLogger(MODID);


	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModConfiguration.preInit();
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (!Loader.isModLoaded("potioncore"))
		{
			CraftingManager.register(UUID.randomUUID().toString(), new RecipeCombinedPotions()); // GameRegistry.addRecipe(new RecipeCombinedPotions());
			RecipeSorter.register(MODID + ":" + RecipeCombinedPotions.class.getSimpleName().toLowerCase(), RecipeCombinedPotions.class, RecipeSorter.Category.SHAPELESS, "after:*");
		}
		else
		{
			CraftingManager.register(UUID.randomUUID().toString(), new RecipeCombinedPotions2()); // GameRegistry.addRecipe(new RecipeCombinedPotions2());
			RecipeSorter.register(MODID + ":" + RecipeCombinedPotions2.class.getSimpleName().toLowerCase(), RecipeCombinedPotions2.class, RecipeSorter.Category.SHAPELESS, "after:*");
		}
	}

}
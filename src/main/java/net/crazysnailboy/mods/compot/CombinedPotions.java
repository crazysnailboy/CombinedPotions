package net.crazysnailboy.mods.compot;

import net.crazysnailboy.mods.compot.item.crafting.RecipeCombinedPotions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = CombinedPotions.MODID, name = CombinedPotions.MODNAME, version = CombinedPotions.VERSION, updateJSON = CombinedPotions.UPDATEJSON)
public class CombinedPotions
{

    public static final String MODID = "compot";
	public static final String MODNAME = "Combined Potions";
    public static final String VERSION = "1.0";
	public static final String UPDATEJSON = "https://raw.githubusercontent.com/crazysnailboy/CombinedPotions/master/update.json";


    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		GameRegistry.addRecipe(new RecipeCombinedPotions());
    }

}


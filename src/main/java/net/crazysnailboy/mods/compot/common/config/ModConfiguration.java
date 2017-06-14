package net.crazysnailboy.mods.compot.common.config;

import java.io.File;
import net.crazysnailboy.mods.compot.CombinedPotions;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;


public class ModConfiguration
{

	public static int maxPotionEffects = -1;


	public static void preInit()
	{
		// load the configuration options from a file, if present
		File configFile = new File(Loader.instance().getConfigDir(), CombinedPotions.MODID + ".cfg");
		Configuration config = new Configuration(configFile);
		config.load();

		// set the variables to the configuration values
		maxPotionEffects = config.get(Configuration.CATEGORY_GENERAL, "maxPotionEffects", maxPotionEffects, "Maximum number of potion effects that can be applied to a single item. Use -1 for no limit.").getInt();

		// save the configuration if it's changed
		if (config.hasChanged()) config.save();
	}

}

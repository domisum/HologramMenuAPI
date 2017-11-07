package de.domisum.lib.hologrammenu;

import de.domisum.lib.auxilium.util.java.annotations.API;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class HologramMenuLib
{

	// REFERENCES
	private static HologramMenuLib instance;
	private Plugin plugin;

	private HologramMenuManager hologramMenuManager;


	// INIT
	private HologramMenuLib(Plugin plugin)
	{
		this.plugin = plugin;

		onEnable();
	}

	@API public static void enable(Plugin plugin)
	{
		if(instance != null)
			return;

		instance = new HologramMenuLib(plugin);
	}

	@API public static void disable()
	{
		if(instance == null)
			return;

		getInstance().onDisable();
		instance = null;
	}


	private void onEnable()
	{
		this.hologramMenuManager = new HologramMenuManager();

		new PlayerMovementListener();

		getLogger().info(this.getClass().getSimpleName()+" has been enabled");
	}

	private void onDisable()
	{
		this.hologramMenuManager.terminate();

		getLogger().info(this.getClass().getSimpleName()+" has been disabled");
	}


	// GETTERS
	@API public static HologramMenuLib getInstance()
	{
		if(instance == null)
			throw new IllegalArgumentException(HologramMenuLib.class.getSimpleName()+" has to be initialized before usage");

		return instance;
	}

	@API public static Plugin getPlugin()
	{
		return getInstance().plugin;
	}

	public static HologramMenuManager getHologramMenuManager()
	{
		return getInstance().hologramMenuManager;
	}

	@API public Logger getLogger()
	{
		return getInstance().plugin.getLogger();
	}

}

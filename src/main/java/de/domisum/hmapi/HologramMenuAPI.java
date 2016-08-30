package de.domisum.hmapi;

import de.domisum.auxiliumapi.AuxiliumAPI;
import de.domisum.auxiliumapi.util.java.annotations.APIUsage;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class HologramMenuAPI
{

	// REFERENCES
	private static HologramMenuAPI instance;
	private Plugin plugin;

	private HologramMenuManager hologramMenuManager;


	// -------
	// CONSTRUCTOR
	// -------
	private HologramMenuAPI(Plugin plugin)
	{
		instance = this;
		this.plugin = plugin;

		onEnable();
	}

	@APIUsage
	public static void enable(Plugin plugin)
	{
		if(instance != null)
			return;

		new HologramMenuAPI(plugin);
	}

	@APIUsage
	public static void disable()
	{
		if(instance == null)
			return;

		getInstance().onDisable();
		instance = null;
	}


	private void onEnable()
	{
		AuxiliumAPI.enable(this.plugin);
		this.hologramMenuManager = new HologramMenuManager();

		new PlayerMovementListener();

		getLogger().info(this.getClass().getSimpleName()+" has been enabled");
	}

	private void onDisable()
	{
		this.hologramMenuManager.terminate();

		getLogger().info(this.getClass().getSimpleName()+" has been disabled");
	}


	// -------
	// GETTERS
	// -------
	@APIUsage
	public static HologramMenuAPI getInstance()
	{
		if(instance == null)
			throw new IllegalArgumentException(HologramMenuAPI.class.getSimpleName()+" has to be initialized before usage");

		return instance;
	}

	@APIUsage
	public static Plugin getPlugin()
	{
		return getInstance().plugin;
	}

	public static HologramMenuManager getHologramMenuManager()
	{
		return getInstance().hologramMenuManager;
	}

	@APIUsage
	public Logger getLogger()
	{
		return getInstance().plugin.getLogger();
	}

}

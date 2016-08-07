package de.domisum.hmapi;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import de.domisum.auxiliumapi.AuxiliumAPI;

public class HologramMenuAPI
{

	// REFERENCES
	private static HologramMenuAPI instance;
	private JavaPlugin plugin;

	private HologramMenuManager hologramMenuManager;


	// -------
	// CONSTRUCTOR
	// -------
	protected HologramMenuAPI(JavaPlugin plugin)
	{
		instance = this;
		this.plugin = plugin;

		onEnable();
	}

	public static void initialize(JavaPlugin plugin)
	{
		if(instance != null)
			return;

		new HologramMenuAPI(plugin);
	}

	public static void disable()
	{
		if(instance == null)
			return;

		getInstance().onDisable();
		instance = null;
	}


	protected void onEnable()
	{
		AuxiliumAPI.initialize(this.plugin);
		this.hologramMenuManager = new HologramMenuManager();

		new PlayerMovementListener();

		getLogger().info(this.getClass().getSimpleName() + " has been enabled");
	}

	protected void onDisable()
	{
		this.hologramMenuManager.terminate();

		getLogger().info(this.getClass().getSimpleName() + " has been disabled");
	}


	// -------
	// GETTERS
	// -------
	public static HologramMenuAPI getInstance()
	{
		return instance;
	}

	public static JavaPlugin getPlugin()
	{
		return getInstance().plugin;
	}

	public static HologramMenuManager getHologramMenuManager()
	{
		return getInstance().hologramMenuManager;
	}


	public Logger getLogger()
	{
		return getInstance().plugin.getLogger();
	}

}

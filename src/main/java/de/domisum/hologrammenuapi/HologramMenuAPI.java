package de.domisum.hologrammenuapi;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class HologramMenuAPI
{

	// REFERENCES
	private static HologramMenuAPI instance;
	private JavaPlugin plugin;


	// -------
	// CONSTRUCTOR
	// -------
	public HologramMenuAPI(JavaPlugin plugin)
	{
		instance = this;
		this.plugin = plugin;

		onEnable();
	}

	public void onEnable()
	{
		getLogger().info(this.getClass().getSimpleName() + " has been enabled");
	}

	public void onDisable()
	{
		getLogger().info(this.getClass().getSimpleName() + " has been disabled");
	}


	// -------
	// GETTERS
	// -------
	public static HologramMenuAPI getInstance()
	{
		return instance;
	}

	public Logger getLogger()
	{
		return getInstance().plugin.getLogger();
	}

}

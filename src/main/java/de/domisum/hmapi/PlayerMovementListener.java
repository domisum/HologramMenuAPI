package de.domisum.hmapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class PlayerMovementListener implements Listener
{

	// -------
	// CONSTRUCTOR
	// -------
	public PlayerMovementListener()
	{
		registerListener();
	}

	private void registerListener()
	{
		Plugin plugin = HologramMenuAPI.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	// -------
	// EVENTS
	// -------
	@EventHandler
	public void playerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		if(!HologramMenuAPI.getHologramMenuManager().hasMenu(player))
			return;

		HologramMenuAPI.getHologramMenuManager().playerMove(player, event.getTo());
	}

	@EventHandler
	public void playerClick(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.PHYSICAL)
			return;

		Player player = event.getPlayer();
		if(!HologramMenuAPI.getHologramMenuManager().hasMenu(player))
			return;

		HologramMenuAPI.getHologramMenuManager()
				.playerClick(player, event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK);
	}

}

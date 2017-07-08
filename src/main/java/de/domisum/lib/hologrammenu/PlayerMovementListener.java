package de.domisum.lib.hologrammenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class PlayerMovementListener implements Listener
{

	// INIT
	public PlayerMovementListener()
	{
		registerListener();
	}

	private void registerListener()
	{
		Plugin plugin = HologramMenuLib.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}


	// EVENTS
	@EventHandler public void playerClick(PlayerInteractEvent event)
	{
		if(event.getAction() == Action.PHYSICAL)
			return;

		Player player = event.getPlayer();
		if(!HologramMenuLib.getHologramMenuManager().hasMenu(player))
			return;

		HologramMenuLib.getHologramMenuManager()
				.playerClick(player, event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK);
	}

}

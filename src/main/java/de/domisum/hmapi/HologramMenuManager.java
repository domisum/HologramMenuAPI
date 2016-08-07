package de.domisum.hmapi;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.domisum.auxiliumapi.data.structure.pds.PlayerKeyMap;
import de.domisum.hmapi.menu.HologramMenu;

public class HologramMenuManager
{

	// REFERENCES
	protected Map<Player, HologramMenu> activeMenus = new PlayerKeyMap<>();


	// -------
	// CONSTRUCTOR
	// -------
	public HologramMenuManager()
	{

	}

	public void terminate()
	{
		for(HologramMenu hm : this.activeMenus.values())
			hm.terminate();
	}


	// -------
	// GETTERS
	// -------
	public boolean hasMenu(Player player)
	{
		return this.activeMenus.containsKey(player);
	}


	// -------
	// REGISTRATION
	// -------
	public void register(HologramMenu menu)
	{
		if(this.activeMenus.containsKey(menu.getPlayer()))
			this.activeMenus.get(menu.getPlayer()).terminate();

		this.activeMenus.put(menu.getPlayer(), menu);
	}

	public void unregister(HologramMenu menu)
	{
		if(this.activeMenus.get(menu.getPlayer()) != menu)
			return;

		this.activeMenus.remove(menu.getPlayer());
	}


	// -------
	// PLAYER MOVEMENT
	// -------
	public void playerMove(Player player, Location locationTo)
	{
		HologramMenu menu = this.activeMenus.get(player);
		menu.updateLocation(locationTo);
	}

}

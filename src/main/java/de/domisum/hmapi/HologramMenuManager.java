package de.domisum.hmapi;

import de.domisum.auxiliumapi.data.structure.pds.PlayerKeyMap;
import de.domisum.hmapi.menu.HologramMenu;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;

public class HologramMenuManager
{

	// REFERENCES
	private Map<Player, HologramMenu> activeMenus = new PlayerKeyMap<>();


	// -------
	// CONSTRUCTOR
	// -------
	HologramMenuManager()
	{

	}

	void terminate()
	{
		for(HologramMenu hm : this.activeMenus.values())
			hm.terminate();
	}


	// -------
	// GETTERS
	// -------
	boolean hasMenu(Player player)
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
	void playerMove(Player player, Location locationTo)
	{
		HologramMenu menu = this.activeMenus.get(player);

		menu.updateLocation(locationTo);
	}

}

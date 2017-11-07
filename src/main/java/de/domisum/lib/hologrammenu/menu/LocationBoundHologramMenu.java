package de.domisum.lib.hologrammenu.menu;

import de.domisum.lib.auxilium.util.java.annotations.API;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@API
public class LocationBoundHologramMenu extends HologramMenu
{

	// INIT
	public LocationBoundHologramMenu(Player player, Location location)
	{
		super(player, location);
	}


	// SETTERS
	@API public void setLocation(Location location)
	{
		updateLocation(location);
	}
}

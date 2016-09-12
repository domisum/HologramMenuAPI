package de.domisum.lib.hologrammenu.menu;

import de.domisum.lib.auxilium.util.java.annotations.APIUsage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@APIUsage
public class LocationBoundHologramMenu extends HologramMenu
{

	// -------
	// CONSTRUCTOR
	// -------
	public LocationBoundHologramMenu(Player player, Location location)
	{
		super(player, location);
	}


	// -------
	// SETTERS
	// -------
	@APIUsage
	public void setLocation(Location location)
	{
		updateLocation(location);
	}
}

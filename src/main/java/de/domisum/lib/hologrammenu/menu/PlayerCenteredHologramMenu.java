package de.domisum.lib.hologrammenu.menu;

import de.domisum.lib.auxilium.util.java.annotations.APIUsage;
import de.domisum.lib.auxilium.util.math.MathUtil;
import de.domisum.lib.auxiliumspigot.util.LocationUtil;
import de.domisum.lib.hologrammenu.HologramMenuLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@APIUsage
public abstract class PlayerCenteredHologramMenu extends HologramMenu
{

	// CONSTANTS
	private static final float YAW_TOLERANCE = 50;

	// PROPERTIES
	private double baseDistance = 3;
	private double baseHeight = 1.62;


	// INIT
	@APIUsage public PlayerCenteredHologramMenu(Player player)
	{
		super(player, player.getLocation());
	}

	@Override public void terminate()
	{
		HologramMenuLib.getHologramMenuManager().unregister(this);
		hide();
	}


	// GETTERS
	@Override protected Location getBaseLocation()
	{
		return LocationUtil.moveLocationTowardsYaw(this.location, this.baseDistance).add(0, this.baseHeight, 0);
	}


	// SETTERS
	@APIUsage public void setBaseDistance(double baseDistance)
	{
		this.baseDistance = baseDistance;
	}

	@APIUsage public void setBaseHeight(double baseHeight)
	{
		this.baseHeight = baseHeight;
	}


	// MOVEMENT
	@Override public void updateLocation(Location newPlayerLocation)
	{
		boolean movement = !this.location.toVector().equals(newPlayerLocation.toVector());

		this.location.setX(newPlayerLocation.getX());
		this.location.setY(newPlayerLocation.getY());
		this.location.setZ(newPlayerLocation.getZ());

		float newPlayerYaw = newPlayerLocation.getYaw()%360;
		if(newPlayerYaw < 0)
			newPlayerYaw += 360;
		float oldYaw = this.location.getYaw()%360;
		if(oldYaw < 0)
			oldYaw += 360;

		boolean rotation = !MathUtil.isAngleNearDeg(oldYaw, newPlayerYaw, YAW_TOLERANCE);
		if(rotation)
		{
			double delta = newPlayerYaw-oldYaw;
			float newYaw;

			if(delta < -180)
				newYaw = newPlayerYaw-YAW_TOLERANCE;
			else if((delta < 0) || (delta > 180))
				newYaw = newPlayerYaw+YAW_TOLERANCE;
			else
				newYaw = newPlayerYaw-YAW_TOLERANCE;

			newYaw %= 360;
			if(newYaw < 0)
				newYaw += 360;

			this.location.setYaw(newYaw);
		}

		if(rotation || movement)
			updateComponentLocations();

		updateHover();
	}

}

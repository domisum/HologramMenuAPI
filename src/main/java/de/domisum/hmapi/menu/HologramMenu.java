package de.domisum.hmapi.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.domisum.auxiliumapi.data.container.math.Vector3D;
import de.domisum.auxiliumapi.util.bukkit.LocationUtil;
import de.domisum.auxiliumapi.util.math.MathUtil;
import de.domisum.auxiliumapi.util.math.VectorUtil;
import de.domisum.hmapi.HologramMenuAPI;
import de.domisum.hmapi.component.HologramMenuComponent;

public abstract class HologramMenu
{

	// CONSTANTS
	protected static final float YAW_TOLERANCE = 50;

	// REFERENCES
	// <component, offset>
	// this offset uses a nonstandard coordinate format:
	// x: to the right
	// y: upwards
	// z: towards the player
	protected Map<HologramMenuComponent, Vector3D> components = new HashMap<>();
	protected Player player;

	// PROPERTIES
	protected double baseDistance = 3;
	protected double baseHeight = 1.62;

	// STATUS
	protected Location location;


	// -------
	// CONSTRUCTOR
	// -------
	public HologramMenu(Player player)
	{
		this.player = player;

		this.location = player.getLocation();
	}

	/**
	 * Second part of constructor. Called by superclasses to show the constructed menu to the player
	 */
	protected void done()
	{
		for(HologramMenuComponent hmc : this.components.keySet())
			hmc.initialize(this.player);

		HologramMenuAPI.getHologramMenuManager().register(this);
		// this little offset is to make the method register a change and actually update the holograms
		updateLocation(this.location.clone().add(0.01, 0, 0));
		show();
	}

	public void terminate()
	{
		HologramMenuAPI.getHologramMenuManager().unregister(this);
		hide();
	}


	// -------
	// GETTERS
	// -------
	public Player getPlayer()
	{
		return this.player;
	}

	protected Location getViewLocation()
	{
		return this.location.clone().add(0, 1.62, 0);
	}


	// -------
	// VISIBILITY
	// -------
	protected void show()
	{
		for(HologramMenuComponent hmc : this.components.keySet())
			hmc.show();
	}

	protected void hide()
	{
		for(HologramMenuComponent hmc : this.components.keySet())
			hmc.hide();
	}


	// -------
	// MOVEMENT
	// -------
	public void updateLocation(Location newPlayerLocation)
	{
		boolean movement = !this.location.toVector().equals(newPlayerLocation.toVector());

		this.location.setX(newPlayerLocation.getX());
		this.location.setY(newPlayerLocation.getY());
		this.location.setZ(newPlayerLocation.getZ());

		float newPlayerYaw = newPlayerLocation.getYaw() % 360;
		if(newPlayerYaw < 0)
			newPlayerYaw += 360;
		float oldYaw = this.location.getYaw() % 360;
		if(oldYaw < 0)
			oldYaw += 360;

		boolean rotation = !MathUtil.isAngleNearDeg(oldYaw, newPlayerYaw, YAW_TOLERANCE);
		if(rotation)
		{
			double delta = newPlayerYaw - oldYaw;
			float newYaw = 0;

			if(delta < -180)
				newYaw = newPlayerYaw - YAW_TOLERANCE;
			else if((delta < 0) || (delta > 180))
				newYaw = newPlayerYaw + YAW_TOLERANCE;
			else
				newYaw = newPlayerYaw - YAW_TOLERANCE;

			newYaw %= 360;
			if(newYaw < 0)
				newYaw += 360;

			this.location.setYaw(newYaw);
		}

		if(rotation || movement)
			updateComponentLocations();
	}

	protected void updateComponentLocations()
	{
		Location viewLocation = getViewLocation();
		Location base = LocationUtil.moveLocationTowardsYaw(this.location, this.baseDistance).add(0, this.baseHeight, 0);

		for(Entry<HologramMenuComponent, Vector3D> entry : this.components.entrySet())
		{
			Vector3D offsetMc = convertOffsetToMinecraftCoordinates(entry.getValue());
			Vector3D rotatedOffset = VectorUtil.rotateOnXZPlane(offsetMc, -this.location.getYaw());

			Location componentLoc = base.clone().add(rotatedOffset.x, rotatedOffset.y, rotatedOffset.z);

			entry.getKey().setLocation(new Vector3D(componentLoc));
			entry.getKey().setViewLocation(new Vector3D(viewLocation));
		}
	}


	// -------
	// UTIL
	// -------
	protected static Vector3D convertOffsetToMinecraftCoordinates(Vector3D offset)
	{
		return new Vector3D(-offset.x, offset.y, -offset.z);
	}

}

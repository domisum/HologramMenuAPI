package de.domisum.hmapi.menu;

import de.domisum.auxiliumapi.data.container.math.Vector3D;
import de.domisum.auxiliumapi.util.bukkit.LocationUtil;
import de.domisum.auxiliumapi.util.math.VectorUtil;
import de.domisum.hmapi.HologramMenuAPI;
import de.domisum.hmapi.component.HologramMenuComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class HologramMenu
{

	// REFERENCES
	// <component, offset>
	// this offset uses a nonstandard coordinate format:
	// x: to the right
	// y: upwards
	// z: towards the player
	protected Map<HologramMenuComponent, Vector3D> components = new HashMap<>();
	private Player player;

	// STATUS
	Location location;


	// -------
	// CONSTRUCTOR
	// -------
	public HologramMenu(Player player, Location location)
	{
		this.player = player;
		this.location = location;
	}

	/**
	 * Second part of constructor. Called by superclasses to show the constructed menu to the player
	 */
	protected void done()
	{
		HologramMenuAPI.getHologramMenuManager().register(this);

		// this little offset is to make the method register a change and actually update the holograms
		updateLocation(this.player.getLocation().add(0.1, 0, 0));

		for(HologramMenuComponent hmc : this.components.keySet())
			hmc.initialize(this.player);

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


	protected Location getBaseLocation()
	{
		return this.location;
	}

	private Location getViewLocation()
	{
		return this.player.getEyeLocation();
	}


	// -------
	// VISIBILITY
	// -------
	private void show()
	{
		for(HologramMenuComponent hmc : this.components.keySet())
			hmc.show();
	}

	void hide()
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
		if(!movement)
			return;

		// update the rotation of the menu to face the player
		Location yawLocation = LocationUtil.lookAt(this.player.getLocation(), this.location);
		this.location.setYaw(yawLocation.getYaw());

		updateComponentLocations();
	}

	void updateComponentLocations()
	{
		Location viewLocation = getViewLocation();
		Location base = getBaseLocation();

		for(Entry<HologramMenuComponent, Vector3D> entry : this.components.entrySet())
		{
			Vector3D offsetMc = VectorUtil.convertOffsetToMinecraftCoordinates(entry.getValue());
			Vector3D rotatedOffset = VectorUtil.rotateOnXZPlane(offsetMc, -this.location.getYaw());

			Location componentLoc = base.clone().add(rotatedOffset.x, rotatedOffset.y, rotatedOffset.z);

			entry.getKey().setLocation(new Vector3D(componentLoc));
			entry.getKey().setViewLocation(new Vector3D(viewLocation));
		}
	}

}

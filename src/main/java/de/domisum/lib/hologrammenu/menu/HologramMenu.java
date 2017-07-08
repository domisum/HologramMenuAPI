package de.domisum.lib.hologrammenu.menu;

import de.domisum.lib.auxilium.data.container.math.Vector3D;
import de.domisum.lib.auxilium.util.java.annotations.APIUsage;
import de.domisum.lib.auxilium.util.math.VectorUtil;
import de.domisum.lib.auxiliumspigot.data.container.VectorConverter;
import de.domisum.lib.auxiliumspigot.util.LocationUtil;
import de.domisum.lib.hologrammenu.HologramMenuLib;
import de.domisum.lib.hologrammenu.component.HologramMenuComponent;
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
	protected Location location;

	private HologramMenuComponent hoveringComponent;


	// INIT
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
		HologramMenuLib.getHologramMenuManager().register(this);
		for(HologramMenuComponent hmc : this.components.keySet())
			hmc.initialize(this.player);

		updateLocation(null);
		show();
	}

	public void terminate()
	{
		HologramMenuLib.getHologramMenuManager().unregister(this);
		hide();
	}


	// GETTERS
	public Player getPlayer()
	{
		return this.player;
	}


	protected Location getBaseLocation()
	{
		return this.location.clone();
	}

	private Location getViewLocation()
	{
		return this.player.getEyeLocation();
	}


	// UPDATE
	public void update()
	{
		updateLocation(null);
	}


	// VISIBILITY
	private void show()
	{
		for(HologramMenuComponent hmc : this.components.keySet())
			hmc.show();
	}

	protected void hide()
	{
		for(HologramMenuComponent hmc : this.components.keySet())
			hmc.hide();
	}


	// MOVEMENT
	public void updateLocation(Location newLocation)
	{
		if(newLocation != null)
			this.location = newLocation.clone();

		// update the rotation of the menu to face the player
		Location yawLocation = LocationUtil.lookAt(this.player.getLocation(), this.location);
		this.location.setYaw(yawLocation.getYaw());

		updateComponentLocations();
		updateHover();
	}

	protected void updateComponentLocations()
	{
		Location viewLocationBase = getViewLocation();
		Location base = getBaseLocation();

		for(Entry<HologramMenuComponent, Vector3D> entry : this.components.entrySet())
		{
			float rotationYaw = -base.getYaw();
			Vector3D offsetMc = VectorUtil.convertOffsetToMinecraftCoordinates(entry.getValue());
			Vector3D rotatedOffset = VectorUtil.rotateOnXZPlane(offsetMc, rotationYaw);

			Location componentLoc = base.clone().add(rotatedOffset.x, rotatedOffset.y, rotatedOffset.z);
			Location viewLocation = viewLocationBase.clone().add(rotatedOffset.x, rotatedOffset.y, rotatedOffset.z);

			entry.getKey().setLocation(VectorConverter.toVector3D(componentLoc));
			entry.getKey().setViewLocation(VectorConverter.toVector3D(viewLocation));
		}
	}


	// INTERACTION
	protected void updateHover()
	{
		if(this.hoveringComponent != null && this.hoveringComponent.isPlayerLookingAt())
			return;

		for(HologramMenuComponent hmc : this.components.keySet())
			if(hmc.isPlayerLookingAt())
			{
				if(this.hoveringComponent != null)
					this.hoveringComponent.onDehover();

				this.hoveringComponent = hmc;
				hmc.onHover();
				return;
			}

		if(this.hoveringComponent != null)
		{
			this.hoveringComponent.onDehover();
			this.hoveringComponent = null;
		}
	}

	public void click(boolean left)
	{
		if(!left)
		{
			onRightClick();
			return;
		}

		updateHover();

		if(this.hoveringComponent == null)
			return;

		this.hoveringComponent.onClick();
	}

	@APIUsage protected void onRightClick()
	{
		// to be overridden
	}

}

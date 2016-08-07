package de.domisum.hmapi.component;

import org.bukkit.entity.Player;

import de.domisum.auxiliumapi.data.container.math.Vector3D;
import de.domisum.hologramapi.hologram.Hologram;

public class HologramMenuComponent
{

	// REFERENCES
	protected Hologram hologram;
	protected Player player;

	// STATUS
	protected Vector3D location;
	protected Vector3D viewLocation;


	// -------
	// CONSTRUCTOR
	// -------
	public HologramMenuComponent(Hologram hologram)
	{
		this.hologram = hologram;
	}

	public void initialize(Player player)
	{
		this.player = player;
		this.hologram.setWorld(player.getWorld());
	}


	// -------
	// VISIBILITY
	// -------
	public void show()
	{
		this.hologram.showTo(this.player);
	}

	public void hide()
	{
		this.hologram.hideFrom(this.player);
	}


	// -------
	// MOVEMENT
	// -------
	public void setLocation(Vector3D location)
	{
		this.location = location;
		this.hologram.setLocation(this.location);
	}

	public void setViewLocation(Vector3D viewLocation)
	{
		this.viewLocation = viewLocation;
		this.hologram.setViewLocation(this.viewLocation);
	}

}

package de.domisum.lib.hologrammenu.component;

import de.domisum.lib.auxilium.data.container.math.LineSegment3D;
import de.domisum.lib.auxilium.data.container.math.Vector3D;
import de.domisum.lib.auxilium.util.math.VectorUtil;
import de.domisum.lib.auxiliumspigot.data.container.VectorConverter;
import de.domisum.lib.hologram.hologram.Hologram;
import de.domisum.lib.hologram.hologram.TextHologram;
import org.bukkit.entity.Player;

public class HologramMenuComponent<T extends Hologram>
{

	// REFERENCES
	protected T hologram;
	protected Player player;

	// STATUS
	private Vector3D location;


	// INIT
	public HologramMenuComponent(T hologram)
	{
		this.hologram = hologram;
	}

	public String toString()
	{
		return "hmc["+hologram.getClass().getSimpleName()+"]";
	}

	public void initialize(Player player)
	{
		this.player = player;
		hologram.setWorld(player.getWorld());
	}


	// VISIBILITY
	public void show()
	{
		hologram.showTo(player);
	}

	public void hide()
	{
		hologram.hideFrom(player);
	}


	// MOVEMENT
	public void setLocation(Vector3D location)
	{
		this.location = location;
		hologram.setLocation(this.location);
	}

	public void setViewLocation(Vector3D viewLocation)
	{
		hologram.setViewLocation(viewLocation);
	}


	// INTERACTION
	public void onHover()
	{
		// to be overridden
	}

	public void onDehover()
	{
		// to be overridden
	}

	public void onClick()
	{
		// to be overridden
	}

	public boolean isPlayerLookingAt()
	{
		Vector3D playerEyeLocation = VectorConverter.toVector3D(player.getEyeLocation());
		Vector3D lookDirection = VectorConverter.toVector3D(player.getLocation().getDirection());
		Vector3D playerLookLocation = playerEyeLocation.add(lookDirection.multiply(100));
		LineSegment3D playerLookLineSegment = new LineSegment3D(playerEyeLocation, playerLookLocation);

		if(hologram instanceof TextHologram)
		{
			TextHologram textHologram = (TextHologram) hologram;
			LineSegment3D hologramLineSegment = getTextHologramLineSegment(textHologram);

			return playerLookLineSegment.getDistanceTo(hologramLineSegment) < 0.15;
		}

		return playerLookLineSegment.getDistanceTo(location) < 0.25;
	}


	// UTIL
	private LineSegment3D getTextHologramLineSegment(TextHologram hologram)
	{
		Vector3D offset = new Vector3D(hologram.getWidth()/2, 0, 0);
		offset = VectorUtil.convertOffsetToMinecraftCoordinates(offset);

		/*Location yawLocation = LocationUtil
				.lookAt(this.player.getEyeLocation(), this.location.toLocation(this.player.getWorld()));
		double rotationYaw = -yawLocation.getYaw();*/

		// the text hologram's rotation actually just depends on the player's yaw, since it is always parallel to the screen,
		// so just use the player yaw for the rotation
		double rotationYaw = -player.getLocation().getYaw();
		Vector3D rotatedOffset = VectorUtil.rotateOnXZPlane(offset, rotationYaw);

		return new LineSegment3D(location.add(rotatedOffset.invert()), location.add(rotatedOffset));
	}

}

package de.domisum.lib.hologrammenu.component;


import de.domisum.lib.auxilium.data.container.math.LineSegment3D;
import de.domisum.lib.auxilium.data.container.math.Vector3D;
import de.domisum.lib.auxilium.util.math.VectorUtil;
import de.domisum.lib.hologram.hologram.Hologram;
import de.domisum.lib.hologram.hologram.TextHologram;
import org.bukkit.entity.Player;

public class HologramMenuComponent
{

	// REFERENCES
	protected Hologram hologram;
	protected Player player;

	// STATUS
	private Vector3D location;


	// INIT
	public HologramMenuComponent(Hologram hologram)
	{
		this.hologram = hologram;
	}

	public String toString()
	{
		return "hmc["+this.hologram.getClass().getSimpleName()+"]";
	}

	public void initialize(Player player)
	{
		this.player = player;
		this.hologram.setWorld(player.getWorld());
	}


	// VISIBILITY
	public void show()
	{
		this.hologram.showTo(this.player);
	}

	public void hide()
	{
		this.hologram.hideFrom(this.player);
	}


	// MOVEMENT
	public void setLocation(Vector3D location)
	{
		this.location = location;
		this.hologram.setLocation(this.location);
	}

	public void setViewLocation(Vector3D viewLocation)
	{
		this.hologram.setViewLocation(viewLocation);
	}


	// INTERACTION
	public void onHover()
	{

	}

	public void onDehover()
	{

	}

	public void onClick()
	{

	}

	public boolean isPlayerLookingAt()
	{
		Vector3D playerEyeLocation = new Vector3D(this.player.getEyeLocation());
		Vector3D lookDirection = new Vector3D(this.player.getLocation().getDirection());
		Vector3D playerLookLocation = playerEyeLocation.add(lookDirection.multiply(100));
		LineSegment3D playerLookLineSegment = new LineSegment3D(playerEyeLocation, playerLookLocation);

		if(this.hologram instanceof TextHologram)
		{
			TextHologram textHologram = (TextHologram) this.hologram;
			LineSegment3D hologramLineSegment = getTextHologramLineSegment(textHologram);

			return playerLookLineSegment.getDistanceTo(hologramLineSegment) < 0.15;
		}

		return playerLookLineSegment.getDistanceTo(this.location) < 0.25;
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
		double rotationYaw = -this.player.getLocation().getYaw();
		Vector3D rotatedOffset = VectorUtil.rotateOnXZPlane(offset, rotationYaw);

		return new LineSegment3D(this.location.add(rotatedOffset.invert()), this.location.add(rotatedOffset));
	}

}

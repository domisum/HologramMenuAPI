package de.domisum.hmapi.test;

import de.domisum.auxiliumapi.data.container.math.Vector3D;
import de.domisum.hmapi.component.HologramMenuComponent;
import de.domisum.hmapi.menu.HologramMenu;
import de.domisum.hologramapi.hologram.TextHologram;
import de.domisum.hologramapi.hologram.item.ItemHologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class TestMenu extends HologramMenu
{

	// -------
	// CONSTRUCTOR
	// -------
	public TestMenu(Player player)
	{
		super(player, new Location(player.getWorld(), 174.5, 64.6, 60.5));

		addComponents();

		done();
	}

	private void addComponents()
	{
		HologramMenuComponent hmc;

		hmc = new HologramMenuComponent(new TextHologram("Ayy lmaou"));
		this.components.put(hmc, new Vector3D(0, 0, 0));

		ItemStack is = new ItemStack(Material.SKULL_ITEM);
		SkullMeta sm = (SkullMeta) is.getItemMeta();
		sm.setOwner("ayylmao");
		is.setItemMeta(sm);

		ItemHologram ihg = new ItemHologram(is);
		ihg.setRotation(-30);
		hmc = new HologramMenuComponent(ihg);
		this.components.put(hmc, new Vector3D(-1, 0, 0));

		ihg = new ItemHologram(is);
		ihg.setRotation(30);
		hmc = new HologramMenuComponent(ihg);
		this.components.put(hmc, new Vector3D(1, 0, 0));
	}

}

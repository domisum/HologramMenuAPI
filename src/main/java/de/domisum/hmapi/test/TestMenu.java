package de.domisum.hmapi.test;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.domisum.auxiliumapi.data.container.math.Vector3D;
import de.domisum.hmapi.component.HologramMenuComponent;
import de.domisum.hmapi.menu.HologramMenu;
import de.domisum.hologramapi.hologram.TextHologram;
import de.domisum.hologramapi.hologram.item.ItemHologram;

public class TestMenu extends HologramMenu
{

	// -------
	// CONSTRUCTOR
	// -------
	public TestMenu(Player player)
	{
		super(player);

		addComponents();

		done();
	}

	protected void addComponents()
	{
		HologramMenuComponent hmc = null;

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


		// hmc = new HologramMenuComponent(new TextHologram("Links"));
		// this.components.put(hmc, new Vector3D(-1, 0, 0));
	}

}

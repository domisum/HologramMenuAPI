package de.domisum.lib.hologrammenu;

import de.domisum.lib.auxiliumspigot.data.structure.pds.PlayerKeyMap;
import de.domisum.lib.hologrammenu.menu.HologramMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;

public class HologramMenuManager
{

	// REFERENCES
	private Map<Player, HologramMenu> activeMenus = new PlayerKeyMap<>();

	// STATUS
	private BukkitTask updateTask;


	// INIT
	protected HologramMenuManager()
	{

	}

	protected void terminate()
	{
		stopUpdateTask();

		for(HologramMenu hm : this.activeMenus.values())
			hm.terminate();
	}


	// GETTERS
	protected boolean hasMenu(Player player)
	{
		return this.activeMenus.containsKey(player);
	}


	// REGISTRATION
	public void register(HologramMenu menu)
	{
		if(this.activeMenus.containsKey(menu.getPlayer()))
			this.activeMenus.get(menu.getPlayer()).terminate();

		this.activeMenus.put(menu.getPlayer(), menu);

		startUpdateTask();
	}

	public void unregister(HologramMenu menu)
	{
		if(this.activeMenus.get(menu.getPlayer()) != menu)
			return;

		this.activeMenus.remove(menu.getPlayer());

		if(this.activeMenus.size() == 0)
			stopUpdateTask();
	}


	// UPDATE
	private void startUpdateTask()
	{
		if(this.updateTask != null)
			return;

		this.updateTask = Bukkit.getScheduler().runTaskTimer(HologramMenuLib.getPlugin(), this::update, 1, 1);
	}

	private void stopUpdateTask()
	{
		if(this.updateTask == null)
			return;

		this.updateTask.cancel();
		this.updateTask = null;
	}

	private void update()
	{
		for(HologramMenu hm : this.activeMenus.values())
			hm.update();
	}


	// PLAYER INTERACTION
	protected void playerClick(Player player, boolean left)
	{
		HologramMenu menu = this.activeMenus.get(player);
		menu.click(left);
	}

}

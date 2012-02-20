package me.INemesisI.XcraftSignLift;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener {
	public static XcraftSignLift plugin;

	public EventListener(XcraftSignLift instance) {
		plugin = instance;
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		String[] lines = event.getLines();
		if (lines[1].toLowerCase().contains("[lift up]") || lines[1].toLowerCase().contains("[lift down]")) {
			if (!event.getPlayer().hasPermission("XcraftSignLift.create")) {
				event.getPlayer().sendMessage(plugin.getName() + ChatColor.RED + "Du hast keine Rechte, Lifte zu erstellen!");
				event.setCancelled(true);
				return;
			}
			event.setLine(1, plugin.capitalize(lines[1]));
			event.getPlayer().sendMessage(plugin.getName() + "Lift erstellt");
		} else
			return;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				String[] lines = sign.getLines();
				if ((lines[1].contains("[Lift Up]") || (lines[1].contains("[Lift Down]")))) {
					Player player = event.getPlayer();
					if (event.getPlayer().hasPermission("XcraftSignLift.use")) plugin.liftHandler.handle(block, player);
					else
						player.sendMessage(plugin.getName() + ChatColor.RED + "Du hast keine Rechte, Lifte zu benutzen");
				}
			}
		}
	}
}

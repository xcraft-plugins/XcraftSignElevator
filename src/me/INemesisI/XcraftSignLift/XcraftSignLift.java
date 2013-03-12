package me.INemesisI.XcraftSignLift;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class XcraftSignLift extends JavaPlugin {

	// ClassListeners
	private final EventListener eventlistener = new EventListener(this);
	// ClassListeners
	public LiftHandler liftHandler = new LiftHandler(this);

	public Logger log = Logger.getLogger("Minecraft");

	@Override
	public void onDisable() {
		log.info("[" + this.getDescription().getName() + "] disabled!");
	}

	@Override
	public void onEnable() {

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(eventlistener, this);

		log.info("[" + this.getDescription().getName() + "] v" + this.getDescription().getVersion()
				+ " by INemesisI enabled!");
	}

	public String getCName() {
		return ChatColor.DARK_GRAY + "[" + this.getDescription().getName() + "] " + this.getChatColor();
	}

	public ChatColor getChatColor() {
		return ChatColor.DARK_AQUA;
	}

	public String capitalize(String name) {
		String[] split = name.split(" ");
		String string = "";
		for (int i = 0; i < split.length; i++) {
			int index = 0;
			String c = String.valueOf(split[i].charAt(index));
			while (!c.matches("[a-zA-Z]")) {
				index++;
				c = String.valueOf(split[i].charAt(index));
			}
			string += split[i].replaceFirst(c, c.toUpperCase()) + " ";
		}
		return string.substring(0, string.length() - 1);
	}
}
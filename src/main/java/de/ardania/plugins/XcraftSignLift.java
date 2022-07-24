package de.ardania.plugins;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class XcraftSignLift extends JavaPlugin {
    private final EventListener eventlistener = new EventListener(this);
    public final LiftHandler liftHandler = new LiftHandler(this);

    @Override
    public void onDisable() {
        getLogger().info("[" + this.getDescription().getName() + "] disabled!");
    }

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.eventlistener, this);
    }

    public String getChatName() {
        return ChatColor.DARK_GRAY + "[" + this.getDescription().getName() + "] " + this.getChatColor();
    }

    public ChatColor getChatColor() {
        return ChatColor.DARK_AQUA;
    }

    public String capitalize(String name) {
        String[] split = name.split(" ");
        String string = "";
        for (String s : split) {
            int index = 0;
            String c = String.valueOf(s.charAt(index));
            while (!c.matches("[a-zA-Z]")) {
                c = String.valueOf(s.charAt(++index));
            }
            string = string + s.replaceFirst(c, c.toUpperCase()) + " ";
        }
        return string.substring(0, string.length() - 1);
    }
}


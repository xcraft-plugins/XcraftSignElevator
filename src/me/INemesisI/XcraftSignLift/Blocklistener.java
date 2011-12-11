package me.INemesisI.XcraftSignLift;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;

public class Blocklistener extends BlockListener {
    public static XcraftSignLift plugin;
   
    public Blocklistener(XcraftSignLift instance) {
            plugin = instance;
    }
   
    
    @Override
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
		}
		else return;
    }
}

package me.INemesisI.XcraftSignLift;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class XcraftSignLift extends JavaPlugin {
	 
    //ClassListeners
	private final EventListener eventlistener = new EventListener(this);
    //ClassListeners
	public LiftHandler liftHandler = new LiftHandler(this);
       
    public Logger log = Logger.getLogger("Minecraft");
 
    @Override
	public void onDisable() {
    	log.info("[" + getDescription().getName() + "] disabled!");
    }
 
    @Override
	public void onEnable() {
               
    PluginManager pm = this.getServer().getPluginManager();
    pm.registerEvents(eventlistener, this);
    
    log.info("[" + getDescription().getName()+"] v"+getDescription().getVersion()+" by INemesisI enabled!");
    }
        
    public String getName() {
      	return ChatColor.DARK_GRAY + "[" + this.getDescription().getName() + "] " + getChatColor();
   	}
    
    public ChatColor getChatColor() {
    	return ChatColor.DARK_AQUA;
    }
    
    public String capitalize(String name) {
    	String save = name.substring(0, name.indexOf("["));
    	name = name.substring(name.indexOf("["), name.indexOf("]")+1);
    	String[] split = name.split(" ");
    	String string = "";
    	for (int i=0;i<split.length;i++) {
    		String character = ""+ split[i].charAt(0);
    		int num;
			if (character.equals("[") ){  num = 1;
			string += "[";
			}
    		else  num = 0;
			if (i > 0) string += " ";
    	string += Character.toUpperCase(split[i].charAt(num)) + split[i].substring(num+1, split[i].length());
    	}
		return save+string;
    
    }
}
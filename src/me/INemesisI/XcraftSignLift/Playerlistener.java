package me.INemesisI.XcraftSignLift;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;


public class Playerlistener extends PlayerListener{
        public static XcraftSignLift plugin;
       
        public Playerlistener(XcraftSignLift instance) {
                plugin = instance;
        }
 
        @Override
		public void onPlayerInteract(PlayerInteractEvent event) {
        	if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
        		Block block = event.getClickedBlock(); 		
        		if (block.getState() instanceof Sign) {
        			Sign sign = (Sign) block.getState();
        			String[] lines = sign.getLines();
        			if ((lines[1].contains("[Lift Up]") || (lines[1].contains("[Lift Down]")))) {
        				Player player = event.getPlayer();
        				if (event.getPlayer().hasPermission("XcraftSign.lift.use"))
        				plugin.liftHandler.handle(block, player);
        				else
        					player.sendMessage(plugin.getName() + ChatColor.RED + "Du hast keine Rechte, Lifte zu benutzen");
        			}
        		}
        	}
        }
}
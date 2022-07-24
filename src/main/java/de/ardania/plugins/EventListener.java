package de.ardania.plugins;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener {
    public final XcraftSignLift plugin;

    public EventListener(XcraftSignLift instance) {
        plugin = instance;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        String[] lines = event.getLines();
        if (lines[1].equalsIgnoreCase("[lift up]") || lines[1].equalsIgnoreCase("[lift down]")) {
            if (!event.getPlayer().hasPermission("XcraftSignLift.create")) {
                event.getPlayer().sendMessage(plugin.getChatName() + ChatColor.RED + "Du hast keine Rechte, Lifte zu erstellen!");
                event.setCancelled(true);
                return;
            }
            event.setLine(1, plugin.capitalize(lines[1]));
            event.getPlayer().sendMessage(plugin.getChatName() + "Lift erstellt");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Sign sign;
        String[] lines;
        BlockState blockState;
        Block block;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && (block = event.getClickedBlock()) != null && (blockState = block.getState()) instanceof Sign && ((lines = (sign = (Sign)blockState).getLines())[1].equals("[Lift Up]") || lines[1].equals("[Lift Down]"))) {
            Player player = event.getPlayer();
            if (event.getPlayer().hasPermission("XcraftSignLift.use")) {
                plugin.liftHandler.handle(block, sign, player);
            } else {
                player.sendMessage(plugin.getChatName() + ChatColor.RED + "Du hast keine Rechte, Lifte zu benutzen");
            }
            event.setCancelled(true);
        }
    }
}


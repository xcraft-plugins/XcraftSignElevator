package de.ardania.plugins;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class XcraftSignLift extends JavaPlugin implements Listener {
    public final LiftHandler liftHandler = new LiftHandler();

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        String[] lines = event.getLines();
        if (LiftSignUtils.isLiftSign(lines)) {
            if (!event.getPlayer().hasPermission("XcraftSignLift.create")) {
                Messages.sendError(event.getPlayer(), Messages.LIFT_CREATION_MISSING_PERMS);
                event.setCancelled(true);
                return;
            }
            event.setLine(1, LiftSignUtils.capitalize(lines[1]));
            Messages.sendMessage(event.getPlayer(), Messages.LIFT_CREATED);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Block block = event.getClickedBlock();
        if(block == null)
            return;
        BlockState blockState = block.getState();
        if(!(blockState instanceof Sign))
            return;
        Sign sign = (Sign) blockState;
        if (!LiftSignUtils.isLiftSign(sign.getLines()))
            return;

        Player player = event.getPlayer();
        if (!player.hasPermission("XcraftSignLift.use"))
            Messages.sendError(player, Messages.LIFT_USAGE_MISSING_PERMS);
        else
            liftHandler.handle(block, sign, player);
        event.setCancelled(true);
    }
}


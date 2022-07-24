package de.ardania.plugins;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class LiftHandler {
    private final XcraftSignLift plugin;

    public LiftHandler(XcraftSignLift instance) {
        this.plugin = instance;
    }

    public void handle(Block block, Sign sign, Player player) {
        String[] lines = sign.getLines();
        Block tpblock = null;
        if (lines[1].equals("[Lift Up]")) {
            tpblock = this.getNextLiftAbove(block);
        } else if (lines[1].equals("[Lift Down]")) {
            tpblock = this.getNextLiftBelow(block);
        }
        if (tpblock == null) {
            player.sendMessage(plugin.getChatName() + "Es gibt keinen zugehörigen Lift!");
            return;
        }
        Location loc = player.getLocation();
        double y = tpblock.getY();
        loc.setY(y);
        if (!this.hasBlockBelow(loc.getBlock())) {
            player.sendMessage(plugin.getChatName() + "Es gibt keinen Block, auf dem du stehen könntest");
            return;
        }
        if (!this.hasFreeAir(loc.getBlock())) {
            player.sendMessage(plugin.getChatName() + "Es gibt keinen freien Platz!");
            return;
        }
        player.teleport(this.getNextBlockBelow(loc));
        if (!lines[2].isEmpty()) {
            player.sendMessage(plugin.getChatName() + "Du bist nun hier: " + ChatColor.AQUA + lines[2]);
        }
    }

    private Block getNextLiftAbove(Block block) {
        int max = block.getWorld().getMaxHeight() - block.getY();
        for (int i = 0; i < max; ++i) {
            Sign sign;
            Block face = block.getRelative(BlockFace.UP, i);
            BlockState blockState = face.getState();
            if (!(blockState instanceof Sign) || !(sign = (Sign)blockState).getLine(1).equals("[Lift Down]")) continue;
            return face;
        }
        return null;
    }

    private Block getNextLiftBelow(Block block) {
        int max = block.getY() - block.getWorld().getMinHeight();
        for (int i = 0; i < max; ++i) {
            Sign sign;
            Block face = block.getRelative(BlockFace.DOWN, i);
            BlockState blockState = face.getState();
            if (!(blockState instanceof Sign) || !(sign = (Sign)blockState).getLine(1).contains("[Lift Up]")) continue;
            return face;
        }
        return null;
    }

    private boolean hasBlockBelow(Block block) {
        for (int i = 1; i < 4; ++i) {
            Block face = block.getRelative(BlockFace.DOWN, i);
            if (!this.isSolid(face.getType())) continue;
            return true;
        }
        return false;
    }

    private Location getNextBlockBelow(Location loc) {
        for (int i = 1; i < 4; ++i) {
            Block block = loc.getBlock();
            Block face = block.getRelative(BlockFace.DOWN, i);
            if (!this.isSolid(face.getType())) continue;
            int y = loc.getBlockY();
            loc.setY(y - i + 1);
            return loc;
        }
        return loc;
    }

    private boolean hasFreeAir(Block block) {
        if (!this.isSolid(block.getType())) {
            Block face = block.getRelative(BlockFace.DOWN, 1);
            Material mat = face.getType();
            if (!this.isSolid(mat)) {
                return true;
            }
            face = block.getRelative(BlockFace.UP, 1);
            mat = face.getType();
            return !this.isSolid(mat);
        }
        return false;
    }

    private boolean isSolid(Material mat) {
        if (mat.isSolid()) {
            switch (mat) {
                case ACACIA_DOOR: 
                case ACACIA_FENCE_GATE: 
                case ACACIA_PRESSURE_PLATE: 
                case ACACIA_SIGN: 
                case ACACIA_TRAPDOOR: 
                case ACACIA_WALL_SIGN: 
                case BIRCH_DOOR: 
                case BIRCH_FENCE_GATE: 
                case BIRCH_PRESSURE_PLATE: 
                case BIRCH_SIGN: 
                case BIRCH_TRAPDOOR: 
                case BIRCH_WALL_SIGN: 
                case BLACK_BANNER: 
                case BLACK_WALL_BANNER: 
                case BLUE_BANNER: 
                case BLUE_WALL_BANNER: 
                case BROWN_BANNER: 
                case BROWN_WALL_BANNER: 
                case CRIMSON_DOOR: 
                case CRIMSON_FENCE_GATE: 
                case CRIMSON_PRESSURE_PLATE: 
                case CRIMSON_SIGN: 
                case CRIMSON_TRAPDOOR: 
                case CRIMSON_WALL_SIGN: 
                case CYAN_BANNER: 
                case CYAN_WALL_BANNER: 
                case DARK_OAK_DOOR: 
                case DARK_OAK_FENCE_GATE: 
                case DARK_OAK_PRESSURE_PLATE: 
                case DARK_OAK_SIGN: 
                case DARK_OAK_TRAPDOOR: 
                case DARK_OAK_WALL_SIGN: 
                case DEAD_BRAIN_CORAL: 
                case DEAD_BRAIN_CORAL_FAN: 
                case DEAD_BRAIN_CORAL_WALL_FAN: 
                case DEAD_BUBBLE_CORAL: 
                case DEAD_BUBBLE_CORAL_FAN: 
                case DEAD_BUBBLE_CORAL_WALL_FAN: 
                case DEAD_FIRE_CORAL: 
                case DEAD_FIRE_CORAL_FAN: 
                case DEAD_FIRE_CORAL_WALL_FAN: 
                case DEAD_HORN_CORAL: 
                case DEAD_HORN_CORAL_FAN: 
                case DEAD_HORN_CORAL_WALL_FAN: 
                case DEAD_TUBE_CORAL: 
                case DEAD_TUBE_CORAL_FAN: 
                case DEAD_TUBE_CORAL_WALL_FAN: 
                case GRAY_BANNER: 
                case GRAY_WALL_BANNER: 
                case GREEN_BANNER: 
                case GREEN_WALL_BANNER: 
                case HEAVY_WEIGHTED_PRESSURE_PLATE: 
                case IRON_DOOR: 
                case IRON_TRAPDOOR: 
                case JUKEBOX: 
                case JUNGLE_DOOR: 
                case JUNGLE_FENCE_GATE: 
                case JUNGLE_PRESSURE_PLATE: 
                case JUNGLE_SIGN: 
                case JUNGLE_TRAPDOOR: 
                case JUNGLE_WALL_SIGN: 
                case LIGHT_BLUE_BANNER: 
                case LIGHT_BLUE_WALL_BANNER: 
                case LIGHT_GRAY_BANNER: 
                case LIGHT_GRAY_WALL_BANNER: 
                case LIGHT_WEIGHTED_PRESSURE_PLATE: 
                case LIME_BANNER: 
                case LIME_WALL_BANNER: 
                case MAGENTA_BANNER: 
                case MAGENTA_WALL_BANNER: 
                case OAK_DOOR: 
                case OAK_FENCE_GATE: 
                case OAK_PRESSURE_PLATE: 
                case OAK_SIGN: 
                case OAK_TRAPDOOR: 
                case OAK_WALL_SIGN: 
                case ORANGE_BANNER: 
                case ORANGE_WALL_BANNER: 
                case PINK_BANNER: 
                case PINK_WALL_BANNER: 
                case POLISHED_BLACKSTONE_PRESSURE_PLATE: 
                case PURPLE_BANNER: 
                case PURPLE_WALL_BANNER: 
                case RED_BANNER: 
                case RED_WALL_BANNER: 
                case SPRUCE_DOOR: 
                case SPRUCE_FENCE_GATE: 
                case SPRUCE_PRESSURE_PLATE: 
                case SPRUCE_SIGN: 
                case SPRUCE_TRAPDOOR: 
                case SPRUCE_WALL_SIGN: 
                case STONE_PRESSURE_PLATE: 
                case WARPED_DOOR: 
                case WARPED_FENCE_GATE: 
                case WARPED_PRESSURE_PLATE: 
                case WARPED_SIGN: 
                case WARPED_TRAPDOOR: 
                case WARPED_WALL_SIGN: 
                case WHITE_BANNER: 
                case WHITE_WALL_BANNER: 
                case YELLOW_BANNER: 
                case YELLOW_WALL_BANNER: {
                    return false;
                }
                default: {
                    return true;
                }
            }
        } else {
            return false;
        }
    }
}


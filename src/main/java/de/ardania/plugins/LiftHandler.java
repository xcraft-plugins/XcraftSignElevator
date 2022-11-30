package de.ardania.plugins;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final Set<Material> customNonSolidMaterial = new HashSet<>();
    private boolean isSolid(Material mat) {
        if (mat.isSolid()) {
            return !customNonSolidMaterial.contains(mat);
        } else {
            return false;
        }
    }

    public void setCustomNonSolidMaterials(List<String> nonSolidMaterials) {
        customNonSolidMaterial.clear();
        customNonSolidMaterial.addAll(
            nonSolidMaterials.stream()
                .map(Material::matchMaterial)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
        );
    }
}


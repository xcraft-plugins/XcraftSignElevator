package me.INemesisI.XcraftSignLift;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class LiftHandler {
	XcraftSignLift plugin;

	public LiftHandler(XcraftSignLift instance) {
		plugin = instance;
	}

	public void handle(Block block, Player player) {
		Sign sign = (Sign) block.getState();
		String[] lines = sign.getLines();
		Block tpblock = null;
		if (lines[1].equals("[Lift Up]")) {
			tpblock = this.getNextLiftAbove(block);
		}
		if (lines[1].equals("[Lift Down]")) {
			tpblock = this.getNextLiftBelow(block);
		}
		if (tpblock == null) {
			player.sendMessage(plugin.getCName() + "Es gibt keinen zugehörigen Lift!");
			return;
		}
		Location loc = player.getLocation();
		double y = loc.getY();
		y = -(int) y;
		y = +tpblock.getY();
		loc.setY(y);
		if (!this.hasBlockBelow(loc.getBlock())) {
			player.sendMessage(plugin.getCName() + "Es gibt keinen Block, auf dem du stehen könntest");
			return;
		}
		if (!this.hasFreeAir(loc.getBlock())) {
			player.sendMessage(plugin.getCName() + "Es gibt keinen freien Platz!");
			return;
		}
		player.teleport(this.getNextBlockBelow(loc));
		if (!lines[2].isEmpty()) {
			player.sendMessage(plugin.getCName() + "Du bist nun hier: " + ChatColor.AQUA + lines[2]);
		}

	}

	public boolean isLift(Block block) {
		Sign sign = (Sign) block.getState();
		if (block.getType().equals(Material.SIGN) || block.getType().equals(Material.SIGN_POST)
				|| block.getType().equals(Material.WALL_SIGN)) {
			String[] lines = sign.getLines();
			return (lines[1].equals("[Lift Up]") || lines[1].equals("[Lift Down]"));
		} else {
			return false;
		}
	}

	public Block getNextLiftAbove(Block block) {
		int max = 255 - block.getY();
		for (int i = 1; i < max; i++) {
			Block face = block.getRelative(BlockFace.UP, i);
			if (face.getType().equals(Material.SIGN) || face.getType().equals(Material.SIGN_POST)
					|| face.getType().equals(Material.WALL_SIGN)) {
				Sign sign = (Sign) face.getState();
				if (sign.getLine(1).equals("[Lift Down]")) {
					return face;
				}
			}
		}
		return null;
	}

	public Block getNextLiftBelow(Block block) {
		int max = block.getY();
		for (int i = 1; i < max; i++) {
			Block face = block.getRelative(BlockFace.DOWN, i);
			if (face.getType().equals(Material.SIGN) || face.getType().equals(Material.SIGN_POST)
					|| face.getType().equals(Material.WALL_SIGN)) {
				Sign sign = (Sign) face.getState();
				if (sign.getLine(1).contains("[Lift Up]")) {
					return face;
				}
			}
		}
		return null;
	}

	public boolean hasBlockBelow(Block block) {
		for (int i = 1; i < 4; i++) {
			Block face = block.getRelative(BlockFace.DOWN, i);
			if (face.getTypeId() != 0) {
				return true;
			}
		}
		return false;
	}

	public Location getNextBlockBelow(Location loc) {
		for (int i = 1; i < 4; i++) {
			Block block = loc.getBlock();
			Block face = block.getRelative(BlockFace.DOWN, i);
			if (face.getTypeId() != 0) {
				int y = loc.getBlockY();
				loc.setY((y - i) + 1);
				return loc;
			}
		}
		return loc;
	}

	public boolean hasFreeAir(Block block) {
		if ((block.getTypeId() == 0) || (block.getTypeId() == 63) || (block.getTypeId() == 68)) {
			Block face = block.getRelative(BlockFace.DOWN, 1);
			int id = face.getTypeId();
			if ((id == 0) || (id == 50) || (id == 68) || (id == 63) || (id == 44) || (id == 77) || (id == 75)
					|| (id == 76) || (id == 72) || (id == 70) || (id == 55)) {
				return true;
			} else {
				face = block.getRelative(BlockFace.UP, 1);
				id = face.getTypeId();
				if ((id == 0) || (id == 50) || (id == 68) || (id == 63) || (id == 44) || (id == 77) || (id == 75)
						|| (id == 76) || (id == 72) || (id == 70) || (id == 55)) {
					return true;
				}
			}
		}
		return false;
	}

}

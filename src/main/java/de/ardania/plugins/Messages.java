package de.ardania.plugins;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {
    private Messages() {}

    public static final String CHAT_PREFIX_NAME = "SignLift";
    public static final ChatColor CHAT_PREFIX_COLOR = ChatColor.DARK_GRAY;
    public static final ChatColor CHAT_MESSAGE_COLOR = ChatColor.DARK_AQUA;
    public static final ChatColor CHAT_ERROR_COLOR = ChatColor.DARK_AQUA;

    public static final String CHAT_PREFIX = CHAT_PREFIX_COLOR + "[" + CHAT_PREFIX_NAME + "] ";

    public static void sendMessage(Player player, String message) {
        player.sendMessage(CHAT_PREFIX + CHAT_MESSAGE_COLOR + message);
    }
    public static void sendError(Player player, String error) {
        player.sendMessage(CHAT_PREFIX + CHAT_ERROR_COLOR + error);
    }

    public static final String LIFT_CREATED = "Lift erstellt";
    public static final String LIFT_CREATION_MISSING_PERMS = "Du hast keine Rechte, Lifte zu erstellen!";
    public static final String LIFT_USAGE_MISSING_PERMS = "Du hast keine Rechte, Lifte zu benutzen!";
}

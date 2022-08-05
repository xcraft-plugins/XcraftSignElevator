package de.ardania.plugins;

public class LiftSignUtils {
    private LiftSignUtils() {}

    public static final String LIFT_UP_STRING = "[lift up]";
    public static final String LIFT_DOWN_STRING = "[lift down]";

    public static boolean isLiftSign(String[] lines) {
        return lines[1].equalsIgnoreCase(LIFT_UP_STRING)
            || lines[1].equalsIgnoreCase(LIFT_DOWN_STRING);
    }

    public static String capitalize(String name) {
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

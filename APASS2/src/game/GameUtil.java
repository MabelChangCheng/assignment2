package game;

import participant.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

class GameUtil {

    private static final Set<String> POSSIBLE_STATES = new HashSet<String>(Arrays.asList(
            "ACT", "NSW", "NT", "QLD", "SA", "TAS", "VIC", "WA"));

    private static final Set<String> POSSIBLE_TYPES = new HashSet<String>(Arrays.asList(
            "officer", "sprinter", "super", "swimmer", "cyclist"));

    /**
     * Loads participants from the specified file.
     *
     * @param filename the specified file.
     * @return list of participants.
     * @throws FileNotFoundException when the file is not found.
     */
    static List<Participant> loadParticipants(String filename)
            throws FileNotFoundException {
        Scanner input = null;
        Map<String, Participant> participants = new HashMap<String, Participant>();
        try {
            input = new Scanner(new FileInputStream(filename));
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                String[] tokens = line.split(",\\s+");
                if (tokens.length != 5) {
                    System.err.println("line ignored, invalid number of tokens: " + line);
                    continue;
                }
                String ID = tokens[0];
                String type = tokens[1];
                String name = tokens[2];
                String ageStr = tokens[3];
                String state = tokens[4];
                int age;
                if (ID.isEmpty()) {
                    System.err.println("line ignored, missing ID: " + line);
                    continue;
                }
                if (participants.containsKey(ID)) {
                    System.err.println("line ignored, duplicates: " + line);
                    continue;
                }
                if (type.isEmpty()) {
                    System.err.println("line ignored, missing type: " + line);
                    continue;
                }
                if (!POSSIBLE_TYPES.contains(type)) {
                    System.err.println("line ignored, invalid type: " + line);
                    continue;
                }
                if (name.isEmpty()) {
                    System.err.println("line ignored, missing name: " + line);
                    continue;
                }
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    System.err.println("line ignored, invalid age: " + line);
                    continue;
                }
                if (state.isEmpty()) {
                    System.err.println("line ignored, missing state: " + line);
                    continue;
                }
                if (!POSSIBLE_STATES.contains(state)) {
                    System.err.println("line ignored, invalid state: " + line);
                    continue;
                }
                if ("officer".equals(type)) {
                    participants.put(ID, new Official(ID, name, age, state));
                } else if ("sprinter".equals(type)) {
                    participants.put(ID, new Sprinter(ID, name, age, state));
                } else if ("swimmer".equals(type)) {
                    participants.put(ID, new Swimmer(ID, name, age, state));
                } else if ("cyclist".equals(type)) {
                    participants.put(ID, new Cycling(ID, name, age, state));
                } else {
                    participants.put(ID, new Super(ID, name, age, state));
                }
            }
            return new ArrayList<Participant>(participants.values());

        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * Formats time.
     */
    public static String formatTime(int ms) {
        int seconds = ms / 1000 % 60;
        int minutes = ms / 1000 / 60 % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Formats rank.
     */
    public static String formatRank(int rank) {
        switch (rank) {
            case 1:
                return "1st";
            case 2:
                return "2nd";
            case 3:
                return "3rd";
            default:
                return rank + "st";
        }
    }

    /**
     * Formats datetime.
     */
    public static String formatDateTime(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }
}

package game;

import exception.GameFullException;
import exception.NoRefereeException;
import exception.TooFewAthleteException;
import participant.Athlete;
import participant.Official;

import java.util.*;

/**
 * Ozlympic game.
 */
public class Game {


    // Minimum number of athletes in a game.
    public static final int MIN_ATHLETES = 4;

    // Maximum number of athletes in a game.
    public static final int MAX_ATHLETES = 8;

    // Game id.
    private static int GAME_ID = 1;

    // Simulation time interval in ms.
    private static final int SIMULATION_INTERVAL = 20;

    // Points awarded to the top 3 winners.
    private static final int[] POINTS = {5, 3, 1};

    // id of the game.
    private final String id;

    // event of the game.
    private final EventType event;

    // athletes of the game.
    private final List<Athlete> athletes;

    // if the game is finished.
    private boolean finished = false;

    // referee of the game.
    private Official referee;

    // times of the athletes.
    private int[] times;

    // ranks of the athletes.
    private int[] ranks;

    // Start time of the game.
    private Date startTime;

    // Game panel.
    private GamePanel gamePanel;

    // Total time.
    private int totalTime;

    // Current time for simulation in ms.
    private int currentTime;

    /**
     * Constructor.
     *
     * @param event event of the game.
     */
    public Game(EventType event, GamePanel panel) {
        this.id = String.format("%c%02d", event.getSymbol(), GAME_ID++);
        this.event = event;
        this.athletes = new ArrayList<>();
        this.referee = null;
        this.finished = false;
        this.gamePanel = panel;
    }

    /**
     * Adds athletes to the game.
     *
     * @param newAthletes the list of athletes to be added.
     */
    public void addAthletes(Collection<Athlete> newAthletes) {
        if (athletes.size() + newAthletes.size() > MAX_ATHLETES) {
            throw new GameFullException();
        }
        athletes.addAll(newAthletes);
    }

    /**
     * Sets referee to the game.
     *
     * @param referee the referee to be set.
     */
    public void setReferee(Official referee) {
        this.referee = referee;
    }


    /**
     * Starts the game.
     */
    public void startGame() {

        // Validate the game.
        if (referee == null) {
            throw new NoRefereeException();
        }
        if (athletes.size() < MIN_ATHLETES) {
            throw new TooFewAthleteException();
        }

        startTime = new Date();

        times = new int[athletes.size()];
        ranks = new int[athletes.size()];

        // Generate time for each athlete.
        for (int i = 0; i < athletes.size(); i++) {
            times[i] = athletes.get(i).compete(event);
            ranks[i] = 1;
        }

        // Calculate rank for each athlete.
        for (int i = 0; i < athletes.size(); i++) {
            for (int j = 0; j < athletes.size(); j++) {
                if (times[i] > times[j]) {
                    ranks[i]++;
                }
            }
        }

        // Add points to the top 3 winners.
        for (int i = 0; i < athletes.size(); i++) {
            if (ranks[i] <= POINTS.length) {
                athletes.get(i).addPoints(POINTS[ranks[i] - 1]);
            }
        }

        // Start simulation.
        simulation();
        finished = true;
    }

    /**
     * Realtime simulation.
     */
    private void simulation() {
        if (gamePanel != null) {
            gamePanel.setGame(this);
            gamePanel.repaint();
        }

        totalTime = 0;
        for (int time : times) {
            totalTime = Math.max(time, totalTime);
        }

        for (currentTime = 0; currentTime < totalTime * 1000; currentTime += SIMULATION_INTERVAL) {
            try {
                Thread.sleep(SIMULATION_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (gamePanel != null) {
                gamePanel.repaint();
            }
        }
    }

    /**
     * @return the athletes in the game.
     */
    public List<Athlete> getAthletes() {
        return athletes;
    }

    /**
     * @return a string represents the game.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(id).append(", ")
                .append(referee.getId()).append(", ")
                .append(GameUtil.formatDateTime(startTime)).append("\n");

        sortAthletes();

        for (int i = 0; i < athletes.size(); i++) {
            Athlete athlete = athletes.get(i);
            builder.append(athlete.getId()).append(", ")
                    .append(times[i]).append(", ")
                    .append(athlete.getPoints()).append("\n");
        }
        return builder.toString();
    }

    /**
     * Sorts athletes by their times.
     */
    private void sortAthletes() {
        for (int i = 0; i < athletes.size(); i++) {
            for (int j = i + 1; j < athletes.size(); j++) {
                if (times[i] > times[j]) {
                    Athlete temp = athletes.get(i);
                    athletes.set(i, athletes.get(j));
                    athletes.set(j, temp);

                    int tempRank = ranks[i];
                    ranks[i] = ranks[j];
                    ranks[j] = tempRank;

                    int tempTime = times[i];
                    times[i] = times[j];
                    times[j] = tempTime;
                }
            }
        }
    }

    /**
     * @return true if the game is finished or false otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @return the event of the game.
     */
    public EventType getEvent() {
        return event;
    }

    /**
     * Determines whether the athlete is the winner of this game.
     *
     * @param athlete the input athlete.
     * @return true if he is the winner or false otherwise.
     */
    public boolean isWinner(Athlete athlete) {
        for (int i = 0; i < athletes.size(); i++) {
            if (athletes.get(i) == athlete && ranks[i] == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the total game time.
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * @return the current time of simulation.
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * Returns the athlete's time.
     */
    public int getAthleteTime(int index) {
        return times[index] * 1000;
    }


    /**
     * Returns the athlete's rank.
     */
    public int getAthleteRank(int index) {
        return ranks[index];
    }

    /**
     * @return the game id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return the referee.
     */
    public Official getReferee() {
        return referee;
    }

    /**
     * @return the start time.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @return a string containing the top 3 winners of the game.
     */
    public String getTop3Winners() {
        if (!finished) {
            return null;
        }
        StringBuilder winners = new StringBuilder();
        for (int rank = 1; rank <= 3; rank++) {
            for (int i = 0; i < athletes.size(); i++) {
                if (ranks[i] == rank) {
                    if (winners.length() > 0) {
                        winners.append(", ");
                    }
                    winners.append(GameUtil.formatRank(rank))
                            .append(":").append(athletes.get(i).getId());
                }
            }
        }
        return winners.toString();
    }
}

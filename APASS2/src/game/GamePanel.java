package game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class GamePanel extends JPanel {

    private Game game;

    /**
     * Sets the game.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawGamePanel(g);

        if (game != null) {
            // draw text
            g.setColor(Color.BLUE);
            g.drawString(game.getId(), 60, 15);
            g.drawString(GameUtil.formatTime(game.getCurrentTime()), 175, 15);
            g.drawString(game.getReferee().toShortString(), 320, 15);
            g.drawString(game.isFinished() ? "FINISHED" : "RUNNING", 505, 15);

            int trackHeight = (getHeight() - 40) / Game.MAX_ATHLETES;
            for (int i = 0; i < game.getAthletes().size(); i++) {

                // draw athlete name
                g.setColor(Color.BLACK);
                g.drawString(game.getAthletes().get(i).toShortString(),
                        130 - g.getFontMetrics().stringWidth(game.getAthletes().get(i).toShortString()) - 10,
                        30 + i * trackHeight + trackHeight / 2 + g.getFontMetrics().getHeight() / 2);

                // draw athlete position.
                int currentTime = game.getCurrentTime();
                int athleteTime = game.getAthleteTime(i);
                int athleteRadius = trackHeight / 3;
                double percentage = currentTime < athleteTime ? 1.0 * currentTime / athleteTime : 1.0;

                g.setColor(Color.RED);
                g.fillOval((int) (135 + (getWidth() - 230) * percentage),
                        30 + i * trackHeight + trackHeight / 2 - athleteRadius / 2, athleteRadius, athleteRadius);

                // draw time and rank.
                if (currentTime >= athleteTime) {
                    g.setColor(Color.MAGENTA);
                    g.drawString(GameUtil.formatTime(athleteTime),
                            getWidth() - 70,
                            30 + i * trackHeight + trackHeight / 2 + g.getFontMetrics().getHeight() / 2);

                    g.setColor(Color.BLUE);
                    g.drawString(GameUtil.formatRank(game.getAthleteRank(i)),
                            getWidth() - 30,
                            30 + i * trackHeight + trackHeight / 2 + g.getFontMetrics().getHeight() / 2);
                }
            }
        }
    }

    /**
     * Draws the game panel.
     */
    private void drawGamePanel(Graphics g) {

        // background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // text
        g.setColor(Color.BLACK);
        g.drawString("Game: ", 10, 15);
        g.drawString("Time: ", 130, 15);
        g.drawString("Referee: ", 260, 15);
        g.drawString("Status: ", 450, 15);

        // tracks
        g.setColor(Color.LIGHT_GRAY);
        int trackHeight = (getHeight() - 40) / Game.MAX_ATHLETES;
        for (int i = 0; i <= Game.MAX_ATHLETES; i++) {
            g.drawLine(130, 30 + i * trackHeight, getWidth() - 100, 30 + i * trackHeight);
        }
        g.drawLine(130, 30, 130, 30 + Game.MAX_ATHLETES * trackHeight);
        g.drawLine(getWidth() - 100, 30, getWidth() - 100, 30 + Game.MAX_ATHLETES * trackHeight);
    }
}

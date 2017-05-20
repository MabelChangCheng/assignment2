package game;

import exception.GameException;
import participant.Athlete;
import participant.Official;
import participant.Participant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

public class Ozlympic extends JFrame {

    private static final String PARTICIPANTS_FILE ="participants.txt";
    private static final String GAME_RESULT_FILE = "gameResults.txt";

    private JButton runButton;
    private GamePanel gamePanel;
    private JList<Athlete> athleteBox;
    private JList<String> eventBox;
    private JList<Official> refereeBox;
    private DefaultTableModel athleteTableModel;
    private DefaultTableModel gameTableModel;
    private List<Participant> participants = new ArrayList<Participant>();
    private List<Game> games = new ArrayList<Game>();

    /**
     * Constructor.
     */
    public Ozlympic() {
        setSize(800, 500);
        setLocationRelativeTo(null);
        setTitle("Ozlympic");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initTabPanel();
        initMenu();
    }

    /**
     * Initializes the tab panels.
     */
    private void initTabPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.add("Run Game", createRunPanel());
        tabbedPane.add("Games", createGameTable());
        tabbedPane.add("Athletes", createAthleteTable());
    }

    /**
     * Initializes the menu.
     */
    private void initMenu() {

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem loadButton = new JMenuItem("Load participants");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadParticipants();
            }
        });
        menu.add(loadButton);

        JMenuItem saveButton = new JMenuItem("Save game results");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGameResults();
            }
        });
        menu.add(saveButton);
        menu.addSeparator();

        JMenuItem exitButton = new JMenuItem("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(exitButton);
    }

    /**
     * Saves game results to the file.
     */
    private void saveGameResults() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(GAME_RESULT_FILE));

            for (Game game : games) {
                writer.println(game);
            }

            JOptionPane.showMessageDialog(this, "Game results saved to file " + GAME_RESULT_FILE,
                    "Info", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Loads participants from file.
     */
    private void loadParticipants() {
        JFileChooser fileChooser = new JFileChooser(".");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                setParticipants(GameUtil.loadParticipants(file.getAbsolutePath()));
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Sets participants to the game window.
     */
    private void setParticipants(List<Participant> participants) {
        this.participants = participants;
        this.games.clear();

        DefaultListModel<Official> refereeModel = (DefaultListModel<Official>) refereeBox.getModel();
        refereeModel.clear();
        DefaultListModel<Athlete> athleteModel = (DefaultListModel<Athlete>) athleteBox.getModel();
        athleteModel.clear();
        for (Participant participant : participants) {
            if (participant instanceof Official) {
                refereeModel.addElement((Official) participant);
            } else {
                athleteModel.addElement((Athlete) participant);
            }
        }

        updateAthleteTable();
        updateGameTable();
    }

    /**
     * Creates the running panel.
     */
    private JComponent createRunPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel row = new JPanel(new FlowLayout());
        panel.add(row, BorderLayout.NORTH);
        JLabel label = new JLabel("Event:");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setPreferredSize(new Dimension(50, 140));
        row.add(label);

        eventBox = new JList<String>(new String[]{"swimming", "cycling", "running"});
        eventBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        row.add(createJScrollPane(eventBox, 80));

        label = new JLabel("Athlete:");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setPreferredSize(new Dimension(50, 140));
        row.add(label);

        athleteBox = new JList<Athlete>(new DefaultListModel<Athlete>());
        athleteBox.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        row.add(createJScrollPane(athleteBox, 200));

        label = new JLabel("Referee:");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setPreferredSize(new Dimension(50, 140));
        row.add(label);

        refereeBox = new JList<Official>(new DefaultListModel<Official>());
        refereeBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        row.add(createJScrollPane(refereeBox, 200));

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        runButton = new JButton("RUN");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
        runButton.setPreferredSize(new Dimension(100, 50));
        JButton resetButton = new JButton("RESET");
        resetButton.setPreferredSize(new Dimension(100, 50));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        buttonPanel.add(resetButton);
        buttonPanel.add(runButton);
        row.add(buttonPanel);

        gamePanel = new GamePanel();
        panel.add(gamePanel);

        return panel;
    }

    /**
     * Creates the game table.
     */
    private JComponent createGameTable() {
        JPanel panel = new JPanel(new BorderLayout());
        gameTableModel = new DefaultTableModel();
        gameTableModel.addColumn("Game ID");
        gameTableModel.addColumn("Official ID");
        gameTableModel.addColumn("Time");
        gameTableModel.addColumn("Top winners");
        JTable table = new JTable(gameTableModel);
        table.setEnabled(false);
        panel.add(table, BorderLayout.CENTER);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        return panel;
    }

    /**
     * Creates the athlete table.
     */
    private JComponent createAthleteTable() {
        JPanel panel = new JPanel(new BorderLayout());
        athleteTableModel = new DefaultTableModel();
        athleteTableModel.addColumn("ID");
        athleteTableModel.addColumn("Name");
        athleteTableModel.addColumn("Type");
        athleteTableModel.addColumn("Age");
        athleteTableModel.addColumn("State");
        athleteTableModel.addColumn("Points");
        JTable table = new JTable(athleteTableModel);
        table.setEnabled(false);
        panel.add(table, BorderLayout.CENTER);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        return panel;
    }

    /**
     * Runs a game.
     */
    private void run() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runButton.setEnabled(false);

                    if (eventBox.getSelectedValue() == null) {
                        throw new GameException("Please select an event.");
                    }
                    Game game = new Game(EventType.parse(eventBox.getSelectedValue()), gamePanel);
                    game.addAthletes(athleteBox.getSelectedValuesList());
                    game.setReferee(refereeBox.getSelectedValue());
                    game.startGame();
                    games.add(game);
                    updateGameTable();
                    updateAthleteTable();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(Ozlympic.this, e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);

                } finally {
                    runButton.setEnabled(true);
                }
            }
        }).start();

    }

    /**
     * Resets the selections and game.
     */
    private void reset() {
        eventBox.clearSelection();
        athleteBox.clearSelection();
        refereeBox.clearSelection();
        gamePanel.setGame(null);
        gamePanel.repaint();
        runButton.setEnabled(true);
    }

    /**
     * Updates the game table.
     */
    private void updateGameTable() {
        gameTableModel.setRowCount(0);
        for (Game game : games) {
            gameTableModel.addRow(new Object[]{
                    game.getId(),
                    game.getReferee().getId(),
                    GameUtil.formatDateTime(game.getStartTime()),
                    game.getTop3Winners()
            });
        }
    }

    /**
     * Updates the athlete table.
     */
    private void updateAthleteTable() {
        List<Athlete> athletes = new ArrayList<Athlete>();
        for (Participant participant : participants) {
            if (participant instanceof Athlete) {
                athletes.add((Athlete) participant);
            }
        }
        Collections.sort(athletes);
        athleteTableModel.setRowCount(0);
        for (Athlete athlete : athletes) {
            athleteTableModel.addRow(new Object[]{
                    athlete.getId(),
                    athlete.getName(),
                    athlete.getClass().getSimpleName(),
                    athlete.getAge(),
                    athlete.getState(),
                    athlete.getPoints()
            });
        }
    }

    /**
     * Creates a scroll pane.
     */
    private static JScrollPane createJScrollPane(JComponent component, int width) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setPreferredSize(new Dimension(width, 140));
        return scrollPane;
    }

    public static void main(String[] args) {
        Ozlympic gui = new Ozlympic();
        gui.setVisible(true);
        try {
            gui.setParticipants(GameUtil.loadParticipants(PARTICIPANTS_FILE));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(gui, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessingGameGUIEnhanced extends JFrame {

    private Random rand = new Random();

    // Game state
    private int targetNumber;
    private int rangeMax;         // numbers are 1..rangeMax
    private int maxAttempts;
    private int attemptsLeft;

    // UI components
    private JLabel lblTitle = new JLabel("🎮 GuessMaster");
    private JLabel lblInstruction = new JLabel("Pick a difficulty and guess the number:");
    private JComboBox<String> cmbDifficulty;
    private JTextField txtGuess = new JTextField();
    private JButton btnGuess = new JButton("Guess");
    private JButton btnNewGame = new JButton("New Game");
    private JLabel lblFeedback = new JLabel(" ");
    private JLabel lblAttempts = new JLabel("Attempts left: ");
    private JProgressBar closenessBar = new JProgressBar(0, 100);

    private JPanel mainPanel;

    public GuessingGameGUIEnhanced() {
        super("GuessMaster - Color Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 350);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(150, 50, 50, 50));
        mainPanel.setBackground(new Color(250, 250, 255)); // light blue background
        add(mainPanel);

        initUI();
        setupActions();

        pack();
        setResizable(false);
        newGame(); 
    }

    private void initUI() {
        // Title
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitle.setForeground(new Color(40, 70, 120));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Center controls
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Instruction
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        center.add(lblInstruction, gbc);

        // Difficulty
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        center.add(new JLabel("Difficulty:"), gbc);

        cmbDifficulty = new JComboBox<>(new String[]{
                "Easy (1-10, 5 tries)", "Medium (1-50, 7 tries)", "Hard (1-100, 10 tries)"
        });
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        center.add(cmbDifficulty, gbc);

        // Guess input
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        center.add(new JLabel("Your guess:"), gbc);

        txtGuess.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 2;
        center.add(txtGuess, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        center.add(btnGuess, gbc);

        // Feedback
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        lblFeedback.setHorizontalAlignment(SwingConstants.CENTER);
        lblFeedback.setFont(new Font("SansSerif", Font.BOLD, 14));
        center.add(lblFeedback, gbc);

        // Closeness bar
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        closenessBar.setStringPainted(true);
        center.add(closenessBar, gbc);

        mainPanel.add(center, BorderLayout.CENTER);

        // South panel
        JPanel south = new JPanel(new BorderLayout());
        south.setOpaque(false);
        south.add(lblAttempts, BorderLayout.WEST);
        south.add(btnNewGame, BorderLayout.EAST);
        mainPanel.add(south, BorderLayout.SOUTH);
    }

    private void setupActions() {
        txtGuess.addActionListener(e -> btnGuess.doClick());
        btnGuess.addActionListener(e -> onGuessPressed());
        btnNewGame.addActionListener(e -> newGame());
    }

    private void onGuessPressed() {
        String raw = txtGuess.getText().trim();
        if (raw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number.");
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid number.");
            txtGuess.requestFocusInWindow();
            return;
        }

        if (guess < 1 || guess > rangeMax) {
            JOptionPane.showMessageDialog(this, "Enter a number between 1 and " + rangeMax);
            txtGuess.requestFocusInWindow();
            return;
        }

        attemptsLeft--;
        updateAttemptsLabel();

        int diff = Math.abs(guess - targetNumber);
        int maxDiff = Math.max(1, rangeMax - 1);
        int closenessPercent = Math.max(0, 100 - (diff * 100 / maxDiff));
        closenessBar.setValue(closenessPercent);
        closenessBar.setString("Closeness: " + closenessPercent + "%");

        if (guess == targetNumber) {
            lblFeedback.setText("🎉 Correct! The number was " + targetNumber);
            lblFeedback.setForeground(new Color(0, 100, 0));
            mainPanel.setBackground(new Color(200, 255, 200)); // light green
            endGame(true);
        } else {
            if (guess > targetNumber) {
                lblFeedback.setText("⬆ Too High!");
                lblFeedback.setForeground(Color.RED);
                mainPanel.setBackground(new Color(255, 220, 220)); // light red
            } else {
                lblFeedback.setText("⬇ Too Low!");
                lblFeedback.setForeground(new Color(0, 0, 150));
                mainPanel.setBackground(new Color(220, 230, 255)); // light blue
            }

            if (attemptsLeft <= 0) {
                lblFeedback.setText("💀 Game Over! The number was " + targetNumber);
                lblFeedback.setForeground(Color.BLACK);
                mainPanel.setBackground(new Color(220, 220, 220)); // grey
                endGame(false);
            }
        }

        txtGuess.setText("");
        txtGuess.requestFocus();
    }

    private void endGame(boolean won) {
        txtGuess.setEditable(false);
        btnGuess.setEnabled(false);

        SwingUtilities.invokeLater(() -> {
            String msg = won ? "🎉 You Win! Play again?" : "💀 Game Over! The number was " + targetNumber + "\nPlay again?";
            int option = JOptionPane.showOptionDialog(
                    this, msg, "Game Result",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new String[]{"Play Again", "Exit"}, "Play Again"
            );
            if (option == JOptionPane.YES_OPTION) {
                newGame();
            } else {
                System.exit(0);
            }
        });
    }

    private void updateAttemptsLabel() {
        lblAttempts.setText("Attempts left: " + attemptsLeft + " (Range 1-" + rangeMax + ")");
    }

    private void newGame() {
        int sel = cmbDifficulty.getSelectedIndex();
        if (sel == 0) { rangeMax = 10; maxAttempts = 5; }
        else if (sel == 1) { rangeMax = 50; maxAttempts = 7; }
        else { rangeMax = 100; maxAttempts = 10; }

        targetNumber = rand.nextInt(rangeMax) + 1;
        attemptsLeft = maxAttempts;

        lblFeedback.setText("New game started! Make your guess.");
        lblFeedback.setForeground(Color.DARK_GRAY);
        closenessBar.setValue(0);
        closenessBar.setString("Closeness: 0%");

        txtGuess.setEditable(true);
        btnGuess.setEnabled(true);
        txtGuess.setText("");
        txtGuess.requestFocus();

        mainPanel.setBackground(new Color(230, 240, 255)); 

        updateAttemptsLabel();

        System.out.println("[DEBUG] Target: " + targetNumber);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuessingGameGUIEnhanced().setVisible(true));
    }
}

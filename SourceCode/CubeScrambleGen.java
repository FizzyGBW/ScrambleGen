package SourceCode;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CubeScrambleGen extends JFrame {

    private static final String[] MOVES_2X2 = {"U", "U'", "D", "D'", "R", "R'", "L", "L'", "B", "B'", "F", "F'"};
    private static final String[] MOVES_3X3 = {"U", "U'", "D", "D'", "R", "R'", "L", "L'", "B", "B'", "F", "F'"};
    private static final String[] MOVES_4X4 = {"U", "U'", "D", "D'", "R", "R'", "L", "L'", "B", "B'", "F", "F'", "u", "u'", "f", "f'", "b", "b'", "r", "r'", "l", "l'", "d", "d'"};
    private static final String[] MOVES_PYRAMIX = {"U", "U'", "u", "u'", "R", "R'", "r", "r'", "L", "L'", "l", "l'", "B", "B'", "b", "b'"};
    private static final String[] MOVES_CLOCK = {
            "RU+", "RU'", "RU", "RU-",
            "RD+", "RD'", "RD", "RD-",
            "LU+", "LU'", "LU", "LU-",
            "LD+", "LD'", "LD", "LD-"
    };
    private static final String[] MOVES_5X5 = {
            "FM", "FM'", "RM", "RM'", "LM", "LM'", "UM", "UM'", "BM", "BM'", "DM", "DM'"
    };
    private static final String[] MOVES_6X6 = {
            "Bb", "Bb'", "Uu", "Uu'", "Ff", "Ff'", "Rr", "Rr'", "Ll", "Ll'", "Dd", "Dd'"
    };
    private static final String[] MOVES_7X7 = {
            "Bb", "Bb'", "Uu", "Uu'", "Ff", "Ff'", "Rr", "Rr'", "Ll", "Ll'", "Dd", "Dd'",
            "FM", "FM'", "RM", "RM'", "LM", "LM'", "UM", "UM'", "BM", "BM'", "DM", "DM'"
    };
    private static final String[] MOVES_SKEWB = {"F", "F'", "R", "R'", "B", "B'", "L", "L'"};
    private static final String[] MOVES_MEGAMINX = {
            "R", "L", "U", "D", "F", "B", "R'", "L'", "U'", "D'", "F'", "B'",
            "MR", "ML", "MU", "MD", "MF", "MB", "MR'", "ML'", "MU'", "MD'", "MF'", "MB'"
    };

    public CubeScrambleGen() {
        setTitle("Rubik's Cube Scramble Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton scrambleButton = new JButton("Generate Scramble");
        JLabel scrambleLabel = new JLabel();

        Font font = new Font("Arial", Font.PLAIN, 50);
        scrambleLabel.setFont(font);

        scrambleLabel.setHorizontalAlignment(JLabel.CENTER);

        JComboBox<String> cubeSizeComboBox = new JComboBox<>(new String[]{"2x2", "3x3", "4x4", "5x5", "6x6", "7x7", "Pyramix", "Clock", "Skewb", "Megaminx"});
        cubeSizeComboBox.setSelectedItem("3x3");

        scrambleButton.addActionListener(e -> {
            String cubeType = (String) cubeSizeComboBox.getSelectedItem();
            String scramble = generateScramble(cubeType);
            scrambleLabel.setText("<html><div style='text-align: center;'>" + scramble + "</div></html>");
        });

        this.setPreferredSize(new Dimension(1024, 524));
        scrambleButton.setFont(font);
        scrambleButton.setMaximumSize(new Dimension(100, 100));
        scrambleButton.setPreferredSize(new Dimension(100, 100));
        scrambleButton.setSize(new Dimension(100, 100));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(cubeSizeComboBox, BorderLayout.PAGE_START);
        panel.add(scrambleButton, BorderLayout.SOUTH);
        panel.add(scrambleLabel, BorderLayout.CENTER);

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String generateScramble(String cubeType) {
        String[] moves;
        int numberOfMoves = 20; 

        if ("2x2".equals(cubeType)) {
            moves = MOVES_2X2;
            numberOfMoves = 10;     
        } else if ("3x3".equals(cubeType)) {
            moves = MOVES_3X3;
        } else if ("4x4".equals(cubeType) || "5x5".equals(cubeType) || "6x6".equals(cubeType) || "7x7".equals(cubeType)) {
            moves = MOVES_4X4;
            if ("5x5".equals(cubeType)) {
                moves = concatenateArrays(moves, MOVES_5X5);
            } else if ("6x6".equals(cubeType)) {
                moves = concatenateArrays(moves, MOVES_6X6);
            } else if ("7x7".equals(cubeType)) {
                moves = concatenateArrays(moves, MOVES_7X7);
            }
        } else if ("Pyramix".equals(cubeType)) {
            moves = MOVES_PYRAMIX;
        } else if ("Clock".equals(cubeType)) {
            moves = MOVES_CLOCK;
        } else if ("Skewb".equals(cubeType)) {
            moves = MOVES_SKEWB;
        } else if ("Megaminx".equals(cubeType)) {
            moves = MOVES_MEGAMINX;
        } else {
            throw new IllegalArgumentException("Invalid cube type");
        }

        StringBuilder scramble = new StringBuilder();
        Random random = new Random();

        String lastMove = "";
        String currentMove;

        for (int i = 0; i < numberOfMoves; i++) {
            int randomMoveIndex = random.nextInt(moves.length);
            currentMove = moves[randomMoveIndex];

            while (currentMove.equals(lastMove) || currentMove.equals(lastMove.replace("'", "")) || lastMove.equals(currentMove.replace("'", ""))) {
                randomMoveIndex = random.nextInt(moves.length);
                currentMove = moves[randomMoveIndex];
            }

            scramble.append(currentMove).append(" ");
            lastMove = currentMove;
        }

        return scramble.toString().trim();
    }

    private String[] concatenateArrays(String[] array1, String[] array2) {
        String[] result = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CubeScrambleGen::new);
    }
}

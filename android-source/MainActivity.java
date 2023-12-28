package de.damian.cubescramble;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Spinner cubeSizeSpinner;
    private TextView scrambleTextView;

    private static final String[] MOVES_2X2 = {"U", "U'", "D", "D'", "R", "R'", "L", "L'", "B", "B'", "F", "F'"};
    private static final String[] MOVES_3X3 = {"U", "U'", "D", "D'", "R", "R'", "L", "L'", "B", "B'", "F", "F'"};
    private static final String[] MOVES_4X4 = {
            "U", "U'", "D", "D'", "R", "R'", "L", "L'", "B", "B'", "F", "F'",
            "u", "u'", "f", "f'", "b", "b'", "r", "r'", "l", "l'", "d", "d'"
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
    private static final String[] MOVES_PYRAMIX = {
            "U", "U'", "u", "u'", "R", "R'", "r", "r'", "L", "L'", "l", "l'", "B", "B'", "b", "b'"
    };
    private static final String[] MOVES_CLOCK = {
            "RU+", "RU'", "RU", "RU-",
            "RD+", "RD'", "RD", "RD-",
            "LU+", "LU'", "LU", "LU-",
            "LD+", "LD'", "LD", "LD-"
    };
    private static final String[] MOVES_SKEWB = {"F", "F'", "R", "R'", "B", "B'", "L", "L'"};
    private static final String[] MOVES_MEGAMINX = {
            "R", "L", "U", "D", "F", "B", "R'", "L'", "U'", "D'", "F'", "B'",
            "MR", "ML", "MU", "MD", "MF", "MB", "MR'", "ML'", "MU'", "MD'", "MF'", "MB'"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cubeSizeSpinner = findViewById(R.id.cubeSizeSpinner);
        Button generateButton = findViewById(R.id.generateButton);
        scrambleTextView = findViewById(R.id.scrambleTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cube_sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cubeSizeSpinner.setAdapter(adapter);

        generateButton.setOnClickListener(v -> generateScramble());
    }

    private void generateScramble() {
            String cubeType = cubeSizeSpinner.getSelectedItem().toString();
        String scramble = generateScramble(cubeType);
        scrambleTextView.setText(scramble);
    }

    private String generateScramble(String cubeType) {
        String[] moves;
        int numberOfMoves = 20;

        switch (cubeType) {
            case "2x2":
                moves = MOVES_2X2;
                numberOfMoves = 10;
                break;
            case "3x3":
                moves = MOVES_3X3;
                break;
            case "4x4":
                moves = MOVES_4X4;
                break;
            case "5x5":
                moves = MOVES_5X5;
                numberOfMoves = 22;
                break;
            case "6x6":
                moves = MOVES_6X6;
                numberOfMoves = 25;
                break;
            case "7x7":
                moves = MOVES_7X7;
                numberOfMoves = 30;
                break;
            case "Pyramix":
                moves = MOVES_PYRAMIX;
                break;
            case "Clock":
                moves = MOVES_CLOCK;
                break;
            case "Skewb":
                moves = MOVES_SKEWB;
                break;
            case "Megaminx":
                moves = MOVES_MEGAMINX;
                numberOfMoves = 30;
                break;
            default:
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
}


package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoveCalculator extends SingleStepPieceMoveCalculator {
    @Override
    protected int[][] getMovementDirections() {
        return new int[][]{
                {-2, -1}, {-2, 1},
                {-1, 2}, {1, 2},
                {-1, -2}, {1, -2},
                {2, 1}, {2, -1}
        };
    }
}
package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator extends SlidingPieceMoveCalculator{
    @Override
    protected int[][] getMovementDirections() {
        return new int[][]{
                {-1, 0}, {1, 0},
                {0, -1}, {0, 1}
        };
    }

}

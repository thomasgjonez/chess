package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator extends SlidingPieceMoveCalculator{
    @Override
    protected int[][] getMovementDirections() {
        return new int[][]{
                {-1, 1}, {-1, -1}, {1, 1}, {1, -1}
        };
    }
}
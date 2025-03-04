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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

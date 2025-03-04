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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
package lenzInsanityCleanCrafting.Craft;

import java.util.function.Function;

public enum EdgesDirectionsENUM {
    UP(IndexSelection::decreasedY),
    DOWN(IndexSelection::increasedY),
    LEFT(IndexSelection::decreasedX),
    RIGHT(IndexSelection::increasedX),
    UP_LEFT(IndexSelection::drecreasedXdecreasedY),
    UP_RIGHT(IndexSelection::increasedXDecreasedY),
    DOWN_LEFT(IndexSelection::decreasedXIncreasedY),
    DOWN_RIGHT(IndexSelection::increasedXIncreasedY);

    private final Function<? super IndexSelection, ? extends IndexSelection> directionOperation;
    EdgesDirectionsENUM(Function<? super IndexSelection, ? extends IndexSelection> operation){
        this.directionOperation = operation;
    }

    /**
     * Applies the direction operation to the index selection
     * and returns the new index selection.
     * Operations:
     *  UP: decreasedY
     *  DOWN: increasedY
     *  LEFT: decreasedX
     *  RIGHT: increasedX
     *  UP_LEFT: decreasedBoth
     *  UP_RIGHT: increasedXDecreasedY
     *  DOWN_LEFT: decreasedXIncreasedY
     *  DOWN_RIGHT: increasedBoth
     *
     * @param indexSelection the index selection to apply the operation to
     * @return the new index selection
     */
    public IndexSelection applyIndexChange(IndexSelection indexSelection){
        return directionOperation.apply(indexSelection);
    }

    public EdgesDirectionsENUM getOpposite(){
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP_LEFT -> DOWN_RIGHT;
            case UP_RIGHT -> DOWN_LEFT;
            case DOWN_LEFT -> UP_RIGHT;
            case DOWN_RIGHT -> UP_LEFT;
        };
    }
}

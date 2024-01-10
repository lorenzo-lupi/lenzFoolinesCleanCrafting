package lenzInsanityCleanCrafting.Craft;

public enum PositionOnTheCraftingTableENUM {
    LEFT_UP_CORNER,
    RIGHT_UP_CORNER,
    RIGHT_DOWN_CORNER,
    LEFT_DOWN_CORNER,
    LEFT_EDGE,
    UP_EDGE,
    RIGHT_EDGE,
    DOWN_EDGE,
    MIDDLE;

    public static PositionOnTheCraftingTableENUM fromIndex(IndexSelection indexSelection,
                                                           int height,
                                                           int span){

        if(indexSelection.x() == 0 && indexSelection.y() == 0) return LEFT_UP_CORNER;
        if(indexSelection.x() == span - 1 && indexSelection.y() == 0) return RIGHT_UP_CORNER;
        if(indexSelection.x() == span - 1 && indexSelection.y() == height - 1) return RIGHT_DOWN_CORNER;
        if(indexSelection.x() == 0 && indexSelection.y() == height - 1) return LEFT_DOWN_CORNER;
        if(indexSelection.x() == 0) return LEFT_EDGE;
        if(indexSelection.y() == 0) return UP_EDGE;
        if(indexSelection.x() == span - 1) return RIGHT_EDGE;
        if(indexSelection.y() == height - 1) return DOWN_EDGE;
        return MIDDLE;

    }
}

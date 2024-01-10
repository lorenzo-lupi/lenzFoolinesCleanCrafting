package lenzInsanityCleanCrafting.Craft;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class RecipeGraphNode<T> {
    private final T buildingBlock;
    Map<EdgesDirectionsENUM, RecipeGraphNode<T>> edges;

    public RecipeGraphNode(T buildingBlock){
        if(buildingBlock == null)
            throw new NullPointerException("buildingBlock cannot be null");
        this.buildingBlock = buildingBlock;
        edges = new EnumMap<>(EdgesDirectionsENUM.class);
    }

    public T getBuildingBlock(){
        return buildingBlock;
    }

    public RecipeGraphNode<T> navigateEdges(EdgesDirectionsENUM edgesDirections){
        return edges.get(edgesDirections);
    }

}

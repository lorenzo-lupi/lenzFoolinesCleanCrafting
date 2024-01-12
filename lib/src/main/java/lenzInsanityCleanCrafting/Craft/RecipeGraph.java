package lenzInsanityCleanCrafting.Craft;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A graph that represents a crafting recipe.
 * The root of the graph is the left most, top most slot block.
 * @param <T> the type of the building block
 */
public class RecipeGraph<T> {
    private final int size;
    private final RecipeGraphNode<T> root;

    public RecipeGraph(RecipeGraphNode<T> root, int size){
        this.root = root;
        this.size = size;
    }

    public RecipeGraphNode<T> getRoot(){
        return root;
    }

    public int getSize(){
        return size;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof RecipeGraph<?> that))
            return false;
        if(!(size == that.size && root.equals(that.root))) return false;
        Set<Object> theseVisitedNodes = new HashSet<>();
        Set<Object> thoseVisitedNodes = new HashSet<>();
        LinkedList<RecipeGraphNode<?>> thisQueue = new LinkedList<>();
        LinkedList<RecipeGraphNode<?>> thatQueue = new LinkedList<>();
        thisQueue.add(root);
        thatQueue.add(that.root);
        while(!thisQueue.isEmpty() && !thatQueue.isEmpty()){
            RecipeGraphNode<?> thisNode = thisQueue.poll(),
                                thatNode = thatQueue.poll();
            theseVisitedNodes.add(thisNode);
            thoseVisitedNodes.add(thatNode);
            if(!thisNode.getBuildingBlock().equals(thatNode.getBuildingBlock())) return false;
            for(EdgesDirectionsENUM edgesDirections : EdgesDirectionsENUM.values()){
                RecipeGraphNode<?> thisNextNode = thisNode.navigateEdges(edgesDirections);
                RecipeGraphNode<?> thatNextNode = thatNode.navigateEdges(edgesDirections);
                if(thisNextNode != null && thatNextNode != null
                        && !theseVisitedNodes.contains(thisNextNode)
                        && !thoseVisitedNodes.contains(thatNextNode)){
                    thisQueue.add(thisNextNode);
                    thatQueue.add(thatNextNode);
                }else if(thisNextNode != null || thatNextNode != null) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        Set<RecipeGraphNode<T>> visitedNodes = new HashSet<>();
        LinkedList<RecipeGraphNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            RecipeGraphNode<T> node = queue.poll();
            visitedNodes.add(node);
            hash += node.getBuildingBlock().hashCode();
            for(EdgesDirectionsENUM edgesDirections : EdgesDirectionsENUM.values()){
                hash += edgesDirections.hashCode();
                RecipeGraphNode<T> nextNode = node.navigateEdges(edgesDirections);
                if(nextNode != null && !visitedNodes.contains(nextNode)) queue.add(nextNode);
            }
        }
        return hash;
    }
}

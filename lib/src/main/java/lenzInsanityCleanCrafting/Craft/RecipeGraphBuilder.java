package lenzInsanityCleanCrafting.Craft;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

public class RecipeGraphBuilder<T> {
    private final ArrayList<ArrayList<RecipeGraphNode<T>>> craftingTableMatrix;
    private IndexSelection rootIndex;
    private RecipeGraphNode<T> root;
    private int size;

    //region EdgesDirections
    private final Map<PositionOnTheCraftingTableENUM, Consumer<IndexSelection>> positionOnTheCraftingTableConsumerMap =
            Map.of(PositionOnTheCraftingTableENUM.LEFT_UP_CORNER, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.RIGHT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN_RIGHT, indexSelection);
                    },
                    PositionOnTheCraftingTableENUM.RIGHT_UP_CORNER, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN_LEFT, indexSelection);
                    },
                    PositionOnTheCraftingTableENUM.RIGHT_DOWN_CORNER, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP_LEFT, indexSelection);
                    },
                    PositionOnTheCraftingTableENUM.LEFT_DOWN_CORNER, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.RIGHT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP_RIGHT, indexSelection);
                    },
                    PositionOnTheCraftingTableENUM.LEFT_EDGE, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.RIGHT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP_RIGHT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN_RIGHT, indexSelection);
                    },
                    PositionOnTheCraftingTableENUM.UP_EDGE, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.RIGHT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN_LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN_RIGHT, indexSelection);
                    },
                    PositionOnTheCraftingTableENUM.RIGHT_EDGE, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP_LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN_LEFT, indexSelection);
                    },
                    PositionOnTheCraftingTableENUM.DOWN_EDGE, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.RIGHT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP_LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP_RIGHT, indexSelection);
                    },
                    PositionOnTheCraftingTableENUM.MIDDLE, indexSelection -> {
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.RIGHT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP_LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.UP_RIGHT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN_LEFT, indexSelection);
                        setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM.DOWN_RIGHT, indexSelection);
                    }
            );
    //endregion

    public static <T> RecipeGraphBuilder<T> getLenzInsanityCraftTable(){
        return new RecipeGraphBuilder<T>(3, 3);
    }

    /**
     * Creates a new RecipeGraphBuilder with the specified height and span.
     * The height and span must be positive integers.
     *
     * @param height is the height of the crafting table
     * @param span is the width of the crafting table
     */
    public RecipeGraphBuilder(int height, int span) {
        if (height <= 0 || span <= 0)
            throw new IllegalArgumentException("Height and span must be positive integers");
        this.craftingTableMatrix = new ArrayList<>(span);
        buildMatrix(height, span);

        this.root = null;
        this.size = 0;
    }

    private void buildMatrix(int height, int span) {
        for (int i = 0; i < span; i++) {
            craftingTableMatrix.add(new ArrayList<>(height));
            for (int j = 0; j < height; j++) {
                craftingTableMatrix.get(i).add(null);
            }
        }
    }


    private RecipeGraphNode<T> getCraftingTableMatrixNode(IndexSelection indexSelection) {
        return craftingTableMatrix.get(indexSelection.x()).get(indexSelection.y());
    }

    private void setCraftingTableMatrixNode(IndexSelection indexSelection, RecipeGraphNode<T> recipeGraphNode) {
        craftingTableMatrix.get(indexSelection.x()).set(indexSelection.y(), recipeGraphNode);

    }

    /**
     * Adds a building block to the crafting table matrix.
     * The building block will be added to the specified index selection.
     * If there is already a building block at the specified index selection,
     * the old building block will be replaced by the new one.
     * If the building block is null, a NullPointerException will be thrown.
     * @param buildingBlock the building block to add
     * @param indexSelection the index selection to add the building block to
     */
    public void addBlock(T buildingBlock, IndexSelection indexSelection) {
        if (indexSelection.x() >= craftingTableMatrix.size() || indexSelection.y() >= craftingTableMatrix.get(0).size())
            throw new IllegalArgumentException("Index out of bounds");

        rootReplacement(indexSelection, new RecipeGraphNode<>(buildingBlock));
        if(getCraftingTableMatrixNode(indexSelection) == null) this.size++;

        RecipeGraphNode<T> recipeGraphNode = new RecipeGraphNode<>(buildingBlock);
        setCraftingTableMatrixNode(indexSelection, recipeGraphNode);

        positionOnTheCraftingTableConsumerMap.get(PositionOnTheCraftingTableENUM.fromIndex(indexSelection,
                craftingTableMatrix.size(),
                craftingTableMatrix.get(0).size())).accept(indexSelection);
    }

    private void setGraphNodeEdgeInGivenDirection(EdgesDirectionsENUM edgeDirection, IndexSelection indexSelection) {
        
        if (getCraftingTableMatrixNode(edgeDirection.applyIndexChange(indexSelection)) != null) {
            
            getCraftingTableMatrixNode(indexSelection)
                    .edges.put(edgeDirection, getCraftingTableMatrixNode(edgeDirection.applyIndexChange(indexSelection)));

            getCraftingTableMatrixNode(edgeDirection.applyIndexChange(indexSelection))
                    .edges.put(edgeDirection.getOpposite(), getCraftingTableMatrixNode(indexSelection));

        }
    }

    private void rootReplacement(IndexSelection indexSelection, RecipeGraphNode<T> recipeGraphNode){
        if(this.root == null) {
            this.root = recipeGraphNode;
            this.rootIndex = indexSelection;
        }
        else if(indexSelection.equals(this.rootIndex))
            this.root = recipeGraphNode;
        else if (indexSelection.leftUpMore(this.rootIndex)) {
            this.rootIndex = indexSelection;
            this.root = recipeGraphNode;
        }
    }

    /**
     * Builds the RecipeGraph.
     * @return the RecipeGraph if all the nodes are linked, null otherwise
     */
    public RecipeGraph<T> build(){
        if(isAllLinked())
            return new RecipeGraph<>(this.root, this.size);
        else
            return null;
    }

    //O(n) where n is the number of nodes reachable from the root :(
    private boolean isAllLinked(){
        int counter = 0;
        LinkedList<RecipeGraphNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            RecipeGraphNode<T> node = queue.poll();
            counter++;
            for(EdgesDirectionsENUM edgesDirections : EdgesDirectionsENUM.values()){
                RecipeGraphNode<T> nextNode = node.navigateEdges(edgesDirections);
                if(nextNode != null) queue.add(nextNode);
            }
        }
        return counter == this.size;
    }

}

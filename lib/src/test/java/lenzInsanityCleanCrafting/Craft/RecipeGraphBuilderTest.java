package lenzInsanityCleanCrafting.Craft;

import lenzInsanityCleanCrafting.CraftingBlockENUM;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeGraphBuilderTest {
    @Test
    void testBuildGraph(){
        RecipeGraphBuilder<CraftingBlockENUM> recipeGraphBuilder = new RecipeGraphBuilder<>(3, 3);
        recipeGraphBuilder.addBlock(CraftingBlockENUM.WOODEN_PLANK, new IndexSelection(1,1));
        recipeGraphBuilder.addBlock(CraftingBlockENUM.BRICK, new IndexSelection(2, 0));
        recipeGraphBuilder.addBlock(CraftingBlockENUM.BRICK, new IndexSelection(2, 1));
        recipeGraphBuilder.addBlock(CraftingBlockENUM.BRICK, new IndexSelection(2, 2));
        RecipeGraph<CraftingBlockENUM> graph1 = recipeGraphBuilder.build();
        recipeGraphBuilder.reset();

        recipeGraphBuilder.addBlock(CraftingBlockENUM.BRICK, new IndexSelection(2, 2));
        recipeGraphBuilder.addBlock(CraftingBlockENUM.BRICK, new IndexSelection(2, 1));
        recipeGraphBuilder.addBlock(CraftingBlockENUM.BRICK, new IndexSelection(2, 0));
        recipeGraphBuilder.addBlock(CraftingBlockENUM.WOODEN_PLANK, new IndexSelection(1,1));
        RecipeGraph<CraftingBlockENUM> graph2 = recipeGraphBuilder.build();
        assertEquals(graph1, graph2);
    }
}
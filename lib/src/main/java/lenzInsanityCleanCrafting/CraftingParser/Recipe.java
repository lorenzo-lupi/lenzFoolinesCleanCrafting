package lenzInsanityCleanCrafting.CraftingParser;

import lenzInsanityCleanCrafting.Craft.RecipeGraph;

public record Recipe<T>(T output,
                     int outputAmount) {
    public Recipe {
        if(outputAmount < 0 || output == null)
            throw new IllegalArgumentException("outputAmount must be positive, output and graphNode must not be null");
    }

}

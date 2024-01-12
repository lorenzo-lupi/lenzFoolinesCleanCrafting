package lenzInsanityCleanCrafting.CraftingRecipes;
import lenzInsanityCleanCrafting.Craft.RecipeGraph;
import lenzInsanityCleanCrafting.CraftingParser.Recipe;

import java.util.HashMap;
import java.util.Map;

public class CraftingRecipes<E extends Enum<E>>{
    private static Map<? super Class,
            CraftingRecipes> recipesMap = new HashMap<>();
    private Map<RecipeGraph<E>, Recipe<E>> recipes;
    private CraftingRecipes(Object recipes) {
        this.recipes = (Map<RecipeGraph<E>, Recipe<E>>)recipes;
    }

    public Recipe<E> getBlockFromRecipe(RecipeGraph<E> graph){
        return recipes.get(graph);
    }

    public static <T extends Enum<T>> void setRecipe(Class<T> enumClass,
                                 Map<RecipeGraph<T>, Recipe<T>> recipesMap){


        CraftingRecipes<T> craftingRecipes = new CraftingRecipes<>(recipesMap);
        CraftingRecipes.recipesMap.put(enumClass, craftingRecipes);
    }

    public static <T extends Enum<T>> Recipe<T> getRecipe(Class<T> enumClass, RecipeGraph<T> graph){
        return ((CraftingRecipes<T>)recipesMap.get(enumClass)).getBlockFromRecipe(graph);
    }
}

package lenzInsanityCleanCrafting.CraftingParser;

import lenzInsanityCleanCrafting.Craft.IndexSelection;
import lenzInsanityCleanCrafting.Craft.RecipeGraph;
import lenzInsanityCleanCrafting.Craft.RecipeGraphBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


/** Language spec for 3x3 crafting table
 * S -> {NAME \n
 * NUMBER
 * X X X
 * X X X
 * X X X
 * }S'
 * S' -> EOF | ,S
 * NAME -> [A-Z_][a-z_]+
 * NUMBER -> [0-9]+
 * X -> NAME | 0
 */
public class CraftingParser<E extends Enum<E>> {
    private final BufferedReader reader;
    private int currentLine;
    private Map<RecipeGraph<E>, Recipe<E>> recipes;
    private Class<E> enumClass;
    private RecipeGraphBuilder<E> graphBuilder;

    public CraftingParser(Reader reader,
                          Class<E> enumClass,
                          RecipeGraphBuilder<E> graphBuilder) {
        if (reader == null)
            throw new NullPointerException("fileReader cannot be null");
        this.reader = new BufferedReader(reader);
        this.currentLine = 0;
        this.recipes = new HashMap<>();
        this.enumClass = enumClass;
        this.graphBuilder = graphBuilder;
    }

    public Map<RecipeGraph<E>, Recipe<E>> parse() throws IOException, ParsingException {
        readBlock();
        while (reader.read() == ',' )
            readBlock();
        return this.recipes;
    }

    private void readBlock() throws IOException, ParsingException {
        if (reader.read() != '{')
            throw new ParsingException("Syntax error at line " + this.currentLine + ": expected '{'");
        E blockName = readBlockName();
        recipes.put(readBlockMatrix(), new Recipe<>(blockName,  readOutputNumber()));
        if (reader.read() != '}')
            throw new ParsingException("Syntax error at line " + this.currentLine + ": expected '}'");
    }

    private E readBlockName() throws IOException, ParsingException {
        currentLine++;
        String name = reader.readLine().trim();
        try {
            return Enum.valueOf(enumClass, name);
        } catch (IllegalArgumentException e) {
            throw new ParsingException("Syntax error at line " + this.currentLine + ": expected block name");
        }
    }

    private int readOutputNumber() throws IOException, ParsingException {
        currentLine++;
        String number = (reader.readLine()).trim();
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Syntax error at line " + this.currentLine + ": expected number");
        }
    }

    private RecipeGraph<E> readBlockMatrix() throws IOException, ParsingException {
        for (int i = 0; i < graphBuilder.getCraftingTableHeight(); i++) readBlockMatrixLine(graphBuilder, i);
        return graphBuilder.build();
    }

    private void readBlockMatrixLine(RecipeGraphBuilder<E> builder, int lineIndex) throws IOException, ParsingException {
        currentLine++;
        String[] blocks = reader.readLine().split(" ");
        if (blocks.length != builder.getCraftingTableSpan())
            throw new ParsingException("Syntax error at line " + this.currentLine + ": expected "+builder.getCraftingTableSpan()+" blocks");

        for (int i = 0; i < builder.getCraftingTableSpan(); i++) {
            String blockName = blocks[i].trim();
            if (!blockName.equals("0")) {
                try {
                    builder.addBlock(Enum.valueOf(enumClass, blockName), new IndexSelection(i, lineIndex));
                } catch (IllegalArgumentException e) {
                    throw new ParsingException("Syntax error at line " + this.currentLine + ": expected block name");
                }
            }
        }
    }


}

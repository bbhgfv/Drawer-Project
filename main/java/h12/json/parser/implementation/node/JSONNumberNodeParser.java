package h12.json.parser.implementation.node;

import h12.exceptions.InvalidNumberException;
import h12.json.JSONNumber;
import h12.json.implementation.node.JSONNumberNode;

import java.io.IOException;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A parser based on a node implementation that parses a {@link h12.json.JSONNumber}.
 *
 * <p> Example:
 * <p> Input: -69.420
 * <p> Output: {@code JSONNumber.of(-69.420)}
 */
public class JSONNumberNodeParser implements JSONNodeParser {

    private final JSONElementNodeParser parser;

    /**
     * Creates a new {@link JSONNumberNodeParser}-Instance.
     *
     * @param parser The main {@link JSONElementNodeParser}.
     */
    public JSONNumberNodeParser(JSONElementNodeParser parser) {
        this.parser = parser;
    }

    /**
     * Parses a {@link JSONNumber}.
     *
     * @return The parsed {@link JSONNumberNode}.
     * @throws IOException            If an {@link IOException} occurs while reading the contents of the reader.
     * @throws InvalidNumberException If the parsed {@link JSONNumber} is invalid.
     */
    @Override
    public JSONNumberNode parse() throws IOException, InvalidNumberException {
        boolean isDouble = false;
        String string;
        string=parser.readUntil(i -> i == -1 || (i !='+' && i!='-' && i!='.'&& !Character.isDigit(i)));
        char[] chars = string.toCharArray();
        for(char i : chars){
            if(i=='.'){
                isDouble=true;
            }
        }
        try {
            if (isDouble) {
                return new JSONNumberNode(Double.parseDouble(string));
            } else {
                return new JSONNumberNode(Integer.parseInt(string));
            }
        }
        catch (NumberFormatException e){
            throw new InvalidNumberException(string);
        }
    }

}

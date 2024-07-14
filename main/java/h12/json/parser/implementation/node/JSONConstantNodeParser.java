package h12.json.parser.implementation.node;

import h12.exceptions.InvalidConstantException;
import h12.json.JSONConstant;
import h12.json.JSONConstants;
import h12.json.implementation.node.JSONConstantNode;

import java.io.IOException;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A parser based on a node implementation that parses a {@link h12.json.JSONConstant}.
 * <p>
 * <p> Example:
 * <p> Input: false
 * <p> Output: {@code JSONConstant.FALSE}
 */
public class JSONConstantNodeParser implements JSONNodeParser {

    private final JSONElementNodeParser parser;

    /**
     * Creates a new {@link JSONConstantNodeParser}-Instance.
     *
     * @param parser The main {@link JSONElementNodeParser}.
     */
    public JSONConstantNodeParser(JSONElementNodeParser parser) {
        this.parser = parser;
    }

    /**
     * Parses a {@link JSONConstant}.
     *
     * @return The parsed {@link JSONConstantNode}.
     * @throws IOException              If an {@link IOException} occurs while reading the contents of the reader.
     * @throws InvalidConstantException If the read {@link JSONConstant} is not a valid constant as defined in {@link JSONConstants}.
     */
    @Override
    public JSONConstantNode parse() throws IOException, InvalidConstantException {
        String string;
        string = parser.readUntil(i -> i == -1 || !Character.isLetter(i));
        if(string.equals("false")){
            return new JSONConstantNode(JSONConstants.FALSE);
        } else if (string.equals("true")) {
            return new JSONConstantNode(JSONConstants.TRUE);
        } else if (string.equals("null")) {
            return new JSONConstantNode(JSONConstants.NULL);
        }
        else {
            throw new InvalidConstantException(string);
        }
    }

}

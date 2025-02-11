package h12.json.parser.implementation.node;

import h12.exceptions.BadFileEndingException;
import h12.exceptions.JSONParseException;
import h12.json.JSONArray;
import h12.json.JSONElement;
import h12.json.implementation.node.JSONStringNode;

import java.io.IOException;

import static h12.json.JSONObject.JSONObjectEntry;
import static h12.json.implementation.node.JSONObjectNode.JSONObjectEntryNode;
import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A parser based on a node implementation that parses a {@link JSONObjectEntry}.
 *
 * <p> Example:
 * <P> Input:
 * <pre>
 * "abc": 69</pre>
 * <p> Output: {@code JSONObjectEntry.of("abc", JSONNumber.of(69))}
 */
public class JSONObjectEntryNodeParser implements JSONNodeParser {

    private final JSONElementNodeParser parser;

    /**
     * Creates a new {@link JSONObjectEntryNodeParser}-Instance.
     *
     * @param parser The main {@link JSONElementNodeParser}.
     */
    public JSONObjectEntryNodeParser(JSONElementNodeParser parser) {
        this.parser = parser;
    }

    /**
     * Parses a {@link JSONObjectEntry}.
     *
     * @return The parsed {@link JSONObjectEntryNode}.
     * @throws IOException            If an {@link IOException} occurs while reading the contents of the reader.
     * @throws BadFileEndingException If the reader ends before the {@link JSONObjectEntry} is completed.
     * @throws JSONParseException     If the parsed {@link JSONArray} is invalid in any other way.
     */
    @Override
    public JSONObjectEntryNode parse() throws IOException, JSONParseException {
        JSONStringNodeParser jsonStringNodeParser = parser.getStringParser();
        JSONStringNode jsonStringNode = jsonStringNodeParser.parse();
        parser.accept(':');
        JSONElement jsonElement = parser.parse();
        if(jsonElement==null){
            throw new BadFileEndingException();
        }
        return new JSONObjectEntryNode(jsonStringNode,jsonElement);
    }
}

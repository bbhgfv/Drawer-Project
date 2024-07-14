package h12.json.parser.implementation.node;

import h12.exceptions.BadFileEndingException;
import h12.exceptions.JSONParseException;
import h12.exceptions.TrailingCommaException;
import h12.exceptions.UnexpectedCharacterException;
import h12.json.JSONArray;
import h12.json.JSONElement;
import h12.json.implementation.node.JSONArrayNode;
import h12.json.implementation.node.JSONNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A parser based on a node implementation that parses a {@link h12.json.JSONArray}.
 *
 * <p> Example:
 * <p> Input:
 * <pre>
 * [
 *   420,
 *   "abc"
 * ]</pre>
 * <p> Output: {@code JSONArray.of(JSONNumber.of(420), JSONString.of("abc"))}
 */
public class JSONArrayNodeParser implements JSONNodeParser {

    private final JSONElementNodeParser parser;

    /**
     * Creates a new {@link JSONArrayNodeParser}-Instance.
     *
     * @param parser The main {@link JSONElementNodeParser}.
     */
    public JSONArrayNodeParser(JSONElementNodeParser parser) {
        this.parser = parser;
    }

    /**
     * Parses a {@link JSONArray}.
     *
     * @return The parsed {@link JSONArrayNode}.
     * @throws IOException            If an {@link IOException} occurs while reading the contents of the reader.
     * @throws TrailingCommaException If the parsed {@link JSONArray} contains a trailing comma.
     * @throws BadFileEndingException If the reader ends before the {@link JSONArray} is completed.
     * @throws JSONParseException     If the parsed {@link JSONArray} is invalid in any other way.
     */
    @Override
    public JSONArrayNode parse() throws IOException, JSONParseException {
        List<JSONElement> jsonElementList = new ArrayList<>();
        parser.accept('[');
        JSONArrayNode arrayNode;
        while ((char)parser.peek() != ']'){
            JSONElement element=parser.parse();
            if(element==null){
                throw new BadFileEndingException();
            }
            jsonElementList.add(element);
            if (parser.peek()==-1) {
                throw new BadFileEndingException();
            }
            if((char)parser.peek()==','){
                parser.accept(',');
                if(parser.peek()==']'){
                    throw new TrailingCommaException();
                }
            }
            else if((char)parser.peek()==']'){
                break;
            } else{
                parser.accept(']');
            }
        }
        parser.accept(']');
        arrayNode = new JSONArrayNode(jsonElementList);
        return arrayNode;

    }

}

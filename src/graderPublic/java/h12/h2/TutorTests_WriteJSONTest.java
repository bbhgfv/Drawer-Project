package h12.h2;

import h12.json.JSONElement;
import h12.json.implementation.node.JSONNumberNode;
import h12.json.implementation.node.JSONStringNode;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.function.Consumer;

import static h12.json.implementation.node.JSONObjectNode.JSONObjectEntryNode;
import static org.mockito.Mockito.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

public class TutorTests_WriteJSONTest {

    public void testWriteJSONNode(JSONElement element, String expected, int indentation) throws Throwable {
        testWriteJSONNode(element, expected, indentation, null);
    }

    public void testWriteJSONNode(JSONElement element, String expected, int indentation, Consumer<Context> verifier) throws Throwable {
        Context context = contextBuilder()
            .add("input", expected)
            .add("indentation", indentation)
            .subject(element.getClass().getSimpleName() + "#write(BufferedWriter, int)")
            .build();

        String actual = getActual(element, indentation, context);

        String message = "Methode did not write the correct String to the BufferedWriter.";

        if (actual.replace(" ", "").replace("\n", "")
            .equals(expected.replace(" ", "").replace("\n", ""))) {
            message += " The contents of the expected and actual json element are equal but the whitespaces do not match.";
        }

        String finalMessage = message;
        assertEquals(createJSONString(expected), createJSONString(actual), context, TR -> finalMessage);

        if (verifier != null) verifier.accept(context);
    }

    public String createJSONString(String json) {
        return "\\<span style=\"white-space: pre;\"\\>\n" + json + "\n\\</span\\>";
    }

    public String getActual(JSONElement element, int indentation, Context context) throws IOException {
        StringWriter writer = new StringWriter();
        try (BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            call(() -> element.write(bufferedWriter, indentation), context, TR -> "Unexpected exception was thrown");
        }

        return writer.getBuffer().toString();
    }

    public JSONNumberNode createMockedJSONNumber(Number number) {
        return spy(new JSONNumberNode(number));
    }

    public JSONStringNode createMockedJSONString(String string) {
        return spy(new JSONStringNode(string));
    }

    public JSONObjectEntryNode createMockedJSONObjectEntry(JSONStringNode jsonStringNode, JSONElement jsonElement) {
        return spy(new JSONObjectEntryNode(jsonStringNode, jsonElement));
    }

    protected Consumer<Context> createVerifier(JSONElement jsonElement, int count) {
        return context -> {
            try {
                verify(jsonElement, times(count)).write(any(), anyInt());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (MockitoAssertionError e) {
                fail(context, TR -> "Expected the method write of the object " + jsonElement.toString() +
                    " to be called exactly " + count + " times but it wasn't.\n Original message: " + e.getMessage());
            }
        };
    }
}

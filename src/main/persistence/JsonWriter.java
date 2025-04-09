package persistence;

import model.*;

import org.json.JSONObject;

import java.io.*;

// Writes a file to save the current restaurant database inputs.
// Code below is modelled after `JsonWriter` class in `persistance` package of JsonSerializationDemo.
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /*
     * MODIFIES: this
     * EFFECTS: opens writer;
     *          throws FileNotFoundException if destination file cannot be opened for writing
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /*
     * MODIFIES: this
     * EFFECTS: writes a JSON file to represent restaurant database
     */
    public void write(Menu menu, Tabs system) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("MENU", menu.toJson());
        jsonObject.put("P.O.S. SYSTEM", system.toJson());

        saveToFile(jsonObject.toString(TAB));
    }

    /*
     * MODIFIES: this
     * EFFECTS: closes writer
     */
    public void close() {
        writer.close();
    }

    /*
     * MODIFIES: this
     * EFFECTS: writes string to file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}

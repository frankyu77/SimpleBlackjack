package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// code taken from JsonSerializationDemo from Phase 2 by the 210 team!!
// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to detination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer. If the destination file cannot be opened then throw FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of the workroom to a file
    //public void write(WorkRoom workroom) {

    //}

}

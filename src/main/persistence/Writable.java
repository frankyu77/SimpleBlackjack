package persistence;

import org.json.JSONObject;

// code taken from JsonSerializationDemo from Phase 2 by the 210 team!!
public interface Writable {
    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}

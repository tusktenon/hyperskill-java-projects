package shared;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public record Response(
        @SerializedName("response") Status status, JsonElement value, String reason) {

    public enum Status {OK, ERROR}

    public static Response ok() {
        return new Response(Status.OK, null, null);
    }

    public static Response ok(JsonElement value) {
        return new Response(Status.OK, value, null);
    }

    public static Response err(String reason) {
        return new Response(Status.ERROR, null, reason);
    }
}

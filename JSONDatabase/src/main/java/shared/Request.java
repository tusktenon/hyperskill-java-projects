package shared;

import com.google.gson.JsonElement;

public record Request(Type type, JsonElement key, JsonElement value) {

    public enum Type {get, set, delete, exit}
}

package shared;

public record Request(Type type, String key, String value) {

    public enum Type {get, set, delete, exit}
}

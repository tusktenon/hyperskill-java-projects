package shared;

public record Request(Type type, Integer index, String content) {

    public enum Type {
        get, set, delete, exit;

        int parameters() {
            return switch (this) {
                case get, delete -> 1;
                case set -> 2;
                case exit -> 0;
            };
        }
    }

    public static class RequestFormatException extends RuntimeException {
        public RequestFormatException(String message) {
            super(message);
        }
    }

    public static Request parse(String message) {
        String[] tokens = message.split("\\s+", 3);
        try {
            Type type = Type.valueOf(tokens[0]);
            if (tokens.length != 1 + type.parameters()) {
                throw new RequestFormatException(
                        "Request type '%s' requires %d parameters; got %d".formatted(
                                type, type.parameters(), tokens.length));
            }
            Integer index = tokens.length > 1 ? Integer.parseInt(tokens[1]) : null;
            String content = tokens.length == 3 ? tokens[2] : null;
            return new Request(type, index, content);
        } catch (NumberFormatException e) {
            throw new RequestFormatException("Non-integer index value");
        } catch (IllegalArgumentException e) {
            throw new RequestFormatException("Unknown request type '%s'".formatted(tokens[0]));
        }
    }

    @Override
    public String toString() {
        return switch (type) {
            case get -> "get " + index;
            case set -> "set " + index + " " + content;
            case delete -> "delete " + index;
            case exit -> "exit";
        };
    }
}

package protocol;

import java.io.Serializable;

public record Response(String status, Double result) implements Serializable {
    public static Response ok(Double value) {
        return new Response("OK", value);
    }

    public static Response invalid() {
        return new Response("Invalid", null);
    }

    public static Response unsupported() {
        return new Response("Unsupported", null);
    }
}

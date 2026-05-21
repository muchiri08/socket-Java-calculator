package protocol;

import java.io.Serializable;

public record Request(Double num1, Double num2, String operator) implements Serializable {
}

package src.validators;

import java.util.List;

public interface Validador<T> {
    List<String> validar(T objeto);
}

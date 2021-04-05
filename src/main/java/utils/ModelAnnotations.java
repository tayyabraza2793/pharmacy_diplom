package utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ModelAnnotations {
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Order {
        int value();
    }
}

package utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GetSet {
    public List<Method> getters;
    public List<Method> setters;

    public GetSet() {
        getters = new ArrayList<>();
        setters = new ArrayList<>();
    }
}

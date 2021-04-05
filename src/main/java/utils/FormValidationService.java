package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormValidationService {
    public ArrayList<String> validate(HashMap<String, String[]> dict) {
        ArrayList<String> exceptions = new ArrayList<>();
        for (Map.Entry<String, String[]> pair : dict.entrySet()) {
            if (pair.getValue()[0] == null || pair.getValue()[0].isEmpty())
                exceptions.add(pair.getKey() + " not valid");
        }
        return exceptions;
    }

    public String validatePassword(String password, String confirmPassword) {
        if (password != null && !password.isEmpty() && confirmPassword != null && !confirmPassword.isEmpty()) {
            if (password.equals(confirmPassword)) return null;
            else return "Password and confirm password should match";
        } else return "Input not valid";
    }
}

package ru.rtksoftlabs.LicenseCommons.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class CustomCase extends PropertyNamingStrategy.PropertyNamingStrategyBase {
    @Override
    public String translate(String input) {
        if (input == null) {
            return input;
        }
        else {
            char[] arr = input.toCharArray();
            StringBuilder result = new StringBuilder();
            result.append(arr[0]);

            for (int i = 1; i < arr.length; i++) {
                if (Character.isUpperCase(arr[i])) {
                    if (Character.isUpperCase(arr[i-1])) {
                        result.append(Character.toLowerCase(arr[i]));
                    }
                    else {
                        result.append("." + Character.toLowerCase(arr[i]));
                    }
                } else {
                    result.append(arr[i]);
                }
            }
            return result.toString();
        }
    }
}

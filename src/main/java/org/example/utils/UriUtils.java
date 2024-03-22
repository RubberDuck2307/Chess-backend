package org.example.utils;

import java.net.URI;

public class UriUtils {

    public static String extractPathAttributes(URI uri, String attribute) {
        String path = uri.toString();
        String att = "?" + attribute + "=";
        String path2 = extractAttribute(path, att);
        if (path2 != null) return path2;
        att = "&" + attribute + "=";
        return extractAttribute(path, att);
    }

    private static String extractAttribute(String path, String att) {
        if (path.contains(att)) {
            int start = path.indexOf(att) + att.length();
            int end = path.indexOf("&", start);
            if (end == -1) {
                end = path.length();
            }
            return path.substring(start, end);
        }
        return null;
    }
}

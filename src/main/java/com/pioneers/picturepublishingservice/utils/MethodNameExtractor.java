package com.pioneers.picturepublishingservice.utils;

/**
 * Utility class for extracting the original method name from an exception stack trace.
 *
 * @author esraa
 */
public final class MethodNameExtractor {

    private MethodNameExtractor() {
        throw new AssertionError("Utility class");
    }

    /**
     * Extracts the original method name from the given exception's stack trace.
     *
     * @param e the exception whose stack trace should be analyzed
     * @return the original method name with {@code ()}, or {@code "unknown"} if none found
     */
    public static String extractOriginalMethodName(Exception e) {
        for (StackTraceElement element : e.getStackTrace()) {
            String method = element.getMethodName();
            if (!method.startsWith("lambda") && !method.equals("orElseThrow")) {
                return method + "()";
            }
        }
        return "unknown";
    }
}

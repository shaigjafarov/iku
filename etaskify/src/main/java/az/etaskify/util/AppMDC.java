package az.etaskify.util;

import org.slf4j.MDC;

import java.util.UUID;

public class AppMDC {

    public static String TraceID = "traceID";

    public static void addTraceIDtoLog() {
        String requestId = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        MDC.clear();
        MDC.put(TraceID, requestId);
    }

    public static String getTraceID() {
        return MDC.get(TraceID);
    }

}
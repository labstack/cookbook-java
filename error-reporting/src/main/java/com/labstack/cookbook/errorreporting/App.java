package com.labstack.cookbook.errorreporting;

import com.labstack.Client;
import com.labstack.Fields;
import com.labstack.Log;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;

public class App {
    public static void main(String[] args) {
        // Initialize LabStack client and log service
        Client client = new Client("<ACCOUNT_ID>", "<API_KEY>");
        Log log = client.log();
        log.getFields()
                .add("app_id", "1")
                .add("app_name", "error-reporting");
        log.setDispatchInterval(5);

        port(1323);

        // Routes
        get("/crash", (request, response) -> {
            throw new RuntimeException("fatal error");
        });

        get("/error", (request, response) -> {
            try {
                throw new Exception("non-fatal error");
            } catch (Exception e) {
                log.error(new Fields().add("message", e.getMessage()));
            }
            response.status(204);
            return "";
        });

        // Error handler
        exception(RuntimeException.class, (exception, request, response) -> {
            log.fatal(new Fields()
                    .add("message", exception.getMessage())
                    .add("stack_trace", Log.getStackTrace(exception)));
            response.status(204);
            response.body("error");
        });
    }
}

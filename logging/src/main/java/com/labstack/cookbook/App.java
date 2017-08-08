package com.labstack.cookbook;

import static spark.Spark.post;
import com.labstack.Client;
import com.labstack.Fields;
import com.labstack.Level;
import com.labstack.Log;

public class App {
    public static void main(String[] args) {
        // Initialize LabStack client and log service
        Client client = new Client("<ACCOUNT_ID>", "<API_KEY>");
        Log log = client.log();
        log.setLevel(Level.DEBUG);
        log.getFields()
                .add("app_id", "app1")
                .add("app_name", "app");
        log.setDispatchInterval(5);

        // Routes for each log level
        post("/debug", (request, response) -> {
            response.status(204);
            log.debug(new Fields().add("message", "debug message"));
            return "";
        });

        post("/info", (request, response) -> {
            response.status(204);
            log.info(new Fields().add("message", "info message"));
            return "";
        });

        post("/warn", (request, response) -> {
            response.status(204);
            log.warn(new Fields().add("message", "warn message"));
            return "";
        });

        post("/error", (request, response) -> {
            response.status(204);
            log.error(new Fields().add("message", "error message"));
            return "";
        });

        post("/fatal", (request, response) -> {
            response.status(204);
            log.fatal(new Fields().add("message", "fatal message"));
            return "";
        });
    }
}

package main.java;
import static spark.Spark.*;

public class Main {
    static int counter = 0;
    public static void main(String[] args) {

      staticFiles.externalLocation("public");
      // http://sparkjava.com/documentation
      port(1234);
      // calling get will make your app start listening for the GET path with the /hello endpoint
      get("/counterUpdate", (req, res) -> {
        // Main.counter++;
        return ++Main.counter;
      });

      get("/status", (req, res) -> {
        return Main.counter;
      });
    }
}

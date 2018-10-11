package html;
import static spark.Spark.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.*;

public class Main {

    public static void main(String[] args) {
      port(1234);
      // calling get will make your app start listening for the GET path with the /hello endpoint
      get("/hello", (req, res) -> "Hello World");
    }
}

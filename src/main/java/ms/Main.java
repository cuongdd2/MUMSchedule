package ms;

import static spark.Spark.*;

public class Main {
  public static void main(String[] args) {
    exception(Exception.class, (e, req, res) -> e.printStackTrace()); // print all exceptions
    staticFiles.location("/public");
    port(9860);
    get("/hello", (req, res) -> "Hello World");
  }
}

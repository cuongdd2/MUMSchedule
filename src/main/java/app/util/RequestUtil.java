package app.util;

import spark.Request;

public class RequestUtil {
  public static String getName(Request request) {
    return request.queryParams("name");
  }
}

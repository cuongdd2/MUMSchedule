package app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import spark.Request;

public class RequestUtil {

  public static String getSessionLocale(Request request) {
    return request.session().attribute("locale");
  }
  public static String getSessionCurrentUser(Request request) {
    return request.session().attribute("currentUser");
  }

  public static String getName(Request request) {
    return request.queryParams("name");
  }

  public static String getQueryUsername(Request request) {
        return request.queryParams("username");
    }
    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }
    public static String getQueryLoginRedirect(Request request) {
        return request.queryParams("loginRedirect");
    }

  public static Timestamp getStartDate(Request request) {
    return Timestamp.valueOf(request.queryParams("startDate"));
  }

  public static Map<String, String> body(Request request) {
    String bodyStr = request.body();
    String[] arr = bodyStr.split("&");
    Map<String, String> data = new HashMap<>();
    for (String s : arr) {
      String[] kv = s.split("=");
      try {
        data.put(URLDecoder.decode(kv[0], "utf-8"), URLDecoder.decode(kv[1], "utf-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return data;
  }

  public static boolean removeSessionAttrLoggedOut(Request request) {
      Object loggedOut = request.session().attribute("loggedOut");
      request.session().removeAttribute("loggedOut");
      return loggedOut != null;
  }

    public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }

  public static LocalDate parseDate(String str) {
    return LocalDate.parse(str);
  }
}

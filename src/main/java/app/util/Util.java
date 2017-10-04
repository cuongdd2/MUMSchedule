package app.util;

public class Util {

  public static String name2Email(String name) {
    String[] arr = name.split(" ");
    return (arr[0].charAt(0) + arr[arr.length - 1]).toLowerCase() + "@mum.edu";
  }
}

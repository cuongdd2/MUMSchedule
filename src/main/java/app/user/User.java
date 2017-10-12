package app.user;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class User {
  private int id;
  final private String email;
  final private String password;
}

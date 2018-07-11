import java.io.Serializable;

/**
 * Created by Dominic on 2018-01-10.
 */
public class Account implements Serializable {

  static final long serialVersionUID = 2803084889927077166L;
  private String username;
  private String password;

  public Account(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return username;
  }
}

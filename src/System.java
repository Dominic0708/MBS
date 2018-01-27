import java.util.ArrayList;

/**
 * Created by Dominic on 2018-01-10.
 */
public class System {

  public static ArrayList<Account> accounts = new ArrayList<>();
  public static ArrayList<Client> clients = new ArrayList<>();

  public static boolean successfulLogin(String username, String password) {
    for (Account a : accounts) {
      if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
        return true;
      }
    }
    return false;
  }

  public static Client clientExists(String name) {
    for (Client c : clients) {
      if ((c.getName()).equals(name)) {
        return c;
      }
    }
    return null;
  }

  public static void addClient(Client c) {
    System.clients.add(c);
  }
}
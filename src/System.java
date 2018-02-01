import java.util.ArrayList;

/**
 * Created by Dominic on 2018-01-10.
 */
public class System {

  public static ArrayList<Account> accounts = new ArrayList<>();
  public static ArrayList<Client> clients = new ArrayList<>();
  public static String masterPassword = "imabeast";
  public static Account currentAccount;

  public static boolean successfulLogin(String username, String password) {
    for (Account a : accounts) {
      if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
        currentAccount = a;
        return true;
      }
    }
    return false;
  }

  public static Account accountExists(String username) {
    for (Account a : accounts) {
      if ((a.getUsername()).equals(username)) {
        return a;
      }
    }
    return null;
  }

  public static void addAccount(Account a) {
    System.accounts.add(a);
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

  public static ArrayList<Account> getAccounts() {
    return System.accounts;
  }
}

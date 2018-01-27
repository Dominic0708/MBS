/**
 * Created by Dominic on 2018-01-12.
 */
public class AccountManagementTool {

  public static void addAccount(String username, String password) {
    System.accounts.add(new Account(username, password));
  }

  public static void deleteAccount(String username) {
    Account account = null;
    for (Account a : System.accounts) {
      if (a.getUsername().equals(username)) {
        account = a;
      }
    }
    if (account != null) {
      System.accounts.remove(account);
    }
  }
}

import java.io.Serializable;
import java.util.ArrayList;

class Configuration implements Serializable {

  static final long serialVersionUID = -28737459458392194L;
  private ArrayList<Account> accounts;
  private ArrayList<Client> clients;

  public void saveConfiguration() {
    this.accounts = System.accounts;
    this.clients = System.clients;
  }

  public void loadConfiguration() {
    System.accounts = this.accounts;
    System.clients = this.clients;
  }
}

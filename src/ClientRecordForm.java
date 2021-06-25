import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dominic on 2018-01-13.
 */
public class ClientRecordForm {

  private JPanel recordPanel;
  private JPanel infoPanel;
  private JPanel buttonPanel;
  private JTextField nameField;
  private JTextField dobField;
  private JTextField addressField;
  private JTextField emailField;
  private JTextField phoneField;
  private JTextField ageField;
  private JTextField heightField;
  private JTextField weightField;
  private JButton confirmButton;
  private JButton backButton;
  private JLabel nameLabel;
  private JLabel dobLabel;
  private JLabel addressLabel;
  private JLabel emailLabel;
  private JLabel phoneLabel;
  private JLabel ageLabel;
  private JLabel heightLabel;
  private JLabel sessionLabel;
  private JLabel weightLabel;
  private JLabel sessionCountLabel;
  private JPanel notePanel;
  private JSpinner spinner;
  private JScrollPane noteScroll;
  private JScrollPane recordScroll;
  private JList records;
  private JTextArea notesArea;
  private JLabel editorLabel;
  private JProgressBar paymentProgressBar;
  private JLabel paymentProgressLabel;
  private JLabel paidAmountLabel;
  private JLabel paidLabel;
  private JButton financialAccountButton;
  private JPanel photoPanel;
  private static JFrame frame;
  private Client client;
  public int mode;

  public ClientRecordForm() {
    refresh();
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ClientManagementForm form = new ClientManagementForm();
        ClientManagementForm.getFrame().setContentPane(form.getPanel());
        ClientManagementForm.getFrame().setVisible(true);
        frame.dispose();
      }
    });
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (client != null) {
          if (mode == 1) {
            if (validInfo()) {
              if (System.clientExists(nameField.getText().trim()) == null || nameField.getText().trim()
                  .equals(client.getName())) {
                int sc = (Integer) spinner.getValue() + client.getSessionCount();

                client.setInfo(nameField.getText().trim(), new CustomDate(dobField.getText()),
                    addressField.getText(),
                    emailField.getText(), phoneField.getText(),
                    Integer.parseInt(ageField.getText()),
                    Integer.parseInt(heightField.getText()),
                    Integer.parseInt(weightField.getText()),
                    sc);

                client.addRecord(
                    new Date() + "   -   Session change: " + spinner.getValue() + "   -   by "
                        + System.currentAccount.getUsername());

                JOptionPane.showMessageDialog(null,
                    "Client info has been updated!" + java.lang.System.getProperty("line.separator")
                        + "Session change: " + spinner.getValue());
                mode = 1;
                refresh();
              } else {
                JOptionPane.showMessageDialog(null, "Client already exists!");
                refresh();
              }
//            ClientManagementForm form = new ClientManagementForm();
//            ClientManagementForm.getFrame().setContentPane(form.getPanel());
//            ClientManagementForm.getFrame().setVisible(true);
//            frame.dispose();
            } else {
              JOptionPane.showMessageDialog(null, "Invalid info entered!");
              refresh();
            }
          }
        } else {
          if (System.clientExists(nameField.getText().trim()) != null) {
            JOptionPane.showMessageDialog(null, "Client already exists!");
            client = System.clientExists(nameField.getText().trim());
            refresh();
            mode = 1;
          } else {
            if (validInfo()) {
              client = new Client(nameField.getText().trim(), new CustomDate(dobField.getText()),
                  addressField.getText(),
                  emailField.getText(), phoneField.getText(), Integer.parseInt(ageField.getText()),
                  Integer.parseInt(heightField.getText()), Integer.parseInt(weightField.getText()),
                  (Integer) spinner.getValue());
              System.addClient(client);
              client.addRecord(
                  new Date() + "   -   Client has been created with: " + spinner.getValue()
                      + "   -   by "
                      + System.currentAccount.getUsername());
              JOptionPane.showMessageDialog(null,
                  "Client has been created with " + spinner.getValue() + " sessions!");
              refresh();
              mode = 1;
//            ClientManagementForm form = new ClientManagementForm();
//            ClientManagementForm.getFrame().setContentPane(form.getPanel());
//            ClientManagementForm.getFrame().setVisible(true);
//            frame.dispose();
            } else {
              client = new Client(nameField.getText().trim(), new CustomDate(dobField.getText()),
                  addressField.getText(),
                  emailField.getText(), phoneField.getText(), 0, 0, 0,
                  (Integer) spinner.getValue());
              System.addClient(client);
              client.addRecord(
                  new Date() + "   -   Client has been created with: " + spinner.getValue()
                      + "   -   by "
                      + System.currentAccount.getUsername());
              JOptionPane.showMessageDialog(null,
                  "Client has been created with " + spinner.getValue() + " sessions!");
              refresh();
              mode = 1;
            }
          }
        }
        Configuration config = new Configuration();
        config.saveConfiguration();
        try {
          CustomFileHandler.saveConfiguration("Config", config);
        } catch (Exception exp) {
          JOptionPane.showMessageDialog(null, "Failed to save configuration");
        }
      }
    });
    financialAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (client != null) {
          String financialAccountPassword = JOptionPane
              .showInputDialog(null, "Please enter password for access:", "MBS",
                  JOptionPane.INFORMATION_MESSAGE);
          if (financialAccountPassword != null && financialAccountPassword
              .equals(System.financialAccountPassword)) {
            FinancialAccountForm form = new FinancialAccountForm();
            form.setFinancialAccount(client.getFinancialAccount());
            form.setClient(client);
            form.refresh();
            FinancialAccountForm.setFrame(new JFrame("MBS Client Financial Account"));
            FinancialAccountForm.getFrame().setContentPane(form.getPanel());
            FinancialAccountForm.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            FinancialAccountForm.getFrame().setSize(1200, 800);
            FinancialAccountForm.getFrame().setLocationRelativeTo(null);
            FinancialAccountForm.getFrame().setVisible(true);
            frame.dispose();
            JOptionPane.showMessageDialog(null, "Access granted." + "\n"
                + "Note: Under normal operations you should only type in the amount and receive the money."
                + "\n"
                + "Do not change the fields above the progress bar unless there is an actual change.");
          } else if (financialAccountPassword != null && !financialAccountPassword
              .equals(System.financialAccountPassword)) {
            JOptionPane.showMessageDialog(null, "Access denied.");
          }
        }
      }
    });
    notesArea.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if (client != null) {
          client.popNote();
          client.addNote(notesArea.getText());
        }
      }
    });
  }

  public JPanel getPanel() {
    return this.recordPanel;
  }

  public static JFrame getFrame() {
    return frame;
  }

  public static void setFrame(final JFrame frame) {
    ClientRecordForm.frame = frame;
  }

  public void setClient(Client c) {
    client = c;
  }

  public void refresh() {
    if (client != null) {
      if (client.getFinancialAccount() == null) {
        client.setFinancialAccount(new FinancialAccount());
      }
      this.nameField.setText(client.getName());
      this.dobField.setText(client.getDob().toString());
      this.addressField.setText(client.getAddress());
      this.emailField.setText(client.getEmail());
      this.phoneField.setText(client.getPhone());
      this.ageField.setText(String.valueOf(client.getAge()));
      this.heightField.setText(String.valueOf(client.getHeight()));
      this.weightField.setText(String.valueOf(client.getWeight()));
      this.sessionCountLabel.setText(String.valueOf(client.getSessionCount()));
      this.paymentProgressBar.setValue(client.getFinancialAccount().getPercentage());
      this.paidAmountLabel.setText(
          String.valueOf(client.getFinancialAccount().getPaid()) + "/" + String
              .valueOf(client.getFinancialAccount().getTotalPurchase()));
      spinner.setValue(0);

      try {
        notesArea.setText(client.getNotes().get(0));
      } catch (Exception e) {
      }

      DefaultListModel<String> recordList = new DefaultListModel<>();

      ArrayList<String> tempRecord = new ArrayList<>(client.getRecords());
      Collections.reverse(tempRecord);

      for (String record : tempRecord) {
        recordList.addElement(record);
      }
      this.records.setModel(recordList);
    }
  }

  private boolean validInfo() {
    try {
      int age = Integer.parseInt(ageField.getText());
      int height = Integer.parseInt(heightField.getText());
      int weight = Integer.parseInt(weightField.getText());
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}

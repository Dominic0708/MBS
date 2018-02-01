import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.util.Date;

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
  private JList notes;
  private JList records;
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

        if (System.clientExists(nameField.getText()) != null) {
          if (mode == 0) {
            JOptionPane.showMessageDialog(null, "Client already exists!");
            client = System.clientExists(nameField.getText());
            refresh();
            mode = 1;
          } else {
            if (validInfo()) {
              client = System.clientExists(nameField.getText());

              int sc = (Integer) spinner.getValue() + client.getSessionCount();

              client.setInfo(nameField.getText(), new CustomDate(dobField.getText()),
                  addressField.getText(),
                  emailField.getText(), phoneField.getText(), Integer.parseInt(ageField.getText()),
                  Integer.parseInt(heightField.getText()), Integer.parseInt(weightField.getText()),
                  sc);

              client.addRecord(
                  new Date() + "   -   Session change: " + spinner.getValue() + "   -   by "
                      + System.currentAccount.getUsername());

              JOptionPane.showMessageDialog(null,
                  "Client info has been updated!" + java.lang.System.getProperty("line.separator")
                      + "Session change: " + spinner.getValue());

              refresh();

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
          if (validInfo()) {
            client = new Client(nameField.getText(), new CustomDate(dobField.getText()),
                addressField.getText(),
                emailField.getText(), phoneField.getText(), Integer.parseInt(ageField.getText()),
                Integer.parseInt(heightField.getText()), Integer.parseInt(weightField.getText()),
                (Integer) spinner.getValue());
            System.addClient(client);
            client.addRecord(
                new Date() + "   -   Session change: " + spinner.getValue() + "   -   by "
                    + System.currentAccount.getUsername());
            JOptionPane.showMessageDialog(null,
                "Client has been created with " + spinner.getValue() + " sessions!");
            refresh();
//            ClientManagementForm form = new ClientManagementForm();
//            ClientManagementForm.getFrame().setContentPane(form.getPanel());
//            ClientManagementForm.getFrame().setVisible(true);
//            frame.dispose();
          } else {
            JOptionPane.showMessageDialog(null, "Invalid info entered!");
            refresh();
          }
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
      this.nameField.setText(client.getName());
      this.dobField.setText(client.getDob().toString());
      this.addressField.setText(client.getAddress());
      this.emailField.setText(client.getEmail());
      this.phoneField.setText(client.getPhone());
      this.ageField.setText(String.valueOf(client.getAge()));
      this.heightField.setText(String.valueOf(client.getHeight()));
      this.weightField.setText(String.valueOf(client.getWeight()));
      this.sessionCountLabel.setText(String.valueOf(client.getSessionCount()));

      DefaultListModel<String> noteList = new DefaultListModel<>();
      for (String note : client.getNotes()) {
        noteList.addElement(note);
      }
      this.notes.setModel(noteList);

      DefaultListModel<String> recordList = new DefaultListModel<>();
      for (String record : client.getRecords()) {
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

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
  private JLabel membershipLabel;
  private JLabel phoneLabel;
  private JLabel ageLabel;
  private JLabel heightLabel;
  private JLabel sessionLabel;
  private JLabel weightLabel;
  private JLabel membershipCountLabel;
  private JLabel sessionCountLabel;
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
            client = new Client(nameField.getText(), new Date(dobField.getText()),
                addressField.getText(),
                emailField.getText(), phoneField.getText(), Integer.parseInt(ageField.getText()),
                Integer.parseInt(heightField.getText()), Integer.parseInt(weightField.getText()));
            JOptionPane.showMessageDialog(null, "Client info has been updated!");
          }
        } else {
          Client c = new Client(nameField.getText(), new Date(dobField.getText()),
              addressField.getText(),
              emailField.getText(), phoneField.getText(), Integer.parseInt(ageField.getText()),
              Integer.parseInt(heightField.getText()), Integer.parseInt(weightField.getText()));
          System.addClient(c);
          client = c;
          refresh();
          JOptionPane.showMessageDialog(null, "Client has been created!");
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
      this.membershipCountLabel.setText(String.valueOf(client.getMembershipCount()));
    }
  }
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Created by Dominic on 2018-01-26.
 */
public class AccountManagementForm {

  private JPanel accountPanel;
  private JPanel inputPanel;
  private JPanel buttonPanel;
  private JTextField usernameField;
  private JTextField passwordField;
  private JLabel usernameLabel;
  private JLabel passwordLabel;
  private JButton confirmButton;
  private JButton backButton;
  private JButton deleteAccountButton;
  private static JFrame frame;

  public AccountManagementForm() {
    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (usernameField.getText().equals("")) {
          JOptionPane.showMessageDialog(null, "Account username cannot be empty!");
        } else {
          String masterPassword = JOptionPane
              .showInputDialog(null, "Please enter the master password to confirm:", "MBS",
                  JOptionPane.INFORMATION_MESSAGE);
          if (masterPassword != null && masterPassword.equals(System.masterPassword)) {
            Account account = System.accountExists(usernameField.getText());
            if (account != null) {
              account.setPassword(passwordField.getText());
              JOptionPane.showMessageDialog(null, "Account password updated!");
            } else {
              AccountManagementTool.addAccount(usernameField.getText(), passwordField.getText());
              JOptionPane.showMessageDialog(null, "Account has been created!");
            }
          }
        }
      }
    });
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        LoginForm form = new LoginForm();
        LoginForm.getFrame().setContentPane(form.getPanel());
        LoginForm.getFrame().setVisible(true);
        frame.dispose();
      }
    });
    deleteAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String username = JOptionPane
            .showInputDialog(null,
                "Please enter the username you want to delete: " + System.getAccounts(), "MBS",
                JOptionPane.INFORMATION_MESSAGE);
        if (username != null) {
          if (System.accountExists(username) != null) {
            String masterPassword = JOptionPane
                .showInputDialog(null, "Please enter the master password to confirm:", "MBS",
                    JOptionPane.INFORMATION_MESSAGE);
            if (masterPassword != null && masterPassword.equals(System.masterPassword)) {
              AccountManagementTool.deleteAccount(username);
              JOptionPane.showMessageDialog(null, "Account " + username + " has been deleted!");
            }
          } else {
            JOptionPane.showMessageDialog(null, "Account not found!");
          }
        }
      }
    });
  }

  public JPanel getPanel() {
    return this.accountPanel;
  }

  public static JFrame getFrame() {
    return frame;
  }

  public static void setFrame(final JFrame frame) {
    AccountManagementForm.frame = frame;
  }
}

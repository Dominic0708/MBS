import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Toolkit;

/**
 * Created by Dominic on 2018-01-10.
 */
public class LoginForm {

  private JPanel loginPanel;
  private JPanel inputPanel;
  private JPanel buttonPanel;
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JLabel usernameLabel;
  private JLabel passwordLabel;
  private JButton loginButton;
  private JButton exitButton;
  private JButton accountManagementButton;
  private static JFrame frame;
  static int width;
  static int height;

  public LoginForm() {
    exitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Configuration config = new Configuration();
        config.saveConfiguration();
        try {
          CustomFileHandler.saveConfiguration("Config", config);
        } catch (Exception exp) {
          JOptionPane.showMessageDialog(null, "Failed to save configuration");
        }
        java.lang.System.exit(0);
      }
    });
    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (System
            .successfulLogin(usernameField.getText(), new String(passwordField.getPassword()))) {
          SystemForm form = new SystemForm();
          SystemForm.setFrame(new JFrame("MBS"));
          SystemForm.getFrame().setContentPane(form.getPanel());
          SystemForm.getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          SystemForm.getFrame().setSize(400, 200);
          SystemForm.getFrame().setLocationRelativeTo(null);
          SystemForm.getFrame().setVisible(true);
          frame.dispose();
        }
      }
    });
    accountManagementButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }

  public static void main(String[] args) {
    width = Toolkit.getDefaultToolkit().getScreenSize().width;
    height = Toolkit.getDefaultToolkit().getScreenSize().height;
    frame = new JFrame("MBS Login");
    frame.setContentPane(new LoginForm().getPanel());
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(300, 200);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    AccountManagementTool.addAccount("Dominic", "19960708");
    AccountManagementTool.addAccount("Itay", "imabeast");
    loadConfiguration();
  }

  public JPanel getPanel() {
    return this.loginPanel;
  }

  public static JFrame getFrame() {
    return frame;
  }

  public static void setFrame(final JFrame frame) {
    LoginForm.frame = frame;
  }

  static void loadConfiguration() {
    try {
      CustomFileHandler.readConfiguration("Config").loadConfiguration();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }
}

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
import java.util.List;

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
  static boolean debugMode = true;

  public LoginForm() {
    exitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Configuration config = new Configuration();
        config.saveConfiguration();
        try {
          CustomFileHandler.saveConfiguration("Config", config);
          CustomFileHandler.saveConfiguration("Config_Back_Up", config);
          CustomFileHandler.setOfflineStatus();
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
          SystemForm.setFrame(new JFrame("MBS System"));
          SystemForm.getFrame().setContentPane(form.getPanel());
          SystemForm.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
          SystemForm.getFrame().setSize(400, 200);
          SystemForm.getFrame().setLocationRelativeTo(null);
          SystemForm.getFrame().setVisible(true);
          frame.dispose();
        } else {
          JOptionPane.showMessageDialog(null, "Wrong username or password!");
        }
      }
    });
    accountManagementButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        AccountManagementForm form = new AccountManagementForm();
        AccountManagementForm.setFrame(new JFrame("MBS Account Management"));
        AccountManagementForm.getFrame().setContentPane(form.getPanel());
        AccountManagementForm.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        AccountManagementForm.getFrame().setSize(300, 200);
        AccountManagementForm.getFrame().setLocationRelativeTo(null);
        AccountManagementForm.getFrame().setVisible(true);
      }
    });
  }

  public static void main(String[] args) {
    if (!debugMode) {
      try {
        List<String> status = CustomFileHandler.readFile("Status");
        if (status.contains("1")) {
          JOptionPane.showMessageDialog(null, "System already running!");
          java.lang.System.exit(0);
        } else {
          CustomFileHandler.setOnlineStatus();
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        java.lang.System.exit(0);
      }
      width = Toolkit.getDefaultToolkit().getScreenSize().width;
      height = Toolkit.getDefaultToolkit().getScreenSize().height;
      frame = new JFrame("MBS Login");
      frame.setContentPane(new LoginForm().getPanel());
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.setSize(300, 200);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      loadConfiguration();
      System.setCurrentDate();
    } else {




      width = Toolkit.getDefaultToolkit().getScreenSize().width;
      height = Toolkit.getDefaultToolkit().getScreenSize().height;
      frame = new JFrame("MBS Login");
      frame.setContentPane(new LoginForm().getPanel());
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.setSize(300, 200);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      System.setCurrentDate();




    }
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

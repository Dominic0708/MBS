import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Created by Dominic on 2018-01-11.
 */
public class SystemForm {

  private JPanel systemPanel;
  private JPanel functionPanel;
  private JPanel buttonPanel;
  private JButton clientButton;
  private JButton logoffButton;
  private JLabel totalClientsLabel;
  private static JFrame frame;

  public SystemForm() {
    refresh();
    logoffButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        LoginForm form = new LoginForm();
        LoginForm.getFrame().setContentPane(form.getPanel());
        LoginForm.getFrame().setVisible(true);
        frame.dispose();
      }
    });
    clientButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ClientManagementForm form = new ClientManagementForm();
        ClientManagementForm.setFrame(new JFrame("MBS Client Management"));
        ClientManagementForm.getFrame().setContentPane(form.getPanel());
        ClientManagementForm.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ClientManagementForm.getFrame().setSize(800, 800);
        ClientManagementForm.getFrame().setLocationRelativeTo(null);
        ClientManagementForm.getFrame().setVisible(true);
        frame.dispose();
      }
    });
  }

  public JPanel getPanel() {
    return this.systemPanel;
  }

  public static JFrame getFrame() {
    return frame;
  }

  public static void setFrame(final JFrame frame) {
    SystemForm.frame = frame;
  }

  public void refresh() {
    totalClientsLabel.setText("Total Clients: " + System.clients.size());
  }
}

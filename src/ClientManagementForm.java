import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.DefaultListModel;

/**
 * Created by Dominic on 2018-01-12.
 */
public class ClientManagementForm {

  private JPanel managementPanel;
  private JPanel buttonPanel;
  private JButton addClientButton;
  private JButton recordChangeButton;
  private JButton backButton;
  private JList<String> clientList;
  private JScrollPane clientScroll;
  private static JFrame frame;

  public ClientManagementForm() {
    clientList.setVisibleRowCount(10);
    refresh();
    addClientButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ClientRecordForm form = new ClientRecordForm();
        form.mode = 0;
        ClientRecordForm.setFrame(new JFrame("MBS Client Record"));
        ClientRecordForm.getFrame().setContentPane(form.getPanel());
        ClientRecordForm.getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ClientRecordForm.getFrame().setSize(800, 600);
        ClientRecordForm.getFrame().setLocationRelativeTo(null);
        ClientRecordForm.getFrame().setVisible(true);
        frame.dispose();
      }
    });
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SystemForm form = new SystemForm();
        SystemForm.getFrame().setContentPane(form.getPanel());
        SystemForm.getFrame().setVisible(true);
        frame.dispose();
      }
    });
    recordChangeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = JOptionPane.showInputDialog(null, "Please enter client's full name:", "MBS",
            JOptionPane.INFORMATION_MESSAGE);
        if (name != null) {
          if (System.clientExists(name) != null) {
            ClientRecordForm form = new ClientRecordForm();
            form.mode = 1;
            form.setClient(System.clientExists(name));
            form.refresh();
            ClientRecordForm.setFrame(new JFrame("MBS Client Record"));
            ClientRecordForm.getFrame().setContentPane(form.getPanel());
            ClientRecordForm.getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            ClientRecordForm.getFrame().setSize(800, 600);
            ClientRecordForm.getFrame().setLocationRelativeTo(null);
            ClientRecordForm.getFrame().setVisible(true);
            frame.dispose();
          } else {
            JOptionPane.showMessageDialog(null, "Client has not been found!");
          }
        }
      }
    });
  }

  public JPanel getPanel() {
    return this.managementPanel;
  }

  public static JFrame getFrame() {
    return frame;
  }

  public static void setFrame(final JFrame frame) {
    ClientManagementForm.frame = frame;
  }

  public void refresh() {
    DefaultListModel<String> list = new DefaultListModel<>();
    for (Client c : System.clients) {
      list.addElement(c.getName());
    }
    this.clientList.setModel(list);
  }
}

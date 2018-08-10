import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Dominic on 2018-01-12.
 */
public class ClientManagementForm {

  private JPanel managementPanel;
  private JPanel buttonPanel;
  private JButton addClientButton;
  private JButton clientLookupButton;
  private JButton backButton;
  private JList<String> clientList;
  private JScrollPane clientScroll;
  private JButton deleteClientButton;
  private static JFrame frame;
  private int clickCounter;

  public ClientManagementForm() {
    refresh();
    clickCounter = 0;
    addClientButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ClientRecordForm form = new ClientRecordForm();
        form.mode = 0;
        ClientRecordForm.setFrame(new JFrame("MBS Client Record"));
        ClientRecordForm.getFrame().setContentPane(form.getPanel());
        ClientRecordForm.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ClientRecordForm.getFrame().setSize(1200, 800);
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
    clientLookupButton.addActionListener(new ActionListener() {
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
            ClientRecordForm.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            ClientRecordForm.getFrame().setSize(1200, 800);
            ClientRecordForm.getFrame().setLocationRelativeTo(null);
            ClientRecordForm.getFrame().setVisible(true);
            frame.dispose();
          } else {
            JOptionPane.showMessageDialog(null, "Client has not been found!");
          }
        }
      }
    });
    clientList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        clickCounter = 0;
      }
    });
    clientList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String selected = clientList.getSelectedValue();
        if (System.clientExists(selected) != null && clickCounter == 1) {
          ClientRecordForm form = new ClientRecordForm();
          form.mode = 1;
          form.setClient(System.clientExists(selected));
          form.refresh();
          ClientRecordForm.setFrame(new JFrame("MBS Client Record"));
          ClientRecordForm.getFrame().setContentPane(form.getPanel());
          ClientRecordForm.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
          ClientRecordForm.getFrame().setSize(1200, 800);
          ClientRecordForm.getFrame().setLocationRelativeTo(null);
          ClientRecordForm.getFrame().setVisible(true);
          clickCounter = 0;
          frame.dispose();
        } else if (System.clientExists(selected) != null && clickCounter == 0) {
          clickCounter = 1;
        }
      }
    });
    deleteClientButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = JOptionPane.showInputDialog(null, "Please enter client's full name:", "MBS",
            JOptionPane.INFORMATION_MESSAGE);
        if (name != null) {
          if (System.clientExists(name) != null) {
            String password = JOptionPane
                .showInputDialog(null, "Please enter the master password to confirm deletion:",
                    "MBS",
                    JOptionPane.INFORMATION_MESSAGE);
            if (password != null && password.equals(System.masterPassword)) {
              System.deleteClient(name);
              refresh();
            } else {
              JOptionPane.showMessageDialog(null, "Master password incorrect!");
            }
          } else {
            JOptionPane.showMessageDialog(null, "Client has not been found!");
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

    ArrayList<String> temp = new ArrayList<>();

    for (Client c : System.clients) {
      int length1 = 50;
      int length2 = 80;

      int spaceLength1 = length1 - c.getName().length();
      StringBuilder spaces = new StringBuilder();
      for (int i = 0; i < spaceLength1; i++) {
        spaces.append(" ");
      }

      String line = c.getName() + spaces.toString() + "Sessions: " + c.getSessionCount();

      int spaceLength2 = length2 - line.length();
      spaces = new StringBuilder();
      for (int i = 0; i < spaceLength2; i++) {
        spaces.append(" ");
      }

      line += spaces.toString() + "Amount Due: " + c.getFinancialAccount().getAmountDue();

      temp.add(line);
    }

    Collections.sort(temp);

    for (String name : temp) {
      list.addElement(name);
    }
    this.clientList.setModel(list);
  }
}

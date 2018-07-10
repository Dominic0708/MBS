import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dominic on 2018-07-10.
 */
public class FinancialAccountForm {

  private JPanel financialPanel;
  private JPanel infoPanel;
  private JPanel notePanel;
  private JPanel buttonPanel;
  private JLabel nameLabel;
  private JLabel paymentProgressLabel;
  private JLabel totalPurchaseLabel;
  private JLabel paidLabel;
  private JLabel owedLabel;
  private JLabel paymentTypeLabel;
  private JLabel amountDueLabel;
  private JLabel nextPaymentDateLabel;
  private JTextField nameField;
  private JTextField totalPurchaseField;
  private JTextField paidField;
  private JTextField owedField;
  private JComboBox<String> paymentTypeBox;
  private JTextField amountDueField;
  private JTextField nextPaymentDateField;
  private JProgressBar paymentProgressBar;
  private JButton confirmButton;
  private JButton backButton;
  private JTextArea notesArea;
  private JList records;
  private JLabel amountLabel;
  private JTextField amountField;
  private JButton receiveButton;
  private static JFrame frame;
  private FinancialAccount financialAccount;
  private Client client;

  public FinancialAccountForm() {
    refresh();
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ClientRecordForm form = new ClientRecordForm();
        form.setClient(client);
        form.mode = 1;
        form.refresh();
        ClientRecordForm.getFrame().setContentPane(form.getPanel());
        ClientRecordForm.getFrame().setVisible(true);
        frame.dispose();
      }
    });

    confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (client != null) {
          if (validInfo()) {
            financialAccount
                .setTotalPurchase(Float.parseFloat(totalPurchaseField.getText()));
            financialAccount
                .setPaid(Float.parseFloat(paidField.getText()));
            financialAccount
                .setOwed(financialAccount.getTotalPurchase() - financialAccount.getPaid());
            financialAccount
                .setPaymentType(String.valueOf(paymentTypeBox.getSelectedItem()));
            financialAccount
                .setAmountDue(Float.parseFloat(amountDueField.getText()));
            financialAccount
                .setPaymentDate(nextPaymentDateField.getText());
            financialAccount.updatePercentage();
          }
        }
        refresh();
      }
    });
    notesArea.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if (client != null) {
          financialAccount.popNote();
          financialAccount.addNote(notesArea.getText());
        }
      }
    });
    receiveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          float amount = Float.parseFloat(amountField.getText());
        } catch (Exception exception) {
          JOptionPane.showMessageDialog(null, "Please enter a valid amount!");
        }
        float amount = Float.parseFloat(amountField.getText());
        financialAccount.setPaid(financialAccount.getPaid() + amount);
        financialAccount.setOwed(financialAccount.getOwed() - amount);
        financialAccount.setAmountDue(financialAccount.getAmountDue() - amount);
        financialAccount.updatePercentage();
        refresh();
      }
    });
  }

  public void refresh() {
    if (client != null) {
      this.nameField.setText(client.getName());
      this.totalPurchaseField
          .setText(String.valueOf(financialAccount.getTotalPurchase()));
      this.paidField.setText(String.valueOf(financialAccount.getPaid()));
      this.owedField.setText(String.valueOf(financialAccount.getOwed()));
      this.amountDueField.setText(String.valueOf((financialAccount.getAmountDue())));
      this.nextPaymentDateField.setText(financialAccount.getPaymentDate().toString());
      this.paymentProgressBar.setValue(financialAccount.getPercentage());
      this.amountField.setText(String.valueOf((0)));

      this.paymentTypeBox.removeAllItems();
      this.paymentTypeBox.addItem("Unknown");
      this.paymentTypeBox.addItem("Weekly");
      this.paymentTypeBox.addItem("Bi-Weekly");
      this.paymentTypeBox.addItem("Semi-Monthly");
      this.paymentTypeBox.addItem("Monthly");
      this.paymentTypeBox.setSelectedItem(financialAccount.getPaymentType());

      try {
        notesArea.setText(financialAccount.getNotes().get(0));
      } catch (Exception e) {
      }

      DefaultListModel<String> recordList = new DefaultListModel<>();

      ArrayList<String> tempRecord = new ArrayList<>(financialAccount.getRecords());
      Collections.reverse(tempRecord);

      for (String record : tempRecord) {
        recordList.addElement(record);
      }
      this.records.setModel(recordList);
    }
  }

  private boolean validInfo() {
    try {
      float totalPurchase = Float.parseFloat(totalPurchaseField.getText());
      float amountPaid = Float.parseFloat(paidField.getText());
      float amountOwe = Float.parseFloat(owedField.getText());
      float amountDue = Float.parseFloat(amountDueField.getText());
    } catch (Exception e) {
      return false;
    }
    return true;
  }


  public JPanel getPanel() {
    return this.financialPanel;
  }

  public static JFrame getFrame() {
    return frame;
  }

  public static void setFrame(final JFrame frame) {
    FinancialAccountForm.frame = frame;
  }

  public void setFinancialAccount(FinancialAccount fa) {
    financialAccount = fa;
  }

  public void setClient(Client c) {
    client = c;
  }
}

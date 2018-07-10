import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
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
  private Client client;

  public FinancialAccountForm() {
    refresh();
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ClientRecordForm form = new ClientRecordForm();
        form.setClient(client);
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
            client.getFinancialAccount()
                .setTotalPurchase(Float.parseFloat(totalPurchaseField.getText()));
            client.getFinancialAccount()
                .setPaid(Float.parseFloat(paidField.getText()));
            client.getFinancialAccount()
                .setOwed(Float.parseFloat(owedField.getText()));
            client.getFinancialAccount()
                .setPaymentType(String.valueOf(paymentTypeBox.getSelectedItem()));
            client.getFinancialAccount()
                .setAmountDue(Float.parseFloat(amountDueField.getText()));
            client.getFinancialAccount()
                .setPaymentDate(nextPaymentDateField.getText());
          }
        }
      }
    });
  }

  public void refresh() {
    if (client != null) {
      this.nameField.setText(client.getName());
      this.totalPurchaseField
          .setText(String.valueOf(client.getFinancialAccount().getTotalPurchase()));
      this.paidField.setText(String.valueOf(client.getFinancialAccount().getPaid()));
      this.owedField.setText(String.valueOf(client.getFinancialAccount().getOwed()));
      this.amountDueField.setText(String.valueOf((client.getFinancialAccount().getAmountDue())));
      this.nextPaymentDateField.setText(client.getFinancialAccount().getPaymentDate().toString());
      this.paymentProgressBar.setValue(client.getFinancialAccount().getPercentage());
      this.amountField.setText(String.valueOf((0)));

      this.paymentTypeBox.removeAllItems();
      this.paymentTypeBox.addItem("Unknown");
      this.paymentTypeBox.addItem("Weekly");
      this.paymentTypeBox.addItem("Bi-Weekly");
      this.paymentTypeBox.addItem("Semi-Monthly");
      this.paymentTypeBox.addItem("Monthly");
      this.paymentTypeBox.setSelectedItem(client.getFinancialAccount().getPaymentType());

      try {notesArea.setText(client.getNotes().get(0));}
      catch (Exception e) {
      }

      DefaultListModel<String> recordList = new DefaultListModel<>();

      ArrayList<String> tempRecord = new ArrayList<>(client.getFinancialAccount().getRecords());
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

  public void setClient(Client c) {
    client = c;
  }

}

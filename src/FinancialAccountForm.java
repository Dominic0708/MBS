import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
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
  private JLabel paymentsLeftLabel;
  private JTextField paymentsLeftField;
  private JTextField termAmountField;
  private JLabel termAmountLabel;
  private JScrollPane noteScroll;
  private JScrollPane recordsScroll;
  private static JFrame frame;
  private FinancialAccount financialAccount = new FinancialAccount();
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
                .setTermAmount(Float.parseFloat(termAmountField.getText()));
            financialAccount
                .setAmountDue(Float.parseFloat(amountDueField.getText()));
            financialAccount
                .setPaymentDate(nextPaymentDateField.getText());
            financialAccount.updatePercentage();
            if (financialAccount.getTermAmount() == 0) {
              financialAccount.setPaymentsLeft(0);
            } else {
              financialAccount
                  .setPaymentsLeft((int)
                      Math.ceil(financialAccount.getOwed() / financialAccount.getTermAmount()));
            }
            client.getFinancialAccount().addRecord(
                new Date() + "   -   Account Updated: " + "|" + "Total Purchase: "
                    + financialAccount
                    .getTotalPurchase() + "|" + "Amount Paid: " + financialAccount.getPaid()
                    + "|" + "Payment Type: " + financialAccount.getPaymentType() + "|"
                    + "Term Amount: " + financialAccount.getTermAmount() + "|" + "Amount Due: "
                    + financialAccount.getAmountDue() + "|" + "Next Payment Date: "
                    + financialAccount.getPaymentDate() + "|"
                    + "   -   by "
                    + System.currentAccount.getUsername());
            JOptionPane.showMessageDialog(null,
                "Account Updated: " + "\n" + "Total Purchase: " + financialAccount
                    .getTotalPurchase() + "\n" + "Amount Paid: " + financialAccount.getPaid()
                    + "\n" + "Payment Type: " + financialAccount.getPaymentType() + "\n"
                    + "Term Amount: " + financialAccount.getTermAmount() + "\n" + "Amount Due: "
                    + financialAccount.getAmountDue() + "\n" + "Next Payment Date: "
                    + financialAccount.getPaymentDate());
          }
          client.setFinancialAccount(financialAccount);
        }
        Configuration config = new Configuration();
        config.saveConfiguration();
        try {
          CustomFileHandler.saveConfiguration("Config", config);
        } catch (Exception exp) {
          JOptionPane.showMessageDialog(null, "Failed to save configuration");
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
        if (financialAccount.getTermAmount() == 0) {
          financialAccount.setPaymentsLeft(0);
        } else {
          financialAccount
              .setPaymentsLeft((int)
                  Math.ceil(financialAccount.getOwed() / financialAccount.getTermAmount()));
        }
        financialAccount.updatePercentage();
        if (client != null) {
          client.getFinancialAccount().addRecord(
              new Date() + "   -   Amount received: " + amount
                  + "   -   by "
                  + System.currentAccount.getUsername());
          JOptionPane.showMessageDialog(null, amount + " has been received!");
          client.setFinancialAccount(financialAccount);
        }
        Configuration config = new Configuration();
        config.saveConfiguration();
        try {
          CustomFileHandler.saveConfiguration("Config", config);
        } catch (Exception exp) {
          JOptionPane.showMessageDialog(null, "Failed to save configuration");
        }
        refresh();
      }
    });
  }

  public void refresh() {
    if (client != null) {
      if (client.getFinancialAccount() == null) {
        client.setFinancialAccount(new FinancialAccount());
      }
      financialAccount = client.getFinancialAccount();
      checkNextPayment();
      this.nameField.setText(client.getName());
      this.totalPurchaseField
          .setText(String.valueOf(financialAccount.getTotalPurchase()));
      this.paidField.setText(String.valueOf(financialAccount.getPaid()));
      this.owedField.setText(String.valueOf(financialAccount.getOwed()));
      this.amountDueField.setText(String.valueOf((financialAccount.getAmountDue())));
      this.nextPaymentDateField.setText(financialAccount.getPaymentDate().toString());
      this.paymentProgressBar.setValue(financialAccount.getPercentage());
      this.amountField.setText(String.valueOf((0)));
      this.paymentsLeftField.setText(String.valueOf(financialAccount.getPaymentsLeft()));
      this.termAmountField.setText(String.valueOf(financialAccount.getTermAmount()));

      this.paymentTypeBox.removeAllItems();
      this.paymentTypeBox.addItem("Outright");
      this.paymentTypeBox.addItem("Weekly");
      this.paymentTypeBox.addItem("Bi-Weekly");
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
      float amountOwed = Float.parseFloat(owedField.getText());
      float termAmount = Float.parseFloat(termAmountField.getText());
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

  private void checkNextPayment() {
    if (CustomDate.dayHasPassed(financialAccount.getPaymentDate())) {
      if (financialAccount.getPaymentType().equals("Weekly")) {
        financialAccount.setPaymentDate(financialAccount.getPaymentDate().getNextWeek());
        financialAccount
            .setAmountDue(financialAccount.getAmountDue() + financialAccount.getTermAmount());
      } else if (financialAccount.getPaymentType().equals("Bi-Weekly")) {
        financialAccount
            .setPaymentDate(financialAccount.getPaymentDate().getNextBiWeek());
        financialAccount
            .setAmountDue(financialAccount.getAmountDue() + financialAccount.getTermAmount());
      } else if (financialAccount.getPaymentType().equals("Monthly")) {
        financialAccount
            .setPaymentDate(financialAccount.getPaymentDate().getNextMonth());
        financialAccount
            .setAmountDue(financialAccount.getAmountDue() + financialAccount.getTermAmount());
      }
    }
  }
}

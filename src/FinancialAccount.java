import java.util.ArrayList;

/**
 * Created by Dominic on 2018-07-09.
 */
public class FinancialAccount {

  private float totalPurchase = 0;
  private float paid = 0;
  private float owed = totalPurchase - paid;
  private float termAmount = 0;
  private float amountDue = 0;
  private float percentage = paid / totalPurchase;
  private String paymentType = "Unknown";
  private CustomDate paymentDate = new CustomDate("0/0/0");
  private ArrayList<String> notes = new ArrayList<>();
  private ArrayList<String> records = new ArrayList<>();

  public float getTotalPurchase() {
    return totalPurchase;
  }

  public float getPaid() {
    return paid;
  }

  public float getOwed() {
    return owed;
  }

  public float getTermAmount() {
    return termAmount;
  }

  public float getAmountDue() {
    return amountDue;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public CustomDate getPaymentDate() {
    return paymentDate;
  }

  public int getPercentage() {
    return Math.round(percentage);
  }

  public ArrayList<String> getNotes() {
    return notes;
  }

  public void addNote(String note) {
    this.notes.add(note);
  }

  public ArrayList<String> getRecords() {
    return records;
  }

  public void addRecord(String record) {
    this.records.add(record);
  }

  public void setTotalPurchase(float totalPurchase) {
    this.totalPurchase = totalPurchase;
    updatePercentage();
  }

  public void setPaid(float paid) {
    this.paid = paid;
    updatePercentage();
  }

  public void setOwed(float owed) {
    this.owed = owed;
  }

  public void setTermAmount(float termAmount) {
    this.termAmount = termAmount;
  }

  public void setAmountDue(float amountDue) {
    this.amountDue = amountDue;
  }

  public void updatePercentage() {
    this.percentage = Math.round((paid / totalPurchase) * 100);
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public void setPaymentDate(String date) {
    this.paymentDate = new CustomDate(date);
  }

  public void popNote() {
    if (!notes.isEmpty()) {
      this.notes.remove(notes.size() - 1);
    }
  }
}

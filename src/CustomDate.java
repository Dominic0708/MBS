import java.io.Serializable;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 * Created by Dominic on 2018-01-08.
 */
public class CustomDate implements Serializable {

  static final long serialVersionUID = 2336513392353070221L;
  public int year;
  public int month;
  public int day;

  public CustomDate(String dob) {
    int first = dob.indexOf("/");
    int second = dob.indexOf("/", first + 1);
    if (first == -1 || second == -1) {
      this.year = 0;
      this.month = 0;
      this.day = 0;
    } else {
      this.year = Integer.parseInt((dob.substring(0, first)));
      this.month = Integer.parseInt(dob.substring(first + 1, second));
      this.day = Integer.parseInt(dob.substring(second + 1));
    }
  }

  public CustomDate() {
    LocalDate now = LocalDate.now();
    year = now.getYear();
    month = now.getMonthValue();
    day = now.getDayOfMonth();
  }

  public CustomDate(int y, int m, int d) {
    this.year = y;
    this.month = m;
    this.day = d;
  }

  public static boolean dayHasPassed(CustomDate date) {
    LocalDate target = LocalDate.of(date.year, date.month, date.day);
    LocalDate now = LocalDate.now();
    now = now.plusDays(1);
    return now.isAfter(target);
  }

  public CustomDate getNextWeek() {
    LocalDate date = LocalDate.of(this.year, this.month, this.day);
    date = date.plusDays(7);
    return new CustomDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
  }

  public CustomDate getNextBiWeek() {
    LocalDate date = LocalDate.of(this.year, this.month, this.day);
    date = date.plusDays(14);
    return new CustomDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
  }

  public CustomDate getNextMonth() {
    LocalDate date = LocalDate.of(this.year, this.month, this.day);
    date = date.plusMonths(1);
    return new CustomDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
  }

  @Override
  public String toString() {
    return year + "/" + month + "/" + day;
  }
}

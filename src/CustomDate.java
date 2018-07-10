import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Dominic on 2018-01-08.
 */
public class CustomDate implements Serializable {

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

  public static boolean dayHasPassed(CustomDate date) {
    if (System.currentDate.year < date.year) {
      return false;
    } else if (System.currentDate.year > date.year) {
      return true;
    } else {
      if (System.currentDate.month < date.month) {
        return false;
      } else if (System.currentDate.month > date.month) {
        return true;
      } else {
        if (System.currentDate.day < date.day) {
          return false;
        } else {
          return true;
        }
      }
    }
  }

  @Override
  public String toString() {
    return year + "/" + month + "/" + day;
  }
}

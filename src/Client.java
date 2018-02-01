import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dominic on 2018-01-08.
 */
public class Client implements Serializable {

  private String name;
  private CustomDate dob;
  private String address;
  private String email;
  private String phone;
  private int age;
  private int height;
  private int weight;
  private int sessionCount;
  private ArrayList<String> notes = new ArrayList<>();
  private ArrayList<String> records = new ArrayList<>();

  public Client(String name, CustomDate dob, String address, String email, String phone, int age,
      int height, int weight, int sc) {
    this.name = name;
    this.dob = dob;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.sessionCount = sc;
  }

  public CustomDate getDob() {
    return dob;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public int getAge() {
    return age;
  }

  public int getHeight() {
    return height;
  }

  public int getWeight() {
    return weight;
  }

  public int getSessionCount() {
    return sessionCount;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDob(CustomDate dob) {
    this.dob = dob;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setSessionCount(int sc) {
    this.sessionCount = sc;
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

  public void popNote() {
    if (!notes.isEmpty()) {
      this.notes.remove(notes.size() - 1);
    }
  }

  public void popRecord() {
    if (!records.isEmpty()) {
      this.records.remove(records.size() - 1);
    }
  }

  public void setInfo(String name, CustomDate dob, String address, String email, String phone,
      int age,
      int height, int weight, int sc) {
    this.name = name;
    this.dob = dob;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.sessionCount = sc;
  }
}

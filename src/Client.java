import java.io.Serializable;

/**
 * Created by Dominic on 2018-01-08.
 */
public class Client implements Serializable{

  private String name;
  private Date dob;
  private String address;
  private String email;
  private String phone;
  private int age;
  private int height;
  private int weight;
  private int sessionCount;
  private int membershipCount;

  public Client(String name, Date dob, String address, String email, String phone, int age, int height, int weight) {
    this.name = name;
    this.dob = dob;
    this.address = address;
    this.email = email;
    this.phone = phone;
    this.age = age;
    this.height = height;
    this.weight = weight;
  }

  public Date getDob() {
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

  public int getMembershipCount() {
    return membershipCount;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDob(Date dob) {
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

  public void setSessionCount(int sessionCount) {
    this.sessionCount = sessionCount;
  }

  public void setMembershipCount(int membershipCount) {
    this.membershipCount = membershipCount;
  }

}

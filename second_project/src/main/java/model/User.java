package model;
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public User(int id, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Номер телефона не может быть пустым");
        }
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("%s %s (ID: %d, Тел: %s)",
                firstName, lastName, id, phoneNumber);
    }
}
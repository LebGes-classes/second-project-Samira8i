package model;

public class Employee extends User {
    private String position;
    private int workPlaceId;
    private boolean isActive;

    public Employee(int id, String firstName, String lastName, String phoneNumber,
                    String position, int workPlaceId) {
        super(id, firstName, lastName, phoneNumber);
        this.position = position;
        this.workPlaceId = workPlaceId;
        this.isActive = true;
    }

    public String getPosition() { return position; }
    public int getWorkPlaceId() { return workPlaceId; }
    public boolean isActive() { return isActive; }

    public void dismiss() {
        this.isActive = false;
        this.workPlaceId = -1;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s, %s)",
                getFirstName(), getLastName(), position,
                isActive ? "активен" : "неактивен");
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setWorkPlaceId(int workPlaceId) {
        this.workPlaceId = workPlaceId;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
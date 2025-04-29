package edu.tcu.cs.frogcrewbackend.member;

import edu.tcu.cs.frogcrewbackend.notification.Notification;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message =  "first name required")
    private String firstName;

    @NotEmpty(message =  "last name required")
    private String lastName;

    @NotEmpty(message =  "email required")
    @Email(message = "Email should be valid.")
    private String email;

    @NotEmpty(message =  "phone number required")
//    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}", message = "A phone number must be in the form XXX-XXX-XXXX")
    private String phoneNumber;

    @NotEmpty(message =  "password required")
    private String password;

    @NotEmpty(message =  "role required")
    private String role;

    @NotEmpty(message =  "qualified positions required")
    private String positions;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Notification> notifications = new ArrayList<>();

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Notification notification) {
        notification.setUser(this);
        this.notifications.add(notification);
    }
}

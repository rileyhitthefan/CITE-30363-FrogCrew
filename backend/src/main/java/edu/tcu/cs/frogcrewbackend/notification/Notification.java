package edu.tcu.cs.frogcrewbackend.notification;

import edu.tcu.cs.frogcrewbackend.member.Member;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import org.apache.catalina.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer notificationId;

    @ManyToOne
    Member user;

    @NotEmpty(message = "empty notification")
    String message;

    Boolean read;

    String date;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

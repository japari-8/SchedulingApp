package model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private String tittle;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;
    private int contactId;


    // FIXME: Does constructor need more parameters?
    public Appointment(int appointmentId, String tittle, String description, String location, String type,
                       LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.tittle = tittle;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }

}

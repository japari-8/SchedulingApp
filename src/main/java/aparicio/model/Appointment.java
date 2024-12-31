package aparicio.model;

import java.time.LocalDateTime;

/** This class creates an appointment.*/
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;
    private int contactId;

    /**This method is used as the constructor for appointments.
     * @param appointmentId the appointment ID
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param startDateTime the appointment start date time
     * @param endDateTime the appointment end date time
     * @param customerId the appointment customer ID
     * @param userId the appointment user ID
     * @param contactId the appointment contact ID
     */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * @param appointmentId the appointment ID to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @param tittle the Title to set
     */
    public void setTitle(String tittle) {
        this.title = tittle;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param startDateTime the start date time to set
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * @param endDateTime the end date time to set
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the start date time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * @return the end date time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

}

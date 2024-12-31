package aparicio.model;


/** This class creates a Contact.*/
public class Contact {
    private int contactId;
    private String contactName;

    /**This method is used as the constructor for contacts.
     * @param contactId the contact ID
     * @param contactName the contact name
     */
    public Contact(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
        //this.email = email;
    }

    /**
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {

        this.contactId = contactId;
    }

    /**
     * @param contactName the contact name to set
     */
    public void setContactName(String contactName) {

        this.contactName = contactName;
    }

    /**
     * @return the contact ID
     */
    public int getContactId() {

        return contactId;
    }

    /**
     * @return the contact name
     */
    public String getContactName() {

        return contactName;
    }

    /**This method overrides the toString method.
     * @return the contact ID and contact name
     */
    @Override
    public String toString() {
        return "[" + contactId + "] " +  contactName;
    }

}



package aparicio.model;

public class Contact {
    private int contactId;
    private String contactName;
    //private String email;

    public Contact(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
        //this.email = email;
    }

    public void setContactId(int contactId) {

        this.contactId = contactId;
    }

    public void setContactName(String contactName) {

        this.contactName = contactName;
    }

    //public void setEmail(String email) {
    //   this.email = email;
    //}

    public int getContactId() {

        return contactId;
    }

    public String getContactName() {

        return contactName;
    }

    @Override
    public String toString() {
        return "[" + contactId + "] " +  contactName;
    }
//public String getEmail() {
    //    return email;
    //}
}



/**
 * Created by Maia on 5/8/2017.
 */

//Contact class that is used for creating the contact objects
public class Contact {
    private String name;
    private String lastName;
    private String number;
    private String street;

    private String city;
    private String state;
    private int zip;

    private static int contactIdCounter= 0;

    protected  int contactID;

    public Contact(String name, String lastName, String number, String street, String city, String state, int zip){
        this.name = name;
        this.lastName = lastName;
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;

        this.contactID = contactIdCounter;
        //Counter remains constant
        contactIdCounter++;
    }

    //Set variable for Contact object
    public void setName(String name){this.name = name;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setNumber(String number){this.number = number;}
    public void setStreet(String street){this.street = street;}
    public void setContactID(Integer cID){this.contactID = cID;}
    public void setCity(String city){this.city = city;}
    public void setState(String state) {this.state = state;}
    public void setZip(Integer zip){this.zip = zip;}

    public String getName(){return this.name;}
    public String getLastName(){return this.lastName;}
    public String getNumber(){return this.number;}
    public String getStreet(){return this.street;}
    public int getContactID() { return contactID;}
    public String getCity(){return this.city;}
    public String getState(){return this.state;}
    public int getZip(){return this.zip;}

    //Add get full name method
    public String getFullName(){return this.name + " " + this.lastName;}
}

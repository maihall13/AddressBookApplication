import java.sql.*;

/**
 * Created by Maia on 5/8/2017.
 */

/*
mysql> create database contacts;
mysql> grant select, insert, create, drop on contats.* to 'maia'@'localhost';
*/


public class ContactsDB {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/contacts";  //Connection string – where's the database?
    static final String USER = "maia";
    static final String PASSWORD = System.getenv("MYSQL_PW");


    //Method used to load database into table - called by AllContactsForm on Startup
    public static void loadTableFromDB() {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS allcontacts(ID INT, Name varchar(30), LastName varchar(30), Phone varchar(50), Street varchar(30), City varchar(30), State varchar(30), Zip INTEGER(22))";
            statement.executeUpdate(createTableSQL);
            System.out.println("Created My Contacts table");

            String fetchAllDataSQL = "SELECT * FROM allcontacts";
            ResultSet rs = statement.executeQuery(fetchAllDataSQL);

            //Cycles through all the rows in the database and adds each row to an array list
            while (rs.next()) {
                String contactName = rs.getString("Name");
                System.out.println("Contact name = " + contactName);
                String contactNumber = rs.getString("Phone");
                String contactStreet = rs.getString("Street");
                String contactLastName = rs.getString("LastName");
                String contactCity = rs.getString("City");
                String contactState = rs.getString("State");
                Integer contactZip = rs.getInt("Zip");

                //creates Contact object and adds to contacts list
                Contact s = new Contact(contactName,contactLastName, contactNumber, contactStreet, contactCity, contactState, contactZip);
                s.setContactID(rs.getInt("ID"));
                AllContactsForm.allContacts.addLast(s);
            }
            //close all connections
            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            System.out.println(se);
        }
    }



    //Method used to add a Contact object to the database
    public static void addContact(Contact newContact) {
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Add input to database
            String addDataSQL = "INSERT INTO allcontacts (ID, Name, LastName, Phone, Street, City, State, Zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement addStatement = conn.prepareStatement(addDataSQL);
            addStatement.setInt(1, newContact.getContactID());
            addStatement.setString(2, newContact.getName());
            addStatement.setString(3, newContact.getLastName());
            addStatement.setString(4, newContact.getNumber());
            addStatement.setString(5, newContact.getStreet());
            addStatement.setString(6, newContact.getCity());
            addStatement.setString(7, newContact.getState());
            addStatement.setInt(8, newContact.getZip());
            addStatement.executeUpdate();

            //confirm completion
            System.out.println("Successfully added row to database from method");

        } catch (SQLException se) {
            System.out.println(se);
        }
    }



    //Method used to update a Contact object - called by EditContactForm
    public static void updateContact(Contact contactUpdating) {

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Add input to database
            String updateDataSQL = ("UPDATE allcontacts SET Name = ?, LastName = ?, Phone = ?, Street = ?, City = ?, State = ?, Zip = ? WHERE ID = ?");
            PreparedStatement updateStatement = conn.prepareStatement(updateDataSQL);
            updateStatement.setString(1, contactUpdating.getName());
            updateStatement.setString(2, contactUpdating.getLastName());
            updateStatement.setString(3, contactUpdating.getNumber());
            updateStatement.setString(4, contactUpdating.getStreet());
            updateStatement.setString(5, contactUpdating.getCity());
            updateStatement.setString(6, contactUpdating.getState());
            updateStatement.setInt(7, contactUpdating.getZip());
            updateStatement.setInt(8, contactUpdating.getContactID());

            updateStatement.executeUpdate();

            //confirm completion
            System.out.println("Successfully updated row to database from method");

        } catch (SQLException se) {
            System.out.println(se);
        }
    }


    public static void deleteContact(Contact newContact) {
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Add input to database
            String addDataSQL = ("DELETE FROM allcontacts WHERE ID = ?");
            PreparedStatement addStatement = conn.prepareStatement(addDataSQL);
            addStatement.setInt(1, newContact.getContactID());
            addStatement.executeUpdate();

            //confirm completion
            System.out.println("Successfully deleted row to database from method");

        } catch (SQLException se) {
            System.out.println(se);
        }
    }
}
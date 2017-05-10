import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Created by Maia on 5/8/2017.
 */
public class AllContactsForm extends JFrame implements WindowListener{
    private JPanel rootPanel;
    private JButton addButton;
    public JTable contactsTable;
    private JButton closeButton;
    private JScrollPane scrollPane;

    //Create table model to allow for editing
    static DefaultTableModel dataModel = new DefaultTableModel(0, 0) {
        //override turns off editing in table and allows for double click method
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    //List that is used to save Contacts to; is iterated through to populate table
    public static LinkedList<Contact> allContacts = new LinkedList<>();



    protected AllContactsForm(){
        setContentPane(rootPanel);
        pack();
        setVisible(true);


        scrollPane.getViewport().setBackground(Color.white);
        JTableHeader header = contactsTable.getTableHeader();

        Color backgroundColor = new Color(255, 242, 211);

        header.setBackground(backgroundColor);
        addButton.setBackground(backgroundColor);
        closeButton.setBackground(backgroundColor);

        //Set dataModel
        contactsTable.setModel(dataModel);
        dataModel.addColumn("Contacts");

        //addWindowListener listens for when the window is opened and runs method(below)
        addWindowListener((WindowListener) this);


        //To add contacts
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Creates Add form
                AddContactForm addForm = new AddContactForm();
                contactsTable.getRowSorter().setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

            }
        });



        //Double Click event used to view more information and edit information about a contact
        contactsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                contactsTable = (JTable)e.getSource();

                if (e.getClickCount() == 2){

                    //Get index of selected item
                    Contact selectedContact = new Contact(null, null, null, null,null, null, 0);
                    String selectedName = contactsTable.getValueAt(contactsTable.getSelectedRow(), 0).toString();

                    //Cycle through list to find match of Contact object in list
                    for(Contact con : allContacts) {
                        if (con.getFullName().equals(selectedName)) {
                            selectedContact = con;
                        }
                    }

                    //Use match from above to create a details form on the selected Contact Object
                    EditContactForm editForm = new EditContactForm(selectedContact);
                }
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
                dispose();
            }
        });
    }





    @Override
    public void windowOpened(WindowEvent e) {
        //Loads database into table using database method
        ContactsDB.loadTableFromDB();

        //Populate table with information from database.
        AddressBookManager.updateTable();
        contactsTable.getRowSorter().setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));

    }
    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(1);
    }
    @Override
    public void windowClosed(WindowEvent e) {    }
    @Override
    public void windowIconified(WindowEvent e) {    }
    @Override
    public void windowDeiconified(WindowEvent e) {    }
    @Override
    public void windowActivated(WindowEvent e) {
        contactsTable.getRowSorter().setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
    }
    @Override
    public void windowDeactivated(WindowEvent e) {    }
}

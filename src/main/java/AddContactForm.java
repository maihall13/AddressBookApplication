import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Maia on 5/8/2017.
 */
public class AddContactForm extends JFrame {
    private JPanel rootPanel;
    private JButton saveButton;
    private JTextField addNameTextField;
    private JTextField addPhoneTextField;
    private JTextField addStreetTextField;
    private JButton cancelButton;
    private JTextField addLastNameTextField;

    private JTextField addZipTextField;
    private JTextField addCityTextField;
    private JComboBox stateComboBox;

    public static String[] stateList = new String[]{
            "", "AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA",
            "HI","ID","IL","IN","IA","KS","KY","LA","ME","MD",
            "MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ",
            "NM","NY","NC","ND","OH","OK","OR","PA","RI","SC",
            "SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};

    protected AddContactForm() {
        setContentPane(rootPanel);
        pack();
        setVisible(true);


        Color backgroundColor = new Color(255, 242, 211);
        saveButton.setBackground(backgroundColor);
        cancelButton.setBackground(backgroundColor);

        for(String state : stateList){
            stateComboBox.addItem(state);
        }


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (addNameTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error: Must have entry for First Name");
                }
                else {
                    if(addZipTextField.getText().isEmpty()){
                        addZipTextField.setText("0");
                    }
                    //Get information from form
                    String name = addNameTextField.getText();
                    String lastName = addLastNameTextField.getText();
                    String number = addPhoneTextField.getText();
                    String street = addStreetTextField.getText();
                    String city = addCityTextField.getText();
                    String state = stateComboBox.getSelectedItem().toString();
                    Integer zip = Integer.valueOf(addZipTextField.getText());

                    //Create Contact object from information from form
                    Contact c = new Contact(name, lastName, number, street, city, state, zip);

                    //Add to contacts list
                    AllContactsForm.allContacts.addLast(c);
                    //Add contact information to database

                    //Add contact to database
                    ContactsDB.addContact(c);

                    //Update the Jtable and close the form
                    AddressBookManager.updateTable();

                    JOptionPane.showMessageDialog(null, "Added Contact to Address Book :)");

                    dispose();
                }
            }
        });

        //Cancel button just closes the form
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        addPhoneTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE)) || (addPhoneTextField.getText().length()>11)) {
                    getToolkit().beep();
                    e.consume();
                }

            }
        });
        addPhoneTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(addPhoneTextField.getText().length()==3){
                    addPhoneTextField.setText(addPhoneTextField.getText() + "-");
                }
                if(addPhoneTextField.getText().length()==7){
                    addPhoneTextField.setText(addPhoneTextField.getText() + "-");
                }

            }
        });
        addZipTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE)) || (addZipTextField.getText().length()>9)) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
        addCityTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(addCityTextField.getText().length()>22){
                    getToolkit().beep();
                    e.consume();
                }
            }
        });


    }

}
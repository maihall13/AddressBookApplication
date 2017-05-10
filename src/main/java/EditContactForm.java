import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Maia on 5/8/2017.
 */
public class EditContactForm extends JFrame {
    private JTextField editNameTextField;
    private JTextField editPhoneTextField;
    private JTextField editStreetTextField;
    private JPanel rootPanel;
    public JButton saveButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JTextField editLastNameTextField;
    private JTextField editCityTextField;
    private JTextField editZIPTextField;
    private JComboBox stateComboBox;

    protected EditContactForm(final Contact contactToEdit) {
        setContentPane(rootPanel);
        pack();
        setVisible(true);


        Color backgroundColor = new Color(255, 242, 211);
        saveButton.setBackground(backgroundColor);
        cancelButton.setBackground(backgroundColor);
        deleteButton.setBackground(backgroundColor);

        for(String state : AddContactForm.stateList){
            stateComboBox.addItem(state);
        }

        //Pulls information from the selected Contact object and fills fields.
        editStreetTextField.setText(contactToEdit.getStreet());
        editLastNameTextField.setText(contactToEdit.getLastName());
        editNameTextField.setText(contactToEdit.getName());
        editPhoneTextField.setText(contactToEdit.getNumber());
        editCityTextField.setText(contactToEdit.getCity());
        stateComboBox.setSelectedItem(contactToEdit.getState());
        editZIPTextField.setText(String.valueOf(contactToEdit.getZip()));


        //Method run when add button clicked
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (editNameTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error: Must have entry for First Name");
                }
                else {
                    if(editZIPTextField.getText().isEmpty()){
                        editZIPTextField.setText("0");
                    }

                    //Update information for the Contact object selected
                    contactToEdit.setName(editNameTextField.getText());
                    contactToEdit.setLastName(editLastNameTextField.getText());
                    contactToEdit.setNumber(editPhoneTextField.getText());
                    contactToEdit.setStreet(editStreetTextField.getText());
                    contactToEdit.setCity(editCityTextField.getText());
                    contactToEdit.setState(stateComboBox.getSelectedItem().toString());
                    contactToEdit.setZip(Integer.valueOf(editZIPTextField.getText()));

                    //Update information in allContacts list by replacing the original Contact object
                    //with the updated one
                    int index = AllContactsForm.allContacts.indexOf(contactToEdit);
                    AllContactsForm.allContacts.set(index, contactToEdit);

                    //Update information in database for Contact object
                    ContactsDB.updateContact(contactToEdit);

                    //Update Jtable
                    AddressBookManager.updateTable();

                    JOptionPane.showMessageDialog(null, "Contact has been successfully modified");

                    //Close form
                    dispose();
                }
            }
        });


        //Method run when the delete button is selected
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Removes Contact object from from allContacts list
                int index = AllContactsForm.allContacts.indexOf(contactToEdit);
                AllContactsForm.allContacts.remove(index);

                //Removes Contact object from database
                ContactsDB.deleteContact(contactToEdit);

                //Update Jtable pulls from allContacts list
                AddressBookManager.updateTable();

                //Close form
                dispose();
            }
        });

        //Cancel button just closes the form
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        editPhoneTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE)) || (editPhoneTextField.getText().length()>11)) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
        editPhoneTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if((editPhoneTextField.getText().length()==3) ||(editPhoneTextField.getText().length()==7)){
                    editPhoneTextField.setText(editPhoneTextField.getText() + "-");
                }
            }
        });
        editZIPTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE)) || (editZIPTextField.getText().length()>9)) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
        editCityTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(editCityTextField.getText().length()>22){
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
    }
}

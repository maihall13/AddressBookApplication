import javax.swing.*;

/**
 * Created by Maia on 5/8/2017.
 */
public class AddressBookManager {
    //calls main form (all contacts form
    public static void main(String[] args) {
        AllContactsForm gui = new AllContactsForm();
    }

    public static void updateTable() {
        int rowCount = AllContactsForm.dataModel.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            AllContactsForm.dataModel.removeRow(i);
        }

        for (Contact rest : AllContactsForm.allContacts) {
            Object[] row = {rest.getFullName()};
            AllContactsForm.dataModel.addRow(row);
        }
    }
}

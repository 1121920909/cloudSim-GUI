package GUI;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.security.PrivateKey;

public class CustomCloudlet {
    private JTable tableCloudletList;
    private JButton buttonDelete;
    private JButton buttonAdd;
    private JButton buttonOk;
    private JPanel panelTitle;
    private JPanel panelMain;
    private JPanel panelBottom;
    private JLabel labelTitle;
    private JLabel labelCloudletList;

    private TableModel clooudletTableModel;
    private Object[] header;
    private Object[][] cloudletData;

    public CustomCloudlet() {
        header = new Object[]{"id","length",""};
    }
}

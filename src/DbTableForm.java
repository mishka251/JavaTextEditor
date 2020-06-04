import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DbTableForm extends JFrame {

    PosgtresDB db;
    String tableName;
    JTable table;
    DefaultTableModel tableModel;
    JButton btnDelete;
    JButton btnDrop;
    JTextField inputDelete;

    DbTableForm(PosgtresDB db, String tableName) {
        this.db = db;
        this.tableName = tableName;

        setLayout(null);
        setSize(700, 400);
        setVisible(true);
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        setTitle("Text Editor. Rezyapov D.N. Вариант 5  " + sd.format(new Date()));

        JPanel panel1 = new JPanel();
        panel1.setBounds(20, 50, 660, 230);
        panel1.setVisible(true);
        add(panel1);

        JLabel lblInput = new JLabel("Значеине удаляемого");
        lblInput.setBounds(10, 10, 100, 20);
        add(lblInput);

        inputDelete = new JTextField();
        inputDelete.setBounds(150, 10, 100, 20);
        add(inputDelete);

        btnDelete = new JButton("Удалить строк");
        btnDelete.setBounds(350, 10, 90, 20);
        add(btnDelete);

        tableModel = new DefaultTableModel();

        table = new JTable(tableModel);
        panel1.add(new JScrollPane(table));

        btnDrop = new JButton("Drop table");
        btnDrop.setBounds(350, 330, 90, 20);
        add(btnDrop);

        this.fillTable();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        btnDrop.addActionListener(this::dropTable);
        btnDelete.addActionListener(this::delete);
    }

    void dropTable(ActionEvent event) {
        int dialogResult = JOptionPane.showConfirmDialog(this, "Точно удалить таблицу?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                db.dropTable(tableName);
                setVisible(false);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void delete(ActionEvent event) {
        String deleteCondition = inputDelete.getText();
        if (deleteCondition == null || deleteCondition.equals("")) {
            JOptionPane.showMessageDialog(this, "Введите фамилию кого удалять");
            return;
        }
        try {
            db.delete(tableName, "Surname", deleteCondition);
            fillTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void fillTable() {
        try {
            while (tableModel.getRowCount() > 0) {
                tableModel.removeRow(0);
            }

            Map<String, ArrayList<Object>> data = db.select(tableName);
            Object[] columnNames = data.keySet().toArray();
            int rows = data.get(columnNames[0]).size();
            int columns = columnNames.length;
            Object[][] values = new Object[rows][];
            for (int i = 0; i < rows; i++) {
                values[i] = new Object[columns];
                for (int j = 0; j < columns; j++) {
                    values[i][j] = data.get(columnNames[j]).get(i);
                }
            }
            tableModel.setColumnIdentifiers(columnNames);
            // Наполнение модели данными
            for (int i = 0; i < values.length; i++) {
                tableModel.addRow(values[i]);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

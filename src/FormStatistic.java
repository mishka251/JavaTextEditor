import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FormStatistic extends JFrame {

    StatisticData data;

    JLabel lblFilledFieldsCount;
    JLabel lblErrorCount;
    JLabel lblSoglasn;
    JLabel lblSavedCount;
    JLabel lblGlasn;

    JButton btnSaveToDb;
    PosgtresDB db;

    FormStatistic(StatisticData data, PosgtresDB db) {
        this.data=data;
        this.db=db;
        setLayout(null);
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        setTitle("Резяпов Д.Н. Вариант 5  " + sd.format(date));
        setSize(570, 200);
        Panel4 panel4 = new Panel4();
        panel4.setVisible(true);
        add(panel4);
        setVisible(true);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String saveTime = data.getSaveTime() == null ? "Не сохранено" : sdfDate.format(data.getSaveTime());
        lblFilledFieldsCount = new JLabel(Integer.toString(data.getFilledFields()));
        lblErrorCount = new JLabel("поле ввода текстового редактора, естественно, ошибок содержать не может");
        lblSoglasn = new JLabel(Integer.toString(data.getSoglasn()));
        lblSavedCount = new JLabel(saveTime);
        lblGlasn = new JLabel(Integer.toString(data.getGlasn()));

        lblFilledFieldsCount.setBounds(177, 5, 120, 20);
        lblErrorCount.setBounds(5, 45, 500, 20);
        lblSoglasn.setBounds(150, 65, 50, 20);
        lblSavedCount.setBounds(185, 85, 250, 20);
        lblGlasn.setBounds(137, 105, 50, 20);

        panel4.add(lblFilledFieldsCount);
        panel4.add(lblErrorCount);
        panel4.add(lblSoglasn);
        panel4.add(lblSavedCount);
        panel4.add(lblGlasn);

        btnSaveToDb = new JButton("Save");
        btnSaveToDb.setBounds(350, 130, 90, 20);
        add(btnSaveToDb);
        btnSaveToDb.addActionListener(this::saveToDb);
    }

    void saveToDb(ActionEvent event){
        String tableName = "statistic";
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        HashMap<String, Object> values = new HashMap<>();
        values.put("filledfields", data.getFilledFields());
        values.put("errorscount", "нет ошибок, т.к. одно текстовое поле");
        values.put("soglasnykh", data.getSoglasn());
        values.put("time", sd.format(new Date()));
        values.put("full_test", data.getFullText());
        CreateInstanceForm form = new CreateInstanceForm(db, tableName, values);
    }

    static class Panel4 extends JPanel {
        Panel4() {
            setLayout(null);
            setBounds(10, 10, 520, 110);
            setBackground(Color.cyan);
            JLabel label1 = new JLabel("Число заполненных полей:");
            JLabel label2 = new JLabel("Kоличество ошибок при заполнении полей:");
            JLabel label3 = new JLabel("Число согласных букв:");
            JLabel label4 = new JLabel("время сохранения файла:");
            JLabel label9 = new JLabel("Число гласных букв:");

            label1.setBounds(10, 5, 162, 20);
            label2.setBounds(10, 25, 255, 20);
            label3.setBounds(10, 65, 150, 20);
            label4.setBounds(10, 85, 180, 20);
            label9.setBounds(10, 105, 170, 20);

            add(label1);
            add(label2);
            add(label3);
            add(label4);
            add(label9);
        }
    }
}

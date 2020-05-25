import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormStatistic extends JFrame {

    JLabel lblFilledFieldsCount;
    JLabel lblErrorCount;
    JLabel lblSoglasn;
    JLabel lblSavedCount;
    JLabel lblGlasn;

    FormStatistic(StatisticData data) {
        setLayout(null);
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        setTitle("Резяпов Д.Н. Вариант 5  " + sd.format(date));
        setSize(370, 170);
        Panel4 panel4 = new Panel4();
        panel4.setVisible(true);
        add(panel4);
        setVisible(true);

        lblFilledFieldsCount = new JLabel("0");
        lblErrorCount = new JLabel("0");
        lblSoglasn = new JLabel(Integer.toString(data.getSoglasn()));
        lblSavedCount = new JLabel("0");
        lblGlasn = new JLabel(Integer.toString(data.getGlasn()));

        lblFilledFieldsCount.setBounds(177, 5, 120, 20);
        lblErrorCount.setBounds(270, 25, 50, 20);
        lblSoglasn.setBounds(150, 45, 50, 20);
        lblSavedCount.setBounds(185, 65, 50, 20);
        lblGlasn.setBounds(137, 85, 50, 20);

        panel4.add(lblFilledFieldsCount);
        panel4.add(lblErrorCount);
        panel4.add(lblSoglasn);
        panel4.add(lblSavedCount);
        panel4.add(lblGlasn);

    }

    static class Panel4 extends JPanel {
        Panel4() {
            setLayout(null);
            setBounds(10, 10, 320, 110);
            setBackground(Color.cyan);
            JLabel label1 = new JLabel("Число заполненных полей:");
            JLabel label2 = new JLabel("Kоличество ошибок при заполнении полей:");
            JLabel label3 = new JLabel("Число согласных букв:");
            JLabel label4 = new JLabel("Число сохраненных резюме:");
            JLabel label9 = new JLabel("Число гласных букв:");

            label1.setBounds(10, 5, 162, 20);
            label2.setBounds(10, 25, 255, 20);
            label3.setBounds(10, 45, 150, 20);
            label4.setBounds(10, 65, 180, 20);
            label9.setBounds(10, 85, 170, 20);

            add(label1);
            add(label2);
            add(label3);
            add(label4);
            add(label9);

        }
    }
}

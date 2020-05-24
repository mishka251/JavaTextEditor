import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormStatistic extends JFrame {

    FormStatistic() {
        setLayout(null);
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        setTitle("Резяпов Д.Н. Вариант 5  " + sd.format(date));
        setSize(370, 170);
        Panel4 panel4 = new Panel4();
        panel4.setVisible(true);
        add(panel4);
        setVisible(true);
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
            JLabel label5 = new JLabel("0");
            JLabel label6 = new JLabel("0");
            JLabel label7 = new JLabel("0");
            JLabel label8 = new JLabel("0");
            JLabel label10 = new JLabel("0");
            label1.setBounds(10, 5, 162, 20);
            label2.setBounds(10, 25, 255, 20);
            label3.setBounds(10, 45, 150, 20);
            label4.setBounds(10, 65, 180, 20);
            label9.setBounds(10, 85, 170, 20);
            label5.setBounds(177, 5, 120, 20);
            label6.setBounds(270, 25, 50, 20);
            label7.setBounds(150, 45, 50, 20);
            label8.setBounds(185, 65, 50, 20);
            label10.setBounds(137, 85, 50, 20);
            add(label1);
            add(label2);
            add(label3);
            add(label4);
            add(label5);
            add(label6);
            add(label7);
            add(label8);
            add(label9);
            add(label10);
        }
    }
}

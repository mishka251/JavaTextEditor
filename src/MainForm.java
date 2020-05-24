import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainForm extends JFrame {
    JPanel panelUp;
    JPanel panelCenter;
    JPanel panelDown;
    JTextArea area;

    JButton[] allButtons;
    JButton btnCopy;
    JButton btnPaste;
    JButton btnColor;
    JButton btnStatistic;
    JButton btnEnter;
    JButton btnSave;
    JButton btnLoad;
    JButton btnFind;
    JButton btnShowBuffer;
    JButton btnToLeft;
    JButton btnToRight;
    JButton btnClose;

    JLabel lblCharactersCount;
    JLabel lblWordsCount;
    JLabel lblLinesCount;
    JLabel lblPunctuationsCount;


    //  static Panel3 panel3;
    MainForm() {
        setLayout(null);
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        setTitle("Text Editor. Rezyapov D.N. Вариант 5  " + sd.format(date));
        setSize(715, 670);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //panel1
        panelUp = new Panel1();
        panelUp.setVisible(true);
        add(panelUp, BorderLayout.NORTH);

        //textarea
        area = new JTextArea();
        area.setBounds(10, 10, 350, 600);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        panelUp.add(area, BorderLayout.CENTER);

        //panel2
        panelCenter = new Panel2();
        panelCenter.setVisible(true);
        add(panelCenter);


        //buttons
        allButtons = new JButton[12];
        allButtons[0] = new JButton("Копировать");
        btnCopy = allButtons[0];
        allButtons[1] = new JButton("Вставить");
        btnPaste = allButtons[1];
        allButtons[2] = new JButton("Цвет");
        btnColor = allButtons[2];
        allButtons[3] = new JButton("Статистика");
        btnStatistic = allButtons[3];
        allButtons[4] = new JButton("Enter");
        btnEnter = allButtons[4];
        allButtons[5] = new JButton("Сохранить");
        btnSave = allButtons[5];
        allButtons[6] = new JButton("Загрузить");
        btnLoad = allButtons[6];
        allButtons[7] = new JButton("Найти");
        btnFind = allButtons[7];
        allButtons[8] = new JButton("буффер");
        btnShowBuffer = allButtons[8];
        allButtons[9] = new JButton("ToLeft");
        btnToLeft = allButtons[9];
        allButtons[10] = new JButton("ToRight");
        btnToRight = allButtons[10];
        allButtons[11] = new JButton("Выход");
        btnClose = allButtons[11];


        for (int i = 0; i < 12; i++) {
            int x = 10 + 100 * (i % 3);
            int y = 5 + 100 * (i / 3);

            allButtons[i].setFont(new Font("Dialog", Font.PLAIN, 10));
            allButtons[i].setBounds(x, y, 95, 50);
            panelCenter.add(allButtons[i], BorderLayout.NORTH);
        }

        btnStatistic.addActionListener(this::showStatistic);
        btnClose.addActionListener(this::close);
        btnColor.addActionListener(this::changeColor);


        //panel3
        panelDown = new Panel3();
        panelDown.setVisible(true);
        add(panelDown, BorderLayout.SOUTH);


        lblCharactersCount = new JLabel("0");
        lblWordsCount = new JLabel("0");
        lblLinesCount = new JLabel("0");
        lblPunctuationsCount = new JLabel("0");

        lblCharactersCount.setBounds(115, 10, 103, 20);
        lblWordsCount.setBounds(85, 40, 74, 20);
        lblLinesCount.setBounds(90, 70, 77, 20);
        lblPunctuationsCount.setBounds(170, 100, 158, 20);

        panelDown.add(lblCharactersCount, BorderLayout.SOUTH);
        panelDown.add(lblWordsCount, BorderLayout.SOUTH);
        panelDown.add(lblLinesCount, BorderLayout.SOUTH);
        panelDown.add(lblPunctuationsCount, BorderLayout.SOUTH);


        setVisible(true);
    }

    /**
     * обработчиик нажания на кнопку показать статистикку - показ формы статистики
     * TODO передавать в форму параметры для показа
     * @param e
     */
    void showStatistic(ActionEvent e) {
        FormStatistic fs = new FormStatistic();
    }

    /**
     * Обработчик нажатия на кнопку закрыть
     * Закрытие приложения
     * @param e
     */
    void close(ActionEvent e) {
        System.exit(0);
    }

    void changeColor(ActionEvent e){
        for(int i=0; i<12; i++){
            Color color = new Color(20*i);
            allButtons[i].setBackground(color);
        }
    }

    static class Panel1 extends JPanel {
        Panel1() {
            setLayout(null);
            setBounds(5, 5, 370, 620);
            setBackground(Color.cyan);
        }
    }

    static class Panel2 extends JPanel {
        Panel2() {
            setBounds(380, 5, 315, 360);
            setBackground(Color.cyan);
            setLayout(null);
        }

    }


    static class Panel3 extends JPanel {
        Panel3() {
            setLayout(null);
            setBounds(380, 370, 315, 255);
            setBackground(Color.cyan);
            JLabel label1 = new JLabel("Число символов:");
            JLabel label2 = new JLabel("Число слов:");
            JLabel label3 = new JLabel("Число строк:");
            JLabel label4 = new JLabel("Число знаков препинания:");

            label1.setBounds(10, 10, 110, 20);
            label2.setBounds(10, 40, 75, 20);
            label3.setBounds(10, 70, 80, 20);
            label4.setBounds(10, 100, 160, 20);

            add(label1, BorderLayout.SOUTH);
            add(label2, BorderLayout.SOUTH);
            add(label3, BorderLayout.SOUTH);
            add(label4, BorderLayout.SOUTH);
        }
    }

}

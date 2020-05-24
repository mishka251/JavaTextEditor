import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainForm extends JFrame {
    JFrame jFrame;
    // JPanel jPanelUp = new JPanel();
    //JPanel jPanelCenter = new JPanel();
    // JPanel jPanelDown = new JPanel();
    static JPanel jpanelUp;
    static JPanel jpanelCenter;
    static JPanel jpanelDown;
    //  static Panel3 panel3;
    MainForm()
    {

        setLayout(null);
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        setTitle("Text Editor. Rezyapov D.N. Вариант 5  "+sd.format(date));
        setSize(715,670);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jpanelUp = new Panel1();
        jpanelUp.setVisible(true);
        add(jpanelUp, BorderLayout.NORTH);
        jpanelCenter = new Panel2();
        jpanelCenter.setVisible(true);
        add(jpanelCenter);
        jpanelDown = new Panel3();
        jpanelDown.setVisible(true);
        add(jpanelDown, BorderLayout.SOUTH);

        setVisible(true);


    }

    static class Panel1 extends JPanel
    {
        Panel1()
        {
            setLayout(null);
            setBounds(5,5,370,620);
            setBackground(Color.cyan);
            JTextArea area = new JTextArea();
            area.setBounds(10,10, 350, 600);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            add(area, BorderLayout.CENTER);

        }
    }

    static class Panel2 extends JPanel
    {
        JButton[] jb;
        Panel2()
        {
            setBounds(380,5,315,360);
            setBackground(Color.cyan);
            setLayout(null);
            jb = new JButton[12];
            jb[0] = new JButton("Копировать");
            jb[1] = new JButton("Вставить");
            jb[2] = new JButton("Цвет");
            jb[3] = new JButton("Статистика");
            jb[4] = new JButton("Enter");
            jb[5] = new JButton("Сохранить");
            jb[6] = new JButton("Загрузить");
            jb[7] = new JButton("Найти");
            jb[8] = new JButton("буффер");
            jb[9] = new JButton("Анкеты");
            jb[10] = new JButton("Вакансии");
            jb[11] = new JButton("Выход");


            for(int i=0;i<12;i++)
            {
                int x=i*100+10,y=10;
                if(i>2)
                {
                    x=(i-3)*100+10;
                    y=100;
                }
                if(i>5)
                {
                    x=(i-6)*100+10;
                    y=200;
                }
                if(i>8)
                {
                    x=(i-9)*100+10;
                    y=300;
                }

                jb[i].setFont(new Font("Dialog", Font.PLAIN, 10));
                jb[i].setBounds(x,y,95,50);
                add(jb[i],BorderLayout.NORTH);
            }
            for(int i=0;i<jb.length;i++)
            {
                jb[i].addActionListener(this::actionPerformed);
            }
        }

        private void actionPerformed(ActionEvent e) {
            if(e.getSource()==jb[3])
            {
                new FormStatistic();
            }
            if(e.getSource()==jb[11])
            {
                System.exit(0);
            }
        }
    }
    static class Panel3 extends JPanel
    {
        Panel3()
        {
            setLayout(null);
            setBounds(380,370,315,255);
            setBackground(Color.cyan);
            JLabel label1=new JLabel("Число символов:");
            JLabel label2=new JLabel("Число слов:");
            JLabel label3=new JLabel("Число строк:");
            JLabel label4=new JLabel("Число знаков препинания:");
            JLabel label5=new JLabel("0");
            JLabel label6=new JLabel("0");
            JLabel label7=new JLabel("0");
            JLabel label8=new JLabel("0");
            label1.setBounds(10,10,110,20);
            label2.setBounds(10,40,75,20);
            label3.setBounds(10,70,80,20);
            label4.setBounds(10,100,160,20);
            label5.setBounds(115,10,103,20);
            label6.setBounds(85,40,74,20);
            label7.setBounds(90,70,77,20);
            label8.setBounds(170,100,158,20);

            add(label1, BorderLayout.SOUTH);
            add(label2, BorderLayout.SOUTH);
            add(label3, BorderLayout.SOUTH);
            add(label4, BorderLayout.SOUTH);
            add(label5, BorderLayout.SOUTH);
            add(label6, BorderLayout.SOUTH);
            add(label7, BorderLayout.SOUTH);
            add(label8, BorderLayout.SOUTH);

        }
    }

}

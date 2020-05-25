import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MainForm extends JFrame implements ClipboardOwner {
    JPanel panelUp;
    JPanel panelCenter;
    JPanel panelDown;
    JTextPane area;

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
    JFileChooser fileChooser;


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
        area = new JTextPane();
        area.setBounds(10, 10, 350, 600);
        // area.setLineWrap(true);
        // area.setWrapStyleWord(true);
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

        btnCopy.addActionListener(this::copyToBuffer);
        btnPaste.addActionListener(this::pasteFromBuffer);

        btnLoad.addActionListener(this::loadFromFile);
        btnSave.addActionListener(this::saveToFile);

        btnEnter.addActionListener(this::addNewLine);

        btnToRight.addActionListener(this::setRightAlignment);
        btnToLeft.addActionListener(this::setLeftAlignment);

        btnFind.addActionListener(this::searchText);

        btnShowBuffer.addActionListener(this::showBuffer);

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

        fileChooser = new JFileChooser();
        setVisible(true);
    }

    /**
     * обработчиик нажания на кнопку показать статистикку - показ формы статистики
     * TODO передавать в форму параметры для показа
     *
     * @param e событие
     */
    void showStatistic(ActionEvent e) {
        FormStatistic fs = new FormStatistic();
    }

    /**
     * Обработчик нажатия на кнопку закрыть
     * Закрытие приложения
     *
     * @param e событие
     */
    void close(ActionEvent e) {
        System.exit(0);
    }

    /**
     * Обработчик нажатия на кнопку цвет
     * Изменение цвета фона каждой из кнопок на Color(20*i)
     *
     * @param e сыобтие
     */
    void changeColor(ActionEvent e) {
        for (int i = 0; i < 12; i++) {
            Color color = new Color(20 * i);
            allButtons[i].setBackground(color);
        }
    }

    /**
     * Обработчик нажатия на кнопки копировать
     * копирование в системный буфер обмена
     *
     * @param e событие
     */
    void copyToBuffer(ActionEvent e) {
        String value = area.getText();
        TransferableText transferableText = new TransferableText(value);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferableText, this);
    }

    /**
     * Обработчик нажатия на кнопку вставить
     * Вставка в текст  из системный буфер обмена
     *
     * @param e событие
     */
    void pasteFromBuffer(ActionEvent e) {

        Transferable clipboardData = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);

        if (clipboardData != null) {
            if (clipboardData.isDataFlavorSupported(TransferableText.HTML_FLAVOR)) {
                try {
                    String text = (String) clipboardData.getTransferData(TransferableText.HTML_FLAVOR);
                    area.setText(area.getText() + text);
                    //area.append(text);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * Обработчик нажатия на кнопк загрузить
     * Загрузка из файла
     *
     * @param e соыбтие
     */
    void loadFromFile(ActionEvent e) {
        int dialogResult = fileChooser.showOpenDialog(this);
        if (dialogResult != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selected = fileChooser.getSelectedFile();
        if (selected.canRead()) {
            try {
                FileReader sr = new FileReader(selected);
                Scanner scanner = new Scanner(sr);
                StringBuilder text = new StringBuilder();
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine());
                    if (scanner.hasNextLine()) {
                        text.append(System.lineSeparator());
                    }
                }
                sr.close();
                area.setText(text.toString());
            } catch (FileNotFoundException ex) {
                //TODO
            } catch (IOException ex) {
                //ex.printStackTrace();
                //TODO
            }
        } else {
            //TODO
        }
    }

    /**
     * Обработчик нажатия кнопки сохранить
     * Сохранеине текста в файл
     *
     * @param e событие
     */
    void saveToFile(ActionEvent e) {
        int dialogResult = fileChooser.showSaveDialog(this);
        if (dialogResult != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selected = fileChooser.getSelectedFile();
        try {
            FileWriter fw = new FileWriter(selected);
            fw.write(area.getText());
            fw.close();
        } catch (java.io.IOException ex) {
            //TODO
        }
    }

    /**
     * Обработчик нажатия на клавишу enter
     * Добавление новой строки в конец текста
     *
     * @param e событие
     */
    void addNewLine(ActionEvent e) {
        area.setText(area.getText() + System.lineSeparator());
    }

    /**
     * Обработчик нажатия на кнопку Выравнивание лево
     * Устаналивает в текстовом редакторе выравнивание слева
     *
     * @param e событие
     */
    void setLeftAlignment(ActionEvent e) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_LEFT);
        StyledDocument doc = (StyledDocument) area.getDocument();
        doc.setParagraphAttributes(0, doc.getLength() - 1, attrs, false);
    }

    /**
     * Обработчик нажатия на кнопку Выравнивание право
     * Устаналивает в текстовом редакторе выравнивание справа
     *
     * @param e событие
     */
    void setRightAlignment(ActionEvent e) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_RIGHT);
        StyledDocument doc = (StyledDocument) area.getDocument();
        doc.setParagraphAttributes(0, doc.getLength() - 1, attrs, false);
    }


    /**
     * Обработчик нажатия на кнопку поиска
     * Поиск текста и выделение в textPane
     * JAVA, почему \r\n - это 2 символа, но в JTextPane CaretPosition/select они считаюься за одного?
     *
     * @param e событие
     */
    void searchText(ActionEvent e) {
        String term = JOptionPane.showInputDialog(this, "Что ищем?");
        String text = area.getText();
        text = text.replaceAll(System.lineSeparator(), "\n");

        int cursorPosition = area.getCaretPosition();

        int position = text.indexOf(term, cursorPosition);
        if (position != -1) {
            area.select(position, position + term.length());
        } else {
            position = text.indexOf(term, 0);
            if (position != -1) {
                area.select(position, position + term.length());
            }
        }
        area.setSelectionColor(Color.BLUE);
        area.grabFocus();
    }


    void showBuffer(ActionEvent e){

        Transferable clipboardData = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);

        if (clipboardData != null) {
            if (clipboardData.isDataFlavorSupported(TransferableText.HTML_FLAVOR)) {
                try {
                    String text = (String) clipboardData.getTransferData(TransferableText.HTML_FLAVOR);
                    JOptionPane.showMessageDialog(this,
                            text,
                            "Содержимое буфера", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
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

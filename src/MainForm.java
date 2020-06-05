import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class MainForm extends JFrame implements ClipboardOwner {
    JPanel panelTextArea;
    JPanel panelButtons;
    JPanel panelInfo;
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


    JButton btnConnectToDb;
    JButton btnShowDb;
    JButton btnCreateTable;


    JLabel lblCharactersCount;
    JLabel lblWordsCount;
    JLabel lblLinesCount;
    JLabel lblPunctuationsCount;
    JFileChooser fileChooser;

    Date saveTime = null;

    PosgtresDB db;

    /**
     * Название таблицы в БД для соискателей(резюме)
     */
    String jobSeekerTableName = "emploeye";

    /**
     * Название поля для текста резюме в таблице соискателей
     */
    String resumeFieldName = "resume";


    void initForm() {
        Date date = new Date();
        db = new PosgtresDB();
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");
        setTitle("Text Editor. Rezyapov D.N. Вариант 5  " + sd.format(date));
        setSize(855, 670);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                close(null);
            }
        });
    }

    MainForm() {
        initForm();
        setLayout(new GridBagLayout());
        GridBagConstraints panelTextConstraints = new GridBagConstraints();

        panelTextConstraints.fill = GridBagConstraints.BOTH;
        panelTextConstraints.gridx = 0;
        panelTextConstraints.gridy = 0;
        panelTextConstraints.gridwidth = 1;
        panelTextConstraints.gridheight = 2;
        panelTextConstraints.weighty = 1;
        panelTextConstraints.weightx = 1.5;
        panelTextConstraints.insets = new Insets(5, 5, 5, 5);
        //panel1
        panelTextArea = new Panel1();
        panelTextArea.setVisible(true);
        add(panelTextArea, panelTextConstraints);

        //textarea
        area = new JTextPane();
        area.setBounds(10, 10, 350, 600);
        area.getDocument().addDocumentListener(new TextChangeListener());
        panelTextArea.add(area);

        //panel2

        GridBagConstraints panelButtonsConstraints = new GridBagConstraints();

        panelButtonsConstraints.fill = GridBagConstraints.BOTH;
        panelButtonsConstraints.gridx = 1;
        panelButtonsConstraints.gridy = 0;
        panelButtonsConstraints.gridwidth = 1;
        panelButtonsConstraints.gridheight = 1;
        panelButtonsConstraints.weightx = 0.5;
        panelButtonsConstraints.weighty = 0.5;

        panelButtonsConstraints.insets = new Insets(5, 5, 5, 5);

        panelButtons = new Panel2();
        panelButtons.setVisible(true);
        add(panelButtons, panelButtonsConstraints);


        //buttons
        allButtons = new JButton[15];
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

        allButtons[12] = new JButton("Connect");
        allButtons[13] = new JButton("Show");
        allButtons[14] = new JButton("CreateTable");
        btnConnectToDb = allButtons[12];
        btnShowDb = allButtons[13];
        btnCreateTable = allButtons[14];


        for (int i = 0; i < 15; i++) {
            GridBagConstraints buttonConstraints = new GridBagConstraints();
            buttonConstraints.gridx = i % 3;
            buttonConstraints.gridy = i / 3;
            buttonConstraints.gridheight = 1;
            buttonConstraints.gridwidth = 1;
            buttonConstraints.weightx = 1;
            buttonConstraints.weighty = 1;
            buttonConstraints.fill = GridBagConstraints.BOTH;
            buttonConstraints.insets = new Insets(0, 10, 15, 10);

            allButtons[i].setFont(new Font("Dialog", Font.PLAIN, 10));
            panelButtons.add(allButtons[i], buttonConstraints);
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


        btnConnectToDb.addActionListener(this::connectToDb);

        btnShowDb.addActionListener(this::showDb);

        btnCreateTable.addActionListener(this::createTable);

        //panel3
        GridBagConstraints panelInfoConstraints = new GridBagConstraints();

        panelInfoConstraints.fill = GridBagConstraints.BOTH;
        panelInfoConstraints.gridx = 1;
        panelInfoConstraints.gridy = 1;
        panelInfoConstraints.gridwidth = 1;
        panelInfoConstraints.gridheight = 1;
        panelInfoConstraints.weightx = 0.5;
        panelInfoConstraints.weighty = 0.5;
        panelInfoConstraints.insets = new Insets(5, 5, 5, 5);

        panelInfo = new Panel3();
        panelInfo.setVisible(true);
        add(panelInfo, panelInfoConstraints);


        lblCharactersCount = new JLabel("0");
        lblWordsCount = new JLabel("0");
        lblLinesCount = new JLabel("0");
        lblPunctuationsCount = new JLabel("0");

        lblCharactersCount.setBounds(115, 10, 103, 20);
        lblWordsCount.setBounds(85, 40, 74, 20);
        lblLinesCount.setBounds(90, 70, 77, 20);
        lblPunctuationsCount.setBounds(170, 100, 158, 20);

        JLabel label1 = new JLabel("Число символов:");
        JLabel label2 = new JLabel("Число слов:");
        JLabel label3 = new JLabel("Число строк:");
        JLabel label4 = new JLabel("Число знаков препинания:");

        label1.setBounds(10, 10, 110, 20);
        label2.setBounds(10, 40, 75, 20);
        label3.setBounds(10, 70, 80, 20);
        label4.setBounds(10, 100, 160, 20);

        panelInfo.add(label1);
        panelInfo.add(label2);
        panelInfo.add(label3);
        panelInfo.add(label4);

        panelInfo.add(lblCharactersCount);
        panelInfo.add(lblWordsCount);
        panelInfo.add(lblLinesCount);
        panelInfo.add(lblPunctuationsCount);


        String variantInfo = "Задание 1 - Rezyapov D.N." + System.lineSeparator() +
                "Задание 2 - текстовый редактор, вариант 5" + System.lineSeparator() +
                "Задание 3 - цвет 100, расстояние 25 пт," + System.lineSeparator() +
                " тип сообщения PLAIN_MESSAGE" + System.lineSeparator() +
                "Задание 4 - кнопки редактирования текста" + System.lineSeparator() +
                "Задание 5 -число символов в тексте, число слов в тексте, число строк " +
                "в тексте, число знаков препинания в тексте;" + System.lineSeparator() +
                "Задание 6 - обработчики кнопок" + System.lineSeparator() +
                "Задание 7 - факториал суммы ASCII-кодов первых " +
                "букв фамилий участников группы + 77, тип сообщения QUESTION_MESSAGE";
        JTextArea variantInfoField = new JTextArea(variantInfo);
        variantInfoField.setLineWrap(true);
        variantInfoField.setWrapStyleWord(true);
        //JLabel variantInfoLabel1 = new JLabel();
        variantInfoField.setBounds(180, 10, 250, 300);
        panelInfo.add(variantInfoField);

        fileChooser = new JFileChooser();
        setVisible(true);
    }

    void connectToDb(ActionEvent event) {
        try {
            db.connect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void showDb(ActionEvent event) {
        DbTableForm tableForm = new DbTableForm(db, jobSeekerTableName);
    }

    void createTable(ActionEvent event) {
        CreateTableForm form = new CreateTableForm(db);
    }

    /**
     * обработчиик нажания на кнопку показать статистикку - показ формы статистики
     *
     * @param e событие
     */
    void showStatistic(ActionEvent e) {
        String text = area.getText();
        int soglasn = text.length() - text.replaceAll("[бвгджзйклмнпрстфхцчшщ]", "").length();
        int glasn = text.length() - text.replaceAll("[аоиеёэыуюя]", "").length();
        int filledFields = area.getText().length() > 0 ? 1 : 0;

        StatisticData sd = new StatisticData(soglasn, glasn, this.saveTime, filledFields, text);
        FormStatistic fs = new FormStatistic(sd, db);
    }

    BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    /**
     * Обработчик нажатия на кнопку закрыть
     * Закрытие приложения
     *
     * @param e событие
     */
    void close(ActionEvent e) {
        String surname = "Rezyapov";
        int firstCharacter = surname.getBytes()[0];
        BigInteger factor = factorial(firstCharacter + 77);
        JOptionPane.showMessageDialog(this, "Факториал = " + factor.toString(), "", JOptionPane.QUESTION_MESSAGE);
        TransferableText transferableText = new TransferableText("");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferableText, this);
        System.exit(0);
    }

    boolean colorChanged = false;

    /**
     * Обработчик нажатия на кнопку цвет
     * Изменение цвета фона каждой из кнопок на Color(20*i)
     *
     * @param e сыобтие
     */
    void changeColor(ActionEvent e) {
        Color color = this.colorChanged ? Color.white : new Color(20 * 5);
        for (int i = 0; i < 12; i++) {
            allButtons[i].setBackground(color);
        }

        int moveLength = colorChanged ? -5 * 5 : 5 * 5;
        GridBagLayout layout = (GridBagLayout) getContentPane().getLayout();
        GridBagConstraints panelTextAreaConstraints = layout.getConstraints(panelTextArea);
        panelTextAreaConstraints.insets.right += moveLength / 2;
        // panelTextAreaInsets
        layout.setConstraints(panelTextArea, panelTextAreaConstraints);

        GridBagConstraints panelInfoConstraints = layout.getConstraints(panelInfo);
        panelInfoConstraints.insets.left += moveLength / 2;
        panelInfoConstraints.insets.top += moveLength / 2;
        layout.setConstraints(panelInfo, panelInfoConstraints);


        GridBagConstraints panelButtonsConstraints = layout.getConstraints(panelButtons);
        panelButtonsConstraints.insets.bottom += moveLength / 2;
        panelButtonsConstraints.insets.left += moveLength / 2;
        layout.setConstraints(panelButtons, panelButtonsConstraints);

        for (int i = 0; i < 12; i++) {
            GridBagLayout buttonsLayout = (GridBagLayout) panelButtons.getLayout();
            GridBagConstraints buttonConstraints = buttonsLayout.getConstraints(allButtons[i]);
            if (colorChanged) {
                buttonConstraints.insets.top = 0;
                buttonConstraints.insets.bottom = 15;
            } else {
                buttonConstraints.insets.top = 15;
                buttonConstraints.insets.bottom = 0;
            }

            buttonsLayout.setConstraints(allButtons[i], buttonConstraints);
        }


        getContentPane().revalidate();
        getContentPane().repaint();
        revalidate();
        repaint();


        JOptionPane.showMessageDialog(this,
                "Сдвинули, покрасили", "",
                JOptionPane.PLAIN_MESSAGE);

        colorChanged = !colorChanged;
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
                this.saveTime = new Date(selected.lastModified());
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
        String[] saveTypes = {"резюме", "договор", "заявление"};
        int result = JOptionPane.showOptionDialog(
                this,
                "Что собираетесь сохранить?",
                "Выбор типа документа",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                saveTypes, saveTypes[0]);
        if (result == -1) {
            return;
        }
        int dialogResult = fileChooser.showSaveDialog(this);
        if (dialogResult != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selected = fileChooser.getSelectedFile();
        try {
            FileWriter fw = new FileWriter(selected);
            fw.write(area.getText());
            fw.close();
            this.saveTime = new Date();
        } catch (java.io.IOException ex) {
            //TODO
        }

        if (result == 0) {
            HashMap<String, Object> filledFields = new HashMap<String, Object>();
            filledFields.put(resumeFieldName, area.getText());
            CreateInstanceForm form = new CreateInstanceForm(db, jobSeekerTableName, filledFields);
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
        area.grabFocus();
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
            } else {
                JOptionPane.showMessageDialog(this, "слово не найдено");
            }
        }
        area.setSelectionColor(Color.BLUE);
        area.grabFocus();
    }


    void showBuffer(ActionEvent e) {

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

    /**
     * Обовление информации о тексте
     */
    void updateInfo() {
        String text = area.getText();
        int characters = text.length();
        int words = text.split("\\s").length;
        int lines = text.split(System.lineSeparator()).length;
        int puncts = text.length() - text.replaceAll("\\p{P}", "").length();
        lblCharactersCount.setText(Integer.toString(characters));
        lblWordsCount.setText(Integer.toString(words));
        lblLinesCount.setText(Integer.toString(lines));
        lblPunctuationsCount.setText(Integer.toString(puncts));
    }

    /**
     * Класс для обработки событий изменения текста в JPaneText
     */
    class TextChangeListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            updateInfo();
        }

        public void removeUpdate(DocumentEvent e) {
            updateInfo();
        }

        public void changedUpdate(DocumentEvent e) {
            updateInfo();
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
            setLayout(new GridBagLayout());
        }

    }


    static class Panel3 extends JPanel {
        Panel3() {
            setLayout(null);
            setBounds(380, 370, 315, 255);
            setBackground(Color.cyan);
        }
    }

}

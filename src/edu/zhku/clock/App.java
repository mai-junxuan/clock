package edu.zhku.clock;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * @author MJX
 * @date 2021/09/08
 */
public class App extends JDialog {
    public static void main(String[] args) {
        new App();
    }

    private final int width = 284;
    private final int height = 300;
    private int hour, minute, second;


    private final JLayeredPane centerPanel = new JLayeredPane();
    private final JPanel datePanel = new JPanel();
    private final JLabel timeLabel = new JLabel();
    /**
     * 表盘
     */
    private  Dial dial;
    /**
     * 指针
     */
    private Pointer pointer;

    public App() {
        init();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
            createDate();
            datePanel.repaint();
            pointer.nextSecond();
            centerPanel.repaint();
        }
    }
    private void init(){
        LocalTime time = LocalTime.now();
        hour = time.getHour() % 12;
        minute = time.getMinute();
        second = time.getSecond();

        dial = new Dial(120);
        pointer = new Pointer(70, 90, 100);
        pointer.setTime(hour, minute, second);

        dial.setBounds(0, 0, width, height);
        pointer.setBounds(0, 0, width, height);
        centerPanel.add(dial, 2);
        centerPanel.add(pointer, 3);
        setContentPane(centerPanel);

        datePanel.add(timeLabel);
        datePanel.setBounds(50,280,200,20);
        centerPanel.add(datePanel,1);

        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(false);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setVisible(false);
            }
        });
        //mainPanel.add(bottom,BorderLayout.SOUTH);
        createSystemTray();
    }
    private void createDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = LocalDateTime.now().format(formatter);

        timeLabel.setText("当前时间:"+format);
        timeLabel.repaint();
    }
    private void createSystemTray(){
        if (SystemTray.isSupported()){
            SystemTray systemTray = SystemTray.getSystemTray();
            PopupMenu menu = new PopupMenu();

            MenuItem exitMenu = new MenuItem("退出");
            exitMenu.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(1);
                }
            });
            menu.add(exitMenu);
            Image image = Toolkit.getDefaultToolkit().getImage("src/clock.png");
            TrayIcon trayIcon = new TrayIcon(image,"时钟",menu);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton()==MouseEvent.BUTTON1){
                        setVisible(!isVisible());
                    }
                }
            });

            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
}


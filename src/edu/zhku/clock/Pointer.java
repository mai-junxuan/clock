package edu.zhku.clock;

import javax.swing.*;
import java.awt.*;

/**
 * @author MJX
 * @date 2021/09/13
 */
// 指针
class Pointer extends JPanel {
    int hourLen, minLen, secLen;
    double hour = 0;   // 0 ~ 60
    double minute = 0; // 0 ~ 60
    int second = 0;    // 0 ~ 60
    final double radDelta = Math.toRadians(6); // 6°
    final Stroke pointerStroke = new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

    public Pointer(int hourLength, int minuteLength, int secondLength) {
        hourLen = hourLength;
        minLen = minuteLength;
        secLen = secondLength;
    }

    public void setTime(int hour, int minute, int second) {
        this.second = second;
        this.minute = minute + this.second / 60.0;
        this.hour = (hour + this.minute / 60.0) * 5.0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 清除上次显示

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        ); // 开启抗锯齿
        g2d.translate(142, 130); // 窗口中心 (?)
        g2d.setStroke(pointerStroke);

        // 时针
        g2d.setColor(Color.RED);
        g2d.drawLine(
                0,
                0,
                (int) (hourLen * Math.sin(radDelta * hour)),
                (int) (-hourLen * Math.cos(radDelta * hour))
        );

        // 分针
        g2d.setColor(Color.BLUE);
        g2d.drawLine(
                0,
                0,
                (int) (minLen * Math.sin(radDelta * minute)),
                (int) (-minLen * Math.cos(radDelta * minute))
        );

        // 秒针
        g2d.setColor(Color.GREEN);
        g2d.drawLine(
                0,
                0,
                (int) (secLen * Math.sin(radDelta * second)),
                (int) (-secLen * Math.cos(radDelta * second))
        );
    }

    public void nextSecond() {
        second += 1;
        if (second >= 60) {
            second = 0;
        }

        minute += 1.0 / 60.0;
        if (minute >= 60) {
            minute = 0;
        }

        hour += 1.0 / 60.0 / 60.0;
        if (hour >= 60) {
            hour = 0;
        }
    }

}

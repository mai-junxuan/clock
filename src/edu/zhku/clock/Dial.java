package edu.zhku.clock;

import javax.swing.*;
import java.awt.*;

/**
 * @author MJX
 * @date 2021/09/13
 */
class Dial extends JPanel {
    int r;
    private final double radDelta = Math.toRadians(30);
    private final Stroke boldStroke = new BasicStroke(2.2f);
    private final Stroke regularStroke = new BasicStroke(1f);

    public Dial(int radius) {
        r = radius;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        ); // 开启抗锯齿
        g2d.translate(142, 130); // 窗口中心 (?)

        // 外围圆形
        g2d.setStroke(boldStroke);
        g2d.drawOval(-r, -r, r * 2, r * 2);

        // 刻度线
        int len;
        for (int i = 0; i < 60; ++i) {
            // 长刻度线
            if (i % 5 == 0) {
                g2d.setStroke(boldStroke);
                len = 10;
                // 短刻度线
            } else {
                g2d.setStroke(regularStroke);
                len = 6;
            }
            g2d.drawLine(0, 2 - r, 0, len - r);
            g2d.rotate(Math.toRadians(6));
        }

        // 数字
        g2d.setFont(new Font(null, Font.BOLD, 14));
        for (int i = 1; i <= 12; ++i) {
            g2d.drawString(
                    String.valueOf(i),
                    (int) (0.84 * r * Math.sin(radDelta * i) - 4), // 带位置修正
                    (int) (0.84 * -r * Math.cos(radDelta * i) + 5)
            );
        }
    }
}

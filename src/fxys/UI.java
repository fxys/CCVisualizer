package fxys;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UI {
    private static final Font font = new Font("Century Gothic", Font.PLAIN, 28);
    private static final Color c = new Color(194, 194, 194, 255);

    public static JLabel label(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font.deriveFont((float) (font.getSize() * 0.6428571429)));
        lbl.setBounds(x, y, 500, 50);
        lbl.setForeground(c);
        return lbl;
    }

    public static JLabel button(String t, MouseAdapter ma, int x, int y, int w, int h, final Color highlight) {
        final Color[] bg = { new Color(84, 84, 84, 255) };
        final Color[] tx = { new Color(255, 255, 255, 255) };
        final JLabel btn = new JLabel() {
            private String text = t;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(bg[0]);
                g2d.fillRoundRect(0, 0, w, h, 12, 12);
                FontMetrics metrics = g.getFontMetrics(font);
                int x = (getWidth() - metrics.stringWidth(text)) / 2;
                int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
                g2d.setFont(font);
                g2d.setColor(tx[0]);
                g2d.drawString(text, x, y);
            }

            public void setText(String t) {
                text = t;
            }

            public String getText() {
                return text;
            }
        };
        btn.setBounds(x, y, w, h);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(ma);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                bg[0] = highlight;
                tx[0] = new Color(194, 194, 194, 255);
                btn.repaint();
            }

            public void mouseExited(MouseEvent e) {
                bg[0] = new Color(84, 84, 84, 255);
                tx[0] = new Color(255, 255, 255, 255);
                btn.repaint();
            }
        });
        return btn;
    }

    public static JSlider slider(int x, int y, int min, int max, int start, int mnt, int mjt) {
        JSlider s = new JSlider(JSlider.HORIZONTAL, min, max, start);
        s.setForeground(c);
        s.setMinorTickSpacing(mnt);
        s.setMajorTickSpacing(mjt);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.setOpaque(false);
        s.setBounds(x, y, 250, 50);
        s.setUI(new BasicSliderUI(s) {
            protected Dimension getThumbSize() {
                return new Dimension(15,25);
            }

            public void paintTicks(Graphics g) {
                Rectangle t = tickRect;
                g.setColor(c);
                g.translate(0, t.y);
                int value;
                int xPos;
                int min = slider.getMinimum();
                int max = slider.getMaximum();
                for(value = min; value <= max; value += slider.getMinorTickSpacing()) {
                    xPos = xPositionForValue(value);
                    g.drawLine(xPos, 0, xPos, t.height / 2 - 1);
                }
                for(value = min; value <= max; value += slider.getMajorTickSpacing()) {
                    xPos = xPositionForValue(value);
                    g.drawLine(xPos, 0, xPos, t.height - 2);
                }
                g.translate(0, -t.y);
            }

            public void paintFocus(Graphics g) { }

            public void paintTrack(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                Rectangle t = trackRect;
                g2d.setColor(new Color(84, 84, 84, 255));
                g2d.fillRoundRect(t.x, t.y + 7, t.width, 10, 10, 10);
            }

            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                Rectangle t = thumbRect;
                g2d.setColor(c);
                g2d.fillRoundRect(t.x, t.y, 10, 25, 10, 10);
            }
        });
        return s;
    }
}

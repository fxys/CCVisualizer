package fxys;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Visualizer extends JLabel {
    public static final List<Shape> s = new ArrayList<>();
    private static final List<Double> t = new ArrayList<>();
    public static double gt = 0;

    public static double getAverageRenderTime() {
        OptionalDouble av = t.stream().mapToDouble(a -> a).average();
        return av.isPresent() ? av.getAsDouble() : '\0';
    }

    @Override
    protected void paintComponent(Graphics g) {
        long nt = System.nanoTime();
        if (!Window.g) return;

        Graphics2D g2d = (Graphics2D) g;
        if(Window.antialiasing)
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(0.3f));
        for (Shape s : s)
            g2d.draw(s);

        s.clear();
        gt = (System.nanoTime() - nt) / 1000000.0;
        t.add(gt);
    }
}

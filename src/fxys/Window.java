package fxys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Window implements Runnable {
    private static final JFrame frame = new JFrame();
    public static final Visualizer v = new Visualizer();
    public static final Settings s = new Settings();
    public static final JLabel l1 = UI.label(null, 1600, 1000);
    public static final JLabel l2 = UI.label(null, 1600, 975);
    private int m;
    private float a;
    private double cx = 960;
    private double cy = 1000;
    public static boolean g = false;
    public static boolean antialiasing = false;
    public static double gt = 0;

    public Window(int max) {
        this.m = max;
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Main.exit(1);
            }
        });
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setContentPane(v);
        frame.setUndecorated(true);
        frame.setBackground(new Color(25, 25, 25, 255));
        frame.setExtendedState(6);
        frame.add(UI.button("Exit", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Main.exit(0);
            }
        }, 1800, 30, 90, 44, new Color(140, 40, 40, 255)));
        frame.add(UI.button("Settings", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                s.toggleVisibility();
            }
        }, 1540, 30, 250, 44, new Color(120, 120, 120, 255)));
        frame.add(l1);
        frame.add(l2);
        frame.setVisible(true);
    }

    private Shape line(double a) {
        double tr = s.getTravel() + 1.0;
        Line2D l = new Line2D.Double(cx, cy, cx, cy - tr);
        double x1 = l.getX1();
        double y1 = l.getY1();
        double x2 = l.getX2();
        double y2 = l.getY2();
        double x3 = Math.cos(a) * (x2 - x1) - Math.sin(a) * (y2 - y1) + x1;
        double y3 = Math.sin(a) * (x2 - x1) + Math.cos(a) * (y2 - y1) + y1;
        Line2D l1 = new Line2D.Double(x1, y1, x3, y3);
        this.cx = l1.getX2();
        this.cy = l1.getY2();
        return l1;
    }

    private void rXY() {
        this.cx = 960;
        this.cy = 1000;
        this.a = (float) (-Math.PI * s.getAngle());
    }

    public void reverse(List<Integer> l) {
        ListIterator<Integer> f = l.listIterator();
        ListIterator<Integer> r = l.listIterator(l.size());
        int mid = l.size() >> 1;
        for (int i = 0; i < mid; i++) {
            int j = f.next();
            f.set(r.previous());
            r.set(j);
        }
    }

    @Override
    public void run() {
        g = false;
        long nt = System.nanoTime();
        this.m = s.getMax();
        List<Shape> l = Visualizer.s;
        for (int i = 4; i <= m; i++) {
            List<Integer> c = new ArrayList<>();
            int n;
            for(n = i; n != 1; n = (n % 2 == 0 ? n / 2 : 3 * n + 1)) {
                c.add(n);
            }
            c.add(n);
            reverse(c);
            for(int j : c) {
                if (j == 1)
                    rXY();
                this.a = this.a >= 360 ? this.a - 360 : this.a <= -360 ? this.a + 360 : this.a;
                this.a = j % 2 == 0 ? this.a + s.getC1() : this.a + s.getC2();
                l.add(line(this.a * 0.017453292519943295));
            }
        }
        gt = (System.nanoTime() - nt) / 1000000.0;
        g = true;
    }
}

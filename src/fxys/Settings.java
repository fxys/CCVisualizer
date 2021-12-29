package fxys;

import javax.swing.*;
import java.awt.*;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Settings {
    private static final JFrame frame = new JFrame("Settings");
    private static final JSlider angleSdr = UI.slider(5, 100, -30, 30, 10, 2, 10);
    private static final JSlider travelSdr = UI.slider(5, 200, 0, 20, 7, 2, 4);
    private static final JSlider c1Sdr = UI.slider(5, 300, -90, 10, -8, 4, 20);
    private static final JSlider c2Sdr = UI.slider(5, 400, -10, 90, 16, 4, 20);
    private static final JSlider lineAmount = UI.slider(4, 500, 0, 20000, 10000, 1000, 5000);
    private static final JLabel antialiasBtn = UI.button("Off", null, 186, 10, 60, 44, new Color(120, 120, 120, 255));

    public Settings() {
        frame.add(angleSdr);
        frame.add(travelSdr);
        frame.add(c1Sdr);
        frame.add(c2Sdr);
        frame.add(lineAmount);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(270,660));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        frame.setType(Window.Type.UTILITY);
        frame.setAlwaysOnTop(true);
        frame.getContentPane().setBackground(new Color(40, 40, 40));
        antialiasBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                fxys.Window.antialiasing ^= true;
                antialiasBtn.setText(Objects.equals(antialiasBtn.getText(), "Off") ? "On" : "Off");
            }
        });
        frame.add(antialiasBtn);
        frame.add(UI.button("Render", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Main.w.run();
                Visualizer v = fxys.Window.v;
                v.repaint(0, 0, 0, v.getWidth(), v.getHeight());
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if(Visualizer.gt > 1)
                                    fxys.Window.l2.setText(String.format("Generation time: %.4fms %n", fxys.Window.gt));
                                    fxys.Window.l1.setText(String.format("Rendering time: %.4fms %n", Visualizer.gt));
                            }
                        },
                        800
                );
            }
        }, 8, 570, 240, 44, new Color(120, 120, 120, 255)));
        frame.add(UI.label("<html><body>Antialiasing: <br>-affects speed</body></html>", 10, 7));
        frame.add(UI.label("Start Angle:", 10, 60));
        frame.add(UI.label("Line Travel:", 10, 160));
        frame.add(UI.label("Angle Change when Odd:", 10, 260));
        frame.add(UI.label("Angle Change when Even:", 10, 360));
        frame.add(UI.label("Amount of lines:", 10, 460));
    }

    public void toggleVisibility() {
        frame.setVisible(!frame.isVisible());
    }

    public double getAngle() {
        return angleSdr.getValue();
    }

    public int getTravel() {
        return travelSdr.getValue();
    }

    public int getMax() {
        return lineAmount.getValue();
    }

    public int getC1() {
        return c1Sdr.getValue();
    }

    public int getC2() {
        return c2Sdr.getValue();
    }
}

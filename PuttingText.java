package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PuttingText extends JFrame {
    private JTextField text = new JTextField();
    private JSlider slider_size = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
    private JSlider slider_thick = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
    private JSlider JColor = new JSlider(JSlider.HORIZONTAL, 1, 8, 1);
    private JButton btn1 = new JButton("Set");
    private JButton btn2 = new JButton("undo");
    private JLabel blank1 = new JLabel("");
    private JLabel blank2 = new JLabel("");
    private JLabel label_text = new JLabel("text");
    private JLabel label_size = new JLabel("size");
    private JLabel size_value = new JLabel(Double.toString(size));
    private JLabel label_thick = new JLabel("thickness");
    private JLabel thick_value = new JLabel(Integer.toString(thick));
    private JLabel LColor = new JLabel("Black");
    private static String str;
    private static String name = "tmp";
    private static double size;
    private static int thick;
    private static double B;
    private static double G;
    private static double R;
    private static double X;
    private static double Y;

    public PuttingText() {
        setTitle("Putting Text");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new GridLayout(5, 3));

        c.add(label_text);
        label_text.setHorizontalAlignment(JLabel.CENTER);
        c.add(text);
        c.add(blank1);

        c.add(label_size);
        label_size.setHorizontalAlignment(JLabel.CENTER);
        slider_size.setMajorTickSpacing(1);
		slider_size.setPaintTicks(true);
        slider_size.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider s = (JSlider)e.getSource();
				size = s.getValue();
                size_value.setText(Double.toString(s.getValue()));
			}
		});
        c.add(slider_size);
        c.add(size_value);

        c.add(label_thick);
        label_thick.setHorizontalAlignment(JLabel.CENTER);
        slider_thick.setMajorTickSpacing(1);
		slider_thick.setPaintTicks(true);
        slider_thick.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider s = (JSlider)e.getSource();
				thick = s.getValue();
                thick_value.setText(Integer.toString(s.getValue()));
			}
		});
        c.add(slider_thick);
        c.add(thick_value);

        JLabel textcolor = new JLabel("Color");
        c.add(textcolor);
        textcolor.setHorizontalAlignment(JLabel.CENTER);
        JColor.setMajorTickSpacing(1);
        JColor.setPaintTicks(true);
        LColor.setOpaque(true);
        LColor.setBackground(Color.WHITE);
        JColor.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider)e.getSource();
                if (s.getValue() == 1) {
                    B = 0;
                    G = 0;
                    R = 0;
                    LColor.setText("Black");
                    LColor.setForeground(Color.BLACK);
                    LColor.setBackground(Color.WHITE);
                }
                else if (s.getValue() == 2) {
                    B = 255;
                    G = 0;
                    R = 0;
                    LColor.setText("Blue");
                    LColor.setForeground(Color.BLUE);
                    LColor.setBackground(Color.WHITE);
                }
                else if (s.getValue() == 3) {
                    B = 0;
                    G = 255;
                    R = 0;
                    LColor.setText("Green");
                    LColor.setForeground(Color.GREEN);
                    LColor.setBackground(Color.BLACK);
                }
                else if (s.getValue() == 4) {
                    B = 0;
                    G = 0;
                    R = 255;
                    LColor.setText("Red");
                    LColor.setForeground(Color.RED);
                    LColor.setBackground(Color.BLACK);
                }
                else if (s.getValue() == 5) {
                    B = 255;
                    G = 255;
                    R = 0;
                    LColor.setText("Aqua");
                    LColor.setForeground(Color.CYAN);
                    LColor.setBackground(Color.BLACK);
                }
                else if (s.getValue() == 6) {
                    B = 255;
                    G = 0;
                    R = 255;
                    LColor.setText("Magenta");
                    LColor.setForeground(Color.MAGENTA);
                    LColor.setBackground(Color.BLACK);
                }
                else if (s.getValue() == 7) {
                    B = 0;
                    G = 255;
                    R = 255;
                    LColor.setText("Yellow");
                    LColor.setForeground(Color.YELLOW);
                    LColor.setBackground(Color.BLACK);
                }
                else if (s.getValue() == 8) {
                    B = 255;
                    G = 255;
                    R = 255;
                    LColor.setText("White");
                    LColor.setForeground(Color.WHITE);
                    LColor.setBackground(Color.BLACK);
                }
            }
        });
        c.add(JColor);
        c.add(LColor);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                str = text.getText();
                JOptionPane.showMessageDialog(null, "Click the bottom left coordinate of the Text.", "Putting Text", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        c.add(btn1);
        c.add(blank2);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        c.add(btn2);

        setSize(500, 400);
        setVisible(true);
    }
    public void puttext(String path, double x, double y) {
        X = x; Y = y;
        str = text.getText();
        try {
            System.load("C:\\Users\\gyumc\\Desktop\\opencv\\build\\java\\x64\\opencv_java460.dll");
        
            Mat img = Imgcodecs.imread(path);
            Mat result_img = new Mat();
            img.copyTo(result_img);
        
            Imgproc.putText(result_img, str, new Point(X, Y), 2, size, new Scalar(B, G, R), thick, 16);
            Imgcodecs.imwrite(name + ".jpg", result_img);
            Main.img_stack.push(result_img);
            JOptionPane.showMessageDialog(null, "Text putting performed Successfully.", "Putting Text", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void undo() {
        Main.img_stack.pop();

        Mat img = Main.img_stack.peek();
        Imgcodecs.imwrite(name + ".jpg", img);

        MenuBar.temp_imgPath = name + ".jpg";
        Main.mb.setImage(MenuBar.temp_imgPath);
        MenuBar.imgPath = MenuBar.temp_imgPath;

        JOptionPane.showMessageDialog(null, "Undoing performed Successfully.", "Putting Text", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
    public String getName() {
        return name;
    }
}

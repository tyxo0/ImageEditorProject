package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class PuttingText extends JFrame {
    JTextField inputText = new JTextField();
    JButton setBtn = new JButton();
    JButton deleteBtn = new JButton();
    JButton undoBtn = new JButton();
    JLabel LColor = new JLabel("Black");
    private static String str;
    private static double size;
    private static int thick;
    private static double B;
    private static double G;
    private static double R;
    private static double X;
    private static double Y;
    Font font = new Font("맑은 고딕", Font.BOLD, 16);

    public PuttingText() {
        this.setTitle("글자 삽입");
        this.setSize(500, 400);
        this.setUndecorated(true);
        this.setResizable(false);
        getRootPane().setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));  //테두리 설정
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);

        // 프레임 드래그 이동 설정
        Resizing.FrameDragListener frameDragListener = new Resizing.FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);
        setLocationRelativeTo(null);

        // 배경 이미지 설정
        ImageIcon background_img = new ImageIcon("img/background/bg03.jpg");
        Image bg_img = background_img.getImage();
        Image temp_img = bg_img.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
        background_img = new ImageIcon(temp_img);
        JLabel back = new JLabel(background_img);
        back.setLayout(null);
        back.setBounds(0, 0, 500, 400);

        // 글자입력 텍스트 레이블
        JLabel label_Text = new JLabel("글자 입력");
        label_Text.setFont(font);
        label_Text.setForeground(Color.WHITE);
        label_Text.setSize(80, 40);
        label_Text.setLocation(67, 40);
        this.add(label_Text);

        // 글자입력 텍스트필드
        inputText.setSize(220, 50);
        inputText.setLocation(200, 35);
        this.add(inputText);

        // 글자크기 텍스트 레이블
        JLabel label_textSize = new JLabel("글자 크기");
        label_textSize.setFont(font);
        label_textSize.setForeground(Color.WHITE);
        label_textSize.setSize(80, 40);
        label_textSize.setLocation(67, 130);
        this.add(label_textSize);

        // 글자크기 조절 슬라이더
        JSlider slider_textSize = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        slider_textSize.setMajorTickSpacing(1);
		slider_textSize.setPaintTicks(true);
        slider_textSize.setOpaque(false);
        slider_textSize.setSize(240, 40);
        slider_textSize.setLocation(180, 135);
        slider_textSize.setForeground(Color.WHITE);
        JLabel size_value = new JLabel(Double.toString(size));  // 글자크기 슬라이더 값 레이블
        slider_textSize.addChangeListener(e -> {
            JSlider s = (JSlider)e.getSource();
            size = s.getValue();
            size_value.setText(Double.toString(s.getValue()));
        });
        size_value.setFont(font);
        size_value.setForeground(Color.WHITE);
        size_value.setSize(40, 40);
        size_value.setLocation(435, 130);
        this.add(slider_textSize);
        this.add(size_value);

        // 글자굵기 텍스트 레이블
        JLabel label_Thick = new JLabel("글자 굵기");
        label_Thick.setFont(font);
        label_Thick.setForeground(Color.WHITE);
        label_Thick.setSize(80, 40);
        label_Thick.setLocation(67, 195);
        this.add(label_Thick);

        // 글자굵기 조절 슬라이더
        JSlider slider_textThick = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        slider_textThick.setMajorTickSpacing(1);
		slider_textThick.setPaintTicks(true);
        slider_textThick.setOpaque(false);
        slider_textThick.setSize(240, 40);
        slider_textThick.setLocation(180, 200);
        slider_textThick.setForeground(Color.WHITE);
        JLabel thick_value = new JLabel(Double.toString(thick));  // 글자굵기 슬라이더 값 레이블
        slider_textThick.addChangeListener(e -> {
            JSlider s = (JSlider)e.getSource();
            thick = s.getValue();
            thick_value.setText(Double.toString(s.getValue()));
        });
        thick_value.setFont(font);
        thick_value.setForeground(Color.WHITE);
        thick_value.setSize(40, 40);
        thick_value.setLocation(435, 195);
        this.add(slider_textThick);
        this.add(thick_value);

        // 글자색상 텍스트 레이블
        JLabel label_textColor = new JLabel("글자 색상");
        label_textColor.setFont(font);
        label_textColor.setForeground(Color.WHITE);
        label_textColor.setSize(80, 40);
        label_textColor.setLocation(67, 260);
        this.add(label_textColor);

        JSlider slider_textColor = new JSlider(JSlider.HORIZONTAL, 1, 8, 1);
        slider_textColor.setMajorTickSpacing(1);
        slider_textColor.setPaintTicks(true);
        slider_textColor.setOpaque(false);
        slider_textColor.setSize(210, 40);
        slider_textColor.setLocation(180, 265);
        slider_textColor.setForeground(Color.WHITE);

        slider_textColor.addChangeListener(e -> {
            JSlider s = (JSlider)e.getSource();
            if (s.getValue() == 1) {
                B = 0;
                G = 0;
                R = 0;
                LColor.setText("Black");
                LColor.setForeground(Color.WHITE);
            }
            else if (s.getValue() == 2) {
                B = 255;
                G = 0;
                R = 0;
                LColor.setText("Blue");
                LColor.setForeground(Color.BLUE);
            }
            else if (s.getValue() == 3) {
                B = 0;
                G = 255;
                R = 0;
                LColor.setText("Green");
                LColor.setForeground(Color.GREEN);
            }
            else if (s.getValue() == 4) {
                B = 0;
                G = 0;
                R = 255;
                LColor.setText("Red");
                LColor.setForeground(Color.RED);
            }
            else if (s.getValue() == 5) {
                B = 255;
                G = 255;
                R = 0;
                LColor.setText("Aqua");
                LColor.setForeground(Color.CYAN);
            }
            else if (s.getValue() == 6) {
                B = 255;
                G = 0;
                R = 255;
                LColor.setText("Magenta");
                LColor.setForeground(Color.MAGENTA);
            }
            else if (s.getValue() == 7) {
                B = 0;
                G = 255;
                R = 255;
                LColor.setText("Yellow");
                LColor.setForeground(Color.YELLOW);
            }
            else if (s.getValue() == 8) {
                B = 255;
                G = 255;
                R = 255;
                LColor.setText("White");
                LColor.setForeground(Color.WHITE);
            }
        });
        LColor.setFont(font);
        LColor.setForeground(Color.WHITE);
        LColor.setSize(80, 40);
        LColor.setLocation(405, 260);
        this.add(slider_textColor);
        this.add(LColor);

        // 붙이기 버튼
        setBtn.setIcon(new ImageIcon("img/icon/pasteIcon.png"));
        setBtn.setSize(90, 35);
        setBtn.setLocation(90, 340);
        setBtn.setFont(font);
        setBtn.setBackground(Color.LIGHT_GRAY);
        setBtn.addActionListener(e -> {
            JLabel click_Info = new JLabel("위치를 클릭하새요");
            click_Info.setFont(font);
            JOptionPane.showMessageDialog(null, click_Info, "글자 삽입", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
        this.add(setBtn);

        // 되돌리기 버튼
        undoBtn.setIcon(new ImageIcon("img/icon/undoIcon.png"));
        undoBtn.setSize(90, 35);
        undoBtn.setLocation(210, 340);
        undoBtn.setFont(font);
        undoBtn.setBackground(Color.LIGHT_GRAY);
        undoBtn.addActionListener(e -> undo());
        this.add(undoBtn);

        // 취소 버튼
        deleteBtn.setIcon(new ImageIcon("img/icon/deleteIcon.png"));
        deleteBtn.setSize(90, 35);
        deleteBtn.setLocation(330, 340);
        deleteBtn.setFont(font);
        deleteBtn.setBackground(Color.LIGHT_GRAY);
        this.add(deleteBtn);

        this.add(back);
        this.setVisible(true);
    }
    public void puttext(String path, double x, double y) {
        X = x; Y = y;
        str = inputText.getText();
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
            Mat img = Imgcodecs.imread(path);
            Mat result_img = new Mat();
            img.copyTo(result_img);
        
            Imgproc.putText(result_img, str, new Point(X, Y), 2, size, new Scalar(B, G, R), thick, 16);
            Imgcodecs.imwrite(MenuBar.temp_imgPath, result_img);
            Main.img_stack.push(result_img);
            dispose();
        
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void undo() {
        if (Main.img_stack.size() > 1) {
            Main.img_stack.pop();

            Mat img = Main.img_stack.peek();
            Imgcodecs.imwrite(MenuBar.temp_imgPath, img);
            MenuBar.setImage(MenuBar.temp_imgPath);
            JLabel undo_Info = new JLabel("되돌리기 완료");
            undo_Info.setFont(font);
            JOptionPane.showMessageDialog(null, undo_Info, "글자 삽입", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        else {
            JLabel undo_Info2 = new JLabel(" 마지막입니다");
            undo_Info2.setFont(font);
            JOptionPane.showMessageDialog(null, undo_Info2, "글자 삽입", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public String getName() {
        String name = "tmp";
        return name;
    }
}

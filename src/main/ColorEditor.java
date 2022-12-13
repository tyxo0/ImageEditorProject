package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import javax.swing.border.MatteBorder;

public class ColorEditor extends JFrame implements MouseListener {
    private int angle;
    String before_Color = "Red";
    Mat raw_img;  //백업된 Mat이미지
    Mat change_img = new Mat();  //수정될 Mat이미지
    Mat imgHSV = new Mat();
    Mat result_img = new Mat();
    Mat out_img = new Mat();
    HashMap<String, Color_Range> color_Map = new HashMap<>();
    Font font = new Font("맑은 고딕", Font.BOLD, 16);

    public void change_Color(String b_color, int delta_val) { // 변경할 색상,0~180 //스크롤로 색상 조절
        //색상: color_Map.mid를 중앙값으로 놓고 스크롤로 조절
        //밝기: 0부터 최대값 255
        //채도: 0부터 255
        try {
            Imgproc.cvtColor(change_img, imgHSV, Imgproc.COLOR_BGR2HSV); //HSV로 이미지 변환
            imgHSV.copyTo(result_img);

            int r = change_img.rows();
            int c = change_img.cols();

            //**** 변경 값 ****//
            int min_Val = color_Map.get(b_color).min;
            int max_Val = color_Map.get(b_color).max;

            if (b_color.equals("Red")) {
                for (int i = 0; i < r; i++) {
                    for (int j = 0; j < c; j++) {
                        double[] data = imgHSV.get(i, j); //Stores element in an array
                        if (data[0] >= min_Val || data[0] < max_Val && (data[1] != 0)) {
                            var color_val = data[0] + delta_val;
                            if (color_val >= 180) data[0] = color_val - 180;
                            else data[0] = color_val;
                        }
                        result_img.put(i, j, data); //Puts element back into matrix
                    }
                }
            } else {
                for (int i = 0; i < r; i++) {
                    for (int j = 0; j < c; j++) {
                        double[] data = imgHSV.get(i, j); //Stores element in an array
                        if (data[0] >= min_Val && data[0] < max_Val && (data[1] != 0)) {
                            var color_val = data[0] + delta_val;
                            if (color_val >= 180) data[0] = color_val - 180;
                            else data[0] = color_val;
                        }
                        result_img.put(i, j, data); //Puts element back into matrix
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //(사진에서 변경될 색상, 변경 후 색상, 사진 경로)
    public ColorEditor(String file_addr) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // 프레임 설정
        this.setSize(600, 350);
        this.setUndecorated(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        getRootPane().setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));  //테두리 설정
        this.setLayout(null);

        // 프레임 드래그 이동 설정
        Resizing.FrameDragListener frameDragListener = new Resizing.FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);
        setLocationRelativeTo(null);

        // 배경 이미지 설정
        ImageIcon background_img = new ImageIcon("img/background/bg03.jpg");
        Image bg_img = background_img.getImage();
        Image temp_img = bg_img.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        background_img = new ImageIcon(temp_img);
        JLabel back = new JLabel(background_img);
        back.setLayout(null);
        back.setBounds(0, 0, 600, 400);

        // 확인버튼
        JButton setBtn = new JButton(" 확인");
        setBtn.setSize(130, 40);
        setBtn.setLocation(140, 270);
        setBtn.setFont(font);
        setBtn.setBackground(Color.LIGHT_GRAY);
        setBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(setBtn);

        // 되돌리기 버튼
        JButton undoBtn = new JButton(" 되돌리기");
        undoBtn.setSize(150, 40);
        undoBtn.setLocation(300, 270);
        undoBtn.setIcon(new ImageIcon("img/icon/undoIcon.png"));
        undoBtn.setFont(font);
        undoBtn.setBackground(Color.LIGHT_GRAY);
        undoBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(undoBtn);

        // 색상 레이블 설정
        JLabel[] colorLabel = new JLabel[9];
        JRadioButton[] colorRBtn = new JRadioButton[9];
        for(int i=0; i<colorRBtn.length; i++){
            colorLabel[i] = new JLabel(new ImageIcon("img/icon/imagecolor/colorIcon" + i + ".png"));
            colorLabel[i].setSize(64, 64);
            colorLabel[i].setLocation(20+(i*60), 10);
            this.add(colorLabel[i]);
        }

        // 색상 선택 라디오버튼 설정
        ButtonGroup RbtnGroup = new ButtonGroup();
        for(int i=0; i<colorRBtn.length; i++){
            colorRBtn[i] = new JRadioButton();
            RbtnGroup.add(colorRBtn[i]);
            colorRBtn[i].setSize(50, 50);
            colorRBtn[i].setLocation(42+(i*60), 58);
            colorRBtn[i].setOpaque(false);
            colorRBtn[i].setForeground(Color.WHITE);
            this.add(colorRBtn[i]);
        }
        colorRBtn[0].setSelected(true);

        // 슬라이더 설정
        JSlider Color_slider = new JSlider();
        Color_slider.addMouseListener(this);
        Color_slider.setMinimum(0);
        Color_slider.setMaximum(180);
        Color_slider.setValue(0);
        Color_slider.setOrientation(JSlider.HORIZONTAL);
        Color_slider.setMajorTickSpacing(20);
        Color_slider.setPaintTicks(true);
        Color_slider.setSize(450, 50);
        Color_slider.setLocation(110, 140);
        Color_slider.setForeground(Color.WHITE);
        Color_slider.setOpaque(false);
        this.add(Color_slider);

        // 슬라이더 값 레이블
        JLabel angle_Label = new JLabel(Integer.toString(angle));
        angle_Label.setText("0");
        angle_Label.setFont(font);
        angle_Label.setForeground(Color.WHITE);
        angle_Label.setHorizontalAlignment(JLabel.CENTER);
        angle_Label.setSize(50, 50);
        angle_Label.setLocation(310, 170);
        this.add(angle_Label);

        // 텍스트용 레이블
        JLabel text_Label = new JLabel("색상 변경");
        text_Label.setSize(70, 70);
        text_Label.setLocation(25, 125);
        text_Label.setFont(font);
        text_Label.setForeground(Color.WHITE);
        this.add(text_Label);

        // 경로에 있는 이미지 백업
        raw_img = Imgcodecs.imread(file_addr);
        raw_img.copyTo(change_img);

        // 색상맵
        color_Map.put("Red", new Color_Range(170, 10)); // 빨간색: 170~10
        color_Map.put("Orange", new Color_Range(10, 20));// 주황색: 10~20
        color_Map.put("Yellow", new Color_Range(20, 40));// 노란색: 20~40
        color_Map.put("Green", new Color_Range(40, 80));// 초록색: 40~ 80 // 30~83
        color_Map.put("Cyan", new Color_Range(80, 110));// 하늘색: 80 110 // 70~ 110
        color_Map.put("Blue", new Color_Range(110, 130));// 파란색: 110 130
        color_Map.put("Purple", new Color_Range(130, 150));// 보라색: 130 150
        color_Map.put("Pink", new Color_Range(150, 170));// 핑크색: 150 170
        color_Map.put("All", new Color_Range(0, 180));

        Color_slider.addChangeListener(re -> {
            JSlider s = (JSlider)re.getSource();
            angle = s.getValue();
            angle_Label.setText(Integer.toString(s.getValue()));
        });

        setBtn.addActionListener(e -> {
            this.dispose();
        });

        undoBtn.addActionListener(e -> {
            Imgcodecs.imwrite(MenuBar.temp_imgPath, raw_img);
            MenuBar.setImage(MenuBar.temp_imgPath);
        });

        colorRBtn[0].addActionListener(e -> {
            before_Color = "Red";
        });
        colorRBtn[1].addActionListener(e -> {
            before_Color = "Orange";
        });
        colorRBtn[2].addActionListener(e -> {
            before_Color = "Yellow";
        });
        colorRBtn[3].addActionListener(e -> {
            before_Color = "Green";
        });
        colorRBtn[4].addActionListener(e -> {
            before_Color = "Cyan";
        });
        colorRBtn[5].addActionListener(e -> {
            before_Color = "Blue";
        });
        colorRBtn[6].addActionListener(e -> {
            before_Color = "Purple";
        });
        colorRBtn[7].addActionListener(e -> {
            before_Color = "Pink";
        });
        colorRBtn[8].addActionListener(e -> {
            before_Color = "All";
        });

        add(back);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JSlider release = (JSlider) e.getSource();
        change_Color(before_Color, release.getValue());
        Imgproc.cvtColor(result_img, out_img, Imgproc.COLOR_HSV2BGR);
        Imgcodecs.imwrite(MenuBar.temp_imgPath, out_img);
        MenuBar.setImage(MenuBar.temp_imgPath);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

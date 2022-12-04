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

public class ColorEditor extends JFrame implements MouseListener {
    private int sliderValue;
    final Mat raw_img;  //백업된 Mat이미지
    final Mat change_img = new Mat();  //수정될 Mat이미지
    final Mat imgHSV = new Mat();
    final Mat result_img = new Mat();
    final Mat out_img = new Mat();
    final HashMap<String, Color_Range> color_Map = new HashMap<>();
    String setColor = "";
    JPanel panel = new JPanel();
    JPanel colorPanel = new JPanel();
    JButton undoBtn = new JButton( " 뒤로가기");
    JButton setBtn = new JButton(" 확인");
    JButton deleteBtn = new JButton(" 취소");
    Sliders colorSlider = new Sliders(JSlider.HORIZONTAL, 0, 180);
    ButtonGroup btnGroup = new ButtonGroup();
    JRadioButton[] colorRBtn = new JRadioButton[9];

    public void changeColor(Mat raw_img, Mat imgHSV, Mat result_img, HashMap<String, Color_Range> color_Map, int value) {
        //색상: color_Map.mid를 중앙값으로 놓고 스크롤로 조절
        //밝기: 0을 부터 최대값 255
        //채도: 0부터 255
        try {
            Imgproc.cvtColor(raw_img, imgHSV, Imgproc.COLOR_BGR2HSV); //HSV로 이미지 변환
            imgHSV.copyTo(result_img);

            int r = raw_img.rows();
            int c = raw_img.cols();

            //**** 변경 값 ****//
            int min_Val = color_Map.get(setColor).min;
            int max_Val = color_Map.get(setColor).max;
            // int mid_Val =color_Map.get(color_Key).mid;

            if (setColor.equals("Red")) {
                for (int i = 0; i < r; i++) {
                    for (int j = 0; j < c; j++) {
                        double[] data = imgHSV.get(i, j); //Stores element in an array
                        if (data[0] >= min_Val || data[0] < max_Val && (data[1] != 0)) {
                            var color_val = data[0] + value;
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
                            var color_val = data[0] + value;
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

    public ColorEditor(String color, String file_addr) { //(사진에서 변경될 색상, 변경 후 색상, 사진 경로)
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        setColor = color;

        // 프레임 설정
        setSize(720, 480);
        setBackground(new Color(50, 50, 50));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        colorPanel.setLayout(new GridLayout(1, 9));
        colorPanel.setBackground(new Color(50, 50, 50));
        panel.setLayout(new GridLayout(2, 2));
        panel.setBackground(new Color(50, 50, 50));

        setBtn.setSize(24, 24);
        setBtn.setIcon(new ImageIcon("img/icon/setIcon.png"));
        setBtn.addMouseListener(this);
        undoBtn.setSize(24, 24);
        undoBtn.setIcon(new ImageIcon("img/icon/undoIcon.png"));
        undoBtn.addMouseListener(this);
        deleteBtn.setSize(24, 24);
        deleteBtn.setIcon(new ImageIcon("img/icon/deleteIcon.png"));
        deleteBtn.addMouseListener(this);

        // 라디오버튼 설정
        for(int i=0; i<colorRBtn.length; i++){
            colorRBtn[i] = new JRadioButton(new ImageIcon("img/icon/color/colorIcon" + i + ".png"));
            colorRBtn[i].addMouseListener(this);
            btnGroup.add(colorRBtn[i]);
            colorPanel.add(colorRBtn[i]);
        }
        add(colorPanel, BorderLayout.NORTH);

        // panel에 컴포넌트 붙이기
        panel.add(colorSlider);
        panel.add(undoBtn);
        panel.add(setBtn);
        panel.add(deleteBtn);
        add(panel, BorderLayout.SOUTH);

        raw_img = Imgcodecs.imread(file_addr);
        raw_img.copyTo(change_img);
//        raw_img.copyTo(result_img);

        // hue(색상): 0~180, saturation(채도), value(밝기):0~255
        // 초록색 계열 hue: 30~83
        color_Map.put("Red", new Color_Range(170, 10)); // 빨간색: 170~10
        color_Map.put("Orange", new Color_Range(10, 20));// 주황색: 10~20
        color_Map.put("Yellow", new Color_Range(20, 40));// 노란색: 20~40
        color_Map.put("Green", new Color_Range(40, 80));// 초록색: 40~ 80 // 30~83
        color_Map.put("Cyan", new Color_Range(80, 110));// 하늘색: 80 110 // 70~ 110
        color_Map.put("Blue", new Color_Range(110, 130));// 파란색: 110 130
        color_Map.put("Purple", new Color_Range(130, 150));// 보라색: 130 150
        color_Map.put("Pink", new Color_Range(150, 170));// 핑크색: 150 170
        color_Map.put("All", new Color_Range(0, 180));

        setBtn.addActionListener(e -> {
            Imgproc.cvtColor(result_img, out_img, Imgproc.COLOR_HSV2BGR);
            Imgcodecs.imwrite(Main.mb.imgName + "_temp" + ".png", out_img);
            MenuBar.temp_imgPath = Main.mb.imgName + "_temp" + ".png";  // 임시이미지 경로 업데이트
            Main.mb.setImage(MenuBar.temp_imgPath);
        });

        deleteBtn.addActionListener(e -> {
            Imgcodecs.imwrite(Main.mb.imgName + "_temp" + ".png", raw_img);
            MenuBar.temp_imgPath = Main.mb.imgName + "_temp" + ".png";  // 임시이미지 경로 업데이트
            Main.mb.setImage(MenuBar.temp_imgPath);
        });

        colorSlider.addChangeListener(e -> {
            JSlider s = (JSlider) e.getSource();
            sliderValue = s.getValue();
            changeColor(raw_img, imgHSV, result_img, color_Map, sliderValue);
        });

        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        AbstractButton rb_click = (AbstractButton) e.getSource();  //라디오버튼 클릭반환

        // 색상선택 시 이벤트
        if(rb_click == colorRBtn[0]){
            setColor = "Red";
        }
        if(rb_click == colorRBtn[1]){
            setColor = "Orange";
        }
        if(rb_click == colorRBtn[2]){
            setColor = "Yellow";
        }
        if(rb_click == colorRBtn[3]){
            setColor = "Green";
        }
        if(rb_click == colorRBtn[4]){
            setColor = "Cyan";
        }
        if(rb_click == colorRBtn[5]){
            setColor = "Blue";
        }
        if(rb_click == colorRBtn[6]){
            setColor = "Purple";
        }
        if(rb_click == colorRBtn[7]){
            setColor = "Pink";
        }
        if(rb_click == colorRBtn[8]){
            setColor = "All";
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        Sliders release = (Sliders) e.getSource();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

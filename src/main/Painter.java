package main;

import java.awt.*;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

// 그리기
public class Painter extends JFrame{
    int angle;
    int pixcope;  //그릴 픽셀 범위
    double imgMag;
    String file1;
    String inp_Color = "Red";
    Mat backgroundImg;
    Mat tempMat;
    JRadioButton[] colorRBtn = new JRadioButton[9];
    JButton closeBtn = new JButton(" 닫기");
    JButton undoBtn = new JButton(" 되돌리기");
    JLabel angle_Label = new JLabel(Integer.toString(angle));
    JSlider Color_slider = new JSlider();
    HashMap <String,Scalar> pen_Color = new HashMap<>();
    Stack <Mat> painterStack;  // 그린 이미지 스택
    Font font = new Font("맑은 고딕", Font.BOLD, 16);

    public Painter(String file1){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // 배경 이미지 설정
        ImageIcon background_img = new ImageIcon("img/background/bg03.jpg");
        Image bg_img = background_img.getImage();
        Image temp_img = bg_img.getScaledInstance(600, 350, Image.SCALE_SMOOTH);
        background_img = new ImageIcon(temp_img);
        JLabel back = new JLabel(background_img);
        back.setLayout(null);
        back.setBounds(0, 0, 600, 350);

        // 프레임 기본설정
        this.setTitle("회전");
        this.setUndecorated(true);
        this.setSize(600, 350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        getRootPane().setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));  //테두리 설정
        this.setLayout(null);

        // 프레임 드래그 이동 설정
        Resizing.FrameDragListener frameDragListener = new Resizing.FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);
        setLocationRelativeTo(null);

        this.file1 = file1; //처음 받은 이미지
        backgroundImg = Imgcodecs.imread(file1);
        
        painterStack = new Stack<>(); // 그린 이미지 스택
        painterStack.push(backgroundImg);

        //펜 색깔
        pen_Color.put("Red",new Scalar(0,0,255)); //bgr
        pen_Color.put("Orange",new Scalar(0,165,255));
        pen_Color.put("Yellow", new Scalar(0,212,255));
        pen_Color.put("Green", new Scalar(0,255,0));
        pen_Color.put("Cyan", new Scalar(255,165,0));
        pen_Color.put("Blue",new Scalar(255,0,0));
        pen_Color.put("Purple", new Scalar(255,0,139));
        pen_Color.put("Black",new Scalar(0,0,0));
        pen_Color.put("White",new Scalar(255,255,255));

        // 색상 레이블 설정
        JLabel[] colorLabel = new JLabel[9];
        for(int i=0; i<colorRBtn.length; i++){
            colorLabel[i] = new JLabel(new ImageIcon("img/icon/pencolor/colorIcon" + i + ".png"));
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
            colorRBtn[i].setBackground(Color.WHITE);
            this.add(colorRBtn[i]);
        }
        colorRBtn[0].setSelected(true);

        // 되돌리기 버튼
        undoBtn.setSize(150, 40);
        undoBtn.setLocation(140, 270);
        undoBtn.setIcon(new ImageIcon("img/icon/undoIcon.png"));
        undoBtn.setFont(font);
        undoBtn.setBackground(Color.LIGHT_GRAY);
        undoBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(undoBtn);

        // 닫기 버튼
        closeBtn.setSize(130, 40);
        closeBtn.setLocation(300, 270);
        closeBtn.setFont(font);
        closeBtn.setBackground(Color.LIGHT_GRAY);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> {
            this.dispose();
        });
        this.add(closeBtn);

        // 슬라이더 설정
//        Color_slider.addMouseListener(this);
        Color_slider.setMinimum(1);
        Color_slider.setMaximum(10);
        Color_slider.setValue(1);
        Color_slider.setOrientation(JSlider.HORIZONTAL);
        Color_slider.setMajorTickSpacing(1);
        Color_slider.setPaintTicks(true);
        Color_slider.setSize(450, 50);
        Color_slider.setLocation(110, 140);
        Color_slider.setForeground(Color.WHITE);
        Color_slider.setOpaque(false);
        this.add(Color_slider);

        // 슬라이더 값 레이블
        angle_Label.setText("1");
        angle_Label.setFont(font);
        angle_Label.setForeground(Color.WHITE);
        angle_Label.setHorizontalAlignment(JLabel.CENTER);
        angle_Label.setSize(50, 50);
        angle_Label.setLocation(310, 170);
        this.add(angle_Label);

        // 텍스트용 레이블
        JLabel text_Label = new JLabel("펜 굵기");
        text_Label.setSize(70, 70);
        text_Label.setLocation(25, 125);
        text_Label.setFont(font);
        text_Label.setForeground(Color.WHITE);
        this.add(text_Label);

        this.add(back);
        this.setVisible(true);
    }

    public void backKey(){

        painterStack.pop();
        if(painterStack.empty()) {
            undoBtn.setEnabled(false);
            painterStack.push(backgroundImg);
        }

        Imgcodecs.imwrite(MenuBar.temp_imgPath, painterStack.peek());
    }
    public void pointDrawer(Stack<Point> inpPoints, String inp_Color, int penSize){
        
        tempMat = new Mat();
        if(painterStack.empty()) painterStack.push(backgroundImg);
        painterStack.peek().copyTo(tempMat); //스택 가장 위에 있는 이미지 불러오기

        this.imgMag = MenuBar.ui_imgMag;
        Scalar colorScalar = pen_Color.get(inp_Color);
        while(inpPoints.size() != 0) {
            //나누는 값이 크면 굵고 작으면 얇다
            pixcope = (int) ((backgroundImg.width() / 1000) * penSize + (int) (backgroundImg.height() / 1000) * penSize);

            //그릴 픽셀 범위
            try {
                Point temp = inpPoints.pop();
                Imgproc.circle(tempMat, temp, pixcope, colorScalar, -1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
         painterStack.push(tempMat); //수정한 이미지 스택에 저장
         undoBtn.setEnabled(true);
         Imgcodecs.imwrite(MenuBar.temp_imgPath, tempMat);
    }
}
 
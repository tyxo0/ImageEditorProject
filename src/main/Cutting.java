package main;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Cutting extends JFrame{

    public void preview(){
        Imgcodecs.imwrite(MenuBar.temp_imgPath, square_img);  // 사각형이미지 저장
        MenuBar.setImage(MenuBar.temp_imgPath);  // 사각형 그린 이미지 출력
        Imgcodecs.imwrite(MenuBar.temp_imgPath, finalRaw_img);
    }

    Mat square_img = new Mat() ; //=raw_img; //사각형 그린 이미지
    Mat finalRaw_img = new Mat();
    Font font = new Font("맑은 고딕", Font.BOLD, 16);
    public Cutting(String file_addr, Point inpPoint1, Point inpPoint2){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        System.out.println("cutting");
        try{
            Mat raw_img = new Mat();
            Mat cutted_img = new Mat();

            raw_img = Imgcodecs.imread(file_addr);

            Point startPoint = new Point();
            Point endPoint = new Point();

            startPoint.x = Math.min(inpPoint1.x, inpPoint2.x); // start point에 최소 점들 할당
            startPoint.y= Math.min(inpPoint1.y, inpPoint2.y);
            endPoint.x = Math.max(inpPoint1.x, inpPoint2.x); // endpoint에 최대 점들 할당
            endPoint.y= Math.max(inpPoint1.y, inpPoint2.y);

            cutted_img = raw_img.submat((int)startPoint.y, (int)endPoint.y,(int)startPoint.x, (int)endPoint.x); // 자른 결과

            raw_img.copyTo(square_img); //사각형 그린 이미지
            Imgproc.rectangle(square_img, inpPoint1, inpPoint2,new Scalar(255,255,255),5);

            // 프레임 설정
            this.setSize(175, 75);
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
            JButton setBtn = new JButton();
            setBtn.setIcon(new ImageIcon("img/icon/setIcon.png"));
            setBtn.setSize(70, 45);
            setBtn.setLocation(10, 15);
            setBtn.setBackground(Color.LIGHT_GRAY);
            setBtn.setFont(font);
            setBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            Mat finalCutted_img = cutted_img;
            setBtn.addActionListener(e -> {
                // 확인클릭 저장 // 취소클릭-> raw_img로 저장
                Imgcodecs.imwrite(MenuBar.temp_imgPath, finalCutted_img); //자른 이미지 저장
                MenuBar.setImage(MenuBar.temp_imgPath); // 자른 이미지 출력
                this.dispose();
            });
            this.add(setBtn);


            // 취소 버튼
            JButton deleteBtn = new JButton();
            deleteBtn.setIcon(new ImageIcon("img/icon/deleteIcon.png"));
            deleteBtn.setSize(70, 45);
            deleteBtn.setLocation(90, 15);
            deleteBtn.setBackground(Color.LIGHT_GRAY);
            deleteBtn.setFont(font);

            raw_img.copyTo(finalRaw_img);
            deleteBtn.addActionListener(e -> {
                Imgcodecs.imwrite(MenuBar.temp_imgPath, finalRaw_img);  // 사각형이미지 저장
                MenuBar.setImage(MenuBar.temp_imgPath);  // 사각형 그린 이미지 출력
                this.dispose();
            });
            this.add(deleteBtn);
            this.add(back);
            this.setVisible(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
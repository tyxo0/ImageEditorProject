package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class Rotation extends JFrame {
    private int angle;
    JFrame previewFrame = HighGui.createJFrame("미리보기 열기", 1);
    Font font = new Font("맑은 고딕", Font.BOLD, 16);

    public Rotation(String file_addr){
        System.out.println("회전 실행됨.");

        // 배경 이미지 설정
        ImageIcon background_img = new ImageIcon("img/background/bg03.jpg");
        Image bg_img = background_img.getImage();
        Image temp_img = bg_img.getScaledInstance(500, 250, Image.SCALE_SMOOTH);
        background_img = new ImageIcon(temp_img);
        JLabel back = new JLabel(background_img);
        back.setLayout(null);
        back.setBounds(0, 0, 500, 250);

        // 크기변경 프레임 기본설정
        this.setTitle("회전");
        this.setUndecorated(true);
        this.setSize(500, 250);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        getRootPane().setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));  //테두리 설정
        this.setLayout(null);

        // 프레임 드래그 이동 설정
        Resizing.FrameDragListener frameDragListener = new Resizing.FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);
        setLocationRelativeTo(null);

        // 슬라이더 생성
        JSlider rotation_Slider = new JSlider();
        rotation_Slider.setMinimum(-360);
        rotation_Slider.setMaximum(360);
        rotation_Slider.setValue(0);
        rotation_Slider.setOrientation(JSlider.HORIZONTAL);
        rotation_Slider.setMajorTickSpacing(120);
        rotation_Slider.setMinorTickSpacing(60);
        rotation_Slider.setPaintTicks(true);
        rotation_Slider.setSize(450, 50);
        rotation_Slider.setLocation(25, 60);
        rotation_Slider.setForeground(Color.WHITE);
        rotation_Slider.setOpaque(false);

        // 슬라이더 값 레이블
        JLabel angle_label = new JLabel(Integer.toString(angle));
        angle_label.setText("0°");
        angle_label.setFont(font);
        angle_label.setForeground(Color.WHITE);
        angle_label.setSize(50, 50);
        angle_label.setLocation(246, 90);

        // 텍스트 레이블
        JLabel text_Label = new JLabel("회전각도");
        text_Label.setFont(font);
        text_Label.setForeground(Color.WHITE);
        text_Label.setSize(70, 50);
        text_Label.setLocation(215, 15);

        // 버튼 설정
        JButton setBtn = new JButton("확인");
        setBtn.setFont(font);
        setBtn.setBackground(Color.LIGHT_GRAY);
        setBtn.setSize(120, 40);
        setBtn.setLocation(100, 190);
        setBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton previewBtn = new JButton("미리보기 열기");
        previewBtn.setFont(font);
        previewBtn.setBackground(Color.LIGHT_GRAY);
        previewBtn.setSize(150, 40);
        previewBtn.setLocation(240, 190);
        previewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));


        this.add(rotation_Slider);
        this.add(angle_label);
        this.add(text_Label);
        this.add(setBtn);
        this.add(previewBtn);
        this.add(back);

        try {
            rotation_Slider.addChangeListener(re -> {
                JSlider s = (JSlider)re.getSource();
                angle = s.getValue();
                angle_label.setText(Integer.toString(s.getValue()) + '°');
            });

            setBtn.addActionListener(se -> {
                try {
                    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

                    Mat src = Imgcodecs.imread(file_addr);
                    Mat dst = new Mat();

                    if (angle == 90 || angle == -270)
                        Core.rotate(src, dst, Core.ROTATE_90_CLOCKWISE);
                    else if (angle == 180 || angle == -180)
                        Core.rotate(src, dst, Core.ROTATE_180);
                    else if (angle == 270 || angle == -90)
                        Core.rotate(src, dst, Core.ROTATE_90_COUNTERCLOCKWISE);
                    else {
                        Point rotPoint = new Point(src.cols() / 2.0, src.rows() / 2.0);

                        Mat rotMat = Imgproc.getRotationMatrix2D(rotPoint, angle, 1);
                        Imgproc.warpAffine(src, dst, rotMat, src.size(), Imgproc.WARP_INVERSE_MAP);
                    }

                    Imgcodecs.imwrite(MenuBar.temp_imgPath, dst);
                    MenuBar.setImage(MenuBar.temp_imgPath);

                    previewFrame.dispose();
                    this.dispose();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });


            previewFrame.setTitle("미리보기 열기");
            previewFrame.setUndecorated(true);
            previewFrame.setResizable(false);
            previewFrame.setSize(500, 300);
            JLabel imgLabel = new JLabel();

            previewBtn.addActionListener(pe -> {
                JButton btn = (JButton) pe.getSource();
                if (btn.getText().equals("미리보기 열기")) {
                    try {
                        previewBtn.setText("미리보기 닫기");
                        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

                        Mat src = Imgcodecs.imread(file_addr);
                        Mat dst = new Mat();

                        if (angle == 90 || angle == -270)
                            Core.rotate(src, dst, Core.ROTATE_90_CLOCKWISE);
                        else if (angle == 180 || angle == -180)
                            Core.rotate(src, dst, Core.ROTATE_180);
                        else if (angle == 270 || angle == -90)
                            Core.rotate(src, dst, Core.ROTATE_90_COUNTERCLOCKWISE);
                        else {
                            Point rotPoint = new Point(src.cols() / 2.0, src.rows() / 2.0);

                            Mat rotMat = Imgproc.getRotationMatrix2D(rotPoint, angle, 1);
                            Imgproc.warpAffine(src, dst, rotMat, src.size(), Imgproc.WARP_INVERSE_MAP);
                        }

                        Mat resize = new Mat();
                        Size size = new Size(500, 300);
                        Imgproc.resize(dst, resize, size);

                        Image img = HighGui.toBufferedImage(resize);
                        imgLabel.setIcon(new ImageIcon(img));
                        imgLabel.setSize(500, 300);
                        imgLabel.setLocation(10, 10);
                        previewFrame.getContentPane().add(imgLabel);
                        previewFrame.setLocation(this.getX(), this.getY() + 250);
                        previewFrame.setVisible(true);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (btn.getText().equals("미리보기 닫기")) {
                    previewBtn.setText("미리보기 열기");
                    previewFrame.dispatchEvent(new WindowEvent(previewFrame, WindowEvent.WINDOW_CLOSING));
                    previewFrame.dispose();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }
}

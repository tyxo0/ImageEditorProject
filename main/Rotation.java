package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.*;
import javax.swing.*;

public class Rotation extends JFrame {
    private int angle;
    Sliders slider = new Sliders(JSlider.HORIZONTAL, -360, 360);
    JButton setBtn = new JButton("확인");
    JButton previewBtn = new JButton("미리보기");

    public Rotation(String file_addr){
        System.out.println("회전 실행됨.");
        setLayout(new BorderLayout());

        // 슬라이더 생성
        this.add(slider);

        // 레이블 설정
//        JLabel label = new JLabel(Integer.toString(angle));
//        label.setHorizontalAlignment(JLabel.CENTER);
//        add(label);

        // 버튼 설정
        this.add(setBtn, BorderLayout.SOUTH);
        this.add(previewBtn, BorderLayout.SOUTH);

        this.setVisible(true);

        try {
            slider.addChangeListener(e -> {
                JSlider s = (JSlider) e.getSource();
                angle = s.getValue();
//                label.setText(Integer.toString(s.getValue()));
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

                    Imgcodecs.imwrite(Main.mb.imgName + "_temp" + ".png", dst);

                    MenuBar.temp_imgPath = Main.mb.imgName + "_temp" + ".png";  // 임시이미지 경로 업데이트
                    Main.mb.setImage(MenuBar.temp_imgPath);

                    //기능 꺼지도록
//                    JOptionPane.showMessageDialog(null, "저장되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            previewBtn.addActionListener(pe -> {
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

                    JFrame previewFrame = HighGui.createJFrame("미리보기", 1);
                    previewFrame.setSize(1000, 800);
                    previewFrame.setLocationRelativeTo(null);
                    previewFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    previewFrame.setVisible(true);

                    Mat resize = new Mat();
                    Size size = new Size(1000, 800);
                    Imgproc.resize(dst, resize, size);

                    Image img = HighGui.toBufferedImage(resize);
                    JLabel imgLabel = new JLabel(new ImageIcon(img));
                    imgLabel.setSize(1000, 800);
                    imgLabel.setLocation(10, 10);
                    previewFrame.getContentPane().add(imgLabel);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

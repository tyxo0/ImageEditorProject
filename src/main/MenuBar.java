package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

//최대값보다 작아질 때까지 이미지를 배율로 줄이기
public class MenuBar extends JMenuBar implements ActionListener {
    // 전역 변수
    public static String imgPath = "";
    public static String temp_imgPath = "";

    // 지역 변수
    String imgName = "";
    static int resizeHeight;  //높이의 최대값
    static int resizeWidth;  //너비의 최대값
    static double ui_imgMag ;// UI로 바뀐 이미지 배율
    static JLabel imgLabel = new JLabel();
    JMenuItem save = new JMenuItem(" 저장");
    JMenuItem load = new JMenuItem(" 불러오기");
    final JFileChooser fileChooser = new JFileChooser();
    Font font = new Font("a도담도담 보통", Font.BOLD, 16);
    Mat tempImg_mat;

    public MenuBar() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        tempImg_mat = new Mat();

        //메뉴바와 메뉴 설정
        setBackground(new Color(50, 50, 50));
        setBorder(new LineBorder(new Color(50, 50, 50)));  //메뉴바 경계선도 색상변경

        JMenu fileMenu = new JMenu(" 파일");
        fileMenu.setFont(font);
        fileMenu.setForeground(new Color(198, 206, 215));
        fileMenu.setIcon(new ImageIcon("img/icon/fileIcon.png"));

        //저장 메뉴아이템 구현
        save.setFont(font);
        save.setForeground(Color.WHITE);
        save.setBackground(new Color(50, 50, 50));
        save.addActionListener(this);
        save.setIcon(new ImageIcon("img/icon/saveIcon.png"));

        //불러오기 메뉴아이템 구현
        load.setFont(font);
        load.setForeground(Color.WHITE);
        load.setBackground(new Color(50, 50, 50));
        load.addActionListener(this);
        load.setIcon(new ImageIcon("img/icon/loadIcon.png"));

        fileMenu.add(save);
        fileMenu.add(load);
        add(fileMenu);
    }

    public static void setImage(String Path){
        System.out.println("이미지 세팅이 실행 되었습니다");
        System.out.println("현재 경로: " + Path);

        ImageIcon imgIcon = new ImageIcon(Path);
        Mat matImg = Imgcodecs.imread(Path);

        // 높이에 대한 너비의 비율
        double Width_ratio = (double)matImg.width() / (double)matImg.height();
        Width_ratio = Math.round(Width_ratio * 1000) / 1000.0;  //넷째자리에서 반올림
        System.out.println("반올림 후 비율:" + Width_ratio);

        // 항상 보기 좋은 크기의 이미지로 설정
        if((int)(imgIcon.getIconWidth() * Width_ratio) < 1200 ){ //너비가 최대값을 초과하지 않으면
            resizeHeight = 600;
            resizeWidth = (int)((double)resizeHeight * Width_ratio);
        } else{  //너비가 최대값을 초과하면
            resizeWidth = 1000;
            resizeHeight = (int)((double)resizeWidth / Width_ratio);
        }
        System.out.println("UI바뀔 너비" + resizeWidth);
        System.out.println("UI바뀔 높이" + resizeHeight);

        ui_imgMag = (double)matImg.width()/resizeWidth;

        // 이미지라벨 설정
        Image img = imgIcon.getImage();
        Image resized_Img = img.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_SMOOTH);
        ImageIcon resized_imgIcon = new ImageIcon(resized_Img);
        System.out.println("아이콘 변경 직전 이미지경로: " + Path);
        System.out.println("===================================");
        imgLabel.setIcon(resized_imgIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem click = (JMenuItem)e.getSource();

        if(click == save){
            if(Objects.equals(MenuBar.imgPath, "")){
                JLabel error_Label = new JLabel("불러온 이미지가 없습니다");
                error_Label.setFont(font);
                JOptionPane.showMessageDialog(null, error_Label, "경고", JOptionPane.INFORMATION_MESSAGE);
            } else {
                fileChooser.setDialogTitle("이미지 저장");
                fileChooser.setCurrentDirectory(new File("/")); // 현재 사용 디렉토리를 지정

                int ret = fileChooser.showSaveDialog(null);

                //fileChooser.getSelectedFile().toPath() + "." + fileChooser.getFileFilter().getDescription()
                if(ret == JFileChooser.APPROVE_OPTION){  // 저장을 클릭
                    tempImg_mat = Imgcodecs.imread(temp_imgPath);
                    Imgcodecs.imwrite(fileChooser.getSelectedFile().getPath(), tempImg_mat);
                    imgLabel.setIcon(null);
//                    setImage(null);

                    try {
                        Path temp_filePath = Paths.get(temp_imgPath);

                        // 파일 삭제
                        Files.delete(temp_filePath);

                    } catch (NoSuchFileException fde) {
                        System.out.println("삭제하려는 파일/디렉토리가 없습니다");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }

        if(click == load){
            fileChooser.setDialogTitle("이미지 불러오기");
            fileChooser.setCurrentDirectory(new File("/")); // 현재 사용 디렉토리를 지정
            fileChooser.setAcceptAllFileFilterUsed(true);   // Fileter 모든 파일 적용
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // 파일 선택 모드(단일 파일만 선택가능)

            // filter 확장자 추가
            FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter(".jpg", "jpg");
            FileNameExtensionFilter pngFilter = new FileNameExtensionFilter(".png", "png");
            fileChooser.setFileFilter(jpgFilter);
            fileChooser.setFileFilter(pngFilter);

            //열기용 창 만들기
            int returnVal = fileChooser.showOpenDialog(null);

            if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭
                imgPath = fileChooser.getSelectedFile().getPath();
                imgName = imgPath.substring(0, imgPath.length()-4);  //.확장자를 뺀 절대 경로

                temp_imgPath = imgName + "_temp" + imgPath.substring(imgPath.length()-4);
                Imgcodecs.imwrite(temp_imgPath, Imgcodecs.imread(imgPath));

                System.out.println(imgPath);  //불러온 이미지 경로 확인용
                setImage(imgPath);
            }
        }
    }
}



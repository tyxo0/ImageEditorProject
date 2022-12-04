package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

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

    public MenuBar() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //메뉴바와 메뉴 설정
        setBackground(new Color(192, 192, 192));
        JMenu fileMenu = new JMenu(" 파일");
        fileMenu.setIcon(new ImageIcon("img/icon/fileIcon.png"));

        //저장 메뉴아이템 구현
        save.addActionListener(this);
        save.setIcon(new ImageIcon("img/icon/saveIcon.png"));

        //불러오기 메뉴아이템 구현
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
            //추가 구현하기

            // filter 확장자 추가
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image File", "jpg", "png");
            fileChooser.setFileFilter(filter);

            //저장용 창 만들기
            fileChooser.showOpenDialog(null);
        }

        if(click == load){
            fileChooser.setDialogTitle("파일 탐색기");
            fileChooser.setCurrentDirectory(new File("/")); // 현재 사용 디렉토리를 지정
            fileChooser.setAcceptAllFileFilterUsed(true);   // Fileter 모든 파일 적용
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // 파일 선택 모드(단일 파일만 선택가능)

            // filter 확장자 추가
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image File", "jpg", "png");
            fileChooser.setFileFilter(filter);

            //열기용 창 만들기
            int returnVal = fileChooser.showOpenDialog(null);

            if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭
                imgPath = fileChooser.getSelectedFile().getPath();
                imgName = imgPath.substring(0, imgPath.length()-4);  //.확장자를 뺀 절대 경로

                temp_imgPath = imgName + "_temp" + imgPath.substring(imgPath.length()-4); //파일명 변경;
                Imgcodecs.imwrite(temp_imgPath, Imgcodecs.imread(imgPath));

                System.out.println(imgPath);  //불러온 이미지 경로 확인용
                setImage(imgPath);
            }
            else if(returnVal == JFileChooser.CANCEL_OPTION) { // 취소를 클릭
                imgPath = "";
            }
        }
    }
}



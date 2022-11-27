package main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuBar extends JMenuBar implements ActionListener {
    String imgPath = "";
    int resizeHeight = 700;
    int resizeWidth;
    JLabel imgLabel = new JLabel();
    JMenuItem save = new JMenuItem(" 저장");
    JMenuItem load = new JMenuItem(" 불러오기");
    JFileChooser fileChooser = new JFileChooser();

    public MenuBar() {
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

                //높이에 대한 너비의 비율을 구하여 이미지 리사이즈
                ImageIcon imgIcon = new ImageIcon(imgPath);
                double ratio = (double)imgIcon.getIconWidth() / (double)imgIcon.getIconHeight();
//                System.out.println("이미지 비율:" + ratio);
                ratio = Math.round(ratio * 1000) / 1000.0;  //넷째자리에서 반올림
//                System.out.println("반올림 후:" + ratio);
                resizeWidth = (int)((double)resizeHeight*ratio);
//                System.out.println("바뀔 너비" + resizeWidth);
//                System.out.println("바뀔 높이" + resizeHeight);
                
                // 이미지라벨 설정
                Image img = imgIcon.getImage();
                Image resized_Img = img.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_SMOOTH);
                ImageIcon resized_imgIcon = new ImageIcon(resized_Img);
                imgLabel.setIcon(resized_imgIcon);

            }
            else if(returnVal == JFileChooser.CANCEL_OPTION) { // 취소를 클릭
                imgPath = "";
            }
        }
    }
}



package main;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

public class MenuBar extends JMenuBar implements ActionListener {
    public static String imgPath = "";
    int resizeHeight;  //높이의 최대값
                            //최대값보다 작아질 때까지 이미지를 배율로 줄이기
    int resizeWidth;  //너비의 최대값
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

    public void setImage(String imgPath){
        System.out.println("이미지 세팅이 실행 되었습니다");
        System.out.println("현재 경로: " + imgPath);

        ImageIcon imgIcon = new ImageIcon(imgPath);
        // 높이에 대한 너비의 비율
        double Width_ratio = (double)imgIcon.getIconWidth() / (double)imgIcon.getIconHeight();
        Width_ratio = Math.round(Width_ratio * 1000) / 1000.0;  //넷째자리에서 반올림
        System.out.println("반올림 후 비율:" + Width_ratio);

        // 프레임의 크기를 초과하는 경우만 처리(실제 사진의 크기를 느낄 수 있다) or 항상 보기 좋은 크기의 이미지로 설정(보기 좋다)
        if((int)(imgIcon.getIconWidth() * Width_ratio) < 1200 ){ //너비가 최대값을 초과하지 않으면
            resizeHeight = 600;
            resizeWidth = (int)((double)resizeHeight * Width_ratio);
        } else{  //너비가 최대값을 초과하면
            resizeWidth = 1000;
            resizeHeight = (int)((double)resizeWidth / Width_ratio);
        }
        System.out.println("바뀔 너비" + resizeWidth);
        System.out.println("바뀔 높이" + resizeHeight);

        // 이미지라벨 설정
        Image img = imgIcon.getImage();
        Image resized_Img = img.getScaledInstance(resizeWidth, resizeHeight, Image.SCALE_SMOOTH);
        ImageIcon resized_imgIcon = new ImageIcon(resized_Img);
        System.out.println("아이콘 변경 직전 이미지경로: " + imgPath);
        imgLabel.setIcon(resized_imgIcon);
    }

    public void mgps() {
        imgLabel.addMouseMotionListener(new MouseInputAdapter() { // 마우스 이벤트
            @Override
            public void mouseMoved(MouseEvent e) { // 마우스 움직일때.
                System.out.println(e.getX()+" "+e.getY()+" "+imgLabel.getWidth()+" "+imgLabel.getHeight());//x좌표,y좌표 출력
            }
        });
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
                System.out.println(imgPath);  //불러온 이미지 경로 확인용
                setImage(imgPath);
                mgps();
            }
            else if(returnVal == JFileChooser.CANCEL_OPTION) { // 취소를 클릭
                imgPath = "";
            }
        }
    }
}



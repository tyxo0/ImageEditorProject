package main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    int frameWidth = 1482; // 1400(이미지 영역) + 50(빈칸) + 32(툴바 영역)
    int frameHeight = 924; // 900(이미지 영역) + 24(메뉴바 영역) + 50(빈칸)
    final static MenuBar mb = new MenuBar();
    JPanel basicPanel = new JPanel();
    //    Sliders slider = new Sliders(JSlider.HORIZONTAL, -255, 255);
    ToolBar tb = new ToolBar();
//    ImgLabel il = new ImgLabel();

    public Main(){
        // 프레임 설정
        setTitle("사진 편집기");
        pack();
        setSize(frameWidth, frameHeight);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

//        setUndecorated(true);  //프레임 장식(프레임의 기본 설정) 초기화
//        setAlwaysOnTop(true);
//        getRootPane().setBorder(new MatteBorder(3, 3, 3, 3, Color.BLACK));  //테두리 설정
//        getContentPane().setLayout(new BorderLayout());
//        getContentPane().add(new JTextField("test text"), BorderLayout.WEST);

        // 프레임에 연결된 컨텐트팬을 설정
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());

        // 컴포넌트를 붙일 패널 설정
        basicPanel.setLayout(new BorderLayout());
        basicPanel.setBackground(new Color(50, 50, 50));

        // 컨테이너에 컴포넌트 붙이기
        setJMenuBar(mb);
        mb.imgLabel.setHorizontalAlignment(0);
        basicPanel.add(mb.imgLabel, BorderLayout.CENTER);
        basicPanel.add(tb, BorderLayout.EAST);
//        functionPanel.add(slider, BorderLayout.CENTER);

        // 패널을 컨테이너에 삽입
        c.add(basicPanel, BorderLayout.CENTER);

//        functionPanel.setVisible(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

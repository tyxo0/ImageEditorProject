package main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    int frameWidth = 1440;
    int frameHeight = 950;
    MenuBar mb = new MenuBar();
    ToolBar tb = new ToolBar();
//    ImgLabel il = new ImgLabel();

    public Main(){
        //프레임 설정
        setTitle("사진 편집기");
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setBackground(new Color(50, 50, 50));
        c.setLayout(new BorderLayout());

        //컨테이너에 컴포넌트 붙이기
        setJMenuBar(mb);
        mb.imgLabel.setHorizontalAlignment(0);
        add(mb.imgLabel, BorderLayout.CENTER);
        add(tb, BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

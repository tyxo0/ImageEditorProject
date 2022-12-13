package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.util.Stack;

public class Main extends JFrame {
    public static Stack<Mat> img_stack = new Stack<Mat>();
    int frameWidth = 1482; // 1400(이미지 영역) + 50(빈칸) + 32(툴바 영역)
    int frameHeight = 924; // 900(이미지 영역) + 24(메뉴바 영역) + 50(빈칸)
    final static MenuBar mb = new MenuBar();
     JPanel basicPanel = new JPanel();
    ToolBar tb = new ToolBar();

    public Main(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // 프레임 설정
        setTitle("사진 편집기");
        setUndecorated(true);
        pack();
        setSize(frameWidth, frameHeight);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

        DefaultMetalTheme z = new DefaultMetalTheme () {
            //inactive title color
            public ColorUIResource
            getWindowTitleInactiveBackground() {
                return new ColorUIResource(new Color(50, 50, 50));
            }
            //active title color
            public ColorUIResource
            getWindowTitleBackground() {
                return new ColorUIResource(new Color(50, 50, 50));
            }
            //start ActiveBumps
            public ColorUIResource
            getPrimaryControlHighlight() {
                return new ColorUIResource(new Color(50, 50, 50));
            }
            public ColorUIResource
            getPrimaryControlDarkShadow() {
                return new ColorUIResource(new Color(50, 50, 50));
            }

            public ColorUIResource
            getPrimaryControl() {
                return new ColorUIResource(new Color(50, 50, 50));
            }
            //end ActiveBumps

            //start inActiveBumps
            public ColorUIResource
            getControlHighlight () {
                return new ColorUIResource(new Color(50, 50, 50));
            }

            public ColorUIResource
            getControlDarkShadow () {
                return new ColorUIResource(new Color(50, 50, 50));
            }

            public ColorUIResource
            getControl () {
                return new ColorUIResource(new Color(50, 50, 50));
            }
            //end inActiveBumps
        };

        MetalLookAndFeel.setCurrentTheme(z);
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel ());
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
        SwingUtilities.updateComponentTreeUI (this);

        // 프레임에 연결된 컨텐트팬을 설정
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());

        // 컴포넌트를 붙일 패널 설정
        basicPanel.setLayout(new BorderLayout());
        basicPanel.setBackground(new Color(40, 40, 40));

        // 컨테이너에 컴포넌트 붙이기
        setJMenuBar(mb);
        MenuBar.imgLabel.setHorizontalAlignment(0);
        basicPanel.add(MenuBar.imgLabel, BorderLayout.CENTER);
        basicPanel.add(tb, BorderLayout.EAST);

        // 패널을 컨테이너에 삽입
        c.add(basicPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

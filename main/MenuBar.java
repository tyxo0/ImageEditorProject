package main;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {
    public MenuBar(){
        setBackground(new Color(192, 192, 192));
        JMenu fileMenu = new JMenu(" 파일");
        fileMenu.setIcon(new ImageIcon("img/icon/fileIcon.png"));

        JMenuItem save = new JMenuItem(" 저장");
        save.setIcon(new ImageIcon("img/icon/saveIcon.png"));
        JMenuItem load = new JMenuItem(" 불러오기");
        load.setIcon(new ImageIcon("img/icon/loadIcon.png"));
        fileMenu.add(save);
        fileMenu.add(load);
        add(fileMenu);
    }
}

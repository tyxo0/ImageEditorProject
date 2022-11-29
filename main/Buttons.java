package main;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Buttons implements MouseListener {
    JButton[] toolButton = new JButton[9];

    public Buttons(){
        //툴바 버튼에 아이콘을 붙이고, 각 버튼에 리스너 등록
        for(int i=0; i<toolButton.length; i++){
            toolButton[i] = new JButton(new ImageIcon("img/icon/toolIcon" + i + ".png"));
            toolButton[i].addMouseListener(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){
        //이벤트 처리코드 작성
        JButton btn = (JButton)e.getSource();
        if(btn == toolButton[0]){
            //크기변경 구현
            //개선사항: 1회성인 것, 같은 파일에 2번 동작할 경우 _resize가 2번 붙음
            new ResizingPic(MenuBar.imgPath);
        }

        if(btn == toolButton[1]){
            //회전 구현
        }

        if(btn == toolButton[2]){
            //밝기변경 구현
        }

        if(btn == toolButton[3]){
            //색상선택 구현
        }

        if(btn == toolButton[4]){
            //채도변경 구현
        }

        if(btn == toolButton[5]){
            //자르기 구현
        }

        if(btn == toolButton[6]){
            //그리기 구현
        }

        if(btn == toolButton[7]){
            //글자 삽입 구현
        }

        if(btn == toolButton[8]){
            //사진 붙이기 구현
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

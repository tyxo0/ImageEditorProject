package main;

import javax.swing.*;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.Stack;

class MyMouseListener implements MouseListener, MouseMotionListener {

    // public void mousePressed(MouseEvent e) {

    // }

    public void mouseClicked(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e){

    }

    public void mouseMoved(MouseEvent e){
        // System.out.println("moved " + clickBool);

    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

};
public class ToolButtons implements MouseListener {
    JButton[] toolButton = new JButton[9];
    boolean clickBool;
    int resizeCNT = 0;
    Painter painter;
    Mat init_mat;  //resize 이미지 깨짐 방지를 위한 백업
    MyMouseListener mouselistener ; // buttons에서 쓸 mouselistener
    public ToolButtons(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        MenuBar.imgLabel.addMouseListener(mouselistener);
        //툴바 버튼에 아이콘을 붙이고, 각 버튼에 리스너 등록
        for(int i=0; i<toolButton.length; i++){
            toolButton[i] = new JButton(new ImageIcon("img/icon/tool/toolIcon" + i + ".png"));
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
            MenuBar.imgLabel.removeMouseListener(mouselistener);
            if(resizeCNT != 0){
                new ResizingPic(init_mat);
            }
            else{
                init_mat = Imgcodecs.imread(MenuBar.temp_imgPath);
                new ResizingPic(init_mat);
            }
            resizeCNT ++;
            mouselistener = new MyMouseListener();
            MenuBar.imgLabel.addMouseListener(mouselistener);
        }

        if(btn == toolButton[1]){
            //회전 구현
            resizeCNT = 0;
            MenuBar.imgLabel.removeMouseListener(mouselistener);

            mouselistener = new MyMouseListener();
            MenuBar.imgLabel.addMouseListener(mouselistener);
        }

        if(btn == toolButton[2]){
            //밝기변경 구현
            resizeCNT = 0;
            MenuBar.imgLabel.removeMouseListener(mouselistener);

            mouselistener = new MyMouseListener();
            MenuBar.imgLabel.addMouseListener(mouselistener);
        }

        if(btn == toolButton[3]){
            //색상선택 구현
            resizeCNT = 0;
            MenuBar.imgLabel.removeMouseListener(mouselistener);
            new ColorEditor("Red", MenuBar.temp_imgPath);
            mouselistener = new MyMouseListener();
            MenuBar.imgLabel.addMouseListener(mouselistener);
        }

        if(btn == toolButton[4]){
            //채도변경 구현
            resizeCNT = 0;
            MenuBar.imgLabel.removeMouseListener(mouselistener);

            mouselistener = new MyMouseListener();
            MenuBar.imgLabel.addMouseListener(mouselistener);
        }

        if(btn == toolButton[5]){
            //자르기 구현
            resizeCNT = 0;
            MenuBar.imgLabel.removeMouseListener(mouselistener);
            Point starPoint = new Point();
            Point endPoint = new Point();
            mouselistener = new MyMouseListener(){
                // var MouseListener = new MyMouseListener(){
                public void mousePressed(MouseEvent e){
                    clickBool= true;
                    starPoint.x = (e.getX() -((double)MenuBar.imgLabel.getWidth()-MenuBar.resizeWidth)/2)*MenuBar.ui_imgMag;

                    starPoint.y =(e.getY() -((double)MenuBar.imgLabel.getHeight()-MenuBar.resizeHeight)/2)*MenuBar.ui_imgMag;
                }
                public void mouseReleased(MouseEvent e){
                    if(clickBool){
                        endPoint.x = (e.getX() -((double)MenuBar.imgLabel.getWidth()-MenuBar.resizeWidth)/2)*MenuBar.ui_imgMag;
                        endPoint.y = (e.getY() -((double)MenuBar.imgLabel.getHeight()-MenuBar.resizeHeight)/2)*MenuBar.ui_imgMag;
                        Cutting cutting = new Cutting(MenuBar.temp_imgPath, starPoint, endPoint);
                        clickBool =false;
                    }

                }
            };
            MenuBar.imgLabel.addMouseListener(mouselistener);
        }



        if(btn == toolButton[6]){
            //그리기 구현 **추가
            resizeCNT = 0;
            MenuBar.imgLabel.removeMouseListener(mouselistener);
            Stack <Point> draggedPoints = new Stack<>(); //점 모아둔 stack
            painter = new Painter(MenuBar.temp_imgPath);
            // Painter painter = new Painter("KakaoTalk_20220628_163120775.jpg");

            // MyMouseListener mouselistener = new MyMouseListener(){
            mouselistener = new MyMouseListener(){
                public void mouseExited(MouseEvent e) { // 뒤로가기 버튼이벤트화
                    try{
                        painter.backKey();
                        MenuBar.setImage(MenuBar.temp_imgPath); // 이전 이미지 불러옴
                        if(painter.painterStack.empty()) painter.painterStack.push(painter.backgroundImg);
                    }catch(Exception e1){
                        e1.printStackTrace();
                    }
                }
                public void mouseReleased(MouseEvent e){ // 점 전달해서 이미지 수정
                    System.out.println("Realeased");

                    painter.pointDrawer(draggedPoints, "Orange",1);
                    MenuBar.setImage(MenuBar.temp_imgPath); // 수정된 이미지 불러옴
                    System.out.println(painter.painterStack.size());
                }
                public void mouseDragged(MouseEvent e){ //점 저장

                    Point tempPoint = new Point((e.getX() -((double)MenuBar.imgLabel.getWidth()-MenuBar.resizeWidth)/2)*MenuBar.ui_imgMag,
                            (e.getY() -((double)MenuBar.imgLabel.getHeight()-MenuBar.resizeHeight)/2)*MenuBar.ui_imgMag);
                    if(!draggedPoints.contains(tempPoint))
                        draggedPoints.push(tempPoint);
                }
            };
            MenuBar.imgLabel.addMouseMotionListener(mouselistener);
            MenuBar.imgLabel.addMouseListener(mouselistener);
        }

        if(btn == toolButton[7]){
            //글자 삽입 구현
            resizeCNT = 0;
            MenuBar.imgLabel.removeMouseListener(mouselistener);
            // PuttingText text = new PuttingText();
            // MenuBar.imgLabel.addMouseListener(new MouseInputAdapter() {
            //     public void mouseClicked(MouseEvent e) {
            //         text.puttext(MenuBar.imgPath, (e.getX() - (MenuBar.imgLabel.getWidth() - MenuBar.resizeWidth) / 2) * MenuBar.imgMag, (e.getY() - (MenuBar.imgLabel.getHeight() - MenuBar.resizeHeight) / 2) * MenuBar.imgMag);
            //         MenuBar.imgLabel.removeMouseListener(this);
            //     }
            //     public void mouseReleased(MouseEvent e){

            //     }
            // });
            mouselistener = new MyMouseListener();
            MenuBar.imgLabel.addMouseListener(mouselistener);
        }

        if(btn == toolButton[8]){
            //사진 붙이기 구현
            resizeCNT = 0;
            MenuBar.imgLabel.removeMouseListener(mouselistener);
            Combiner combiner =  new Combiner(MenuBar.temp_imgPath);
            mouselistener = new MyMouseListener(){

                public void mouseClicked(MouseEvent e){ // 점 전달해서 이미지 수정

                    combiner.replace_img("cutted.jpg",
                            (int)((e.getX() -(MenuBar.imgLabel.getWidth()-MenuBar.resizeWidth)/2)*MenuBar.ui_imgMag)
                            , (int)((e.getY() -(MenuBar.imgLabel.getHeight()-MenuBar.resizeHeight)/2)*MenuBar.ui_imgMag)
                    );
                    // System.out.println(MenuBar.temp_imgPath);
                    MenuBar.setImage(MenuBar.temp_imgPath); // 수정된 이미지 불러옴
                }

            };
            MenuBar.imgLabel.addMouseListener(mouselistener);
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
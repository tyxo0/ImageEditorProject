package main;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.text.Highlighter.Highlight;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class Painter{// 그리기
    
    Mat backgroundImg;
    Mat tempMat;
    HashMap <String,Scalar> pen_Color = new HashMap<>();
    String file1;
    int mouse_x; int mouse_y;
    double imgMag; boolean mouseClicked;
    String inp_Color;

    int pixcope;
    //그릴 픽셀 범위
    Rect selectedPix;
    Stack <Mat> painterStack; // 그린 이미지 스택


    public Painter(String file1){
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);   
        this.file1 = file1; //처음 받은 이미지
        
        backgroundImg = Imgcodecs.imread(file1);
        
        painterStack = new Stack<>(); // 그린 이미지 스택
         painterStack.push(backgroundImg);

        //펜 색깔 // ui구현 필요
        pen_Color.put("Red",new Scalar(0,0,255)); //bgr
        pen_Color.put("Orange",new Scalar(0,165,255));
        pen_Color.put("Yellow", new Scalar(0,212,255));
        pen_Color.put("Green", new Scalar(0,255,0));
        pen_Color.put("Cyan", new Scalar(255,165,0));
        pen_Color.put("Blue",new Scalar(255,0,0));
        pen_Color.put("Purple", new Scalar(255,0,139));
        pen_Color.put("Black",new Scalar(0,0,0));
        pen_Color.put("White",new Scalar(255,255,255));
        
    }
    public void backKey(){
        
        painterStack.pop();
        if(painterStack.empty()) painterStack.push(backgroundImg);
        // tempMat = painterStack.peek();
        Imgcodecs.imwrite(MenuBar.temp_imgPath, painterStack.peek());
        // if(!painterStack.empty()) {
            
            
        //     System.out.println("pop "+ painterStack.size() );
            
        // }

    }
    public void pointDrawer(Stack<Point> inpPoints,
      String inp_Color, int penSize){
        
        tempMat = new Mat();
        if(painterStack.empty()) painterStack.push(backgroundImg);
        painterStack.peek().copyTo(tempMat); //스택 가장 위에 있는 이미지 불러오기

        this.imgMag = MenuBar.ui_imgMag;
        // Mat tempMat = backgroundImg;
        Scalar colorScalar = pen_Color.get(inp_Color);
        while(inpPoints.size() !=0){
            pixcope = (int)((backgroundImg.width()/1000)*penSize + (int)(backgroundImg.height()/1000)*penSize);
            //나누는 값이 크면 굵고 작으면 얇다
            //그릴 픽셀 범위
            try{

                Point temp = inpPoints.pop();
                // double data[] = backgroundImg.get((int)temp.y, (int)temp.x);
                Imgproc.circle(tempMat, temp, pixcope,colorScalar, -1);

                }
                catch(Exception e){
                    e.printStackTrace();
                }
              
        }

        // MenuBar.temp_imgPath = file1.substring(0, file1.length()-4) +
        //  "_Painted" +file1.substring(file1.length()-4, file1.length()); //파일명 변경
        
         painterStack.push(tempMat); //수정한 이미지 스택에 저장
         Imgcodecs.imwrite(MenuBar.temp_imgPath, tempMat);
        
    }

}
 
// package main;

// import java.util.HashMap;
// import java.util.Stack;

// import org.opencv.core.Core;
// import org.opencv.core.Mat;
// import org.opencv.core.Point;
// import org.opencv.core.Rect;
// import org.opencv.core.Scalar;
// import org.opencv.imgcodecs.Imgcodecs;
// import org.opencv.imgproc.Imgproc;


// public class Painter {// 그리기
    
//     Mat backgroundImg;
//     HashMap <String,Integer> pen_Color = new HashMap<>();
//     String file1;
//     int mouse_x; int mouse_y;
//     double imgMag; boolean mouseClicked;
//     String inp_Color;

//     int pixcope;
//     //그릴 픽셀 범위
//     Rect selectedPix;

//     public Painter(String file1){
//         // 배경 이미지, 마우스 위치, ui크기, 배율, 마우스 눌렸는지
//         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);   
//         this.file1 = file1;
//         // this.mouse_x =mouse_x;
//         // this.mouse_y =mouse_y;
//         // this.imgMag =imgMag;
//         // this.mouseClicked =mouseClicked;
//         // this.inp_Color = inp_Color;
//         backgroundImg = Imgcodecs.imread(file1);
//         pixcope = (int)((backgroundImg.width()/1000 *5+ (int)backgroundImg.height()/1000 *5));// //그릴 픽셀 범위
//         // hue(색상): 0~180, saturation(채도), value(밝기):0~255
//         // 초록색 계열 hue: 30~83
//         pen_Color.put("Red",0); // 빨간색: 170~10
//         pen_Color.put("Orange",20);// 주황색: 10~20
//         pen_Color.put("Yellow", 40);// 노란색: 20~40 /
//         pen_Color.put("Green", 55);// 초록색: 40~ 80 // 30~83
//         pen_Color.put("Cyan", 100);// 하늘색: 80 110 // 70~ 110
//         pen_Color.put("Blue",120);// 파란색: 110 130
//         pen_Color.put("Purple",140);// 보라색: 130 150
//         pen_Color.put("Black",0);
//         pen_Color.put("White",0);
        
//     }
//     public void pointDrawer(Stack<Point> inpPoints,
//       String inp_Color){
//     // public void pointDrawer(int mouse_x, int mouse_y,
//     //   String inp_Color){
//     // public void pointDrawer(int mouse_x, int mouse_y,
//     // double imgMag, boolean mouseClicked, String inp_Color){ 
//         // this.mouse_x =mouse_x;
//         // this.mouse_y =mouse_y;
//         this.imgMag = MenuBar.ui_imgMag;
//         // this.mouseClicked =mouseClicked;
//         // this.inp_Color =inp_Color;
        
        
//         Scalar colorScalar = new Scalar(255,255,255);
//         while(inpPoints.size() !=0){

//             try{

//                 Point temp = inpPoints.pop();
//                 // mouse_x = (int) temp.x;
//                 // mouse_y = (int) temp.y;
//                 // selectedPix = new Rect(new Point((int)(mouse_x*imgMag),(int)(mouse_y*imgMag))
//                 //  , new Point((int)(mouse_x*imgMag)+pixcope, (int)(mouse_y*imgMag) +pixcope));
                
//                 //원본에서 바꿀 범위
                
//                 // System.out.println(selectedPix);
        
//                 //원본에 씌우기
        
//                 // Imgproc.cvtColor(backgroundImg, backgroundImg, Imgproc.COLOR_BGR2HSV);
//                 // Imgproc.rectangle(backgroundImg, selectedPix, new Scalar (255,0,0), -1);
//                 Imgproc.circle(backgroundImg, temp, pixcope,colorScalar, -1);
//                 // for(int j = selectedPix.y; j<selectedPix.height; j++){
//                 //     if(j>=0){
//                 //         for(int i =selectedPix.x; i<selectedPix.width ; i++){
//                 //             double [] data = backgroundImg.get( j,i); // bgr
                            
//                 //             // data = penColor;
        
//                 //             if(data ==  null){
//                 //                 // System.out.println("back: " + i +" "+ j);
//                 //                 // System.out.println("backSkipped: " + i +" "+ j);
//                 //                 // System.out.println("over: " + over_i + " " + over_j);
//                 //                 // HighGui.imshow("CombinationImg", backgroundImg);
//                 //                 // HighGui.imshow("Overlay", overlayImg);
//                 //                 // HighGui.waitKey();
//                 //                 continue;
//                 //             }
//                 //             if(inp_Color.equals("Black"))
//                 //                 backgroundImg.put(j, i, 255, 0, 0);
//                 //             else if (inp_Color.equals("White"))
//                 //                 backgroundImg.put(j, i, 0, 0, 255);
//                 //             else  backgroundImg.put(j, i, pen_Color.get(inp_Color),200,255);
//                 //         }//bgr
//                 //     }
        
//                 // }
//                 // Imgproc.cvtColor(backgroundImg, backgroundImg, Imgproc.COLOR_HSV2BGR);
               
//                 // if(!mouseClicked){
//                 //     Imgcodecs.imwrite(file1.substring(0, file1.length()-4) +"_Drawed.jpg", backgroundImg);
                    
//                 // }
                
//                 // HighGui.imshow("Overlay", overlayImg);
//                 // HighGui.imshow("CombinationImg", backgroundImg);
//                 // HighGui.waitKey();
//                 }
//                 catch(Exception e){
//                     e.printStackTrace();
//                 }
//                 Imgcodecs.imwrite(file1.substring(0, file1.length()-4) + file1.substring(file1.length()-4, file1.length()), backgroundImg);
//         }
        
//     }
//     // public static void main(String[] args) {
//     //     // new Painter("cartoon-city-landscape-night-view_52683-69417.png", 0, 0,4.624, true, "Purple");
//     //     new Painter("KakaoTalk_20220628_163120775.jpg", 500, 0,4.624, true, "Yellow");
//     // }
// }
 
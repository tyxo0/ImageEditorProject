package main;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class Combiner {// 합성
    Mat raw_img = new Mat();
    Mat backgroundImg = new Mat();
    Mat overlayImg = new Mat();
    String file1;    
    public Combiner(String file1 ){
        // 배경 이미지
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
        
        raw_img =  Imgcodecs.imread(file1);
        this.file1 = MenuBar.imgPath;
        // backgroundImg = Imgcodecs.imread(file1);
    }

    public void replace_img(String file2, int mouse_x, int mouse_y){
        // 자를 이미지, 마우스 위치
        // raw_img = Imgcodecs.imread(MenuBar.temp_imgPath);
        // raw_img = Imgcodecs.imread(MenuBar.imgPath);
        System.out.println(MenuBar.imgPath);
        if(!file1.equals(MenuBar.imgPath))  {
            file1 =MenuBar.imgPath;
            Imgcodecs.imread(file1).copyTo(raw_img);
        }
            

        overlayImg =Imgcodecs.imread(file2);
        raw_img.copyTo(backgroundImg);
        
        Rect backRoi = new Rect(mouse_x - (int)overlayImg.size().width/2,(int)(mouse_y)- (int)overlayImg.size().height/2
        , (int)(mouse_x) + (int)overlayImg.size().width -(int)overlayImg.size().width/2, (int)(mouse_y) + (int)overlayImg.size().height -(int)overlayImg.size().height/2);

        System.out.println(backRoi);
         //원본에 씌우기
         int over_i =0;
         int over_j= 0;
         for(int j = backRoi.y; j<backRoi.height; j++){
             if(j>=0){
                 for(int i =backRoi.x; i<backRoi.width ; i++){
                     double [] data = overlayImg.get( over_j, over_i++);
                     if(i<0 || data ==  null){

                         continue;
                     }
                     backgroundImg.put(j, i, data[0],data[1],data[2]);
                 }
             }
             over_i=0;
             over_j++;
 
         }
        //  MenuBar.temp_imgPath = MenuBar.imgPath+"_CombinatedImg.jpg";
        System.out.println(MenuBar.temp_imgPath);
        Imgcodecs.imwrite(MenuBar.temp_imgPath, backgroundImg);

    }

}
// public class Combiner {// 합성
//     Mat backgroundImg = new Mat();
        
//     Mat overlayImg = new Mat();
//     public Combiner(String file1 ){
//         // 배경 이미지, 자를 이미지, 마우스 위치, ui크기, 배율, 마우스 눌렸는지
//         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);    
//         try{
//         // 이미지 두개 고르기 cutting 후 씌우기
//         // 초기화 버튼: 배경 다시 불러오기 
//         // 자르기 버튼: file2 이미지 보여주고 자르기

//         //배경 이미지
//         // Mat backgroundImg = new Mat();
        
//         // Mat overlayImg = new Mat();
//         backgroundImg = Imgcodecs.imread(file1);
        

//         //씌울 이미지 


//         // Rect backRoi = new Rect((int)(mouse_x*imgMag) - (int)overlayImg.size().width/2,(int)(mouse_y*imgMag)- (int)overlayImg.size().height/2
//         // , (int)(mouse_x*imgMag) + (int)overlayImg.size().width -(int)overlayImg.size().width/2, (int)(mouse_y*imgMag) + (int)overlayImg.size().height -(int)overlayImg.size().height/2);
//         // //원본에서 바꿀 범위
        
//         // Rect backRoi = new Rect(1000 - (int)overlayImg.size().width/2,500- (int)overlayImg.size().height/2
//         // , 1000 + (int)overlayImg.size().width -(int)overlayImg.size().width/2, 500 + (int)overlayImg.size().height -(int)overlayImg.size().height/2);
//         //원본에서 바꿀 범위
        
//         // System.out.println(backRoi);
//         // System.out.println(overlayImg.size());


//         // //원본에 씌우기
//         // int over_i =0;
//         // int over_j= 0;
//         // for(int j = backRoi.y; j<backRoi.height; j++){
//         //     if(j>=0){
//         //         for(int i =backRoi.x; i<backRoi.width ; i++){
//         //             double [] data = overlayImg.get( over_j, over_i++);
//         //             if(i<0 || data ==  null){
//         //                 // System.out.println("back: " + i +" "+ j);
//         //                 // System.out.println("backSkipped: " + i +" "+ j);
//         //                 // System.out.println("over: " + over_i + " " + over_j);
//         //                 // HighGui.imshow("CombinationImg", backgroundImg);
//         //                 // HighGui.imshow("Overlay", overlayImg);
//         //                 // HighGui.waitKey();
//         //                 continue;
//         //             }
//         //             backgroundImg.put(j, i, data[0],data[1],data[2]);
//         //         }
//         //     }
//         //     over_i=0;
//         //     over_j++;

//         // }

//         if(mouseClicked){
//             Imgcodecs.imwrite(file1 +"_CombinatedImg.jpg", backgroundImg);

//         }
        
//         // HighGui.imshow("Overlay", overlayImg);
//         // HighGui.imshow("CombinationImg", backgroundImg);
//         // HighGui.waitKey();
//         }
//         catch(Exception e){
//             e.printStackTrace();
//         }

        
//     }
//     public void replace_img(String file2, int mouse_x, int mouse_y,
//     double imgMag, boolean mouseClicked){
//         overlayImg =Imgcodecs.imread(file2);
//         Rect backRoi = new Rect((int)(mouse_x*imgMag) - (int)overlayImg.size().width/2,(int)(mouse_y*imgMag)- (int)overlayImg.size().height/2
//         , (int)(mouse_x*imgMag) + (int)overlayImg.size().width -(int)overlayImg.size().width/2, (int)(mouse_y*imgMag) + (int)overlayImg.size().height -(int)overlayImg.size().height/2);
//         //원본에서 바꿀 범위

//          //원본에 씌우기
//          int over_i =0;
//          int over_j= 0;
//          for(int j = backRoi.y; j<backRoi.height; j++){
//              if(j>=0){
//                  for(int i =backRoi.x; i<backRoi.width ; i++){
//                      double [] data = overlayImg.get( over_j, over_i++);
//                      if(i<0 || data ==  null){
//                          // System.out.println("back: " + i +" "+ j);
//                          // System.out.println("backSkipped: " + i +" "+ j);
//                          // System.out.println("over: " + over_i + " " + over_j);
//                          // HighGui.imshow("CombinationImg", backgroundImg);
//                          // HighGui.imshow("Overlay", overlayImg);
//                          // HighGui.waitKey();
//                          continue;
//                      }
//                      backgroundImg.put(j, i, data[0],data[1],data[2]);
//                  }
//              }
//              over_i=0;
//              over_j++;
 
//          }
//          if(mouseClicked){
//             Imgcodecs.imwrite(file1 +"_CombinatedImg.jpg", backgroundImg);

//         }
//     }
//     public static void main(String[] args) {
//         new Combiner("KakaoTalk_20220628_163120775.jpg");
//     }
// }

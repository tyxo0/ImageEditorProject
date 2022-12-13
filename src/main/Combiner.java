package main;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

// 합성
public class Combiner {
    Mat raw_img;
    Mat backgroundImg = new Mat();
    Mat overlayImg = new Mat();
    String file1;    
    public Combiner(String file1){
        // 배경 이미지
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
        
        raw_img =  Imgcodecs.imread(file1);
        this.file1 = MenuBar.imgPath;
    }

    public void replace_img(String file2, int mouse_x, int mouse_y){
        if(!file1.equals(MenuBar.imgPath)){
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
        System.out.println(MenuBar.temp_imgPath);
        Imgcodecs.imwrite(MenuBar.temp_imgPath, backgroundImg);
    }
}
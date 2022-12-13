package copy;

import javax.swing.*;


// 테스트 코드
public class test {
    public test(){
        try{
            String str = "1,234,567";
            String new_str = str.replace(",", "");
            int num = Integer.parseInt(new_str);
            num += 3;
            System.out.println("num + 3= " + num);

        }catch (NumberFormatException n){
            JOptionPane.showMessageDialog(null, "1~3000의 정수를 입력하세요");
        }
    }

    public static void main(String[] args) {
        new test();
    }
}

import javax.swing.JOptionPane;

public class intro   {
     public static void main(String[] arg) {
        int a , b  ;
        float c = 0; 
        a = 0 ; 
        b = 0 ; 
        c = 0 ; 
   
         a  = Integer.parseInt(JOptionPane.showInputDialog("enter the Num 1" ,a));
         b = Integer.parseInt(JOptionPane.showInputDialog(" Enter the sceond num2",b));
         c = Integer.parseInt(JOptionPane.showInputDialog("Enter the third num3 ", c) );
    
        JOptionPane.showMessageDialog( null," ="+ (float)(a*b*c)/100 );
    }
}

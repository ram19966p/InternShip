  public class CammandLine {
     public static void main(String[] args) {
        int a , b , c ; 
        String str1 , str2 ;
        str1 = args[0] ;
        str2 = args[1];
        a = Integer.parseInt(str1) ;
        b = Integer.parseInt(str2);
         c = a+b ; 
       System.out.println(" Its Name The sum of Two /number >>" + c   );
     }
    
}

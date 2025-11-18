package Revision;




 public  class Sum {


    public static void sumOfAllNaturalNumber(int  n ){
        int t = 0 ; 
          for( int i = 0 ; i <= n ; i++){
              t   =   t+i ; 

          }
          System.out.println(t+" << All the sum of Natural Number");
    }

             public  static boolean  isEven(int n ){
                int rem = n% 2 ; 
                if( rem == 0 ){
                    return true ;
                }
                   return false ; 
             }

       
       public  static void evenOrodd(int n ){
           if( isEven(n)){
            System.out.println("it is a Even number");
           }else{
            System.out.println("it is  odd number");
           }

       }
  

         public static int isBiteve(int n ){
             if( (n & 1) == 0 ){
                 return 0 ; 
             } 
              return 1 ;
         }

       public static void bitFuneven(int n ){
           if(isBiteve(n) == 0 ){
             System.out.println(" it is even ");
           } else{
            System.out.println(" it is odd");
           }
       }

         public static void main(String[] arg) {
            //  sumOfAllNaturalNumber(5);
                int n = 4 ; 

            // evenOrodd(n);
             isBiteve(n) ;
             System.out.println("Name");
    }
}
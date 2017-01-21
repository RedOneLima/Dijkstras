import java.util.*;

public class DijkstrasAlgorithm {
    protected int[][] LinkCostMatrix;
    ArrayList<String> yPri;
    final char INF = '\u221e';
    int[] N, D, p;
    String[] v;
    int nPos = 1;

    public static void main(String[]args) {
        DijkstrasAlgorithm obj = new DijkstrasAlgorithm();
    }//main
//*******************************************************************************************************************//
    public DijkstrasAlgorithm(){
        //call class to read file, and build matrix
        LinkMatrix matrix1 = new LinkMatrix();
        LinkCostMatrix = matrix1.getLinkCostMatrix();
        matrix1.printMatrix();
        try {
            //map the shortest path tree
            shortestPath();
            //build the path display
            mapPath();
        }catch (Exception e){};
    }//constructor
//*******************************************************************************************************************//
    public void shortestPath()throws Exception{
        N = new int[LinkCostMatrix.length];
        N[0] = 0;
        D = new int[LinkCostMatrix.length];
        p = new int[LinkCostMatrix.length];
        v = new String[LinkCostMatrix.length];
        yPri = new ArrayList<String>();
//Initialization
        //Filling D[i] with the mapping of V0
        for(int i=0;i<LinkCostMatrix.length;i++){
            D[i] = LinkCostMatrix[0][i];
        }
        //Filling p[i] with either 0 router or -1 to signify no link
        for(int i=0;i<LinkCostMatrix.length;i++){
            if(D[i]>-1)p[i]=0;
            else p[i] = -1;
            v[i] = "V"+i;
        }
        println("");
        printProgess("Initialization: ");
        println("");

//End of initalization
       while(nPos < N.length) {
           int minCost = Integer.MAX_VALUE;
           int minI = 0;
           for (int i = 0; i < D.length; i++) {
               if (!inN(i)) {
                   if (D[i] < minCost && D[i] != 0 && D[i] != -1) {
                       minCost = D[i];
                       minI = i;
                   }
               }
           }
           N[nPos++] = minI;

           yPri.add("(V"+p[minI]+", V"+minI+")");
           for (int i = 0; i < LinkCostMatrix.length; i++) {
               if ((minCost + LinkCostMatrix[minI][i] < D[i] || D[i] == -1) && !inN(i)) {
                   if ((LinkCostMatrix[minI][i] != -1 && D[i] == -1)||D[i] != -1 && LinkCostMatrix[minI][i] != -1)  {
                       D[i] = minCost + LinkCostMatrix[minI][i];
                       p[i] = minI;
                   }
               }
           }
           printProgess("Itteration "+(nPos-1)+": ");
           println("");
           Thread.sleep(500);
       }
    }
//*******************************************************************************************************************//
    public void mapPath(){
        int dest;
        String link;
        //header
        println(pad("Dest",8)+ "|  Link");
        for(int i=1; i<N.length; i++){
            int j = i;
            while(p[j] != 0){
                j = p[j];
            }
            dest = i;
            link = "(V0, V"+j+")";
            println(pad("V"+dest,10)+ pad("| "+link,10));
        }
    }
//*******************************************************************************************************************//
    public boolean inN(int inN){
        //checks if the given element is in N'
        for(int i=0;i<N.length;i++){if(N[i]==inN) return true;}
        return false;
    }
//*******************************************************************************************************************//
    public void println(String s){
        System.out.println(s);
    }
//*******************************************************************************************************************//
    public void print(String s){
        System.out.print(s);
    }
//*******************************************************************************************************************//
    public void printIterable(int[] s){
        for(int i=0;i<s.length;i++){
            if(s[i]==-1) print(INF+"\t");
            else print(s[i]+"\t");
        }
        println("");
    }
//*******************************************************************************************************************//
    public void printIterable(String[] s){
        for(int i=0;i<s.length;i++){
            print(s[i]+"\t");
        }
        println("");
    }
//*******************************************************************************************************************//
    public void printIterable(ArrayList<String> s){
        for(int i=0;i<s.size();i++){
            print(s.get(i));
        }
        println("");
    }
//*******************************************************************************************************************//
    public String pad(String s, int padding){

        for(int i=0;i<padding-s.length();i++) {
            s += " ";
        }
        return s;
    }
//*******************************************************************************************************************//
    public void printProgess(String message){
        println(message);
        print("N': ");
        printIterable(N);
        print("Y': ");
        printIterable(yPri);
        print("Router:\t");
        printIterable(v);
        print("D[i]:\t");
        printIterable(D);
        print("p[i]:\t");
        printIterable(p);
    }
}//class

import java.io.*;
import java.util.*;

public class LinkMatrix {
    private int [][]LinkCostMatrix;
    Scanner input = new Scanner(System.in);
    public final char INF = '\u221e';

    public LinkMatrix(){
        while(true) {
            try {
                LinkCostMatrix = readFile(chooseFile());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Integer expected...");
                continue;
            }
        }
    }
//*******************************************************************************************************************//
    public File chooseFile(){
            Scanner scn = new Scanner(System.in);
            String filename;
            while(true) {
                try {
                    System.out.println("Network map file: ");
                    filename = scn.nextLine();
                    return new File(filename);
                } catch (NullPointerException ioe) {
                    System.out.println( "Invalid file choice");
                    continue;
                }
            }
    }
//*******************************************************************************************************************//
    private int[][] readFile(File file){
        ArrayList link1 = new ArrayList(),link2 = new ArrayList(),linkCost = new ArrayList(),linkcount = new ArrayList();
        int retryCount = 0, nRouters = 0;
        Scanner fileReader = null;

        int line=0;
        while(true) {
            linkcount.clear();
            link1.clear();
            link2.clear();
            linkCost.clear();
            try {
                fileReader = new Scanner(file);
                for(int i=0;i<3;i++){
                        System.out.print(".");
                        Thread.sleep(500);
                }
                System.out.println("");
                System.out.println("File Accepted");
                Thread.sleep(500);
                for (int i = 0; fileReader.hasNext(); i++) {
                    line = i+1;
                    link1.add(Integer.valueOf(fileReader.next()));
                    link2.add(Integer.valueOf(fileReader.next()));
                    linkCost.add(Integer.valueOf(fileReader.next()));

                    if ((int) link1.get(i)<0) throw new NumberFormatException();
                    if ((int) link2.get(i)<0) throw new NumberFormatException();
                    if ((int) linkCost.get(i)<0) throw new NumberFormatException();

                    if (!inItterable(linkcount,(int) link1.get(i))) linkcount.add(link1.get(i));
                    if (!inItterable(linkcount,(int) link2.get(i))) linkcount.add(link2.get(i));
                }
                if(linkcount.size()<2){
                    System.out.println("Network Map must contain at least 2 Routers...");
                    fileReader.close();
                    file = chooseFile();
                    continue;
                }
                System.out.println(linkcount.size()+" Routers detected. Is this correct?(Y/n)");
                if(!input.next().equalsIgnoreCase("y")) {
                    if(retryCount<1) {
                        System.out.println("How many total routers are in the network being mapped?");
                        nRouters = input.nextInt();
                        retryCount++;
                    }else{
                        System.out.println(linkcount.size()+" Routers detected but there should be "+nRouters+" routers.\n" +
                                "Would you like to select a different file?(Y/n)");
                        if(input.next().equalsIgnoreCase("y")){
                            retryCount = 0;
                            file = chooseFile();
                        }
                    }
                    continue;
                }
                fileReader.close();
                return buildCostMatrix(link1, link2, linkCost, linkcount.size());
            }catch (FileNotFoundException e) {
                System.out.println("File not found");
                file = chooseFile();
            }catch (NumberFormatException e){
                System.out.println("Formatting Error Occured at line "+(line+1));
                System.out.println("Must be a positive integer");
                fileReader.close();
                file = chooseFile();
                continue;
            }catch(InputMismatchException e){
                System.out.println("Integer Expected at line "+(line+1));
                System.out.println("Must be labeled as positive integers.");
                fileReader.close();
                file = chooseFile();
                continue;
            }catch (InterruptedException e){
                continue;
            }
        }
    }
//*******************************************************************************************************************//
    private int[][] buildCostMatrix(ArrayList link1, ArrayList link2, ArrayList costLink,int linkcount){
        int costLinkMatrix[][] = new int[linkcount][linkcount];
        for(int i =0; i<costLinkMatrix.length;i++){
            for(int j=0;j<costLinkMatrix[0].length;j++){
                costLinkMatrix[i][j] = -1;
                if(i == j){
                    costLinkMatrix[i][j] = 0;
                }
            }
        }
        for(int i =0; i<costLink.size();i++){
            costLinkMatrix[(int)link1.get(i)][(int)link2.get(i)]= (int)costLink.get(i);
            costLinkMatrix[(int)link2.get(i)][(int)link1.get(i)]= (int)costLink.get(i);
            }
        return costLinkMatrix;
    }
//*******************************************************************************************************************//
    public int[][] getLinkCostMatrix(){
        return LinkCostMatrix;
    }
//*******************************************************************************************************************//
    public void printMatrix(){
        System.out.println("Matrix size is: "+LinkCostMatrix.length+" by: "+LinkCostMatrix[0].length);
        for(int i =0; i<LinkCostMatrix.length;i++){
            for(int j=0;j<LinkCostMatrix[0].length;j++){
                if((int)LinkCostMatrix[i][j] == -1){
                    System.out.print(INF+"\t");
                }else {
                    System.out.print(LinkCostMatrix[i][j] + " \t");
                }
            }
            System.out.println("");
        }
    }
//*******************************************************************************************************************//
    public boolean inItterable(ArrayList itterable,int element) {
        for (int i = 0; i < itterable.size(); i++) {
            if ((int) itterable.get(i) == element) {
                return true;
            }
        }
        return false;
    }
}//class

/**
 * Created by faizan on 11/16/16.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class fileFunctions {
    public static int items=1682;
    public static int users =943;
    public static int[][] data= new int[users][items];

    //reading the input file and creating the matrix
    public static int[][] fileRead(File file){
        for (int i=0; i< users;i++ ){
            for (int j=0; j<items; j++){
                data[i][j]=0;
            }
        }
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                Scanner lineScan = new Scanner(line);
                while (lineScan.hasNext()){
                    int nUser=Integer.parseInt(lineScan.next());
                    int nItem = Integer.parseInt(lineScan.next());
                    int nRate = Integer.parseInt(lineScan.next());
                    if (nItem<=items && nUser<=users) {
                        data[nUser - 1][nItem - 1] = nRate;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int j=0; j<data.length;j++) {
            ArrayList arr= new ArrayList() ;
            for (int i = 0; i < data[j].length; i++) {
                arr.add(data[j][i]);
            }
            driver.ratingMap.put(j,arr);
        }
        return data;
    }

    //constructing the output file
    public static void fileWrite(){
        try{
            FileWriter writer = new FileWriter("Output.txt");
            for (int i=0; i<driver.predictedRating.length; i++){
                for (int j=0; j<driver.predictedRating[i].length; j++){
                    int user =i+1;
                    int item =j+1;
                    writer.write(user+" "+item+ " "+driver.predictedRating[i][j]+"\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

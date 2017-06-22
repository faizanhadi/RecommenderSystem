import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by faizan on 11/16/16.
 *
 * Xij  = rating of Object-j by User-i
 * ni   = average rating of all objects by User-i
 * Normalised rating
 * Vij  = | Xij - ni |
 *
 * Predictive rating of Object-j by User-a :
 * Vaj  = k*(summation of all users - w(a,i)*Vij)
 * k    = 1/(summation of all users(w(a,i)))
 * Xaj  = Vaj + na --Predicted Value
 *
 *
 */


public class driver {
    public static int[][] inputRating;
    public static int[][] predictedRating ;
    public static float[][] normalisedRating;
    public static float[][] cosineWeights;
    public static TreeMap<Integer, Float> averageMap = new TreeMap<>();
    public static TreeMap<Integer, Float> kMap = new TreeMap<>();
    public static TreeMap<Integer, ArrayList<Integer>> ratingMap = new TreeMap<>();

    public static void main (String[] args){

        if (args.length!=1){
            System.err.println("Give the input file");
            System.exit(1);
        }

        String filname = args[0];
        File file = new File(filname);
        if (!file.isFile()){
            System.err.println("File does not exist\nGive the correct file name");
            System.exit(1);
        }

        System.out.println("Scaning input file and generating the output...");
        inputRating= fileFunctions.fileRead(file);      //reading the file and storing the values
        averageMap= BasicCalculation.averageRating(inputRating);    //calculating the average of all objects by a user
        normalisedRating= BasicCalculation.normalisedRating(inputRating);      //normalising the ratings by every user

        predictedRating= new int[fileFunctions.users][fileFunctions.items];
        cosineWeights = new float[fileFunctions.users][fileFunctions.users];
        for (int i=0; i<inputRating.length; i++) {
            for (int j = 0; j < inputRating.length; j++) {
                cosineWeights[i][j]=0;          //initializing the cosine weights
            }
        }

        //Calculating the Cosine Similarity for every User with every other User
        for (int i=0; i< ratingMap.size(); i++){
            for (int j=0; j<ratingMap.size(); j++){
                if (j>i){
                    if (i!=j){
                        cosineSimilarityFunctions.cosineNumerator(i,j,ratingMap.get(i),ratingMap.get(j));
                    }
                }
            }
        }
        cosineSimilarityFunctions.calculatingK();   //Calculating coefficient 'k' based on the cosine similarity

        System.out.println("Predicting the missing ratings for the Users...");
        //Finally predicting the rating by the User for the Item
        for (int i =0; i<inputRating.length; i++){
            for (int j=0; j<inputRating[i].length;j++){
                if (inputRating[i][j] > 0){
                    predictedRating[i][j]=inputRating[i][j];
                }else {
                    float returnedPred = BasicCalculation.predictionAlgorithm(i,j);
                    float predictedR = Math.round(returnedPred);;
                    predictedRating[i][j] = (int)predictedR;
                   // if (predictedR>4)
                     //   System.out.println("omg "+returnedPred+" and "+predictedRating[i][j]);
                }
            }
        }

        System.out.println("Writng to file...\n");
        fileFunctions.fileWrite();    //Generating the output file
        System.out.println("Output.txt generated !");
    }
}

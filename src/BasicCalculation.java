import java.util.TreeMap;

/**
 * Created by faizan on 11/17/16.
 */

public class BasicCalculation {

    //Calculating the average rating of a user for all the items
    public static TreeMap<Integer, Float> averageRating(int[][] userRatings){
        TreeMap<Integer, Float> aMap = new TreeMap<>();

        for (int i=0; i<userRatings.length;i++){
            float average=0;
            int count = 0;
            for (int j=0; j<userRatings[i].length; j++){
                average=average+userRatings[i][j];
                if (userRatings[i][j]!=0){
                    count++;
                }
            }
            if (count==0)
                aMap.put(i,0.0f);
            else {
                float avg =average/count;
                aMap.put(i,avg);
            }
        }
        return aMap;
    }


    //Calculating the normalised rating of all the Users
    public static float[][] normalisedRating (int[][] dataSet){
        float[][] normalData = new float[fileFunctions.users][fileFunctions.items];
        for (int i=0; i<dataSet.length; i++) {
            for (int j = 0; j < dataSet[i].length; j++) {
                normalData[i][j]=0;
            }
        }
        for (int i=0; i<dataSet.length; i++){
            for (int j=0; j<dataSet[i].length; j++){
                if (dataSet[i][j] !=0) {
                    float tempN = dataSet[i][j] - driver.averageMap.get(i);
                    float tempN2 =tempN;
                    if (tempN2 < 0) {
                        normalData[i][j] = -1 * tempN2;
                    } else {
                        normalData[i][j] = tempN2;
                    }
                }
            }
        }
       return normalData;
    }


    //Final Predicting Algorithm based on the cosine weights
    public static float predictionAlgorithm(int user, int item ){
        float pTrueRating;
        float pNormalisedRating=0;
        float pAvgRating=driver.averageMap.get(user);
        float kValue = driver.kMap.get(user);
        float tNRate=0;
        for (int i=0; i< driver.cosineWeights.length;i++){
            float weight = driver.cosineWeights[user][i];
            float nrating = driver.normalisedRating[i][item];
            tNRate = tNRate + weight * nrating;
        }
        pNormalisedRating= (kValue * tNRate);
        pTrueRating= pNormalisedRating +pAvgRating;
        return pTrueRating;
    }
}

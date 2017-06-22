import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by faizan on 11/17/16.
 */


public class cosineSimilarityFunctions {

    //Calculating the numerator for the Cosine Similarity
    public static void cosineNumerator(int user1, int user2, ArrayList<Integer> itemsOfUser1, ArrayList<Integer> itemsOfUser2){
        int numerator=0;
        for (int i=0; i<itemsOfUser1.size();i++){
            numerator=numerator+itemsOfUser1.get(i)*itemsOfUser2.get(i);
        }
        cosineDenominator(user1, user2, numerator, itemsOfUser1, itemsOfUser2);
    }

    //Calculating the denominator for the Cosine Similarity
    public static void cosineDenominator(int user1, int user2, int numerator, ArrayList<Integer> itemsOfUser1,
                                         ArrayList<Integer> itemsOfUser2){
        float denominator=0;
        int sqForUser1=0;
        int sqForUser2=0;

        for (int i=0; i<itemsOfUser1.size();i++){
            sqForUser1=sqForUser1 + itemsOfUser1.get(i)*itemsOfUser1.get(i);
        }
        for (int i=0; i<itemsOfUser2.size();i++){
            sqForUser2=sqForUser2 + itemsOfUser2.get(i)*itemsOfUser2.get(i);
        }
        denominator = (float)Math.sqrt(sqForUser1*sqForUser2);
        cosineWeights(user1,user2, numerator,denominator);
    }

    //Calculating the final weight for the Cosine Similarity
    public static void cosineWeights(int user1, int user2, int numerator, float denominator){

        float cWeight;
        if (denominator!=0 ) {
            float temp = numerator/denominator;
            cWeight=temp;
        }else {
            cWeight=0;
        }
        driver.cosineWeights[user1][user2]=cWeight;
        driver.cosineWeights[user2][user1]=cWeight;
    }

    //Calculating the coefficient 'k' for the Cosine Similarity
    public static void calculatingK(){
        float kTemp;
        for (int i=0; i< driver.cosineWeights.length; i++){
            kTemp=0;
            for (int j=0; j<driver.cosineWeights[i].length;j++){
                kTemp=kTemp + driver.cosineWeights[i][j];
            }
            if (kTemp!=0) {
                float kNew=1/kTemp;
                driver.kMap.put(i, kNew);
            }
            if (kTemp == 0)
                driver.kMap.put(i,0f);
        }
    }
}

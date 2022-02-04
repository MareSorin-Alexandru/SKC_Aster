package game1;

import java.io.*;

public abstract class ScoreHandler {

    public static double[] getScores(double current)
    {
        try {
            //read scores
            double[] scores = new double[10];
            BufferedReader br = new BufferedReader(new FileReader(new File("src/utilities/scores.txt")));
            {
                int i = 0;
                while ((scores[i++]=Double.valueOf(br.readLine()))!=0 && i < 10);
                br.close();
            }

            //insert new score where appropriate
            for (int i = 0; i < 9; i++)
            {
                if(current > scores[i])
                {
                    for (int j = 8; j >= i; j--)
                        scores[j+1]=scores[j];
                    scores[i]=current;
                    break;
                }
            }

            //write back to file
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("src/utilities/scores.txt")));
            for (int i = 0; i < 10; i++) {
                bw.write(String.valueOf(scores[i]));
                bw.newLine();
            }
            bw.close();

            return scores;
        }
        catch (Exception e){return new double[10];}
    }
}

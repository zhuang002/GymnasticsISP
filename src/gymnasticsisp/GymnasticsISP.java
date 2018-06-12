/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymnasticsisp;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Brian
 */
public class GymnasticsISP {

    static Scanner sc = new Scanner(System.in);
    static int comps;
    static String[] names;
    static double[][] execution;
    static double[][] difficulty;
    static Random random = new Random();
    static double[] finalScore;
    static int[] ranks;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("How many competitors are there?");
        comps = sc.nextInt();
        sc.nextLine(); // absorb the line end.
        names = new String[comps];
        execution = new double[comps][5];
        difficulty = new double[comps][5];
        finalScore = new double[comps];
        // Initialize finalScore
        for (int i=0;i<comps;i++) {
            finalScore[i]=0;
        }
        ranks=new int[comps];
        for (int i=0;i<comps;i++) {
            ranks[i]=i;
        }
        for (int i = 0; i < comps; i++) {
            System.out.printf("Enter competitor %d name: ", i);
            names[i] = sc.nextLine();
        }
        System.out.println();
        
        simulateARound();
    }

    private static void simulateARound() {
        for (int i = 0; i < comps; i++) {
            simulateACompetitor(i);
        }
    }

    private static void simulateACompetitor(int competitorIndex) {
        double[] total = new double[5];
        
        for (int i = 0; i < 5; i++) {
            execution[competitorIndex][i] = ((double) random.nextInt(101)) / 10;
            difficulty[competitorIndex][i] = ((double) random.nextInt(101)) / 10;
            total[i] = difficulty[competitorIndex][i] + execution[competitorIndex][i];
        }

        System.out.println("Name: "+ names[competitorIndex]);
        System.out.print("       "); // align the indent
        for (int i = 0; i < 5; i++) {
            System.out.printf("%6s%2d   ","Judge",i);
        }
        System.out.println();

        System.out.print("E:     ");
        for (int i = 0; i < 5; i++) {
            System.out.printf("%5.2f   ",execution[competitorIndex][i]);
        }
        System.out.println();
        System.out.print("D:     ");
        for (int i = 0; i < 5; i++) {
            System.out.printf("%5.2f   ",difficulty[competitorIndex][i]);
        }
        System.out.println();
        System.out.print("TOTAL: ");
        for (int i = 0; i < 5; i++) {
            System.out.printf("%5.2f   ",total[i]);
        }
        System.out.println();
        
        Arrays.sort(total);
        double sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += total[i + 1];
        }
        // finalScore should be accumuated for multiple rounds
        finalScore[competitorIndex] += sum / 3;
        System.out.printf("Final score: %2.1f   ", finalScore[competitorIndex]);

        int currentRank=getNewRank(competitorIndex);
        System.out.println("Rank: " + (currentRank+1));
        printRankBoard(competitorIndex,currentRank);
    }

    private static int getNewRank(int competitorIndex) {
        int counter = 0;
        for (int i = 0; i < competitorIndex; i++) {
            if (finalScore[i] > finalScore[competitorIndex]) {
                counter++;
            }
        }
        return counter;
    }

    private static void printRankBoard(int competitorIndex,int currentRank) {
        
        int lastRank=-1;
        // find previous rank of the competitor;
        for (int i=0; i <comps; i++) {
            if (ranks[i]==competitorIndex) {
                lastRank=i;
                break;
            }
        }
        // Insert the current rank into rank array
        if (currentRank<lastRank) {
            //insert competitor at currentRank position and move old data behind.
            for (int i=lastRank;i>currentRank;i--) {
                ranks[i]=ranks[i-1];
            }
        } 
        else {
            // insert the competitor at currentRank position and move the old data ahead.
            for (int i=lastRank;i<currentRank;i++) {
                ranks[i]=ranks[i-1];
            }
        }
        ranks[currentRank]=competitorIndex;
        
        System.out.println("********** CURRENT RANKS **********");
        for (int i=0;i<comps;i++) {
            int competitor=ranks[i];
            String name = names[competitor];
            double score = finalScore[competitor];
            System.out.printf("Rank %2d: %20s  %2.1f\r\n",i+1,name,score);
        }
        System.out.print("********** End OF RANKS **********\r\n\r\n\r\n");
        
    }
}
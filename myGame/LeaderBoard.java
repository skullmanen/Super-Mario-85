

package myGame;


import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class LeaderBoard {
    int width = 200;
    int height = 200;
    //HashMap<String, Integer> dataFields;
    static float[] topTimes = new float[]{0, 0, 0, 0, 0};

    public LeaderBoard() {
        //dataFields = new HashMap<>();

    }

    public void addTime(float time) {
        //vill jag lägga till alla tider i textfilen direkt or nah??
        if (time > topTimes[4]) {
            topTimes[4] = time;
            Arrays.sort(topTimes);
            reverse(topTimes);
        }
    }

    public void showTimes() {
        for (float time : topTimes) {
            System.out.println(time);
        }
    }

    public static void reverse(float[] array) {

        // Length of the array
        int n = array.length;

        // Swapping the first half elements with last half
        // elements
        for (int i = 0; i < n / 2; i++) {

            // Storing the first half elements temporarily
            float temp = array[i];

            // Assigning the first half to the last half
            array[i] = array[n - i - 1];

            // Assigning the last half to the first half
            array[n - i - 1] = temp;
        }
    }


    public float[] getTopTimes() {
        return topTimes;
    }

    public void writeTimesToTextfile() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Float time : topTimes) {
            String strTime = time.toString();
            stringBuilder.append(strTime).append("/");
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        if(!getLeaderboardTimes().contentEquals(stringBuilder)){
            if(JOptionPane.showConfirmDialog(null, "save times?", "Question", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                String[] options =  {"skullmanen", "other"};
                if ( JOptionPane.showOptionDialog(
                        null, "who played?", "choose an option",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]) == 1){


                }

            }
        }
        try {
            FileWriter fileWriter = new FileWriter("leaderBoard.txt");
            fileWriter.write(stringBuilder.toString());

            fileWriter.close();
            System.out.println("good jobb");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    private String getLeaderboardTimes() {
        try {
            return new String(Files.readAllBytes(Paths.get("leaderBoard.txt")));
        } catch (IOException e){
            System.out.println("could not read file");
        }


        return null;
    }

    public static void getTimesFromTextfile() {
        try {
            File myObj = new File("leaderBoard.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                String data = new String(
                        Files.readAllBytes(Paths.get("leaderBoard.txt")));

                System.out.println(data);
                String[] parts = data.split("/");
                try {
                    for (int i = 0; i < topTimes.length; i++) {
                        //this assumes the times in textfile are fastest and ordered
                        topTimes[i] = Float.parseFloat(parts[i]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("could not get times due to incorrect "
                            + "file structure");
                }
                for (float time : topTimes) {
                    System.out.println(time);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not create file for whatever reason.");

        }
    }
}

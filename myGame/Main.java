/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package myGame;


import gameEngine.GameContainer;

import javax.swing.*;

import static myGame.GameManager.TS;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;



/**
 *
 * @author olles
 */
public class Main
{
    public static void main (String[] args)
    {
		String imagePath ="C:\\Users\\olles\\OneDrive\\Documents\\Super-Mario-85\\resources/colortest1.png";
       /*  try {
           
            File file = new File(imagePath);
            BufferedImage image = ImageIO.read(file);

         
            int width = image.getWidth();
            int height = image.getHeight();

        
         for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    
                    int rgb = image.getRGB(0,0);
                    System.out.println(rgb);
                   
                   
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }*/    
		LeaderBoard.getTimesFromTextfile();
		GameContainer gc = new GameContainer(new GameManager());
		gc.setHeight(TS * 15 - TS / 2);
		gc.setWidth(TS * 16);
		gc.setScale(3f);
		gc.setTiltle("Super Mario Bros 85");
		gc.start();
	
    }
}

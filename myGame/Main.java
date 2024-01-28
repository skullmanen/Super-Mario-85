/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package myGame;


import gameEngine.GameContainer;

import javax.swing.*;

import static myGame.GameManager.TS;



/**
 *
 * @author olles
 */
public class Main
{
    public static void main (String[] args)
    {

		LeaderBoard.getTimesFromTextfile();
		GameContainer gc = new GameContainer(new GameManager());
		gc.setHeight(TS * 15 - TS / 2);
		gc.setWidth(TS * 16);
		gc.setScale(3f);
		gc.setTiltle("Super Mario Bros 85");
		gc.start();
	
    }
}

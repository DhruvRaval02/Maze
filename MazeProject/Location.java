import java.awt.Graphics;
import java.awt.*;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Location{

	private int x;
	private int y;

	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void setX(int newX){
		x = newX;
	}

	public void setY(int newY){
		y = newY;
	}

	public void moveForward(int wallWidthHeight, String directionFacing){
		if(directionFacing.equals("r")){
			x += wallWidthHeight;
		}
		else if(directionFacing.equals("l")){
			x -= wallWidthHeight;
		}
		else if(directionFacing.equals("u")){
			y -= wallWidthHeight;
		}
		else{
			y += wallWidthHeight;
		}
	}

}

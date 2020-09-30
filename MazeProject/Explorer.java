import java.awt.Graphics;
import java.awt.*;
import java.awt.Font;

import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Explorer{

	private Location loc;
	private String directionFacing;
	private int width;
	private int height;

	public Explorer(Location loc, String directionFacing){
		this.loc = loc;
		this.directionFacing = directionFacing;
	}

	public int getWidth(){
		return 5;
	}

	public int getHeight(){
		return 5;
	}

	public Location getLocation(){
		return loc;
	}

	public void setLocation(int x, int y){
		loc.setX(x);
		loc.setY(y);
	}

	public String getDirectionFacing(){
		return directionFacing;
	}

	public Polygon getPolygon(){
		if(directionFacing.equals("r")){
			return new Polygon(new int[]{loc.getX(), loc.getX(), loc.getX() + getWidth()}, new int[]{loc.getY(), loc.getY() + getHeight(), loc.getY() + getHeight()/2}, 3);
		}
		else if(directionFacing.equals("l")){
			return new Polygon(new int[]{loc.getX() + getWidth(), loc.getX() + getWidth(), loc.getX()}, new int[]{loc.getY(), loc.getY() + getHeight(), loc.getY() + getHeight()/2}, 3);
		}
		else if(directionFacing.equals("u")){
			return new Polygon(new int[]{loc.getX(), loc.getX() + getWidth(), loc.getX() + getWidth()/2}, new int[]{loc.getY() + getHeight(), loc.getY() + getHeight(), loc.getY()}, 3);
		}
		else{
			return new Polygon(new int[]{loc.getX(), loc.getX() + getWidth(), loc.getX() + getWidth()/2}, new int[]{loc.getY(), loc.getY(), loc.getY() + getHeight()}, 3);
		}
	}

	public void move(int wallWidthHeight){
		loc.moveForward(wallWidthHeight, directionFacing);
	}

	public void turnRight(){
		if(directionFacing.equals("r")){
			directionFacing = "d";
		}
		else if(directionFacing.equals("l")){
			directionFacing = "u";
		}
		else if(directionFacing.equals("u")){
			directionFacing = "r";
		}
		else{
			directionFacing = "l";
		}
	}

	public void turnLeft(){
		if(directionFacing.equals("r")){
			directionFacing = "u";
		}
		else if(directionFacing.equals("l")){
			directionFacing = "d";
		}
		else if(directionFacing.equals("u")){
			directionFacing = "l";
		}
		else{
			directionFacing = "r";
		}

	}

	public boolean isAbleToMove(Wall [][] array, boolean carry){
        int changeX = 0;
        int changeY = 0;
        if(directionFacing.equals("r")){
            changeX = 1;
            changeY = 0;
        }
        else if (directionFacing.equals("l")){
            changeX = -1;
            changeY = 0;
        }
        else if(directionFacing.equals("u")){
            changeX  = 0;
            changeY = -1;
        }
        else{
            changeX = 0;
            changeY = 1;
        }
        if((loc.getX()/5) + changeX >= 0 && (loc.getX()/5) + changeX < 42 && (loc.getY()/5) + changeY >= 0 && (loc.getY()/5) + changeY < 41){
            System.out.println(loc.getY());
            System.out.println(loc.getX());
            System.out.println(changeY);
            System.out.println(changeX);
            if(array[(loc.getY()/5) + changeY][(loc.getX()/5) + changeX] == null || ((array[(loc.getY()/5) + changeY][(loc.getX()/5) + changeX].getIsDoor()) && carry) || (array[(loc.getY()/5) + changeY][(loc.getX()/5) + changeX].getIsPortal()))
                return true;
        }
        return false;
	}

}

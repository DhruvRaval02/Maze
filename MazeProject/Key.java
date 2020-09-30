import java.awt.Graphics;
import java.awt.*;
import java.awt.Font;

import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Key{
    private Location loc;
    private boolean isCarried;

    public Key(Location loc, boolean isCarried){
        this.loc = loc;
        this.isCarried = isCarried;
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

    public boolean getIsCarried(){
        return isCarried;
    }

    public void setIsCarried(boolean b){
        isCarried = b;
    }

    public Polygon getPolygon(){
		return new Polygon(new int[]{loc.getX(), loc.getX(), loc.getX() + getWidth()}, new int[]{loc.getY(), loc.getY() + getHeight(), loc.getY() + getHeight()/2}, 3);
	}

}
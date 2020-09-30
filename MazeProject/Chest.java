import java.awt.Graphics;
import java.awt.*;
import java.awt.Font;

import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Chest{
    private Location loc;
    private boolean isOpened;

    public Chest(Location loc, boolean isOpened){
        this.loc = loc;
        this.isOpened = isOpened;
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

    public boolean getIsOpened(){
        return isOpened;
    }

    public void setIsOpened(boolean b){
        isOpened = b;
    }

}
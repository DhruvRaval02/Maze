import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.awt.Dimension;
public class MazeProgram extends JPanel implements KeyListener,MouseListener
{
	JFrame frame;
	
	//declare an array to store the maze - Store Wall(s) in the array
	int x=100,y=100;
	Wall [][] wallArray;
	Wall [][] doorArray;
	Location portalOne;
	Location portalTwo;
	ArrayList<Polygon> walls;
	ArrayList<PolyObj> topWalls;
	ArrayList<PolyObj> bottomWalls;
	ArrayList<Polygon> floors;
	ArrayList<Polygon> ceilings;
	ArrayList<Image> keys;
	ArrayList<Image> treasureKeys;
	ArrayList<Polygon> chests;
	int s=5;
	int counter = 0;
	int subtractor = 0;
	Explorer explorer = new Explorer(new Location(0,5), "r");
	Key key = new Key(new Location(5*5,95), false);
	Chest chest = new Chest(new Location(39*5, 35), false);
	Key keyTreasure = new Key(new Location(37*5, 15), false);
	Boolean isFinished = false;
	Boolean isDoorPresent = false;
	Boolean isPortalPresent = false;
	Boolean justPortaled = false;
	Boolean ableToPortal = false;
	Boolean portalDenied = false;
	Boolean interaction = false;
	Boolean inChest = false;
	int moveCounter = 0;

	public Dimension getPreferredSize(){
		return new Dimension(1000, 800);
	}

	public MazeProgram()
	{
		setBoard();
		setWalls();
		frame=new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();		
		frame.setVisible(true);
		frame.addKeyListener(this);
		//this.addMouseListener(this); //in case you need mouse clicking
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setWalls();
		//g.setFont(new Font("Times New Roman",Font.PLAIN,18));
		if(!isFinished){
			g.setColor(Color.BLACK);	//this will set the background color
			g.fillRect(0,0,1000,800);  //since the screen size is 1000x800
									//it will fill the whole visible part
									//of the screen with a black rectangle

		//drawBoard here!

			g.setColor(Color.WHITE);
			if(key.getIsCarried() == false)
				g.drawPolygon(key.getPolygon());
			else{
				g.drawString("White Key Acquired", 750, 45);
				//repaint();
			}
			g.setColor(Color.YELLOW);
			if(keyTreasure.getIsCarried() == false){
				g.setColor(Color.YELLOW);
				g.drawPolygon(keyTreasure.getPolygon());
			}
			else{
				g.drawString("Treasure Key Acquired", 750, 25);
			}
			g.setColor(Color.BLUE);
		//g.drawRect(x+500,y,100,100);	//x & y would be used to located your
									//playable character
									//values would be set below
									//call the x & y values from your Explorer class
									//explorer.getX() and explorer.getY()

		//other commands that might come in handy
		//g.setFont("Times New Roman",Font.PLAIN,18);
				//you can also use Font.BOLD, Font.ITALIC, Font.BOLD|Font.Italic
		//g.drawOval(x,y,10,10);
		//g.fillRect(x,y,100,100);
		//g.fillOval(x,y,10,10);

			g.setColor(Color.WHITE);
			g.drawString("Moves: " + moveCounter, 400,25);
		//g.fillOval(0, 11, s, s);

			if(explorer.getLocation().getX()/s == wallArray.length){
				isFinished = true;
				repaint();
			}


			if(explorer.getLocation().getX() == key.getLocation().getX() && explorer.getLocation().getY() == key.getLocation().getY()){
				key.setIsCarried(true);
				g.drawString("White Key Acquired", 750, 45);
			}
			if(explorer.getLocation().getX() == keyTreasure.getLocation().getX() && explorer.getLocation().getY() == keyTreasure.getLocation().getY()){
				keyTreasure.setIsCarried(true);
				g.setColor(Color.YELLOW);
				g.drawString("Treasure Key Acquired", 750, 25);
				System.out.print("GOT KEY");
			}



			for(int i = 0; i < chests.size(); i++){
				g.setColor(Color.YELLOW);
				g.fillPolygon(chests.get(i));
			}

			for(int x = 0; x < walls.size(); x++){
				if(isDoorPresent){
					g.setColor(Color.WHITE);
					g.fillPolygon(walls.get(x));
					isDoorPresent = false;
					System.out.println("WHITE");
				}
				else if(isPortalPresent){
					g.setColor(new Color(166, 77, 255));
					g.fillPolygon(walls.get(x));
					isPortalPresent = false;
				}
				else{
					for(int i = 0; i < floors.size(); i++){
						if(i == 0 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
							g.setColor(new Color(191, 191, 191));
						}
						else if(i == 1 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
							g.setColor(new Color(166, 166, 166));
						}
						else if(i == 2 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
							g.setColor(new Color(140, 140, 140));
						}
						else if(i == 3 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
							g.setColor(new Color(115, 115, 115));
						}
						else if(i == 4 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
							g.setColor(new Color(89, 89, 89));
						}
						else{
							g.setColor(new Color(191, 191, 191));
						}
					}
					g.fillPolygon(walls.get(x));
					g.setColor(Color.BLACK);
					g.drawPolygon(walls.get(x));
				}
			}

			for(int i = 0; i < topWalls.size(); i++){
				if(topWalls.get(i).isBlack()){
					g.setColor(Color.BLACK);
					//i++;
				}
				else{
				if(i == 0 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(191, 191, 191));
				}
				else if(i == 1 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(166, 166, 166));
				}
				else if(i == 2 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(140, 140, 140));
				}
				else if(i == 3 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(115, 115, 115));
				}
				else if(i == 4 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(89, 89, 89));
				}
				else{
					g.setColor(new Color(191, 191, 191));
				}
				}
				g.fillPolygon(topWalls.get(i).getPolygon());
				g.setColor(Color.BLACK);
				g.drawPolygon(topWalls.get(i).getPolygon());
			}

			for(int i = 0; i < bottomWalls.size(); i++){
				if(bottomWalls.get(i).isBlack()){
					g.setColor(Color.BLACK);
					//i++;
				}
				else{
				if(i == 0 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(191, 191, 191));
				}
				else if(i == 1 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(166, 166, 166));
				}
				else if(i == 2 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(140, 140, 140));
				}
				else if(i == 3 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(115, 115, 115));
				}
				else if(i == 4 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(89, 89, 89));
				}
				else{
					g.setColor(new Color(191, 191, 191));
				}
				}
				g.fillPolygon(bottomWalls.get(i).getPolygon());
				g.setColor(Color.BLACK);
				g.drawPolygon(bottomWalls.get(i).getPolygon());
			}

			

			for(int i = 0; i < floors.size(); i++){
				if(i == 0 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(191, 191, 191));
				}
				else if(i == 1 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(166, 166, 166));
				}
				else if(i == 2 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(140, 140, 140));
				}
				else if(i == 3 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(115, 115, 115));
				}
				else if(i == 4 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(89, 89, 89));
				}
				else{
					g.setColor(new Color(191, 191, 191));
				}
				//g.setColor(Color.GRAY);
				g.fillPolygon(floors.get(i));
				g.setColor(Color.BLACK);
				g.drawPolygon(floors.get(i));
			}
			for(int i  = 0; i < ceilings.size(); i++){
				if(i == 0 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(191, 191, 191));
				}
				else if(i == 1 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(166, 166, 166));
				}
				else if(i == 2 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(140, 140, 140));
				}
				else if(i == 3 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(115, 115, 115));
				}
				else if(i == 4 && (explorer.isAbleToMove(wallArray, key.getIsCarried()))){
					g.setColor(new Color(89, 89, 89));
				}
				else{
					g.setColor(new Color(191, 191, 191));
				}
				g.fillPolygon(ceilings.get(i));
				g.setColor(Color.BLACK);
				g.drawPolygon(ceilings.get(i));
			}

			g.setColor(Color.RED);
			g.drawPolygon(explorer.getPolygon());

			for(int j  = 0; j < wallArray.length; j++){
				for(int i = 0; i < wallArray[0].length; i++){
					if(wallArray[j][i] != null){
						if(wallArray[j][i].getIsDoor() == true)
							g.setColor(Color.WHITE);
						else if(wallArray[j][i].getIsPortal() == true)
							g.setColor(new Color(166, 77, 255));
						else
							g.setColor(Color.BLUE);
						g.fillRect(wallArray[j][i].getY() * s, wallArray[j][i].getX() * s, s, s);
						//System.out.println(wallArray[j][i].getY());
					}
				}
			}

			g.setColor(Color.YELLOW);
			g.fillRect(chest.getLocation().getY(), chest.getLocation().getX(),s, s);

			if(explorer.getLocation().getX() == chest.getLocation().getY() && explorer.getLocation().getY() == chest.getLocation().getX() && !chest.getIsOpened()){
				g.drawString("Press Space to Interact", 400, 775);
				interaction = true;
				if(inChest){
					int rand = (int)Math.random()*5;
					if(rand == 1){
						moveCounter -= 50;
						g.drawString("Minus 50 Moves!", 600, 775);
					}
					else if(rand == 2){
						moveCounter -= 100;
						g.drawString("Minus 100 Moves!", 600, 775);
					}
					else if(rand == 3){
						moveCounter -= 150;
						g.drawString("Minus 150 Moves!", 600, 775);
					}
					else if(rand == 4){
						moveCounter -= 200;
						g.drawString("Minus 200 Moves!", 600, 775);
					}
					else {
						moveCounter -= 250;
						g.drawString("Minus 250 Moves!", 600, 775);
					}
					interaction = false;
					chest.setIsOpened(true);
					inChest = false;
				}
				else
					interaction = false;
				repaint();
			}

			if(explorer.getLocation().getX()/s ==  portalOne.getY() && explorer.getLocation().getY()/s == portalOne.getX() && !justPortaled){
				interaction = true;
				g.setColor(Color.WHITE);
				if(!portalDenied)
					g.drawString("Press Y for Portal", 400, 400);
				
				if(ableToPortal){
					explorer.setLocation(portalTwo.getY()*s, (portalTwo.getX()*s));
					System.out.println("EX" + explorer.getLocation().getX() + "EY" + explorer.getLocation().getY());
					justPortaled = true;
					ableToPortal = false;
					interaction = false;
					portalDenied = false;
				}
				else{
					interaction = false;
				}
				repaint();
			}


			if(explorer.getLocation().getX()/s ==  portalTwo.getY() && explorer.getLocation().getY()/s == portalTwo.getX() && !justPortaled){
				interaction = true;
				g.setColor(Color.WHITE);
				if(!portalDenied)
					g.drawString("Press Y for Portal", 400, 400);
				
				if(ableToPortal){
					explorer.setLocation(portalOne.getY()*s, (portalOne.getX()*s));
					justPortaled = true;
					ableToPortal = false;
					interaction = false;
					portalDenied = false;
				}
				else{
					interaction = false;
				}
				repaint();
			}

			for(int i = 0; i < keys.size(); i++){
				if(key.getIsCarried() == false)
					g.drawImage(keys.get(i), 350, 350, null);
			}

			for(int i = 0; i < treasureKeys.size(); i++){
				if(keyTreasure.getIsCarried() == false)
					g.drawImage(treasureKeys.get(i), 350, 350, null);
			}

			if(justPortaled){
				g.setColor(Color.WHITE);
				g.drawString("Portal Success", 400, 400);
			}

			if(portalDenied){
				g.setColor(Color.WHITE);
				g.drawString("Portal Use Denied", 400, 400);
			}
		}
		else{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1000,800);
			g.setColor(Color.WHITE);
			g.drawString("You Win: " + moveCounter + " Moves", 400, 400);
		}
	}
	public void setBoard()
	{
		//choose your maze design

		//pre-fill maze array here

		File name = new File("MazeOutline.txt");
		try
		{
			wallArray = new Wall[41][85];
			int row  = 0;
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			while( (text=input.readLine())!= null)
			{
				System.out.println(text);
				for(int col = 0; col < text.length(); col++){
					if(text.charAt(col) == '*' || text.charAt(col) == '|')
						wallArray[row][col] = new Wall(row, col, false, false);
					else if(text.charAt(col) == 'd')
						wallArray[row][col] = new Wall(row, col, true, false);
					else if(text.charAt(col) == 'p'){
						wallArray[row][col] = new Wall(row, col, false, true);
						if(counter == 0)
							portalOne = new Location(row, col);
						else
							portalTwo = new Location(row, col);
						counter++;
					}
				}
				row++;
				//your code goes in here to chop up the maze design
				//fill maze array with actual maze stored in text file
			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}

		//setWalls();
	}

	public void setWalls()
	{
		int currentRow = explorer.getLocation().getY()/s;
		int currentCol = explorer.getLocation().getX()/s;
		int j = 0;
		int k = 0;
		int distanceFront = 0;
		int nearestWall = 0;
		//System.out.println(currentRow + "");
		//System.out.println(currentCol + "");
		String direction = explorer.getDirectionFacing();
		walls=new ArrayList<Polygon>();
		topWalls = new ArrayList<PolyObj>();
		bottomWalls = new ArrayList<PolyObj>();
		floors = new ArrayList<Polygon>();
		ceilings = new ArrayList<Polygon>();
		keys = new ArrayList<Image>();
		treasureKeys = new ArrayList<Image>();
		chests = new ArrayList<Polygon>();

		
		//Facing Right
		if(direction.equals("r")){
			while(currentCol + j < 50 && wallArray[currentRow][currentCol + j] == null){
				j++;
				if(distanceFront < 5)
					distanceFront++;
				nearestWall++;
			}
			if(currentCol + j < 50 && wallArray[currentRow][currentCol+j].getIsDoor()){
				isDoorPresent = true;
			}
			if(currentCol + j < 50 && wallArray[currentRow][currentCol+j].getIsPortal()){
				isPortalPresent = true;
			}

			if(explorer.getLocation().getY() == keyTreasure.getLocation().getY() && keyTreasure.getLocation().getX()-explorer.getLocation().getX() < 40 && keyTreasure.getLocation().getX()-explorer.getLocation().getX() > 0){
				try{
				Image keykey = ImageIO.read(this.getClass().getResource("yellowkey.png"));
				treasureKeys.add(keykey);
				}catch(IOException e){
					e.printStackTrace();
				}
			}

			int[]facingX = {50+50*nearestWall, 900-50*nearestWall, 900-50*nearestWall, 50+50*nearestWall};
			int[]facingY = {50+50*nearestWall, 50+50*nearestWall, 750-50*nearestWall, 750-50*nearestWall};
			if(chest.getLocation().getY() - explorer.getLocation().getX() < 20 && chest.getLocation().getY() - explorer.getLocation().getX() > 0 && chest.getLocation().getX() == explorer.getLocation().getY()){
				chests.add(new Polygon(facingX, facingY, 4));
			}
			else if(nearestWall <= distanceFront)
				walls.add(new Polygon(facingX, facingY, 4));
			

			for(int i = 0; i < distanceFront; i++){
				//Left Walls
				int[] topX={50+50*i,100+50*i,100+50*i,50+50*i};
				int[] topY={50+50*i,100+50*i,700-50*i,750-50*i};
				if(wallArray[currentRow-1][currentCol+i]!=null){
					topWalls.add(new PolyObj(new Polygon(topX,topY,4), false));
					//colorBlack = false;
				}
				else{
					topWalls.add(new PolyObj(new Polygon(topX,topY,4), true));
					//colorBlack = true;
				}
				//Right Walls
				int [] bottomX = {900-50*i,850-50*i,850-50*i,900-50*i};
				int [] bottomY={50+50*i,100+50*i,700-50*i,750-50*i};
				if(wallArray[currentRow + 1][currentCol + i] != null){
					bottomWalls.add(new PolyObj(new Polygon(bottomX,bottomY,4), false));
					//colorBlack = false;
				}
				else{
					bottomWalls.add(new PolyObj(new Polygon(bottomX,bottomY,4), true));
					//colorBlack = true;
				}
				int[]floorX = {50+50*i,100+50*i,850-50*i,900-50*i};
				int[]floorY ={750-50*i,700-50*i,700-50*i,750-50*i};
				if(currentCol + i < wallArray.length)
					floors.add(new Polygon(floorX, floorY, 4));

				int[]ceilingX = {50+50*i,100+50*i,850-50*i,900-50*i};
				int[]ceilingY = {50+50*i,100+50*i,100+50*i,50+50*i};
				if(currentCol + i < wallArray.length)
					ceilings.add(new Polygon(ceilingX, ceilingY, 4));
			}
		}
		else if(direction.equals("l")){
			while(currentCol - j >= 0 && wallArray[currentRow][currentCol - j] == null){
				j++;
				if(distanceFront < 5)
					distanceFront++;
				nearestWall++;
			}
			if(currentCol - j >= 0 && wallArray[currentRow][currentCol-j].getIsDoor()){
				isDoorPresent = true;
				System.out.println(isDoorPresent);
			}
			if(currentCol - j >= 0 && wallArray[currentRow][currentCol-j].getIsPortal()){
				isPortalPresent = true;
			}

			int[]facingX = {50+50*nearestWall, 900-50*nearestWall, 900-50*nearestWall, 50+50*nearestWall};
			int[]facingY = {50+50*nearestWall, 50+50*nearestWall, 750-50*nearestWall, 750-50*nearestWall};
			if(nearestWall <= distanceFront)
				walls.add(new Polygon(facingX, facingY, 4));
			
			for(int i = 0; i < distanceFront; i++){
				//Left Walls
				int[] topX={50+50*i,100+50*i,100+50*i,50+50*i};
				int[] topY={50+50*i,100+50*i,700-50*i,750-50*i};
				if(wallArray[currentRow+1][currentCol-i]!=null){
					topWalls.add(new PolyObj(new Polygon(topX,topY,4), false));
				}
				else{
					topWalls.add(new PolyObj(new Polygon(topX,topY,4), true));
				}
				//Right Walls
				int [] bottomX = {900-50*i,850-50*i,850-50*i,900-50*i};
				int [] bottomY={50+50*i,100+50*i,700-50*i,750-50*i};
				if(wallArray[currentRow - 1][currentCol - i] != null){
					bottomWalls.add(new PolyObj(new Polygon(bottomX,bottomY,4), false));
				}
				else{
					bottomWalls.add(new PolyObj(new Polygon(bottomX,bottomY,4), true));
				}
				int[]floorX = {50+50*i,100+50*i,850-50*i,900-50*i};
				int[]floorY ={750-50*i,700-50*i,700-50*i,750-50*i};
				floors.add(new Polygon(floorX, floorY, 4));

				int[]ceilingX = {50+50*i,100+50*i,850-50*i,900-50*i};
				int[]ceilingY = {50+50*i,100+50*i,100+50*i,50+50*i};
				ceilings.add(new Polygon(ceilingX, ceilingY, 4));
			}
		}

		else if(direction.equals("u")){
			while(currentRow - j >= 0 &&wallArray[currentRow - j][currentCol] == null){
				j++;
				if(distanceFront < 5)
					distanceFront++;
				nearestWall++;
			}
			if(wallArray[currentRow-j][currentCol].getIsDoor()){
				isDoorPresent = true;
				System.out.println(isDoorPresent);
			}
			if(wallArray[currentRow-j][currentCol].getIsPortal()){
				isPortalPresent = true;
			}
			if(explorer.getLocation().getX() == key.getLocation().getX() && explorer.getLocation().getY()-key.getLocation().getY() < 15 && explorer.getLocation().getY()-key.getLocation().getY() > 0){
				try{
				Image whiteKey = ImageIO.read(this.getClass().getResource("keyss.png"));
				keys.add(whiteKey);
				}catch(IOException e){
					e.printStackTrace();
				}
			}

			int[]facingX = {50+50*nearestWall, 900-50*nearestWall, 900-50*nearestWall, 50+50*nearestWall};
			int[]facingY = {50+50*nearestWall, 50+50*nearestWall, 750-50*nearestWall, 750-50*nearestWall};
			if(nearestWall <= distanceFront)
				walls.add(new Polygon(facingX, facingY, 4));

			for(int i = 0; i < distanceFront; i++){
				//Left Walls
				int[] topX={50+50*i,100+50*i,100+50*i,50+50*i};
				int[] topY={50+50*i,100+50*i,700-50*i,750-50*i};
				if(currentRow - i >= 0 && currentCol - 1 >= 0){
					if(wallArray[currentRow-i][currentCol-1]!=null){
						topWalls.add(new PolyObj(new Polygon(topX,topY,4), false));					
					}
					else{
						topWalls.add(new PolyObj(new Polygon(topX,topY,4), true));
					}
				}
				//Right Walls
				int [] bottomX = {900-50*i,850-50*i,850-50*i,900-50*i};
				int [] bottomY={50+50*i,100+50*i,700-50*i,750-50*i};
				if(wallArray[currentRow - i][currentCol + 1] != null){
					bottomWalls.add(new PolyObj(new Polygon(bottomX,bottomY,4), false));				
				}
				else{
					bottomWalls.add(new PolyObj(new Polygon(bottomX,bottomY,4), true));
				}
				int[]floorX = {50+50*i,100+50*i,850-50*i,900-50*i};
				int[]floorY ={750-50*i,700-50*i,700-50*i,750-50*i};
				floors.add(new Polygon(floorX, floorY, 4));

				int[]ceilingX = {50+50*i,100+50*i,850-50*i,900-50*i};
				int[]ceilingY = {50+50*i,100+50*i,100+50*i,50+50*i};
				ceilings.add(new Polygon(ceilingX, ceilingY, 4));
			}
		}
		else if(direction.equals("d")){
			while(wallArray[currentRow + j][currentCol] == null){
				j++;
				if(distanceFront < 5)
					distanceFront++;
				nearestWall++;
				System.out.println("RAN");
			}
			if(wallArray[currentRow+j][currentCol].getIsDoor()){
				isDoorPresent = true;
				System.out.println(isDoorPresent);
			}
			if(wallArray[currentRow+j][currentCol].getIsPortal()){
				isPortalPresent = true;
			}


			int[]facingX = {50+50*nearestWall, 900-50*nearestWall, 900-50*nearestWall, 50+50*nearestWall};
			int[]facingY = {50+50*nearestWall, 50+50*nearestWall, 750-50*nearestWall, 750-50*nearestWall};
			if(nearestWall <= distanceFront)
				walls.add(new Polygon(facingX, facingY, 4));

			for(int i = 0; i < distanceFront; i++){
				//Left Walls
				int[] topX={50+50*i,100+50*i,100+50*i,50+50*i};
				int[] topY={50+50*i,100+50*i,700-50*i,750-50*i};
				if(currentCol - 1 >= 0 && wallArray[currentRow+i][currentCol-1]!=null){
					topWalls.add(new PolyObj(new Polygon(topX,topY,4), false));
				}
				else{
					topWalls.add(new PolyObj(new Polygon(topX,topY,4), true));
				}
				//Right Walls
				int [] bottomX = {900-50*i,850-50*i,850-50*i,900-50*i};
				int [] bottomY={50+50*i,100+50*i,700-50*i,750-50*i};
				if(wallArray[currentRow+i][currentCol+1]!=null){
					bottomWalls.add(new PolyObj(new Polygon(bottomX,bottomY,4), false));		
				}
				else{
					bottomWalls.add(new PolyObj(new Polygon(bottomX,bottomY,4), true));
				}
				int[]floorX = {50+50*i,100+50*i,850-50*i,900-50*i};
				int[]floorY ={750-50*i,700-50*i,700-50*i,750-50*i};
				floors.add(new Polygon(floorX, floorY, 4));

				int[]ceilingX = {50+50*i,100+50*i,850-50*i,900-50*i};
				int[]ceilingY = {50+50*i,100+50*i,100+50*i,50+50*i};
				ceilings.add(new Polygon(ceilingX, ceilingY, 4));
				
			}
		}
	}



	public void keyPressed(KeyEvent e)
	{
		System.out.println("CODE" + e.getKeyCode());
		if(e.getKeyCode()==37){
			//x-=10;
			explorer.turnLeft();
		}
		else if (e.getKeyCode() == 39){
			explorer.turnRight();
		}
		else if(e.getKeyCode() == 38){
			if(explorer.isAbleToMove(wallArray, key.getIsCarried()) && !interaction){
				explorer.move(s);
				moveCounter++;
				justPortaled = false;
				portalDenied = false;
			}
			//System.out.println(explorer.isAbleToMove(wallArray, key.getIsCarried()));
		}
		else if(e.getKeyCode() == 89){
			ableToPortal = true;
			interaction = false;
			portalDenied = false;
		}
		else if(e.getKeyCode() == 78){
			interaction = false;
			ableToPortal = false;
			portalDenied = true;
			System.out.println("N");
		}
		else if(e.getKeyCode() == 32){
			interaction = false;
			inChest = true;
			System.out.println("INTERACTION" + interaction);
		}
		repaint();


	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public static void main(String[]args){
		new MazeProgram();
	}
}



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyPanel extends JPanel {


	JLabel spellCardLabel1=new JLabel();
	JLabel spellCardLabel2=new JLabel();
	
ImageIcon attackButtonImage=new ImageIcon("CardGameAssets/attackButton.png");
ImageIcon pressedAttackButtonImage=new ImageIcon("CardGameAssets/attackButton1.png");
ImageIcon useAbilityButtonImage=new ImageIcon("CardGameAssets/useAbilityButton.png");
ImageIcon pressedUseAbilityButtonImage=new ImageIcon("CardGameAssets/useAbilityButton1.png");

ImageIcon rightButtonImage=new ImageIcon("CardGameAssets/rightButton.png");
ImageIcon pressedRightButtonImage=new ImageIcon("CardGameAssets/pressedRightButton.png");

ImageIcon leftButtonImage=new ImageIcon("CardGameAssets/leftButton.png");
ImageIcon pressedLeftButtonImage=new ImageIcon("CardGameAssets/pressedLeftButton.png");

ImageIcon passButton=new ImageIcon("CardGameAssets/passButton.png");
ImageIcon pressedPassButton=new ImageIcon("CardGameAssets/pressedPassButton.png");


ImageIcon passButton1=new ImageIcon("CardGameAssets/passButton1.png");

ImageIcon pressedPassButton1=new ImageIcon("CardGameAssets/pressedPassButton1.png");
	
int size=100;
	GameCard[] allCards=new GameCard[100];
	
	
	
	Hashtable<ImageIcon,Boolean> availableImages=new Hashtable<>();
    Hashtable<ImageIcon,Point>imageCorners=new Hashtable<>();
    Hashtable<ImageIcon,GameCard>cardsOfImages=new Hashtable<>();
    Hashtable<ImageIcon,Boolean> availableButtonImages=new Hashtable<>();
   
    
    Hashtable<ImageIcon,Boolean> availablePassButtonImages=new Hashtable<>();
    
    ArrayList<String>hits=new ArrayList<>();
    Hashtable<String,Point>hitCorners=new Hashtable<>();
    
    boolean[] buttonPressed=new boolean[3]; //0 -> attack, 1 -> right, 2 -> left
    
    boolean[][] enemyCardPresent=new boolean[2][4];

    GameCard theAttackCard=null;
    GameCard theUseAbilityCard=null;
	
	ImageIcon gameBackGroundImage=new ImageIcon("CardGameAssets/newBackGround.png");
	JLabel label1=new JLabel();
	
	ImageIcon BalooonImage;
	
	ImageIcon backGroundForBalooonImage=new ImageIcon("CardGameAssets/backgorundForBaloon.png");
	

	boolean cardButtonPressed[]=new boolean[3];
	
	Point prevPoint;
	
	Point basePoint;
	
	//Point[] imageCorners;
	
	boolean replaced=true;
	
	int index;
	
	boolean pressedPass;
	
	ImageIcon currentImage;
	
	int count=0;
	
	ImageIcon[][]board=new ImageIcon[2][4];
	
	ImageIcon newImage;
	
	//Graphics2D g2D;
	
	//Component c;
	
	boolean turnOfFirstPlayer=true;
	
	boolean buttonPass=false;
	
	GameCard[] player1Array=new GameCard[4];
	GameCard[] player2Array=new GameCard[4];
	
	Stack<GameCard>cardStack=new Stack<>();
	
	Random random=new Random();
	int j1;
	int i1;
	
	Hashtable<GameCard,Integer> shiftTable=new Hashtable<>();
public void arrangeCards(boolean firstPlayer) {
	cardsAreMoving=true;
	GameCard []playerArray;
	if(firstPlayer) playerArray=player1Array;
	else playerArray=player2Array;
		
	
	for(int i=2;i>=0;i--) {
		
		if(playerArray[i]==null) continue;
			
		int j=i+1;
		
		if(playerArray[j]!=null) continue;
				
			while(j<3 && playerArray[j+1]==null) {
				j++;
			}
			
			playerArray[j]=playerArray[i];
			playerArray[i]=null;
			
		
			GameCard card=playerArray[j];
		if(firstPlayer)
			card.tableNumber=j  ;
		else
				card.tableNumber=j +4;
			shiftTable.put(card, j-i);
			
			

			Timer timer=new Timer();
			
			TimerTask task=new TimerTask() {
				
				int a=5;
				int V=0;
				
				int k=shiftTable.get(card);
		
				
				int x=imageCorners.get(card.imageIcon).x;
				
				@Override
				public void run() {
					//System.out.println(k);
					
					// TODO Auto-generated method stub
					if(firstPlayer) {
					imageCorners.replace(card.imageIcon, new Point(imageCorners.get(card.imageIcon).x - a ,imageCorners.get(card.imageIcon).y));
					V-=a;
					}
					else {
						imageCorners.replace(card.imageIcon, new Point(imageCorners.get(card.imageIcon).x + a ,imageCorners.get(card.imageIcon).y));
					V+=a;
					}
					repaint();
					
					if((Math.abs(V) > Math.abs((k)*laneInterval)) ) {
	    					
						if(firstPlayer)
						imageCorners.replace(card.imageIcon, new Point(x- (k)*laneInterval ,imageCorners.get(card.imageIcon).y));
						
						else
							imageCorners.replace(card.imageIcon, new Point(x+ (k)*laneInterval ,imageCorners.get(card.imageIcon).y));
						
						repaint();
						
						
						timer.cancel();
						
						
					}
					
				}
				
			};
			timer.schedule(task, 100, 7);
			
		
	}
	

		ArrayList<GameCard> list=new ArrayList<>();
		int amount=0;
	for(GameCard card: playerArray) {
		
		list.add(card);
		
	}
	
	for(GameCard card: list) {
		 
		if(card==null) 
			amount++;
		
	}
	//System.out.println(amount);
	spawnCards(firstPlayer,amount);
	
	}


Hashtable<GameCard,Integer> ITable=new Hashtable<>();
public void spawnCards(boolean firstPlayer, int amount) {
	int count=0;
	
	if(amount==0) {
		cardsAreMoving=false;
		return ;
	}
	
	for(int i=0;i<amount;i++) {
	
		GameCard card = cardStack.pop();
		
		
			
		
				ITable.put(card, count);
				
				count++;
			
		
				availableImages.put(card.imageIcon, true);
				cardsOfImages.put(card.imageIcon, card);
				
				if(firstPlayer)
                imageCorners.put(card.imageIcon, new Point(1300,20));
				else 
					 imageCorners.put(card.imageIcon, new Point(-300,680));
				

				
                Timer timer1=new Timer();
        		
        		TimerTask task1=new TimerTask() {
        		
        			int i1=ITable.get(card);
        			int i2=ITable.get(card);
        			
        			int k1=3-(amount-i1-1);
        			int k2=(amount-i2-1);
        			
        			
        			int a=5;
        			int V=0;
        			
        			int x=imageCorners.get(card.imageIcon).x ;
        			@Override
        			public void run() { 
        				// TODO Auto-generated method stub
        				
        				if(firstPlayer) {
        					imageCorners.replace(card.imageIcon, new Point(imageCorners.get(card.imageIcon).x  - a ,imageCorners.get(card.imageIcon).y));
        					
        					V-=a;
        					}
        					else {
        						imageCorners.replace(card.imageIcon, new Point(imageCorners.get(card.imageIcon).x + a ,imageCorners.get(card.imageIcon).y));
        					
        						V+=a;
        					}
        				repaint();

        				if((firstPlayer && Math.abs(V) > Math.abs(x-(spellCardInterval+laneInterval/3 + (k1)*laneInterval))) || (firstPlayer==false && Math.abs(V) > Math.abs(x-(spellCardInterval+laneInterval/3 + (k2)*laneInterval))) ) {
        					
        					
        					if(firstPlayer) {
        						imageCorners.replace(card.imageIcon, new Point(spellCardInterval+laneInterval/3 + (k1)*laneInterval ,imageCorners.get(card.imageIcon).y));
            					
        						card.tableNumber=amount-i2-1;
        						player1Array[amount-i1-1]=card;
        						
        						
        						
        						
        					i1++;
        					
        					if(amount-i1-1==-1) {
        						cardsAreMoving=false;
        						i1=0;
        					}
        					}
        					else {
        						imageCorners.replace(card.imageIcon, new Point(spellCardInterval+laneInterval/3 + (k2)*laneInterval ,imageCorners.get(card.imageIcon).y));
            					
        						card.tableNumber=amount-i2-1 + 4;
        						player2Array[amount-i2-1]=card;
        						
        						
        					i2++;
        					if(amount-i2-1==-1) {
        						cardsAreMoving=false;
        						i2=0;
        					}
        					}
        					
        					repaint();
        					
        					
        					
        				
        					timer1.cancel();
        					
        				
        				}
        				
        			}
        			
        		};
        		timer1.schedule(task1, 100, 7);
		
}
	}
	

	

	


	
	MyPanel(){
		
		int baloonSC= 11;
		int candyArcherSC=11;
		int owlSamuraiSC=3;
		int seedWizardSC=12;
		int bookGeekoSC=11;
		int darkTwinsSC=7;
		int cornDudeSC=7;
		int pigSC=13;
		int firePrincessSC=2;
		int jetMMallowSC=2;
		int poopMenSC=15;
		int gymFishSC=2;
		int lavaPuppySC=4;
		
		
		for(int i=0;i<500;i++) {
			int k=0;
			
			for(int j=0;j<baloonSC;j++) {
			allCards[j]=new Baloon(); 
			
			if(j+1==baloonSC ) k+=j;
			
			}
			
			for(int j=1;j<candyArcherSC+1;j++) {
			allCards[k+j]=new CandyArcher();
			
			if(j==candyArcherSC )k+=j;
			}
			
			for(int j=1;j<owlSamuraiSC+1;j++) {
			allCards[k+j]=new OwlSamurai();
			if(j==owlSamuraiSC )k+=j;
			}
			
			for(int j=1;j<seedWizardSC+1;j++) {
			allCards[k+j]=new SeedWizard(); 
			if(j==seedWizardSC )k+=j;
			}
			
			for(int j=1;j<bookGeekoSC+1;j++) {
			allCards[k+j]=new BookGeeko();
			if(j==bookGeekoSC )k+=j;
			}
			
			for(int j=1;j<darkTwinsSC+1;j++) {
			allCards[k+j]=new DarkTwins();
			if(j==darkTwinsSC )k+=j;
			}
			
			for(int j=1;j<cornDudeSC+1;j++) {
			allCards[k+j]=new CornDude();
			if(j==cornDudeSC )k+=j;
			}
			for(int j=1;j<pigSC+1;j++) {
			allCards[k+j]=new Pig();
			if(j==pigSC )k+=j;
			}
			
			for(int j=1;j<firePrincessSC+1;j++) {
			allCards[k+j]=new FirePrincess();
			if(j==firePrincessSC )k+=j;
			}
			for(int j=1;j<jetMMallowSC+1;j++) {
			allCards[k+j]=new JetMMallow();
			if(j==jetMMallowSC )k+=j;
			}
			
			for(int j=1;j<poopMenSC+1;j++) {
			allCards[k+j]=new PoopMen();
			if(j==poopMenSC )k+=j;
			}
			
			for(int j=1;j<gymFishSC+1;j++) {
			allCards[k+j]=new GymFish();
			if(j==gymFishSC )k+=j;
			}
			
			for(int j=1;j<lavaPuppySC+1;j++) {
			allCards[k+j]=new LavaPuppy();
			if(j==lavaPuppySC ) {
				
				k+=j;
			}
			}
		
			
			cardStack.push(allCards[random.nextInt(100)]);
			
		}



	
	
	arrangeCards(true);
	arrangeCards(false );
	
	

availablePassButtonImages.put(passButton, false);

imageCorners.put(passButton, new Point(20,160));

availablePassButtonImages.put(passButton1, false);

imageCorners.put(passButton1, new Point(900,420));

		



	
	
		
		
	
//		imageCorners[0]=new Point(23,230);
//		imageCorners[1]=new Point(1000- spellCardImage2.getIconWidth()-24, 470);
//		
		
       // prevPoint=imageCorners[0];
		
		//spellCardLabel1.setIcon(spellCardImage1);
	//	spellCardLabel1.setBounds(23, 230, spellCardImage1.getIconWidth(), spellCardImage1.getIconHeight());
		

		//spellCardLabel2.setIcon(spellCardImage2);
		//spellCardLabel2.setBounds(1000- spellCardImage2.getIconWidth()-24, 470, spellCardImage2.getIconWidth(), spellCardImage2.getIconHeight());
		
		
		this.add(spellCardLabel1);
		this.add(spellCardLabel2);
		
		this.setPreferredSize(new Dimension(1000,800));
		this.setLayout(null);
		
		
		
		//this.setBackground(new Color(0,200,75));
		
		MouseListener mouseListener=new MouseListener();
		MouseMotionListener mouseMotionListener=new MouseMotionListener();
		
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseMotionListener);
		
	}
	int middleYLocation;
	int middleXLocation=this.getWidth()/2;
	
	int endXLocation=this.getWidth();
	int endYLocation=this.getHeight();
	
	int cardHeight=99;
	int cardWidth=70;
	
	int areaInterval=250;
	
	int spellCardInterval=120;
	
	int laneInterval=190;
	
	int cardSlotGap=20;
	
	boolean oneTimePass=true;
	
	private int slotNum;
	
	int topY=  middleYLocation-areaInterval;
	
	ImageIcon destroyerCardImage;
	
	@Override
public void paint(Graphics g) {
  
	Graphics g2D=(Graphics2D) g;
	 
	 
//	 if(oneTimePass)
//	
//	
//	 oneTimePass=false;
	 
	 
	middleYLocation=this.getHeight()/2;
	
	middleXLocation=this.getWidth()/2;
	
	endXLocation=this.getWidth();
	endYLocation=this.getHeight();
	
	
	areaInterval=250;
	
	 spellCardInterval=120;
	
	laneInterval=190;
	
	 cardSlotGap=20;
	g2D.setColor(new Color(74,18,35));
	g2D.drawImage(gameBackGroundImage.getImage(), 0, 0, null);
	
	g2D.drawLine(spellCardInterval, middleYLocation+1, endXLocation-spellCardInterval, middleYLocation+1);//middle line
	g2D.drawLine(spellCardInterval, middleYLocation, endXLocation-spellCardInterval, middleYLocation);//middle line
	g2D.drawLine(spellCardInterval, middleYLocation-1, endXLocation-spellCardInterval, middleYLocation-1);//middle line
	
	
	g2D.drawLine(spellCardInterval, middleYLocation-areaInterval+1, endXLocation-spellCardInterval, middleYLocation-areaInterval+1);
	g2D.drawLine(spellCardInterval, middleYLocation-areaInterval, endXLocation-spellCardInterval, middleYLocation-areaInterval);
	g2D.drawLine(spellCardInterval, middleYLocation-areaInterval-1, endXLocation-spellCardInterval, middleYLocation-areaInterval-1);

	
	
	g2D.drawLine(spellCardInterval, middleYLocation+areaInterval+1, endXLocation-spellCardInterval, middleYLocation+areaInterval+1);
	g2D.drawLine(spellCardInterval, middleYLocation+areaInterval, endXLocation-spellCardInterval, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval, middleYLocation+areaInterval-1, endXLocation-spellCardInterval, middleYLocation+areaInterval-1);
	
	
	g2D.drawLine(spellCardInterval+1, middleYLocation-areaInterval, spellCardInterval+1, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval, middleYLocation-areaInterval, spellCardInterval, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval-1, middleYLocation-areaInterval, spellCardInterval-1, middleYLocation+areaInterval);
	
	
//	
//	
//	g2D.drawLine(spellCardInterval, middleYLocation-areaInterval, spellCardInterval, middleYLocation+areaInterval);
//	g2D.drawLine(spellCardInterval, middleYLocation-areaInterval, spellCardInterval, middleYLocation+areaInterval);
//	g2D.drawLine(spellCardInterval, middleYLocation-areaInterval, spellCardInterval, middleYLocation+areaInterval);
	
	
	
	g2D.drawLine(endXLocation-spellCardInterval+1, middleYLocation-areaInterval, endXLocation-spellCardInterval+1, middleYLocation+areaInterval);
	g2D.drawLine(endXLocation-spellCardInterval, middleYLocation-areaInterval, endXLocation-spellCardInterval, middleYLocation+areaInterval);
	g2D.drawLine(endXLocation-spellCardInterval-1, middleYLocation-areaInterval, endXLocation-spellCardInterval-1, middleYLocation+areaInterval);
	
	//System.out.println("yellow: middleYLocation" + middleYLocation+" areaInterval" + areaInterval);
	
	g2D.drawLine(spellCardInterval+laneInterval+1, middleYLocation-areaInterval, spellCardInterval+laneInterval+1, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval+laneInterval, middleYLocation-areaInterval, spellCardInterval+laneInterval, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval+laneInterval-1, middleYLocation-areaInterval, spellCardInterval+laneInterval-1, middleYLocation+areaInterval);
	
	
	g2D.drawLine(spellCardInterval+2*laneInterval+1, middleYLocation-areaInterval, spellCardInterval+2*laneInterval+1, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval+2*laneInterval, middleYLocation-areaInterval, spellCardInterval+2*laneInterval, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval+2*laneInterval-1, middleYLocation-areaInterval, spellCardInterval+2*laneInterval-1, middleYLocation+areaInterval);
	
	g2D.drawLine(spellCardInterval+3*laneInterval+1, middleYLocation-areaInterval, spellCardInterval+3*laneInterval+1, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval+3*laneInterval, middleYLocation-areaInterval, spellCardInterval+3*laneInterval, middleYLocation+areaInterval);
	g2D.drawLine(spellCardInterval+3*laneInterval-1, middleYLocation-areaInterval, spellCardInterval+3*laneInterval-1, middleYLocation+areaInterval);
	
	
	//spell car slots
	
//	g2D.drawRect(spellCardLabel1.getX()-cardSlotGap/2, spellCardLabel1.getY()-cardSlotGap/2, cardWidth+cardSlotGap, cardHeight+cardSlotGap);
//	g2D.drawRect(spellCardLabel2.getX()-cardSlotGap/2, spellCardLabel2.getY()-cardSlotGap/2, cardWidth+cardSlotGap, cardHeight+cardSlotGap);
//
//	//game card slots
//	
//	g2D.drawRect(middleXLocation-cardWidth/2-cardSlotGap/2, 20-cardSlotGap/2, cardWidth+cardSlotGap, cardHeight+cardSlotGap);
//	g2D.drawRect(middleXLocation-cardWidth/2-cardSlotGap/2, 10+665-(20-cardSlotGap/2), cardWidth+cardSlotGap, cardHeight+cardSlotGap);
	
	//for testing
	
//g2D.setColor(Color.red);
//	g2D.fillArc(550, 500, 100, 100, 0, 180);
//	g2D.setColor(Color.white);
//	g2D.fillArc(550, 500, 100, 100, 180, 180);

//	g2D.setColor(Color.green);
//	int [] xValues= {400,450,350};
//	int [] yValues= {200,300,300};
	//g2D.fillPolygon(xValues, yValues, 3);
	
//	g2D.setFont(new Font("Ink Free",Font.BOLD,50));
	//g2D.drawString("FREEZE SPELL!", 320, 480);
	
	
	for(ImageIcon image: availablePassButtonImages.keySet()) {
		
		

		image.paintIcon(this, g2D, (int)imageCorners.get(image).getX(), (int)imageCorners.get(image).getY());
	
}
	
	ArrayList<ImageIcon> list1=new ArrayList<>();
	for(ImageIcon image: availableImages.keySet()) {
	list1.add(image);
	}

	for(ImageIcon image: list1) {
	
	if(image.getIconHeight()==99) continue;
			if(imageCorners.get(image)==null) continue;
			image.paintIcon(this, g2D, (int)imageCorners.get(image).getX(), (int)imageCorners.get(image).getY());
			
			String path=image.getDescription();
			
			int dot=path.indexOf('.');
			
		
			if(path.substring(dot-3, dot).equals("red")==false) {
				
				int x= imageCorners.get(image).x;
				int y= imageCorners.get(image).y;
				
				int height=image.getIconHeight();
				int width=image.getIconWidth();
				
				g2D.setFont(new Font("Ink Free",Font.PLAIN,(width)*30/(190)));
				
				g2D.setColor(new Color(0,150,225));
				//g2D.drawString(cardsOfImages.get(image).combatPower+"", x+30, y+230);
				g2D.drawString(cardsOfImages.get(image).combatPower+"", x+width*30/190, y+height*230/250);
				g2D.setColor(new Color(225,60,60));
				//g2D.drawString(cardsOfImages.get(image).HP+"", x+104, y+230);
				g2D.drawString(cardsOfImages.get(image).HP+"", x+ width*104/190, y+height*230/250);
			}
		
	}
	
	if(destroyerCardImageList.isEmpty()==false) {
		g2D.setColor(Color.red);
		ArrayList<ImageIcon>list=new ArrayList<>();
		for(ImageIcon image:destroyerCardImageList) {
			list.add(image);
			
		}
		
		for(ImageIcon image: list) {
			
			if(imageCorners.get(image)!=null) {
				
				
				if(PTC!=0 && cardsOfImages.get(image).turnNum==-1) 
					cardsOfImages.get(image).turnNum=turnCount;
				
				else if (cardsOfImages.get(image).turnNum==-1)
				cardsOfImages.get(image).turnNum=turnCount-1;
				
				if(turnCount-cardsOfImages.get(image).turnNum<2  ) {
					
				
				
		g2D.drawRect(imageCorners.get(image).x-1, imageCorners.get(image).y-1, image.getIconWidth()+2, image.getIconHeight()+2);
	g2D.drawRect(imageCorners.get(image).x, imageCorners.get(image).y, image.getIconWidth(), image.getIconHeight());
	g2D.drawRect(imageCorners.get(image).x-2, imageCorners.get(image).y-2, image.getIconWidth()+4, image.getIconHeight()+4);
				
				}	
				
	else if(imageCorners.get(image)!=null){
		
			Timer timer1=new Timer();
			
			TimerTask task1=new TimerTask() {
				
				@Override
				public void run(){
					
					if(imageCorners.get(image)!=null) {
			g2D.drawRect(imageCorners.get(image).x - i11, imageCorners.get(image).y - i11, image.getIconWidth()+ i11*2, image.getIconHeight() + i11*2);
			i11++;
			endImage=image;
			repaint();
					}
			else {
				i11=0;
				timer1.cancel();
			}
					
				
				
				if(i11>=1000) {
					timer1.cancel();
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.exit(1);
				}
				}
				
			};
			
			timer1.schedule(task1, 500, 5);
		
		
			
	}
	
			}
		else {
			
			
			destroyerCardImageList.remove(image);
		}
		}
	}
	
	
	for(ImageIcon image:list1) {
	
	if(image.getIconHeight()!=99) continue;
			
			image.paintIcon(this, g2D, (int)imageCorners.get(image).getX(), (int)imageCorners.get(image).getY());
			
			String path=image.getDescription();
			
			int dot=path.indexOf('.');
			
			
		
			if(path.substring(dot-3, dot).equals("red")==false) {
				
				int x= imageCorners.get(image).x;
				int y= imageCorners.get(image).y;
				
				int height=image.getIconHeight();
				int width=image.getIconWidth();
				
				g2D.setFont(new Font("Ink Free",Font.PLAIN,(width)*30/(190)));
				
				g2D.setColor(new Color(0,150,225));
				//g2D.drawString(cardsOfImages.get(image).combatPower+"", x+30, y+230);
				g2D.drawString(cardsOfImages.get(image).combatPower+"", x+width*30/190, y+height*230/250);
				g2D.setColor(new Color(225,60,60));
				//g2D.drawString(cardsOfImages.get(image).HP+"", x+104, y+230);
				g2D.drawString(cardsOfImages.get(image).HP+"", x+ width*104/190, y+height*230/250);
			}
		
	}
	
	for(ImageIcon image: availableButtonImages.keySet()) {
		
		

		image.paintIcon(this, g2D, (int)imageCorners.get(image).getX(), (int)imageCorners.get(image).getY());
	
}
		
	
	for(int i=0;i<hits.size();i++) {
		
		g2D.setColor(new Color(225,60,60));
		g2D.setFont(new Font("Ink Free",Font.BOLD,80));
		
		if(hitCorners.get(hits.get(i))!=null)
		g2D.drawString(hits.get(i), hitCorners.get(hits.get(i)).x,  hitCorners.get(hits.get(i)).y);
	
}
	
	if(theAttackCard!=null) {
		
		enemyCard1=null;
		
		int lane=theAttackCard.slotNum;
	
		if(lane>3) enemyCard1=cardsOfImages.get(board[1][lane-4]);

	ImageIcon image;
	image=theAttackCard.normalAttack(this, board, lane, theAttackCard.imageIcon, availableImages, imageCorners, cardsOfImages,availableButtonImages,hits,hitCorners ,buttonPressed , enemyCardPresent);
	
	if(image!=null)
		destroyerCardImageList.add(image);
	
		repaint();
		theAttackCard=null;
		progressiveTurnChanger();

	}
	
//	if(destroyerCardImageList.isEmpty()==false) {
//		g2D.setColor(Color.red);
//		ArrayList<ImageIcon>list=new ArrayList<>();
//		for(ImageIcon image:destroyerCardImageList) {
//			list.add(image);
//			
//		}
//		
//		for(ImageIcon image: list) {
//			if(imageCorners.get(image)!=null) {
//				
//				
//				if(endingTable.get(cardsOfImages.get(image))==null)
//				endingTable.put(cardsOfImages.get(image), turnOfFirstPlayer);
//				
//				
//				if(endingTable.get(cardsOfImages.get(image))==turnOfFirstPlayer) {
//					
//				
//				
//		g2D.drawRect(imageCorners.get(image).x-1, imageCorners.get(image).y-1, image.getIconWidth()+2, image.getIconHeight()+2);
//	g2D.drawRect(imageCorners.get(image).x, imageCorners.get(image).y, image.getIconWidth(), image.getIconHeight());
//	g2D.drawRect(imageCorners.get(image).x-2, imageCorners.get(image).y-2, image.getIconWidth()+4, image.getIconHeight()+4);
//				
//				}	
//				
//	else {
//		
//		
//			Timer timer1=new Timer();
//			
//			TimerTask task1=new TimerTask() {
//				
//				@Override
//				public void run(){
//			g2D.drawRect(imageCorners.get(image).x - i11, imageCorners.get(image).y - i11, image.getIconWidth()+ i11*2, image.getIconHeight() + i11*2);
//			i11++;
//			endImage=image;
//			repaint();
//			
//				
//				
//				if(i11>=1000)
//					timer1.cancel();
//				}
//				
//			};
//			
//			timer1.schedule(task1, 1000, 1);
//		
//		
//			
//	}
//	
//			}
//		else {
//			if(cardsOfImages.get(image)!=null)
//			endingTable.remove(cardsOfImages.get(image));
//			destroyerCardImageList.remove(image);
//		}
//		}
//	}
	
	if(endImage!=null ) {
		
		for(int i=0;i<i11;i++) {
			 if(imageCorners.get(endImage)!=null)
		g2D.drawRect(imageCorners.get(endImage).x - i, imageCorners.get(endImage).y - i, endImage.getIconWidth()+ i*2, endImage.getIconHeight() + i*2);
		
		}
		if(i11>=100)
			g2D.setFont(new Font("Ink Free",Font.BOLD,80));
		g2D.setColor(Color.black);
			g2D.drawString("GAME OVER!", 260, 350);
	}
	
	
}
	ImageIcon endImage=null;
	int i11=-1;
	int hitCount=0;
	GameCard enemyCard1;
//public void paintComponent(Graphics g) {
//	
//}
	ArrayList<ImageIcon>destroyerCardImageList=new ArrayList<>();
	Hashtable<GameCard,Boolean>endingTable=new Hashtable<>();
class MouseListener extends MouseAdapter{
	
	private boolean isMouseInTheArea(MouseEvent e, Point imageCorner, ImageIcon image){
		
		if(!(e.getX()>=imageCorner.getX() && e.getX()<=imageCorner.getX()+image.getIconWidth() &&
		e.getY()>=imageCorner.getY() && e.getY()<=imageCorner.getY()+image.getIconHeight()                )) return false;
		
		return true;
		
	}
	
private int cardSlot(MouseEvent e, Point imageCorner){
		int topY=150;
		int slotNum=-1;
		
//		if((spellCardInterval<=imageCorner.getX() && spellCardInterval*4*laneInterval<=imageCorner.getX()+spellCardImage1.getIconWidth() &&
//				topY<=imageCorner.getY() && topY+2*areaInterval<=imageCorner.getY()+spellCardImage1.getIconHeight()                ))
//			
		
			if(imageCorner.getY()<middleYLocation && imageCorner.getY()>middleYLocation-areaInterval) {
				if(turnOfFirstPlayer==false) return -1;
				
			if(imageCorner.getX()>=spellCardInterval && imageCorner.getX()+cardWidth<spellCardInterval+laneInterval) slotNum=0;
			else if(imageCorner.getX()>=spellCardInterval && imageCorner.getX()+cardWidth<spellCardInterval+laneInterval*2) slotNum=1;
			else if(imageCorner.getX()>=spellCardInterval && imageCorner.getX()+cardWidth<spellCardInterval+laneInterval*3) slotNum=2;
			else if(imageCorner.getX()>=spellCardInterval && imageCorner.getX()+cardWidth<spellCardInterval+laneInterval*4) slotNum=3;
				
			}
			else if(imageCorner.getY()>middleYLocation && imageCorner.getY()<middleYLocation+areaInterval) {
				if(turnOfFirstPlayer==true) return -1;
				
				if(imageCorner.getX()>=spellCardInterval && imageCorner.getX()+cardWidth<spellCardInterval+laneInterval) slotNum=4;
				else if(imageCorner.getX()>=spellCardInterval && imageCorner.getX()+cardWidth<spellCardInterval+laneInterval*2) slotNum=5;
				else if(imageCorner.getX()>=spellCardInterval && imageCorner.getX()+cardWidth<spellCardInterval+laneInterval*3) slotNum=6;
				else if(imageCorner.getX()>=spellCardInterval && imageCorner.getX()+cardWidth<spellCardInterval+laneInterval*4) slotNum=7;
					
				
			}
		
		return slotNum;
		
	}
	
	
	public void mousePressed(MouseEvent e) {
		
//		if(cardsAreMoving) return;
		
		ArrayList<ImageIcon>list2=new ArrayList<>();
		for(ImageIcon image: availablePassButtonImages.keySet()) {
			list2.add(image);
		}
		
		for(ImageIcon image: list2) {
			
			if(isMouseInTheArea(e,imageCorners.get(image),image)) {
			
				if(String.valueOf(image).equals("CardGameAssets/passButton.png") && turnOfFirstPlayer==true) {
					availablePassButtonImages.remove(image);
					imageCorners.put(pressedPassButton, imageCorners.get(image));
				availablePassButtonImages.put(pressedPassButton, true);
				
				
				}
				
				else if(String.valueOf(image).equals("CardGameAssets/passButton1.png") && turnOfFirstPlayer==false) {
					availablePassButtonImages.remove(image);
					
					imageCorners.put(pressedPassButton1, imageCorners.get(image));
					availablePassButtonImages.put(pressedPassButton1, true);
					
					}
			}
			repaint();
		}
		
		pressedPass=false;
	
//if(availableButtonImages.isEmpty()==false) {
	ArrayList<ImageIcon>imageList=new ArrayList<>();
	
		for(ImageIcon image: availableButtonImages.keySet()) {
		imageList.add(image);
		
		}
		
			for(ImageIcon image: imageList) {
				
				
			if(isMouseInTheArea(e,imageCorners.get(image),image)) {
					
				availableButtonImages.remove(image);
			
				//System.out.println(image);
				
				if(String.valueOf(image).equals("CardGameAssets/attackButton.png") && ((cardsOfImages.get(currentImage).slotNum < 4 && enemyCardPresent[1][cardsOfImages.get(currentImage).slotNum]==false )|| ( cardsOfImages.get(currentImage).slotNum >= 4 && enemyCardPresent[0][cardsOfImages.get(currentImage).slotNum-4]==false ))) {
					
					
					//cardButtonPressed[0]=true;
					
					availableButtonImages.put(pressedAttackButtonImage, true);
				
					
					int x=imageCorners.get(image).x;
					int y=imageCorners.get(image).y;
					imageCorners.put(pressedAttackButtonImage,  imageCorners.get(attackButtonImage));// only once would've been enough
					
					
				}
				else if(String.valueOf(image).equals("CardGameAssets/rightButton.png")  &&  ((((cardsOfImages.get(currentImage).slotNum != 3 && cardsOfImages.get(currentImage).slotNum != 7 )) && ( (cardsOfImages.get(currentImage).slotNum < 3 && enemyCardPresent[0][cardsOfImages.get(currentImage).slotNum+1]==false && board[0][cardsOfImages.get(currentImage).slotNum+1]!=null ) || ( cardsOfImages.get(currentImage).slotNum > 3  && enemyCardPresent[1][cardsOfImages.get(currentImage).slotNum-4 +1]==false && board[1][cardsOfImages.get(currentImage).slotNum-4 +1]!=null ))==false ))) {
					
					
					//cardButtonPressed[1]=true;
					
					availableButtonImages.put(pressedRightButtonImage, true);
				
					int x=imageCorners.get(image).x;
					int y=imageCorners.get(image).y;
					imageCorners.put(pressedRightButtonImage, imageCorners.get(rightButtonImage));// only once would've been enough
					
				}
				
				else if(String.valueOf(image).equals("CardGameAssets/leftButton.png")  &&  ((((cardsOfImages.get(currentImage).slotNum != 0 && cardsOfImages.get(currentImage).slotNum != 4 )) && ( (cardsOfImages.get(currentImage).slotNum < 4 && enemyCardPresent[0][cardsOfImages.get(currentImage).slotNum-1]==false && board[0][cardsOfImages.get(currentImage).slotNum-1]!=null ) || ( cardsOfImages.get(currentImage).slotNum > 3  && enemyCardPresent[1][cardsOfImages.get(currentImage).slotNum-4 -1]==false && board[1][cardsOfImages.get(currentImage).slotNum-4 -1]!=null ))==false ))) {
					
					
					//cardButtonPressed[2]=true;
					
					availableButtonImages.put(pressedLeftButtonImage, true);
				
					int x=imageCorners.get(image).x;
					int y=imageCorners.get(image).y;
					imageCorners.put(pressedLeftButtonImage,imageCorners.get(leftButtonImage));// only once would've been enough
					
				}
				
				
				
				
				
				repaint();
			}
		}
		
	//}
	
		for(ImageIcon image: availableImages.keySet()) {
		
			
			if(isMouseInTheArea(e,imageCorners.get(image),image)) {
				
				int slotNum1= cardsOfImages.get(image).slotNum;
				if(( slotNum1<4 &&   slotNum1 >=0 && enemyCardPresent[0][slotNum1]==true  )|| (slotNum1 >3 &&  slotNum1-4 >=0 && enemyCardPresent[1][slotNum1-4]==true  ) ) return;
				
				 currentImage=image;
				 
				 boolean onTheBoard=false;
				 
				 for(ImageIcon[] cardimage2: board) {
				//	 System.out.println("loop");
					 for(ImageIcon cardImage1: cardimage2) {
						 if(cardImage1==null) continue;
//						 System.out.println(cardImage1+"\t cardImage1");
//						 System.out.println(currentImage+"\t currentImage");
						if( cardImage1==currentImage) {
							onTheBoard=true;
							
							
						}
					 }
				 }
				
				 
					if(availableImages.get(currentImage)==false && onTheBoard==false) {
						//System.out.println("first");
						return; //if image can't be clicked 
					}
					
					if(turnOfFirstPlayer==true && imageCorners.get(currentImage).getY()>middleYLocation) {
					//	System.out.println("second");
						return; //if image can't be clicked 
					}
					else if(turnOfFirstPlayer==false && imageCorners.get(currentImage).getY()<middleYLocation){
						//System.out.println("third");
						return; //if image can't be clicked 
					}
				 
		prevPoint=e.getPoint();
	
		if(imageCorners.get(image).getX()==spellCardInterval+laneInterval/3 && imageCorners.get(image).getY()==20)
			 basePoint=new Point(imageCorners.get(image));
		
		
		else if(imageCorners.get(image).getX()==spellCardInterval+laneInterval/3 && imageCorners.get(image).getY()==680)
			 basePoint=new Point(imageCorners.get(image));
		

		else if(imageCorners.get(image).getX()==spellCardInterval+laneInterval/3 + laneInterval && imageCorners.get(image).getY()==20)
			 basePoint=new Point(imageCorners.get(image));
		

		else if(imageCorners.get(image).getX()==spellCardInterval+laneInterval/3 + laneInterval && imageCorners.get(image).getY()==680)
	        basePoint=new Point(imageCorners.get(image));
		

		else if(imageCorners.get(image).getX()==spellCardInterval+laneInterval/3 + laneInterval*2 && imageCorners.get(image).getY()==20)
			 basePoint=new Point(imageCorners.get(image));
		

		else if(imageCorners.get(image).getX()==spellCardInterval+laneInterval/3 + laneInterval*2 && imageCorners.get(image).getY()==680)
			 basePoint=new Point(imageCorners.get(image));
		

		else if(imageCorners.get(image).getX()==spellCardInterval+laneInterval/3 + laneInterval*3 && imageCorners.get(image).getY()==20)
			 basePoint=new Point(imageCorners.get(image));
		

		else if(imageCorners.get(image).getX()==spellCardInterval+laneInterval/3 + laneInterval*3 && imageCorners.get(image).getY()==680)
			 basePoint=new Point(imageCorners.get(image));
		
		
		
		
		
		if(onTheBoard==false) {
			pressedPass=true;
			//System.out.println("onTheBoard==false");
		}
		
		else {
			
			repaint();
			AttackOrUseAbilitiy(imageCorners.get(currentImage));
		}
			}
	}
	}
	
	
private void AttackOrUseAbilitiy(Point imageCorner) {
		// TODO Auto-generated method stub
	buttonPass=true;
if(availableButtonImages.get(pressedAttackButtonImage)==null && availableButtonImages.get(pressedUseAbilityButtonImage)==null) {
	
	
	
	int x=(int)imageCorner.getX();
	int y=(int)imageCorner.getY();
	
	
	availableButtonImages.put(attackButtonImage, false);
	imageCorners.put(attackButtonImage, new Point(x+currentImage.getIconWidth()/2- attackButtonImage.getIconWidth()/2,y+currentImage.getIconHeight()/2-attackButtonImage.getIconHeight()/2+25));
	
//	availableButtonImages.put(useAbilityButtonImage, false);
//	imageCorners.put(useAbilityButtonImage, new Point(x+card.width/2-24,y+card.height/2+100));
	
	availableButtonImages.put(rightButtonImage, false);
	imageCorners.put(rightButtonImage, new Point(x+currentImage.getIconWidth()-rightButtonImage.getIconWidth()-5,y+currentImage.getIconWidth()/2));
	
	availableButtonImages.put(leftButtonImage, false);
	imageCorners.put(leftButtonImage, new Point(x+5,y+currentImage.getIconWidth()/2));
	
	
	
	
	repaint();
	
}

else {// call the attack or useAbility method
	
}
	}

public void mouseReleased(MouseEvent e) {
	
	ArrayList<ImageIcon>list2=new ArrayList<>();
	for(ImageIcon image: availablePassButtonImages.keySet()) {
		list2.add(image);
	}
	
	for(ImageIcon image: list2) {
		
		
		if(isMouseInTheArea(e,imageCorners.get(image),image) && availablePassButtonImages.get(image)) {
			availablePassButtonImages.remove(image);
			if(String.valueOf(image).equals("CardGameAssets/pressedPassButton.png")) {
				
			availablePassButtonImages.put(passButton, false);
			
			
			
			
			
			turnChanger();
			}
			
			else if(String.valueOf(image).equals("CardGameAssets/pressedPassButton1.png")) {
				availablePassButtonImages.put(passButton1, false);
				
				
				
				turnChanger();
				
				}
		}
		repaint();
	}
	
	
	if(buttonPass==true) {
		
		//if(availableButtonImages.isEmpty()==false) {
			ArrayList<ImageIcon>imageList=new ArrayList<>();
			
				for(ImageIcon image: availableButtonImages.keySet()) {
				imageList.add(image);
				
				}
				
					for(ImageIcon image: imageList) {
						
			
			if(availableButtonImages.get(image)==true) {
				availableButtonImages.remove(image);
				//System.out.println(image);
				
				if(String.valueOf(image).equals("CardGameAssets/attackButton1.png") ) {
					 //cardButtonPressed[0]=false;
					availableButtonImages.put(attackButtonImage, false);
				
					for(ImageIcon image1: availableImages.keySet()) {
						
						if(isMouseInTheArea(e,imageCorners.get(image1),image1)) {
							buttonPressed[0]=true;
						//	System.out.println(cardsOfImages.get(image1)+" cardsOfImages.get(image1)");
						startAction(image1);  
						//System.out.println(theAttackCard+" theAttackCard");
						}
					}
					
				
					
				}
				else if(String.valueOf(image).equals("CardGameAssets/pressedLeftButton.png")) {
			
					 //cardButtonPressed[2]=false;
					availableButtonImages.put(leftButtonImage, false);
				
	for(ImageIcon image1: availableImages.keySet()) {
						
						if(isMouseInTheArea(e,imageCorners.get(image1),image1)) {
							buttonPressed[2]=true;
						//	System.out.println(cardsOfImages.get(image1)+" cardsOfImages.get(image1)");
							 startAction( image1);  
						//System.out.println(theAttackCard+" theAttackCard");
						}
					}
					
				
					
				}
				
				else if(String.valueOf(image).equals("CardGameAssets/pressedRightButton.png")) {
					
					 //cardButtonPressed[1]=false;
					availableButtonImages.put(rightButtonImage, false);
				
	for(ImageIcon image1: availableImages.keySet()) {
						
						if(isMouseInTheArea(e,imageCorners.get(image1),image1)) {
							buttonPressed[1]=true;
						//	System.out.println(cardsOfImages.get(image1)+" cardsOfImages.get(image1)");
							 startAction( image1);      
						//System.out.println(theAttackCard+" theAttackCard");
						}
					}
					
				
					
				}
				
			

				
			}
	}
	
	repaint();

	
	
	buttonPass=false;
	}
		
	//}
	
if(pressedPass==false) return;
	
	Point currentPoint=e.getPoint();
	
	int slotNum=cardSlot(e, imageCorners.get(currentImage));
	
	
	
	//System.out.println(slotNum+" slotNum");
	if(  slotNum!=-1    &&
			           
	   ( (slotNum>3 && (board[1][slotNum-4]==null ||  (enemyCardPresent[1][slotNum-4]==true)) )   ||  (slotNum<4 && ( board[0][slotNum]==null ) || ( enemyCardPresent[0][slotNum]==true ))) &&
			 
       ( (( (slotNum>3 && enemyCardPresent[1][slotNum-4]==true ) && cardsOfImages.get(board[1][slotNum-4]).HP - cardsOfImages.get(currentImage).combatPower > 0 )   ||  ( (slotNum<4 && enemyCardPresent[0][slotNum]==true )&& cardsOfImages.get(board[0][slotNum]).HP - cardsOfImages.get(currentImage).combatPower >  0 ) ) == false  )
			
        //there is a PROBLEM HERE!
       
			) { // sits on the board
		
		
		if( ((slotNum>3 && enemyCardPresent[1][slotNum-4]==true) )) {
			enemyCardPresent[1][slotNum-4]=false;
			ImageIcon image = board[1][slotNum-4];
			board[1][slotNum-4]=null;
			availableImages.remove(image);
			imageCorners.remove(image);
			cardsOfImages.remove(image);
			
			
			
				
	}
				if(  (slotNum<4 && enemyCardPresent[0][slotNum]==true )) {
			
					enemyCardPresent[0][slotNum]=false;
					ImageIcon image = board[0][slotNum];
					board[0][slotNum]=null;
					availableImages.remove(image);
					imageCorners.remove(image);
					cardsOfImages.remove(image);
					
					
			
		}

		
		CardImplementetation(slotNum);
		
		
	}
	
	else {
			
		
		imageCorners.get(currentImage).setLocation(basePoint);
		
		
		
			prevPoint=imageCorners.get(currentImage);
			
			repaint();
			
		}
					
	}

private void startAction(ImageIcon image) {
	
	theAttackCard=cardsOfImages.get(image);//starts the whole attacking process 
	theAttackCard.imageIcon=image;      
}

boolean CIPass=false;
private void CardImplementetation(int slotNum1) {
	
	
	
	GameCard card=cardsOfImages.get(currentImage);
	
	//System.out.println(card.tableNumber);
	if(card.tableNumber<4) {
	
		player1Array[card.tableNumber]=null;
	}
	else {
		player2Array[card.tableNumber-4]=null;
	}
	
	slotNum=slotNum1;
	ImageIcon image=currentImage;
	 newImage=currentImage;
	 count=0;
	
	 int wantedX= spellCardInterval+190/2-cardWidth/2;
		int wantedY= (middleYLocation-areaInterval) +280/2-cardHeight/2 -10;
		
		CIPass=false;
		
		if(slotNum>3) {
			slotNum-=4 ;
			wantedY+=areaInterval;
			
			CIPass=true;
			
		}
		
		
		
		
		wantedX+=slotNum*laneInterval;
		
		
		imageCorners.get(newImage).setLocation(new Point(wantedX, wantedY));
		
		prevPoint=imageCorners.get(currentImage);
	 
	Timer timer=new Timer();
	
	TimerTask task=new TimerTask() {
		 public void run(){
		
			 count++;
			 
			 int x=(int)imageCorners.get(newImage).getX() -12; //12
			 int y=(int)imageCorners.get(newImage).getY() -16;  // 16
			 
			 availableImages.remove(newImage);
			 imageCorners.remove(newImage);
			 
			 String url=image.toString();
			 cardsOfImages.remove(newImage);
			 
			 int indexOfDot=url.indexOf('.');
			 url=url.substring(0,indexOfDot)+(count+1)+".png";
			  newImage=new ImageIcon(url);
			  
			  availableImages.put(newImage, false);
			  
			cardsOfImages.put(newImage, card);
			 
			 imageCorners.put(newImage,new Point(x,y));
			 
			 repaint();
			 
			 if(count>=5) {
				 currentImage=newImage;
				 
					
					if(CIPass) {
						
//						wantedY+=areaInterval;
						
						board[1][slotNum]=currentImage;
						cardsOfImages.get(currentImage).slotNum=4+slotNum;
					}
					
					else {
						
						board[0][slotNum]=currentImage;
						
						cardsOfImages.get(currentImage).slotNum=slotNum;
					}
						
					
					
					
//					wantedX+=slotNum*laneInterval;
					
					
//					imageCorners.get(currentImage).setLocation(new Point(wantedX, wantedY));
					
				
					
					availableImages.put(currentImage, false); // it's stable
					
		//			board[0][0]=currentImage;
					
					
					
					
					
					
					repaint();
				 
				 timer.cancel();
				 
				 
				 
//				 cardsOfImages.get(newImage).entranceAction(board, 1);
				 
				 
				// repaint();
				 
			 }
		}
	};
	timer.schedule(task, 5, 10);
	
	
		
		progressiveTurnChanger();
}
	
}

class MouseMotionListener extends MouseMotionAdapter{

	public void mouseDragged(MouseEvent e) {
		
	
				
		
		
		if(pressedPass==false) return;
		
		Point currentPoint=e.getPoint();
		
		imageCorners.get(currentImage).translate(
				
				(int)(currentPoint.getX()-prevPoint.getX())
			,	(int)(currentPoint.getY()-prevPoint.getY())
				);
		prevPoint=currentPoint;
		
		repaint();
		
	}
	
}
	
public void turnChanger() {

	if(turnOfFirstPlayer==true) {
		turnOfFirstPlayer=false;
		PTC=0;
		turnCount++;
		
		repaint();
		arrangeCards(true);
		
		
		
		
	}
	
	
	
	else{
		

		turnOfFirstPlayer=true;
		PTC=0;
		turnCount++;
		
		repaint();
		arrangeCards(false);
		
		
	}
	
	
}
public void progressiveTurnChanger() {

	PTC++;
	
	repaint();
	

	
	if(PTC>=4) {
		

		
		turnChanger();
	
		
	}
	}
boolean cardsAreMoving=false;
boolean switch1=false;
boolean switch2=false;
boolean endGame=false;
int PTC=0;
int turnCount=0;
}

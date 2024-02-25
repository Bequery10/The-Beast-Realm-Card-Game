

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public abstract class GameCard {

	int turnNum=-1;
	
	int shiftingNumber=-1;
	
	int tableNumber=-1;
	
	boolean buttonsOn=false;
	
	int slotNum=-1;
	
	
	int count=0;
	int changer=1;
	
	//game attributes ---------------------------
	
	int combatPower=0;
	int HP=0;
	
	
	//
	
	private String fullClassName=String.valueOf(getClass());
	
	public String name="";
	
	int indexOfDot=fullClassName.indexOf('.');
	

	protected String url="";
	
	 ImageIcon imageIcon;
	
	 int height;
		int width;
		

	GameCard( ){
		
	name=fullClassName.substring(indexOfDot+1);
	
	imageIcon=new ImageIcon(url);
	
	height=imageIcon.getIconHeight();
	width=imageIcon.getIconWidth();
	}
	
	
	
	ImageIcon image;
	
	ImageIcon enemyImage;
	Point enemyPoint;
	ImageIcon redEnemyImage;
	
	private boolean firstRow;
	private boolean attackPass;
	private int attackCount=0;
	private int add=1;
	TimerTask task1=null;
	TimerTask task2=null;
	TimerTask task6=null;
	
	private int x;
	private int y;
	
	private int column1;
	private int row1;
	
	boolean border=false;
	
	public ImageIcon normalAttack(MyPanel panel,ImageIcon [][] matrix,int lane,ImageIcon image, Hashtable<ImageIcon,Boolean> availableImages,Hashtable<ImageIcon,Point> imageCorners, Hashtable<ImageIcon,GameCard> cardsOfImages,Hashtable<ImageIcon,Boolean> availableButtonImages,ArrayList<String> hits,Hashtable<String,Point> hitCorners, boolean[] buttonPressed , boolean[][] enemyCardPresent) {
		this.image=image;
		Timer timer4=new Timer();
		TimerTask task4=new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				availableButtonImages.clear();
				panel.repaint();
				timer4.cancel();
			}
			
		};
		timer4.schedule(task4, 100);
		
		attackPass=true;
		attackCount=0;
		
		int row=0;
		int column=0;
		
		firstRow=true;
		if(lane>3) {
			lane-=4;
			row=1;
			column=lane;
			firstRow=false;
		}
		else {
			column=lane;
		}
		
		column1=column;
		row1=row;
		 x=imageCorners.get(image).x;
		 y=imageCorners.get(image).y;
		
		 
		
		if(buttonPressed[0] &&( (firstRow==true && matrix[1][column]!=null ) || (firstRow==false && matrix[0][column]!=null ) )) {
			if(firstRow)
		enemyImage= matrix[1][column];
			else
				enemyImage= matrix[0][column];
			
		enemyPoint=imageCorners.get(enemyImage);
		
		String path=String.valueOf(enemyImage);
		int indexOfDot=path.indexOf('.');
		path=path.substring(0,indexOfDot)+"red"+".png";
		
		redEnemyImage=new ImageIcon(path);
		
	}
		
		
		else if( buttonPressed[1] && ((firstRow==true && matrix[0][column+1]!=null ) || (firstRow==false && matrix[1][column+1]!=null )) ) {//right
			
			if(firstRow)
				enemyImage= matrix[0][column+1];
					else
						enemyImage= matrix[1][column+1];
					
				enemyPoint=imageCorners.get(enemyImage);
				
				String path=String.valueOf(enemyImage);
				int indexOfDot=path.indexOf('.');
				path=path.substring(0,indexOfDot)+"red"+".png";
				
				redEnemyImage=new ImageIcon(path);
		}
		
		else if(buttonPressed[2] && ((firstRow==true && matrix[0][column-1]!=null ) || (firstRow==false && matrix[1][column-1]!=null )) ) {//left
			
			if(firstRow)
				enemyImage= matrix[0][column-1];
					else
						enemyImage= matrix[1][column-1];
					
				enemyPoint=imageCorners.get(enemyImage);
				
				String path=String.valueOf(enemyImage);
				int indexOfDot=path.indexOf('.');
				path=path.substring(0,indexOfDot)+"red"+".png";
				
				redEnemyImage=new ImageIcon(path);
		}
		if(firstRow==false) {
			
			add*=Math.abs(add)*-1;
		}
		
		if( buttonPressed[0]==true && (  (firstRow==true && matrix[1][column]!=null  && enemyCardPresent[1][column]==false) || (firstRow==false && matrix[0][column]!=null && enemyCardPresent[0][column]==false) ) ) {
			buttonPressed[0]=false;
			
			border=true;
			
		Timer timer=new Timer();
		Timer timer1=new Timer();
		Timer timer2=new Timer();
		
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				attackCount-=add*2;
				
				imageCorners.put(image, new Point(x,y+attackCount));
				panel.repaint();
			
				if(attackCount>=100 || attackCount<=-100) {
					timer1.schedule(task1, 10,1);
					timer.cancel();
				}
				
			}
		};
		timer.schedule(task, 300,10);
		
	
		 task1=new TimerTask() {
			
			@Override
			public void run() {
				attackCount+=add*4;
				imageCorners.put(image, new Point(x,y+attackCount));
				panel.repaint();
				
				
				
				if((firstRow==true && imageCorners.get(image).getY()>265)||(firstRow==false && imageCorners.get(image).getY()<265)) {
					availableImages.remove(enemyImage);
					availableImages.put(redEnemyImage, false);
					imageCorners.put(redEnemyImage, enemyPoint);
					
					
					timer1.cancel();
					timer2.schedule(task2, 100,5);
					panel.repaint();
		            
		            
		            
				}
				
		
			}
		
		};
		
		task2=new TimerTask() {
			 boolean dead=false;
			

			@Override
			public void run() {
				
		
					
				
				attackCount-=add*2;
				
				
				imageCorners.put(image, new Point(x,y+attackCount));
				panel.repaint();

				if(imageCorners.get(image).getY()==y) {
					
					timer2.cancel();
					panel.repaint();
		            
		            
		            dead=deathCheck(cardsOfImages.get(enemyImage));
		           
		          if(dead==true) {
		        	 
		        	  timerA.schedule(taskA, 800,7);//3
		          }
		            
		          else {
		        	  attackCount=0;
			            add=1;
		          }
		            
				}
				
			
				
				if(attackPass==true) {
					
					 cardsOfImages.get(enemyImage).HP    -=		cardsOfImages.get(image).combatPower;
					 
					 availableImages.put(enemyImage,false);
						availableImages.remove(redEnemyImage);
						imageCorners.remove(redEnemyImage);
					 
					 dead=deathCheck(cardsOfImages.get(enemyImage));
			            
			          if(dead==true) {
			        	  availableImages.remove(enemyImage);
			        	  panel.repaint();
			  
			        	 
			          }
					 
					
					
			 
					
			  
			  timer5.schedule(task5, 0, 50);
					
					attackPass=false;
				}
				
			}

			
		};
		

		 timerA=new Timer();
		
		 taskA=new TimerTask() {
int num=1;

			@Override
			public void run() {
				
				attackCount+=add*10;
				
				num*=-1;
				imageCorners.replace(image, new Point(x+num,y+attackCount));
				panel.repaint();
				
				if(imageCorners.get(image).y==imageCorners.get(enemyImage).y) {
					imageCorners.replace(image, new Point(x,y+attackCount));
					attackCount=0;
					add=1;
					timerA.cancel();
					imageCorners.remove(enemyImage);
					
					
					if(firstRow==true) {
						 cardsOfImages.get(image).slotNum+=4;
						 matrix[0][column1]=null;
						matrix[1][column1]=image;
						enemyCardPresent[1][column1]=true;
						
					}
					else {
						 cardsOfImages.get(image).slotNum-=4;
						 matrix[1][column1]=null;
						matrix[0][column1]=image;
						enemyCardPresent[0][column1]=true;
					}
					
				}
				
			}
			
		};
		
		
		

		
		String hit="-"+this.combatPower;
		
		
		
		
		
		 timer5=new Timer();
		 task5=new TimerTask() {

			@Override
			public void run() {
//if(hitPass==false) return;

				hitCount++;

				int x =enemyPoint.x-hitCount;
				int y =enemyPoint.y-hitCount;
				
				
				hits.add(hit);
				hitCorners.put(hit, new Point(x,y));
				panel.repaint();
				
				
				if(hitCount==15) {
					hitPass=false;
					hits.remove(hit);
					hitCorners.remove(hit);
					timer5.cancel();
					panel.repaint();
					hitCount=0;
					
				}
			}
			
		};
		
		
	
		
		
		}
		
		else if( buttonPressed[0]==true && (  (firstRow==true && matrix[1][column]==null ) || (firstRow==false && matrix[0][column]==null ) ) ) {
		
			buttonPressed[0]=false;
			
			 timerA=new Timer();
				
			 taskA=new TimerTask() {
	int num=1;
	int y1=imageCorners.get(image).y;
				@Override
				public void run() {
					
					attackCount+=add*10;
					
					num*=-1;
					imageCorners.replace(image, new Point(x+num,y+attackCount));
					panel.repaint();
					
					if((firstRow == true && imageCorners.get(image).y>=y1+image.getIconHeight() )||( (firstRow==false) &&  imageCorners.get(image).y<=y1-image.getIconHeight() )) {
						imageCorners.replace(image, new Point(x,y+attackCount));
						attackCount=0;
						add=1;
						timerA.cancel();
						
						
						matrix[row1][column1]=null;
						
						if(firstRow==true) {
							 cardsOfImages.get(image).slotNum+=4;
							 matrix[0][column1]=null;
							matrix[1][column1]=image;
							enemyCardPresent[1][column1]=true;
						}
						else {
							 cardsOfImages.get(image).slotNum-=4;
							 matrix[1][column1]=null;
							matrix[0][column1]=image;
							enemyCardPresent[0][column1]=true;
						}
						
						
					}
					
				}
				
			};
			timerA.schedule(taskA, 10, 7);//4
			
			return image;
		}
		
		else if( column <3 && buttonPressed[1]==true && (  (firstRow==true && matrix[0][column+1]==null ) || (firstRow==false && matrix[1][column+1]==null ) ) ) { //right & emptySlot
			
			buttonPressed[1]=false;
			
			
			 if(firstRow==true) add*=1;
			 else if (firstRow==false)add*=-1;
			
			 timerA=new Timer();
			 
			 
			 
			 taskA=new TimerTask() {
			
				 
				
				 
int x1=imageCorners.get(image).x;
				@Override
				public void run() {
//					 if(firstRow==true && add1<0) add1*=1;
//					 else if (firstRow==false  && add1<0)add1=-1;
					 
					attackCount+=add*10;
					
					
					imageCorners.replace(image, new Point(x+attackCount,y));
					panel.repaint();
					
					if(imageCorners.get(image).x>=x1+image.getIconWidth()) {
						imageCorners.replace(image, new Point(x1+image.getIconWidth(),y));
						attackCount=0;
						add=1;
						timerA.cancel();
						
					
						
                       if(firstRow==true) {
                    	   
                    	   cardsOfImages.get(image).slotNum+=1;
                    	   
                    	   matrix[0][column1]=null;
                    	   
                    	   matrix[0][column1+1]=image;
                    	   
                    	   if(enemyCardPresent[0][column1]) {
                    		   enemyCardPresent[0][column1]=false;
                    		   enemyCardPresent[0][column1+1]=true;
                    	   }
                    	   
                       }
			        	  
			        	  else {
			        		  
			        		  cardsOfImages.get(image).slotNum+=1;
			        		  
			        		  matrix[1][column1]=null;
			        		  
			        		  matrix[1][column1+1]=image;
			        		  
			        		  if(enemyCardPresent[1][column1]) {
	                    		   enemyCardPresent[1][column1]=false;
	                    		   enemyCardPresent[1][column1+1]=true;
	                    	   }
			        		  
			        	  }
                       
                      
						
					}
					
				}
				
			};
			timerA.schedule(taskA, 10, 7);//1
			
		}
		
		
		 if( column <3 && buttonPressed[1]==true && (  (firstRow==true && matrix[0][column+1]!=null && enemyCardPresent[0][column+1]==true ) || (firstRow==false && matrix[1][column+1]!=null  && enemyCardPresent[1][column+1]==true) ) ) { //right & emptySlot
		
			buttonPressed[1]=false;
			
			
			 if(firstRow==true) add*=1;
			 else if (firstRow==false)add*=-1;
			
		
			 
			 timerC=new Timer();
			 
			 
			 
			 taskC=new TimerTask() {
			
			boolean dead=false;	 
				
				 boolean pass=false;
int x1=imageCorners.get(image).x;
				@Override
				public void run() {
								 
					attackCount+=add*10;
					
					
					imageCorners.replace(image, new Point(x+attackCount,y));
					panel.repaint();
					
					
					if(imageCorners.get(image).x<=x1 && pass) {
						imageCorners.replace(image, new Point(x,y));
						availableImages.remove(redEnemyImage);
						imageCorners.remove(redEnemyImage);
						
						availableImages.put(enemyImage, false);
						timerC.cancel();
						
						add*=-1;
					}
					
					if(imageCorners.get(image).x>=x1+image.getIconWidth()/2) {
						
						
						
						timer5.schedule(task5, 0,50);

						
						
						if(attackPass==true) {
							
							 cardsOfImages.get(enemyImage).HP    -=		cardsOfImages.get(image).combatPower;
							 
							
							 
							 availableImages.remove(enemyImage);
								availableImages.put(redEnemyImage,false);
								imageCorners.put(redEnemyImage,enemyPoint);
								panel.repaint();
							 
							 dead=deathCheck(cardsOfImages.get(enemyImage));
					            
					          if(dead==true) {
					        	 
					        	
					        	  timerC.cancel();  
					        	  
					        	  imageCorners.remove(enemyImage);
					        	 
					        	  
					        	  
					        	  imageCorners.remove(redEnemyImage);
					        	  availableImages.remove( redEnemyImage);
					        	  
					        	  imageCorners.replace(image, enemyPoint);
					        	  
					        	  panel.repaint();
					  
					        	  cardsOfImages.get(image).slotNum+=1;
					        	  
					        	  if(firstRow==true) {
					        		  
					        		  matrix[0][column1]=null;

					        		  matrix[0][column1+1]=image;
					        		  enemyCardPresent[0][column1+1]=false;
					        	  }
					        	  
					        	  else {
					        		  matrix[1][column1]=null;
					        		  
					        		  matrix[1][column1+1]=image;	
					        		  enemyCardPresent[1][column1+1]=false;
					        	  }
					          }
					          
					          else {
					        	  add*=-1;
					          }
							 
							
							
					 
							
					  
					
							
							attackPass=false;
						}
						
						
						
						pass=true;
					}
					
					
				}
				
			};
			
			timerC.schedule(taskC, 10, 7);
			
			
			
			String hit="-"+this.combatPower;
			
			
			
			
			
			 timer5=new Timer();
			 task5=new TimerTask() {

				@Override
				public void run() {
	//if(hitPass==false) return;

					hitCount++;

					int x =enemyPoint.x-hitCount;
					int y =enemyPoint.y-hitCount;
					
					
					hits.add(hit);
					hitCorners.put(hit, new Point(x,y));
					panel.repaint();
					
					
					if(hitCount==15) {
						hitPass=false;
						hits.remove(hit);
						hitCorners.remove(hit);
						timer5.cancel();
						panel.repaint();
						hitCount=0;
						
					}
				}
				
			};
			
			timerB=new Timer();
			
			taskB1=new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
				
			};
			
		}
		
		else if( column >0 && buttonPressed[2]==true && (  (firstRow==true && matrix[0][column-1]==null ) || (firstRow==false && matrix[1][column-1]==null ) ) ) { //left & emptySlot
			
			
			buttonPressed[2]=false;
			
			
			 if(firstRow==true) add*=-1;
			 else if (firstRow==false)add*=1;
			
			 timerA=new Timer();
			 
			 
			 
			 taskA=new TimerTask() {
			
				 
				
				 
int x1=imageCorners.get(image).x;
				@Override
				public void run() {
//					 if(firstRow==true && add1<0) add1*=1;
//					 else if (firstRow==false  && add1<0)add1=-1;
					 
					attackCount+=add*10;
					
					
					imageCorners.replace(image, new Point(x+attackCount,y));
					panel.repaint();
					
					if(imageCorners.get(image).x<=x1-image.getIconWidth()) {
						imageCorners.replace(image, new Point(x1-image.getIconWidth(),y));
						attackCount=0;
						add=1;
						timerA.cancel();
						
					
						
                       if(firstRow==true) {
                    	   
                    	   cardsOfImages.get(image).slotNum-=1;
                    	   
                    	   matrix[0][column1]=null;
                    	   
                    	   matrix[0][column1-1]=image;
                    	   
                    	   if(enemyCardPresent[0][column1]) {
                    		   enemyCardPresent[0][column1]=false;
                    		   enemyCardPresent[0][column1-1]=true;
                    	   }
                    	   
                       }
			        	  
			        	  else {
			        		  
			        		  cardsOfImages.get(image).slotNum-=1;
			        		  
			        		  matrix[1][column1]=null;
			        		  
			        		  matrix[1][column1-1]=image;
			        		  
			        		  
			        		  if(enemyCardPresent[1][column1]) {
	                    		   enemyCardPresent[1][column1]=false;
	                    		   enemyCardPresent[1][column1-1]=true;
	                    	   }
			        	  }
                       
                      
						
					}
					
				}
				
			};
			timerA.schedule(taskA, 10, 7);//2
			
		}
		 
		else if( column >0 && buttonPressed[2]==true && (  (firstRow==true && matrix[0][column-1]!=null && enemyCardPresent[0][column-1] ) || (firstRow==false && matrix[1][column-1]!=null && enemyCardPresent[1][column-1]) ) ) { //left & emptySlot
			
			
			
			buttonPressed[2]=false;
			
			
			 if(firstRow==true) add*=1;
			 else if (firstRow==false)add*=-1;
			
			
			 
			 timerD=new Timer();
			 
			 
			 
			 taskD=new TimerTask() {
			
			boolean dead=false;	 
				
				 boolean pass=false;
int x1=imageCorners.get(image).x;
				@Override
				public void run() {
					
//					 if(firstRow==true && add1<0) add1*=1;
//					 else if (firstRow==false  && add1<0)add1=-1;
					 
					attackCount-=add*10;
					
					
					imageCorners.replace(image, new Point(x+attackCount,y));
					panel.repaint();
					
					
					if(imageCorners.get(image).x>=x1 && pass) {
						imageCorners.replace(image, new Point(x,y));
						availableImages.remove(redEnemyImage);
						imageCorners.remove(redEnemyImage);
						
						availableImages.put(enemyImage, false);
						timerD.cancel();
						add*=-1;
					}
					
					if(imageCorners.get(image).x<=x1-image.getIconWidth()/2) {
						
						
						
						timer5.schedule(task5, 0,50);

						
						
						if(attackPass==true) {
							
							 cardsOfImages.get(enemyImage).HP    -=		cardsOfImages.get(image).combatPower;
							 
							
							 
							 availableImages.remove(enemyImage);
								availableImages.put(redEnemyImage,false);
								imageCorners.put(redEnemyImage,enemyPoint);
								panel.repaint();
							 
							 dead=deathCheck(cardsOfImages.get(enemyImage));
					            
					          if(dead==true) {
					        	 
					        	
					        	  timerD.cancel();  
					        	  
					        	  imageCorners.remove(enemyImage);
					        	 
					        	  
					        	  
					        	  imageCorners.remove(redEnemyImage);
					        	  availableImages.remove( redEnemyImage);
					        	  
					        	  imageCorners.replace(image, enemyPoint);
					        	  
					        	  panel.repaint();
					  
					        	  cardsOfImages.get(image).slotNum-=1;
					        	  
					        	  if(firstRow==true) {
					        		  
					        		  matrix[0][column1]=null;

					        		  matrix[0][column1-1]=image;
					        		  enemyCardPresent[0][column1-1]=false;
					        	  }
					        	  
					        	  else {
					        		  matrix[1][column1]=null;
					        		  
					        		  matrix[1][column1-1]=image;	
					        		  enemyCardPresent[1][column1-1]=false;
					        	  }
					          }
					          
					          else {
					        	  add*=-1;
					          }
							 
							
							
					 
							
					  
					
							
							attackPass=false;
						}
						
						
						
						pass=true;
					}
					
					
				}
				
			};
			
			timerD.schedule(taskD, 10, 7);
			
			
			
			String hit="-"+this.combatPower;
			
			
			
			
			
			 timer5=new Timer();
			 task5=new TimerTask() {

				@Override
				public void run() {
	//if(hitPass==false) return;

					hitCount++;

					int x =enemyPoint.x-hitCount;
					int y =enemyPoint.y-hitCount;
					
					
					hits.add(hit);
					hitCorners.put(hit, new Point(x,y));
					panel.repaint();
					
					
					if(hitCount==15) {
						hitPass=false;
						hits.remove(hit);
						hitCorners.remove(hit);
						timer5.cancel();
						panel.repaint();
						hitCount=0;
						
					}
				}
				
			};
			
			timerB=new Timer();
			
			taskB1=new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
				
			};
			
		}
		 
		 if ( this.image!=null && enemyImage!=null && deathCheckForBorder(cardsOfImages.get(this.image),cardsOfImages.get(enemyImage)) && ( border )){
			 border=false;
		 return image;
		 }
		 return null;
		 
	}
	

	Timer timerB=null;
	TimerTask taskB1=null;
	TimerTask taskB2=null;
	TimerTask taskB3=null;
	
	Timer timerE;
	TimerTask taskE=null;
	
	
	Timer timerD;
	TimerTask taskD=null;
	
	
	Timer timerC;
	TimerTask taskC=null;
	
	Timer timerA=null;
	TimerTask taskA=null;
	
	Timer timer5=null;
	TimerTask task3=null;
	TimerTask task4=null;
	TimerTask task5=null;
	boolean randomPass=false;
	boolean hitPass=false;
	int hitCount=0;
	private boolean paintPass=false;
	
	private boolean leftPass=true;
	private boolean rightPass=true;
	
	private boolean deathCheck(GameCard enemyCard) {
		
		if(enemyCard.HP<=0) 
			
return true;
		
		
		
		return false;
	}
	
private boolean deathCheckForBorder(GameCard card,GameCard enemyCard) {
		
		if(enemyCard.HP- card.combatPower <=0) 
			
return true;
		
		
		
		return false;
	}


	




	
	
	
}


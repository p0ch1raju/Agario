import java.awt.*
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;

public class BlobGame extends JPanel implements KeyListener {
   Blob playerBlob;
   ArrayList<Blob> evilBlobs;
   int delay;
   boolean gameOver;

   public BlobGame(){
      gameOver=false;
      delay=80;
      evilBlobs=new ArrayList<Blob>();
      playerBlob=new Blob(15,Color.BLUE,400,300,5,5,'w','d');
      int k=0;
      while (k<20){
         createEvilBlob();
         k++;
      }
      
   }

   //Handle the key pressed event from the text field.
   public void keyPressed(KeyEvent e) {
      char c;
      c = e.getKeyChar();
      if (c=='w'){
         playerBlob.moveUp();
      }else if (c=='a'){
         playerBlob.moveLeft();
      }else if (c=='s'){
         playerBlob.moveDown();
      }else if (c=='d'){
         playerBlob.moveRight();
      }
   }
   
   public void checkForCollision(){
      if (playerBlob.getX()>900 || playerBlob.getX()<-100 || playerBlob.getY()>900 || playerBlob.getY()<-100){
         gameOver = true;
      }
      
      for (int i=0; i<evilBlobs.size(); i++){
         if (evilBlobs.get(i).getX()<-100 || evilBlobs.get(i).getX()>900 || evilBlobs.get(i).getY()<-100 || evilBlobs.get(i).getY()>900){
            evilBlobs.remove(i);
            createEvilBlob();
         }
      }
      
      for (int i = 0; i < evilBlobs.size(); i++){
         //if the distance b/w player blob and evil blob is less than the combined radius
         //then they are colliding - eat, change color, remove, and create new evil - call distance?
         if (distance(playerBlob.getX(),playerBlob.getY(), evilBlobs.get(i).getX(),evilBlobs.get(i).getY())<playerBlob.getRadius()+evilBlobs.get(i).getRadius()){
            if (playerBlob.getRadius()>evilBlobs.get(i).getRadius()){
            //player is bigger than evil, then it eats it
               playerBlob.eat();
               playerBlob.changeColor();
               evilBlobs.remove(i);
               createEvilBlob();
               if (playerBlob.getRadius()>175){
                  //game is won
                  for (int j=0; j<evilBlobs.size(); j++){
                     evilBlobs.remove(j);
                     //trying to remove all the evil blobs
                  }
                  //System.out.println("You Won!");
                  gameOver = true;
                  break;
               }
            }else if (playerBlob.getRadius()<evilBlobs.get(i).getRadius()){
               gameOver = true;
            }
         }
      }
   
   }
   
   public void createEvilBlob(){
      //keys
      //take random
      char [] leftRight={'a','d'};
      char [] upDown={'w','s'};
      char leftOrRight=leftRight[(int)(Math.random()*2)];
      char upOrDown=upDown[(int)(Math.random()*2)];
      
      
      //other parameters for blob, initializing
      int deltaXevil=0;
      int deltaYevil=0;
      
      //blob coming from left, right, up down random
      
      //blob coming from left or right at random
      if (leftOrRight=='d'){
         deltaXevil=(int)((Math.random()*10)+2); //speed of the blob between 2 and 10
      }else{
         //deltaXevil=(int)(Math.random()*(10))+1; nope
         deltaXevil=(int)((Math.random()*(-10))+2);
      }
      
      //up or down
      if (upOrDown=='w'){
         deltaYevil=(int)((Math.random()*-10)+2);
      }else{
         deltaYevil=(int)((Math.random()*10)+2);
      }
      
      Blob newEvilBlob=new Blob((int)(Math.random()*((playerBlob.getRadius()+33)-3)+3),Color.RED,((int)(Math.random()*1000)),(int)(Math.random()*1000),deltaXevil,deltaYevil,leftOrRight,upOrDown);
      
         
         //ensures that no blob will spawn at the location of the playerBlob
         if (distance(playerBlob.getX(),playerBlob.getY(), newEvilBlob.getX(),newEvilBlob.getY())<(playerBlob.getRadius()+newEvilBlob.getRadius()-2)){               
            //checks overlap - evil blob can't spawn. Then call the method again
            createEvilBlob();
         }else{
            evilBlobs.add(newEvilBlob);
         }
      
      }


   public static int distance(int x1, int y1, int x2, int y2){
      return (int)Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
   }

   public void paint(Graphics g){
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, 800, 800);
      playerBlob.draw(g);
      for(int i = 0; i < evilBlobs.size(); i++){
         evilBlobs.get(i).draw(g);
      }
      
      if (gameOver){
         g.setColor(Color.GRAY);
         g.fillRect(0,0,800,800);
         if (playerBlob.getRadius()>=175){
            g.setColor(Color.WHITE);
            Font courier = new Font ("Courier New", 1, 100);
            g.setFont(courier);
            g.drawString("YOU WIN!", 147, 300);
         }else if (playerBlob.getRadius()<175){
            g.setColor(Color.WHITE);
            Font courier = new Font ("Courier New", 1, 100);
            g.setFont(courier);
            g.drawString("YOU LOSE!", 127, 300);
         }
      }
   }

   /** Handle the key released event from the text field. */
   public void keyReleased(KeyEvent e) {
      char c;
      c = e.getKeyChar();
   }
   /** Handle the key typed event from the text field. */
   public void keyTyped(KeyEvent e) {
      char c;
      c = e.getKeyChar();     
   }
   
   public void run(){
   
      while(!gameOver){
      
         try {
            Thread.sleep(delay);
         }
         catch(InterruptedException e) {}
         
         playerBlob.move();
         for(int i = 0; i < evilBlobs.size(); i++){
            evilBlobs.get(i).move();    
         }
         
         //check for collision
         checkForCollision();
         
         paintImmediately(0, 0, 1000, 1000);
      }
      System.out.println("GAME OVER");
      System.out.println("Final Blob Size - " + playerBlob.getRadius());
   }
      
   public static void main(String [] arg){
      JFrame runner = new JFrame("KNOCKOFF AGAR.IO");
      runner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      runner.setLocationRelativeTo(null);
      runner.setSize(800, 800);
      runner.setLayout(null);
      runner.setLocation(0, 0);
      
      BlobGame theGame = new BlobGame();
      theGame.setSize(800, 800);
      theGame.setLocation(0, 0);
      runner.getContentPane().add(theGame);
      
      runner.setVisible(true);
   
      runner.addKeyListener(theGame);
      theGame.run();
   }
}

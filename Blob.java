import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;

public class Blob
{
   int radius;
   Color theColor;
   int x, y;
   int deltaX, deltaY;
   char leftRight;
   char upDown;
   
   public Blob(int aRadius, Color aColor, int aX, int aY, int aDeltaX, int aDeltaY, char aLeftRight, char aUpDown){
      this.radius=aRadius;
      this.theColor=aColor;
      this.x=aX;
      this.y=aY;
      this.deltaX=aDeltaX;
      this.deltaY=aDeltaY;
      this.leftRight=aLeftRight;
      this.upDown=aUpDown;
   }
   
   //returns the x coordinate of the blob
   public int getX(){
      return x;
   
   }
    
   public int getDeltaX(){
      return deltaX;
   }
   
   //returns the y coordinate of the blob
   public int getY(){
      return y;
   
   }
   public int getDeltaY(){
      return deltaY;
   }
   
   //returns the radius of the blob
   public int getRadius(){
      return radius;
   
   }

   //increases the size(radius) of the blob
   public void eat(){
      radius+=10;
   
   }
   
   //moves the blob on its current path
   public void move(){
      if(leftRight=='d'){
         x+=deltaX;
      }
      if(leftRight=='a'){
         x+=deltaX;
      }
      if(upDown=='w'){
         y+=deltaY;
      }
      if(upDown=='s'){
         y+=deltaY;
      }
   
   }
   
   //has the blob increase its direction to the right
   public void moveRight(){
      deltaX+=10;
      leftRight='d';
   }
   
   //has the blob increase its direction to the left
   public void moveLeft(){
      deltaX-=10;
      leftRight='d';
   
   }
   
   //has the blob increase its direction up
   public void moveUp(){
      deltaY-=10;
      upDown='w';
   }
   
   //has the blob increase its direction down
   public void moveDown(){
      deltaY+=10;
      upDown='w';
   }
   
   //change colors when the blob is eating
   public void changeColor(){
      int red=(int)(Math.random()*255);
      int blue=(int)(Math.random()*255);
      int green=(int)(Math.random()*255);
      theColor=new Color(red,blue,green);
   
   }
   
   public void draw(Graphics g){
      g.setColor(theColor);
      g.fillOval(x-radius, y-radius, 2*radius+1, 2*radius+1);
   }
}

/*
 * Name:  Hannah Burkhardt
 * Login: cs11fgb 
 * Date:  September 30, 2011
 * File:  Grocery.java
 * Sources of Help: I checked the java docs to review how to sort string arrays.
 *
 * This program mimics a grocery store that sells six products. You can add and  * remove items buy clicking the plus and minus boxes. The program will 
 * calculate your total (including tax) and show which product is most popular.
 */

import java.awt.*;
import objectdraw.*;


/*
 * Name:       class Grocey
 * Purpose:    This class contains all the methods needed for the program.
 * Parameters: see code
 * Return:     none
 */

public class Grocery extends WindowController
{
   //constants and instance variables
   
   //----UI constants and variables----
   private final static int WORLD_WIDTH = 300;
   private final static int WORLD_HEIGHT = 300;
   //box constants
   private final static int BOX_WIDTH = 100; //for large boxes
   private final static int BOX_HEIGHT = 100;
   private final static int S_BOX_WIDTH = 20; //for plus and minus boxes
   private final static int S_BOX_HEIGHT = 20;
   private final static int TEXT_HEIGHT = 15; //height of one line of text 
   //following are the starting points for placing large boxes and +- boxes
   private final static int BOXES_START_HEIGHT = 50;
   private final static int S_BOXES_START_HEIGHT = BOXES_START_HEIGHT + 60;
   //plus and minus boxes
   private FramedRect bananaMinusBox;
   private FramedRect bananaPlusBox;
   private FramedRect broccoliMinusBox;
   private FramedRect broccoliPlusBox;
   private FramedRect juiceMinusBox;
   private FramedRect juicePlusBox;
   private FramedRect sugarMinusBox;
   private FramedRect sugarPlusBox;
   private FramedRect butterMinusBox;
   private FramedRect butterPlusBox;
   private FramedRect bagelMinusBox;
   private FramedRect bagelPlusBox;

   //image constants
   private final static int IMG_WIDTH = 50; //width images should have
   private final static int IMG_HEIGHT = 50; //height images should have
   //following is the starting point for drawing images of the canvas
   private final static int IMG_START_WIDTH = BOX_WIDTH - 2 - IMG_WIDTH;
   private final static int IMG_START_HEIGHT = BOXES_START_HEIGHT + 1;
   //text constants
   private final static int LABEL_START_WIDTH = 3; //offset from left edge of
                                                   //canvas for text
   //starting points for name labels and count labels inside large boxes
   private final static int LABEL_START_HEIGHT = BOXES_START_HEIGHT + 25;    
   private final static int COUNT_LABEL_START_HEIGHT = BOXES_START_HEIGHT + 83;
   private final static int COUNT_LABEL_START_WIDTH = 25;

   //----data and calculation constants and variables----
   //product prices in cents
   private final static int BANANA_PRICE = 125;
   private final static int BROCCOLI_PRICE = 65;
   private final static int JUICE_PRICE = 260;
   private final static int SUGAR_PRICE = 485;
   private final static int BUTTER_PRICE = 373;
   private final static int BAGEL_PRICE = 325;
   //tax percentage
   private final static double TAX = 0.0875;
   //prodcut counts
   private int bananaCount;
   private int broccoliCount;
   private int juiceCount;
   private int sugarCount;
   private int butterCount;
   private int bagelCount;
   //product count text objects
   private Text bananaCountText;
   private Text broccoliCountText;
   private Text juiceCountText;
   private Text sugarCountText;
   private Text butterCountText;
   private Text bagelCountText;
   //totals etc. Text and String objects as well as some ints
   private Text mostPopular;
   private String mostPopularStr;
   private Text secondMostPopular;
   private String secondMostPopularStr;
   private Text subTotalTxt;
   private int subTotal;
   private Text taxTxt;
   private int tax;
   private Text totalTxt;
   private int total;


/*
 * Name:       begin
 * Purpose:    This method sets up the world. It initializes all String and Text *             objects. At the end of the method, methods refreshStrings() is 
 *             called in order to reset the strings' values.
 * Parameters: temporary Strings, Texts, and ints used to set up world
 * Return:     none
 */

   //begin method
   public void begin()
   {
      canvas.clear();
      //reset variables
      bananaCount = 0;
      broccoliCount = 0;
      juiceCount = 0;
      sugarCount = 0;
      butterCount = 0;
      bagelCount = 0;
      mostPopularStr = "Most Popular: ";
      secondMostPopularStr = "2nd Most Popular: ";


      //***----set header text----***
      Text freshG = new Text("Fresh Grocery Store", 0, 0, canvas);
      freshG.moveTo((WORLD_WIDTH - freshG.getWidth()) / 2, 0);
      
      Text useTheB = new Text("Use the buttons to add and remove items.", 
                              0, 
                              0, 
                              canvas);
      useTheB.moveTo((WORLD_WIDTH - useTheB.getWidth()) / 2, TEXT_HEIGHT);
      
      Text toReset = new Text("To reset, move the mouse out and back in.", 
               0, 
               0, 
               canvas);
      toReset.moveTo((WORLD_WIDTH - toReset.getWidth()) / 2, TEXT_HEIGHT * 2);
      
      //***----draw boxes----***
      //----large boxes----
      //variables for box drawing loops
      Location startBoxHere;
      Location startPBoxHere;
      Location startMBoxHere;
      
      //loop iterating through rows
      for (int j = 0; j <= 1; j++) 
      {
         //loop for each row of large boxes
         for (int i = 0; i <= 2; i++)
         {
            startBoxHere = new Location(BOX_WIDTH * i, 
                                        BOXES_START_HEIGHT + BOX_HEIGHT * j);
            new FramedRect(startBoxHere, BOX_WIDTH, BOX_HEIGHT, canvas); 
         }
      }
      
      //----small boxes----
      int a = 0;
      int b = 0;
      //**banana boxes
      startPBoxHere = new Location(BOX_WIDTH * a + 20, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
      startMBoxHere = new Location(BOX_WIDTH * a + 60, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
     
      bananaPlusBox = new FramedRect(startPBoxHere, 
                                     S_BOX_WIDTH, 
                                     S_BOX_HEIGHT, 
                                     canvas);
      //plus sign
      new Line(startPBoxHere.getX() + 5,
               startPBoxHere.getY() + 10,
               startPBoxHere.getX() + 15,
               startPBoxHere.getY() + 10,
               canvas);
      new Line(startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 5,
               startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 15,
               canvas);
           
      bananaMinusBox = new FramedRect(startMBoxHere, 
                                      S_BOX_WIDTH,
                                      S_BOX_HEIGHT, 
                                      canvas);
      //minus sign
      new Line(startMBoxHere.getX() + 5,
               startMBoxHere.getY() + 10,
               startMBoxHere.getX() + 15,
               startMBoxHere.getY() + 10,
               canvas);

      //**broccoli boxes
      a++;
      startPBoxHere = new Location(BOX_WIDTH * a + 20, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
      startMBoxHere = new Location(BOX_WIDTH * a + 60, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
     
      broccoliPlusBox = new FramedRect(startPBoxHere, 
                                     S_BOX_WIDTH, 
                                     S_BOX_HEIGHT, 
                                     canvas);
      //plus sign
      new Line(startPBoxHere.getX() + 5,
               startPBoxHere.getY() + 10,
               startPBoxHere.getX() + 15,
               startPBoxHere.getY() + 10,
               canvas);
      new Line(startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 5,
               startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 15,
               canvas);

      broccoliMinusBox = new FramedRect(startMBoxHere, 
                                      S_BOX_WIDTH,
                                      S_BOX_HEIGHT, 
                                      canvas);
      //minus sign
      new Line(startMBoxHere.getX() + 5,
               startMBoxHere.getY() + 10,
               startMBoxHere.getX() + 15,
               startMBoxHere.getY() + 10,
               canvas);


      //**juice boxes
      a++;
      startPBoxHere = new Location(BOX_WIDTH * a + 20, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
      startMBoxHere = new Location(BOX_WIDTH * a + 60, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
     
      juicePlusBox = new FramedRect(startPBoxHere, 
                                     S_BOX_WIDTH, 
                                     S_BOX_HEIGHT, 
                                     canvas);
      //plus sign
      new Line(startPBoxHere.getX() + 5,
               startPBoxHere.getY() + 10,
               startPBoxHere.getX() + 15,
               startPBoxHere.getY() + 10,
               canvas);
      new Line(startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 5,
               startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 15,
               canvas);

      juiceMinusBox = new FramedRect(startMBoxHere, 
                                      S_BOX_WIDTH,
                                      S_BOX_HEIGHT, 
                                      canvas);
      //minus sign
      new Line(startMBoxHere.getX() + 5,
               startMBoxHere.getY() + 10,
               startMBoxHere.getX() + 15,
               startMBoxHere.getY() + 10,
               canvas);


      //**sugar boxes
      a = 0;
      b++;
      startPBoxHere = new Location(BOX_WIDTH * a + 20, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
      startMBoxHere = new Location(BOX_WIDTH * a + 60, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
     
      sugarPlusBox = new FramedRect(startPBoxHere, 
                                     S_BOX_WIDTH, 
                                     S_BOX_HEIGHT, 
                                     canvas);
      //plus sign
      new Line(startPBoxHere.getX() + 5,
               startPBoxHere.getY() + 10,
               startPBoxHere.getX() + 15,
               startPBoxHere.getY() + 10,
               canvas);
      new Line(startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 5,
               startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 15,
               canvas);

      sugarMinusBox = new FramedRect(startMBoxHere, 
                                      S_BOX_WIDTH,
                                      S_BOX_HEIGHT, 
                                      canvas);
      //minus sign
      new Line(startMBoxHere.getX() + 5,
               startMBoxHere.getY() + 10,
               startMBoxHere.getX() + 15,
               startMBoxHere.getY() + 10,
               canvas);


      //**butter boxes
      a++;
      startPBoxHere = new Location(BOX_WIDTH * a + 20, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
      startMBoxHere = new Location(BOX_WIDTH * a + 60, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
     
      butterPlusBox = new FramedRect(startPBoxHere, 
                                     S_BOX_WIDTH, 
                                     S_BOX_HEIGHT, 
                                     canvas);
      //plus sign
      new Line(startPBoxHere.getX() + 5,
               startPBoxHere.getY() + 10,
               startPBoxHere.getX() + 15,
               startPBoxHere.getY() + 10,
               canvas);
      new Line(startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 5,
               startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 15,
               canvas);

      butterMinusBox = new FramedRect(startMBoxHere, 
                                      S_BOX_WIDTH,
                                      S_BOX_HEIGHT, 
                                      canvas);
      //minus sign
      new Line(startMBoxHere.getX() + 5,
               startMBoxHere.getY() + 10,
               startMBoxHere.getX() + 15,
               startMBoxHere.getY() + 10,
               canvas);


      //**bagel boxes
      a++;
      startPBoxHere = new Location(BOX_WIDTH * a + 20, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
      startMBoxHere = new Location(BOX_WIDTH * a + 60, 
                                   S_BOXES_START_HEIGHT + BOX_HEIGHT * b);
     
      bagelPlusBox = new FramedRect(startPBoxHere, 
                                     S_BOX_WIDTH, 
                                     S_BOX_HEIGHT, 
                                     canvas);
      //plus sign
      new Line(startPBoxHere.getX() + 5,
               startPBoxHere.getY() + 10,
               startPBoxHere.getX() + 15,
               startPBoxHere.getY() + 10,
               canvas);
      new Line(startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 5,
               startPBoxHere.getX() + 10,
               startPBoxHere.getY() + 15,
               canvas);

      bagelMinusBox = new FramedRect(startMBoxHere, 
                                      S_BOX_WIDTH,
                                      S_BOX_HEIGHT, 
                                      canvas);
      //minus sign
      new Line(startMBoxHere.getX() + 5,
               startMBoxHere.getY() + 10,
               startMBoxHere.getX() + 15,
               startMBoxHere.getY() + 10,
               canvas);



  
      //***----display images----***
      //variables for increment for positioning of images
      int xPos1 = IMG_START_WIDTH;
      int xPos2 = IMG_START_WIDTH;
      int yPos = IMG_START_HEIGHT;
      
      //load images and display them
      String imgName = "banana.jpg";
      Image img = getImage(getDocumentBase(), imgName);
      new VisibleImage(img, xPos1, yPos, IMG_WIDTH, IMG_HEIGHT, canvas);

      imgName = "broccoli.jpg";
      img = getImage(getDocumentBase(), imgName);
      new VisibleImage(img, xPos1 += 100, yPos, IMG_WIDTH, IMG_HEIGHT, canvas);

      imgName = "juice.jpg";
      img = getImage(getDocumentBase(), imgName);
      new VisibleImage(img, xPos1 += 100, yPos, IMG_WIDTH, IMG_HEIGHT, canvas);

      imgName = "sugar.jpg";
      img = getImage(getDocumentBase(), imgName);
      new VisibleImage(img, xPos2, yPos += 100, IMG_WIDTH, IMG_HEIGHT, canvas);
      
      imgName = "butter.jpg";
      img = getImage(getDocumentBase(), imgName);
      new VisibleImage(img, xPos2 += 100, yPos, IMG_WIDTH, IMG_HEIGHT, canvas);

      imgName = "bagel.jpg";
      img = getImage(getDocumentBase(), imgName);
      new VisibleImage(img, xPos2 += 100, yPos, IMG_WIDTH, IMG_HEIGHT, canvas);
      
      //***----set up other applet text----***
      int labelWidth1 = LABEL_START_WIDTH;
      int labelWidth2 = LABEL_START_WIDTH;
      int labelHeight = LABEL_START_HEIGHT;
      
      //----static text----
      //product names
      new Text("Banana", 
               labelWidth1, 
               labelHeight, 
               canvas);
      new Text("Broccoli", 
               labelWidth1 += BOX_WIDTH, 
               labelHeight, 
               canvas);
      new Text("Juice", 
               labelWidth1 += BOX_WIDTH, 
               labelHeight, 
               canvas);
      new Text("Sugar", 
               labelWidth2, 
               labelHeight += BOX_HEIGHT, 
               canvas);
      new Text("Butter", 
               labelWidth2 += BOX_WIDTH, 
               labelHeight, 
               canvas);
      new Text("Bagel", 
               labelWidth2 += BOX_WIDTH, 
               labelHeight, 
               canvas);
      //product prices
      labelWidth1 = LABEL_START_WIDTH;
      labelWidth2 = LABEL_START_WIDTH;
      labelHeight = LABEL_START_HEIGHT + TEXT_HEIGHT;
      
      //put prices into appropriate formats by getting whole dollar amounts
      //first, then determining leftover cents
      String bananaPrice = new String("$" + BANANA_PRICE / 100 + "."
                                      + BANANA_PRICE % 100);
      new Text(bananaPrice, labelWidth1, labelHeight, canvas); 
      String broccoliPrice = new String("$" + BROCCOLI_PRICE / 100 + "." 
                                      + BROCCOLI_PRICE % 100);
      new Text(broccoliPrice, labelWidth1 += BOX_WIDTH, labelHeight, canvas);
      String juicePrice = new String("$" + JUICE_PRICE / 100 + "." 
                                      + JUICE_PRICE % 100);
      new Text(juicePrice, labelWidth1 += BOX_WIDTH, labelHeight, canvas);
      String sugarPrice = new String("$" + SUGAR_PRICE / 100 + "." 
                                      + SUGAR_PRICE % 100);
      new Text(sugarPrice, labelWidth2, labelHeight += BOX_HEIGHT, canvas);
      String butterPrice = new String("$" + BUTTER_PRICE / 100 + "." 
                                      + BUTTER_PRICE % 100); 
      new Text(butterPrice, labelWidth2 += BOX_WIDTH, labelHeight, canvas);           String bagelPrice = new String("$" + BAGEL_PRICE / 100 + "." 
                                      + BAGEL_PRICE % 100);
      new Text(bagelPrice, labelWidth2 += BOX_WIDTH, labelHeight, canvas);
      
      //----variable text----
      //product counts
      labelWidth1 = COUNT_LABEL_START_WIDTH;
      labelWidth2 = COUNT_LABEL_START_WIDTH;
      labelHeight = COUNT_LABEL_START_HEIGHT;
      bananaCountText = new Text("Count: 0", 
                                 labelWidth1, 
                                 labelHeight, 
                                 canvas);
      broccoliCountText = new Text("Count: 0", 
                                   labelWidth1 += BOX_WIDTH, 
                                   labelHeight, 
                                   canvas);
      juiceCountText = new Text("Count: 0", 
                                labelWidth1 += BOX_WIDTH, 
                                labelHeight, 
                                canvas);
      sugarCountText = new Text("Count: 0", 
                                labelWidth2, 
                                labelHeight += BOX_HEIGHT, 
                                canvas);
      butterCountText = new Text("Count: 0", 
                                 labelWidth2 += BOX_WIDTH, 
                                 labelHeight, 
                                 canvas);
      bagelCountText = new Text("Count: 0", 
                                labelWidth2 += BOX_WIDTH, 
                                labelHeight, 
                                canvas);

      //initialize most popular strings and totals
      mostPopular = new Text("Most Popular: ",
                             LABEL_START_WIDTH,
                             BOXES_START_HEIGHT + BOX_HEIGHT * 2,
                             canvas);
      secondMostPopular = new Text("2nd Most Popular: ",
                                   LABEL_START_WIDTH,
                                   mostPopular.getY() + TEXT_HEIGHT,
                                   canvas);
      subTotalTxt = new Text("Subtotal: $0.00",
                          175,
                          mostPopular.getY(),
                          canvas);
      taxTxt = new Text("Tax: $0.00",
                     subTotalTxt.getX(),
                     subTotalTxt.getY() + TEXT_HEIGHT,
                     canvas);
      totalTxt = new Text("Total: $0.00",
                       taxTxt.getX(),
                       taxTxt.getY() + TEXT_HEIGHT,
                       canvas);



      //call this method in order to update displays 
      //(possibly to initial values)
      refreshStrings();

   }



/*
 * Name:       onMouseEnter()
 * Purpose:    This method is called when the mouse exits and enters the applet  *             again. It does noting but call begin() because begin() will 
 *             redraw the world and reset everything.
 * Parameters: none
 * Return:     void
 */
   public void onMouseEnter(Location point)
   {
      begin();
   }



/*
 * Name:       onMouseClick()
 * Purpose:    This method is called when the mouse clicks. The method 
 *             determines if the user clicked inside a plus or minus box and, if *             so, increments the item count for the partucular item. The method *             then calls refreshStrings() and setMostPopular() to update Count  *             displays, price subtotal, tax, and total, and 1st and 2nd most po *             pular. 
 * Parameters: none
 * Return:     void
 */
   public void onMouseClick(Location point)
   {
      if (bananaPlusBox.contains(point)) //if banana plus box contains click
      {
         bananaCount++; // increment bananaCount
      } else if (bananaMinusBox.contains(point)) //if banana minus box contains
      {                                          //click
         if (bananaCount > 0) //first check if bananaCount is already zero
         {
            bananaCount--; //if not, decrement bananaCount
         }
      } else if (broccoliPlusBox.contains(point)) //repeat procedure for all
      {                                           //the different products
         broccoliCount++;
      } else if (broccoliMinusBox.contains(point)) 
      {
         if (broccoliCount > 0) 
         {
            broccoliCount--;
         }
      } else if (juicePlusBox.contains(point))
      {
         juiceCount++;
      } else if (juiceMinusBox.contains(point)) 
      {
         if (juiceCount > 0) 
         {
            juiceCount--;
         }
      } else if (sugarPlusBox.contains(point))
      {
         sugarCount++;
      } else if (sugarMinusBox.contains(point)) 
      {
         if (sugarCount > 0) 
         {
            sugarCount--;
         }
      } else if (butterPlusBox.contains(point))
      {
         butterCount++;
      } else if (butterMinusBox.contains(point)) 
      {
         if (butterCount > 0) 
         {
            butterCount--;
         }
      } else if (bagelPlusBox.contains(point))
      {
         bagelCount++;
      } else if (bagelMinusBox.contains(point))
      {
         if (bagelCount > 0)
         {
            bagelCount--;
         }
      }        

      //call refreshStrings() method to update labels according to new and
      //updated counts
      refreshStrings();
      //call setMostPopular() in order to determine the 1st and 2nd most popular
      //products and display their names in the appropriate labels
      setMostPopular();
   }

/*
 * Name:       setMostPopular()
 * Purpose:    This method is called to update the Text objects that display
 *             which two items were chosen most. It uses a string array of
 *             strings with the format "count itemName", for example 
 *             "1 Banana", that can be sorted. the latter part of the String
 *             is then displayed in the "Most Popular: " section in the
 *             applet.
 * Parameters: individual strings to be added to the array; an array to contain  *             these strings that will be sorted.
 * Return:     void
 */

   public void setMostPopular()
   {
      // make strings that start with the number of items and end with product
      // name, so that they can be sorted by number of items
      String str1 = String.format("%d Banana  ", bananaCount);
      String str2 = String.format("%d Broccoli", broccoliCount);
      String str3 = String.format("%d Juice   ", juiceCount);
      String str4 = String.format("%d Sugar   ", sugarCount);
      String str5 = String.format("%d Butter  ", butterCount);
      String str6 = String.format("%d Bagel   ", bagelCount);
      
      // sort array in reverse order so that products with highest count will
      // be on top
      String[] strArray = new String[] {str1, str2, str3, str4, str5, str6};
      java.util.Arrays.sort(strArray, java.util.Collections.reverseOrder());
      
      // after checking if no items have been added to the cart,
      // set most popular and second most popular to top two items in sorted
      // array, cutting off the number of items in the front of the string
      if (strArray[0].startsWith("0") == false)
      {
         mostPopularStr = "Most Popular: " + strArray[0].substring(2, 10);
      }
      if (strArray[1].startsWith("0") == false)
      {
         secondMostPopularStr = "2nd Most Popular: " 
                                + strArray[1].substring(2, 10);
      }

      // add most popular and second most popular string to screen using
      // Text objects mostPopular and secondMostPopular
      mostPopular.setText(mostPopularStr);
      secondMostPopular.setText(secondMostPopularStr);

   }

/*
 * Name:       refreshStrings()
 * Purpose:    The purpose of this method is to update all strings/text objects
               on screen to reflect changes as the applet is being used. This 
               method also executes a small calculation needed to convert the 
               subtotal, tax amount, and total to appropriate dollar values that               are displayed correctly formatted.
 * Parameters: none
 * Return:     void
 */

   public void refreshStrings()
   {
      //update count, popular, and totals strings
      String bananaString = String.format("Count: %d", bananaCount);
      bananaCountText.setText(bananaString);
      String broccoliString = String.format("Count: %d", broccoliCount);
      broccoliCountText.setText(broccoliString);
      String juiceString = String.format("Count: %d", juiceCount);
      juiceCountText.setText(juiceString);
      String sugarString = String.format("Count: %d", sugarCount);
      sugarCountText.setText(sugarString);
      String butterString = String.format("Count: %d", butterCount);
      butterCountText.setText(butterString);
      String bagelString = String.format("Count: %d", bagelCount);
      bagelCountText.setText(bagelString);

      //set totals 
      subTotal = bananaCount * BANANA_PRICE //calculate subtotal
                 + broccoliCount * BROCCOLI_PRICE
                 + juiceCount * JUICE_PRICE
                 + sugarCount * SUGAR_PRICE
                 + butterCount * BUTTER_PRICE
                 + bagelCount * BAGEL_PRICE;
      String subTotalStr = String.format("Subtotal: $" + (subTotal / 100) 
                           + "." + (subTotal % 100)); //make subtotal String
      subTotalTxt.setText(subTotalStr); //set Text object to subtotal String

      tax = (int) (subTotal * TAX); //calculate tax
      String taxStr = String.format("Tax: $" + (tax / 100) 
                      + "." + (tax % 100)); //make tax string
      taxTxt.setText(taxStr); //set Text object to tax string

      total = subTotal + tax; // calculate total
      String totalStr = String.format("Total: $" + (total / 100) 
                        + "." + (total % 100)); //make total string
      totalTxt.setText(totalStr); //set text object to total string


   }



}

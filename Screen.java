import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.util.ArrayList;


public class Screen extends JPanel implements ActionListener {


  private int generationSize;
  private int geneLength;
  private Circle[] currentGeneration;
  private int xInitial;
  private int yInitial;
  private int generationNumber;
  private boolean animationLive;

  public Screen(){
  	setLayout(null);

    generationNumber= 0;
    generationSize = 200;
    geneLength = 100;
    xInitial = 448;
    yInitial = 990;
    currentGeneration = new Circle[generationSize];
    animationLive = true;

    for(int i =0; i< generationSize; i++){
      Circle temp = new Circle(xInitial, yInitial,geneLength);
      currentGeneration[i] = temp;
      // System.out.println(currentGeneration[i].toString());
    }


  	setFocusable(true);

  }

  @Override
  public Dimension getPreferredSize(){
      return new Dimension(1000,1000);
  }

  @Override
  public void paintComponent(Graphics g){
      //only for drawings, not calculations.
    super.paintComponent(g);

    g.setColor(new Color(252, 220, 151));
    g.fillRect(0,0, 1000,1000);
    g.setColor(new Color(235, 136, 38));
    g.fillRect(480,10, 40, 40);

    //draw the generations
    g.setColor(new Color(42, 174, 250));
    for(int i =0; i<generationSize; i++){
     currentGeneration[i].drawMe(g);
    }

    g.setColor(Color.BLACK);
    g.drawString("Generation #" + generationNumber, 10, 10);

  }


public void actionPerformed(ActionEvent e){


}

public void setUpNextGeneration(){
  System.out.println("Setting up next generation.");
  ArrayList<Circle> listOfAllPossibleParents = getListOfAllPossibleParents();
  ArrayList<Circle> actualParents = new ArrayList<Circle>();

  //select random 200 to be parents
  if(listOfAllPossibleParents.size()>generationSize*2){
    //select random 200.
    for(int i =0; i<generationSize*2; i++){
      int rand = (int)(Math.random() * listOfAllPossibleParents.size());
      actualParents.add(listOfAllPossibleParents.get(rand));
    }
  }else{
    System.out.println("ALERTO GENERATION IS DEFUNKED, BUNCH A BITCHES");
  }

  currentGeneration = breedChildren(actualParents);
  generationNumber++;

  animationLive = true;
  animate();
}

public Circle[] breedChildren(ArrayList<Circle> parentsList){

  Circle[] childrenList = new Circle[generationSize];

  for(int i =0; i< generationSize; i++){
    int momIndex = (int)(Math.random()*parentsList.size());
    int dadIndex = (int)(Math.random()*parentsList.size());


    Circle mom = parentsList.get(momIndex);
    Circle dad = parentsList.get(dadIndex);

    Circle child = mom.crossover(dad);
    System.out.println("m: " + momIndex + " d: " +  dadIndex + " " +child);
    childrenList[i] = child;
  }
  return childrenList;


}

public ArrayList<Circle> getListOfAllPossibleParents(){


  ArrayList<Circle> listOfAllPossibleParents = new ArrayList<Circle>();

  for(int i =0; i<generationSize; i++){
    Double fitness = currentGeneration[i].calcFitness();
    // System.out.println("Fitness of this mf:" + fitness);

    //num times based on 2^fitness this is how likely it essentially will be that it gets picked
    int numTimesInArray = (int)(Math.pow(2,fitness*10));

    for(int j =0; j<numTimesInArray; j++){
      //add that many iterations of the parent to the pool of parents to be chosen.
      listOfAllPossibleParents.add(currentGeneration[i]);
    }

  }
  return listOfAllPossibleParents;
}

public void animate(){
  while(animationLive){
    // System.out.println("annimating");

    for(int i =0; i< generationSize; i++){
      if(currentGeneration[i].generationOver()){
        System.out.println("Generation over");
        setUpNextGeneration();
        animationLive = false;
        break;
      }else{
        currentGeneration[i].move();
      }
    }



    try {
        Thread.sleep(50);
    } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
    }
    repaint();

  }
}

}

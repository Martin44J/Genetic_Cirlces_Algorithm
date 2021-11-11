import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class Circle{

  private int x;
  private int y;
  private int yInit;
  private int xInit;
  private int[] xGenes;
  private int[] yGenes;
  private int fitness;
  private int maxVecLength;
  private int geneLength;
  private boolean reachedGoal;

  public Circle(int xInit, int yInit, int geneLength){
    this.x = xInit;
    this.y = yInit;
    this.xInit = xInit;
    this.yInit = yInit;
    this.geneLength = geneLength;
    reachedGoal = false;

    maxVecLength = 20;
    xGenes = new int[geneLength];
    yGenes = new int[geneLength];


    for(int i =0; i<geneLength; i++){
      //-20 to 20
      int xGene = (int)(Math.random() * 2*maxVecLength)-maxVecLength;
      int yGene = (int)(Math.random() * 2*maxVecLength)-maxVecLength;
      xGenes[i] = xGene;
      yGenes[i] = yGene;


    }

  }

  public Circle(int xInit, int yInit, int geneLength, int[] xGenes, int[] yGenes){
    this.x = xInit;
    this.y = yInit;
    this.xInit = xInit;
    this.yInit = yInit;
    this.geneLength = geneLength;
    reachedGoal = false;

    maxVecLength = 20;
    this.xGenes = xGenes;
    this.yGenes = yGenes;




  }

  public void move(int currentGene){
    if(currentGene<geneLength){
      if(x+ xGenes[currentGene] < 1000 && x+ xGenes[currentGene]>0 ){
        x+= xGenes[currentGene];
      }if(y+ yGenes[currentGene] < 1000 && y+ yGenes[currentGene]>0){
        y+= yGenes[currentGene];

      }
      // System.out.println("moving");
    }
    currentGene++;
  }

  // public boolean generationOver(){
  //   return currentGene>geneLength || reachedGoal;
  // }

  public boolean reachedGoal(){
    return reachedGoal;
  }

  public double calcFitness(){
    //goal at  500, 30
    Double distanceToGoal = Math.sqrt(Math.pow(x-500,2) + Math.pow(y-30,2));
    int height = yInit - 30;
    double normalised = height/ distanceToGoal;
    double fitness = Math.round((normalised)*100.0)/100.0;
    return fitness;
  }

  public void drawMe(Graphics g){
    g.fillOval(x-2,y-2,10,10);
  }

  public Circle crossover(Circle other){
    boolean switchOn = true;
    int[] otherXGenes = other.getXGenes();
    int[] otherYGenes = other.getYGenes();
    int[] newXGenes = new int[geneLength];
    int[] newYGenes = new int[geneLength];

    for(int i= 0; i<xGenes.length; i++){
      if(switchOn){
        newXGenes[i] = xGenes[i];
        newYGenes[i] = yGenes[i];
        switchOn = false;
      }else{
        newXGenes[i] = otherXGenes[i];
        newYGenes[i] = otherYGenes[i];
        switchOn = true;
      }
    }

    return new Circle(xInit, yInit, geneLength, newXGenes, newYGenes);
  }

  public void mutate(){
    //from 1 - 10 mutations
    int numMutations = (int)(Math.random()*10) +1;

    for (int i = 0; i<numMutations; i++){
      //for each mutation change some random index of both the x and y genes to a random number
      xGenes[(int)(Math.random() * geneLength)] = (int)(Math.random() * 2*maxVecLength)-maxVecLength;
      yGenes[(int)(Math.random() * geneLength)] = (int)(Math.random() * 2*maxVecLength)-maxVecLength;

    }
  }


  public int[] getXGenes(){
    return xGenes;
  }
  public int[] getYGenes(){
    return yGenes;
  }




  public String toString(){
    String xSTR = "X Genes: [";
    for(int each: xGenes){
      xSTR += each + ", ";
    }
    xSTR += "]";

    String ySTR = "Y Genes: [";
    for(int each: yGenes){
      ySTR += each + ", ";
    }
    ySTR += "]";

    return xSTR + "\n" + ySTR;

  }
}

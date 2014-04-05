package filepack;

class Animal{
	   int level = 0;
	   public void move(){
	      System.out.println("Animals can move");
	   }
	   public void upChain(){
		   level++;
		   System.out.println(level);
	   }
	   
	   public void overR(){
		   System.out.println("animal overR");
	   }
	}

class Dog extends Animal{
	public void move(){
	    super.move(); // invokes the super class method
	    System.out.println("Dogs can walk and run");
	}
	public void upChain(){
		level++;
		super.upChain();
	}
	   
	@Override
	public void overR(){
		System.out.println("Dog overR");
	}	
}

public class TestDog{
	public static void main(String args[]){

	   Animal b = new Dog(); // Animal reference but Dog object
	   b.move(); //Runs the method in Dog class
	   b.upChain();
	   Animal pug = new Pug();
	   pug.upChain();
	}
}

class Pug extends Dog{
	public void upChain(){
		level++;
		super.upChain();
	}
}

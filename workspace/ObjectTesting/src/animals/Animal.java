package animals;
public class Animal{

   public void move(){
      System.out.println("Animals can move");
   }
   
   public void animalMove(){
	   System.out.println("animalMove print");
   }
}


class Dog extends Animal{

   public void move(){
      System.out.println("Dogs can walk and run");
   }
   public static void bark(){
	      System.out.println("Dogs can bark");
   }
   
   private void dogBark(){
	   
   }
}

class BigDog extends Dog{
	private int count = 0;
	public void move(){
		super.move();
		this.BigMove();
		if (count == 0){
			count++;
			this.move();
		}
	}
	
	public void BigMove(){
		System.out.println(" big dog move");
	}
}

class TestDog{

   public static void main(String args[]){
      Animal a = new Animal(); // Animal reference and object
      Animal b = new Dog(); // Animal reference but Dog object
      Dog c = new Dog(); // dog reference and dog object
      Dog bigd = new BigDog();
      
      
   }
}

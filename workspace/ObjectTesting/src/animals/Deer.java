package animals;



public class Deer extends Animal implements Vegetarian{
	public static void main(String args[]){
		Deer d = new Deer();
		Animal a = d;
		Vegetarian v = d;
		Object o = d;

	}
}

interface Vegetarian{}




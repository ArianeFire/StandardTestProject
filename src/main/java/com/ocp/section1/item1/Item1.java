package com.ocp.section1.item1;


/**
 * 
 * Implements Encapsulation
 * 
 * @author seberthe
 *
 */
public class Item1 {

	public static void main(String... args){
		Person mPerson = new Person("Seydou BERTHE", false);
		System.out.println(mPerson);
	}
}

/**
 * Well encapsulated class all members of the class ae private
 */
class Person {
	private String name; 
	private boolean dead;
	
	public Person(String name, boolean dead) {
		super();
		this.name = name;
		this.dead = dead;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	@Override
	public String toString() {
		return String.format("Person %s is %s ", name, dead ? "Dead" : "Alive");
	}
	
}

package com.ocp.section1.item3;

/**
 * 
 * Implement polymorphism.
 * 
 * @author seberthe
 */
public class Item3 {

	public static void main(String... args){
		
		Animal[] zoo = new Animal[3];
		zoo[0] = new Lion("Waraba");
		zoo[1] = new Cat("Choufè Jaack");
		zoo[2] = new Hyena("Souroukouba");
		
		for(Animal singleAnimal: zoo){
			singleAnimal.present();
		}
	}
}

class Animal {
	private String name;
	public Animal(String name){
		this.name = name;
	}
	public void present(){
		System.out.println(String.format("My Name is %s", name));
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

class Lion extends Animal {
	public Lion(String name) {
		super(name);
	}
	@Override
	public void present() {
		System.out.println(String.format("%s here, Roar roar roar...", getName()));
	}
}

class Cat extends Animal {
	public Cat(String name) {
		super(name);
	}
	@Override
	public void present(){
		System.out.println(String.format("%s here, Meow meow meow...", getName()));
	}
}

class Hyena extends Animal {
	public Hyena(String name) {
		super(name);
	}
}

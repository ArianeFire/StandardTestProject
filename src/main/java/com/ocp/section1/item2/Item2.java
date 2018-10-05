package com.ocp.section1.item2;

/**
 * 
 * Implement inheritance including visibility modifiers and composition.
 * 
 * @author seberthe
 *
 */
public class Item2 {
	
	public static void main(String... args){
		Animal lion = new Lion("Waraba", 1);
		Animal cat = new Cat("Kitty");
		
		lion.eat("Grass");
		cat.eat("Fromage");
		lion.eat("Meat");
		
		lion.jump();
		cat.jump();
	}
}

interface CanJump{
	void jump();
}

class CannotJumpBehavior implements CanJump {
	@Override
	public void jump() {
		System.out.println("Unfortunately I can't jump...");
	}
}

class MeduimJumper implements CanJump {
	@Override
	public void jump() {
		System.out.println("I am a Medium Jumper...");
	}
}

class HighLevelJumper implements CanJump {
	@Override
	public void jump() {
		System.out.println("I am a High Lvel Jumper...");
	}
}

class Animal {
	protected String name;
	protected int age;
	private CanJump jumpBehavior;
	
	public Animal(){}
	
	public Animal(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public Animal(String name, int age, CanJump jumpBehavior){
		this(name, age);
		this.jumpBehavior = jumpBehavior;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setJumpBehavior(CanJump jump){
		this.jumpBehavior = jump;
	}
	public void eat(String food){
		System.out.println(String.format("A %s is eating %s", name, food));
	}
	public void jump(){
		this.jumpBehavior.jump();
	}
}

class Lion extends Animal {
	public Lion(String name, int age) {
		super(name, age);
		this.setJumpBehavior(new HighLevelJumper());
	}
	
	@Override
	public void eat(String food){
		if("Meat".equals(food)){
			System.out.println(String.format("Great, A %s eat %s", name, food));
			return;
		}
		System.out.println(String.format("%s doesn't eat a %s", name, food));
	}
}

class Cat extends Animal {
	
	public Cat(String name){
		this.setJumpBehavior(new MeduimJumper());
	}
}

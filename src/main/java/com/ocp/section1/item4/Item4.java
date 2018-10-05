package com.ocp.section1.item4;

/**
 * 
 * Override hasCode, equals, and toString methods from Object class.
 * 
 * @author seberthe
 *
 */
public class Item4 {
	
	public static void main(String... args){
		
		Animal lion = new Animal("Lion", 1);
		Animal lion1 = new Animal("Lion", 1);
		Animal childLion = new Animal("Child Lion", 0);
		
		System.out.println(lion);
		System.out.println(lion1);
		System.out.println(childLion);
		
		System.out.println(String.format("lion == lion => %s", lion == lion1));
		System.out.println(String.format("lion.equals(lion1) = %s => lion1.hashCode() == lion.hasCode() = %s", lion.equals(lion1), lion.hashCode() == lion1.hashCode()));
		System.out.println(String.format("lion.equals(childLion) = %s => lion1.hashCode() == childLion.hasCode() = %s", lion.equals(childLion), lion.hashCode() == childLion.hashCode()));
	}
}

class Animal {
	private String name;
	private int age;
	public Animal(String n, int a) {
		name = n;
		age = a;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() + 3 * age;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Animal)){
			return false;
		}
		Animal mOtheran = (Animal) obj;
		return name.equals(mOtheran.name) && age == mOtheran.age;
	}
	
	@Override
	public String toString() {
		return String.format("Here %s, I am %s of age", name, age);
	}
}


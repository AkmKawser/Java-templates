// Run: java SuperClass.java   (Java 17+)
public class SuperClass {
    static class Base { String hi() { return "Base"; } }                    // nested super class
    static class Derived extends Base { String hi() { return "Derived of " + super.hi(); } }

    public static void main(String[] args) {
        Animal a = new Animal();
        a.eat();
        System.out.println(a.name + ", secret via getter = " + a.getSecret());
        new Car().move();                                                    // abstract super class
        System.out.println(new Savings().type() + ", " + new Current().type()); // sealed super class
        System.out.println(new Derived().hi());
        Dog d = new Dog();                                                   // LivingThing > Mammal > Dog
        System.out.println(d.life() + ", " + d.legs());
        System.out.println(new Creature() instanceof Walkable);              // IMPLEMENTS
        // final class A {} class B extends A {}   // x a final class cannot be a super class
    }
}
class Animal {
    protected String name = "animal";                   // subclasses can use it
    private int secret = 1;                             // not accessible in subclasses
    public void eat() { System.out.println("eating"); }
    int getSecret() { return secret; }
}
abstract class Vehicle { abstract void move(); }
class Car extends Vehicle { void move() { System.out.println("car moves"); } }
sealed class Account permits Savings, Current { String type() { return "account"; } }
final class Savings extends Account { String type() { return "savings"; } }
final class Current extends Account { String type() { return "current"; } }
class LivingThing { String life() { return "alive"; } }
class Mammal extends LivingThing { String legs() { return "4 legs"; } }   // sub of LivingThing, super of Dog
class Dog extends Mammal { }
interface Walkable { }
class Creature implements Walkable { }

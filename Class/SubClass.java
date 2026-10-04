// Run: java SubClass.java   (Java 17+)
public class SubClass {
    static class Base { String hi() { return "Base"; } }
    static class Derived extends Base { String hi() { return "Derived of " + super.hi(); } }

    public static void main(String[] args) {
        Dog d = new Dog();                              // PUBLIC-style sub class
        d.eat();
        System.out.println(new Cat().name);             // package-private sub class
        System.out.println(new Puppy().name + " (final sub class)");
        System.out.println(new Derived().hi());
        System.out.println(new Truck().kind() + ", " + new Bike().kind() + ", " + new Car().kind());
        System.out.println(new MountainBike().kind());  // allowed: Bike is non-sealed
        new Labrador().train();                         // extends + implements
        // class Bad extends Animal, Pet {}            // x one parent only
        // class A extends String {}                    // x String is final
    }
}
class Animal { String name = "animal"; void eat() { System.out.println("eating"); } }
class Dog extends Animal { }
class Cat extends Animal { }
abstract class Pet extends Animal { }                   // abstract sub class
final class Puppy extends Dog { }                       // final sub class stops the chain
sealed class Vehicle permits Car, Bike, Truck { String kind() { return "vehicle"; } }
final class Car extends Vehicle { String kind() { return "car (final)"; } }
sealed class Truck extends Vehicle permits Lorry { String kind() { return "truck (sealed)"; } }
final class Lorry extends Truck { }
non-sealed class Bike extends Vehicle { String kind() { return "bike (non-sealed)"; } }
class MountainBike extends Bike { String kind() { return "mountain bike, reopened by non-sealed"; } }
interface Trainable { default void train() { System.out.println("training"); } }
interface Friendly { }
class Labrador extends Dog implements Trainable, Friendly { }

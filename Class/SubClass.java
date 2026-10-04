/*
 * ============================================================================
 *  SUB CLASS  (a ROLE, not a separate kind of class)
 * ============================================================================
 *
 * WHAT IT IS
 *   "Sub class" describes a RELATIONSHIP: any class that has an "extends"
 *   clause. Dog is a sub class of Animal when written "class Dog extends
 *   Animal". It is also called a child or derived class. The relationship is
 *   "is-a": a Dog IS an Animal.
 *
 * RULES
 *   - Exactly ONE "extends" parent (no multiple class inheritance).
 *   - It may also implement any number of interfaces:
 *         class Labrador extends Dog implements Trainable, Friendly
 *   - It inherits the parent's accessible members and can add its own.
 *   - Constructor chaining: the first thing a constructor does is call a parent
 *     constructor, super(...). If you write nothing, the compiler inserts
 *     super() with no arguments.
 *
 * OVERRIDING
 *   A sub class may replace an inherited method with its own version.
 *   - Same name and parameters; the return type may be a sub type (covariant).
 *   - The access may be widened but never narrowed (protected -> public is OK,
 *     public -> protected is not).
 *   - Use @Override so the compiler checks you got it right.
 *   - super.method() calls the parent's version (see Derived below).
 *   - final and private methods cannot be overridden.
 *
 * WHAT YOU CANNOT EXTEND
 *   final classes (String, Integer, ...), records, enums, and sealed classes
 *   unless you are in their permits list.
 *
 * MODIFIERS A SUB CLASS MAY CARRY
 *   abstract   still incomplete, left for its own sub classes
 *   final      the chain stops here
 *   sealed     it restricts its own sub classes
 *   non-sealed it reopens the family after a sealed parent
 *   If the parent is SEALED, the child MUST be exactly one of: final, sealed,
 *   or non-sealed (see Vehicle, Car, Truck, Bike).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Public and package-private sub classes, abstract and final sub classes,
 *   the three choices for children of a sealed class, a class extending a
 *   non-sealed class, and extends combined with implements.
 * ============================================================================
 */
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

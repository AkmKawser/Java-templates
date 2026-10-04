/*
 * ============================================================================
 *  SUPER CLASS  (a ROLE, not a separate kind of class)
 * ============================================================================
 *
 * WHAT IT IS
 *   "Super class" describes a RELATIONSHIP: it is any class that another class
 *   extends. Animal is the super class of Dog when Dog extends Animal. The same
 *   class can be a super class, a sub class, or both. Also called parent or
 *   base class.
 *
 * THE ROOT
 *   Every class has a super class. If you write no "extends", it is
 *   java.lang.Object. Object itself is the only class with no super class.
 *
 * WHAT A SUB CLASS RECEIVES
 *   public          inherited, usable everywhere
 *   protected       inherited, usable in the subclass (even in another package)
 *   package-private inherited ONLY if the subclass is in the same package
 *   private         NOT accessible. The data still exists inside the object,
 *                   but the subclass must use a getter (see getSecret()).
 *   Constructors are NEVER inherited. A subclass calls them with super(...).
 *
 * WHICH CLASSES CAN BE A SUPER CLASS
 *   normal / abstract class   yes
 *   sealed class              yes, but only its permitted classes can extend it
 *   final class               NO
 *   record                    NO (implicitly final)
 *   enum                      NO
 *
 * WHY IT MATTERS: POLYMORPHISM
 *   A super class reference can hold any sub class object:
 *       Animal a = new Dog();
 *   Calling an overridden method runs the Dog version at runtime.
 *
 * MULTI-LEVEL INHERITANCE
 *   LivingThing > Mammal > Dog. Mammal is a sub class of LivingThing and a
 *   super class of Dog at the same time. Java does not allow two parents.
 *
 * DESIGN TIP
 *   Make a class a super class on purpose. If it is not designed for
 *   extension, mark it final.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   protected vs private fields, an abstract super class, a sealed super class,
 *   multi-level inheritance, and a nested super class.
 * ============================================================================
 */
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

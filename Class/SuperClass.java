/*
 *
 * ============================================================================
 *  1. KINDS OF CLASSES
 * ============================================================================
 *
 *  Java has FOUR places where a type can be declared:
 *    top-level   directly in a .java file
 *    member      inside another class's body (static nested or inner)
 *    local       inside a method, constructor, or block
 *    anonymous   inside an expression, with no name
 *  Everything except top-level is called NESTED. Nested types that are not
 *  static are called INNER (inner member, local, anonymous).
 *
 *   #  KIND               ONE-LINE MEANING
 *  --  -----------------  ---------------------------------------------------
 *   1  Concrete class     Normal, complete class. Can be created with "new".
 *   2  Abstract class     Incomplete class. Cannot use "new"; meant to be extended.
 *   3  Final class        Cannot be extended.
 *   4  Static nested      Member class marked static. No outer object needed.
 *   5  Inner class        Member class without static. Tied to an outer object.
 *   6  Local class        Named class declared inside a method or block.
 *   7  Anonymous class    Nameless class declared and created in one expression.
 *   8  Enum class         Fixed set of named constants. Extends java.lang.Enum.
 *   9  Record class       Immutable data carrier. Extends java.lang.Record.
 *  10  Interface          A contract (not a class, but acts like one).
 *  11  Super class        A ROLE: the class that is extended (the parent).
 *  12  Sub class          A ROLE: the class that extends another (the child).
 *
 *  Kinds 11 and 12 are relationships, not separate kinds of class. Any class
 *  can be a super class, a sub class, or both at once.
 *
 * ============================================================================
 *  2. THE FOUR TEMPLATES
 * ============================================================================
 *  Square brackets [ ] mean optional. Clause order is always:
 *  extends, then implements, then permits.
 *
 *  CLASS
 *    [annotations] [access] [modifiers] class Name [<T>]
 *        [extends Super] [implements I1, I2] [permits A, B] {
 *        // fields, constructors, methods, initializer blocks, nested types
 *    }
 *
 *  INTERFACE
 *    [annotations] [access] [modifiers] interface Name [<T>]
 *        [extends I1, I2] [permits A, B] {
 *        // constants, abstract / default / static / private methods, nested types
 *    }
 *
 *  ENUM
 *    [annotations] [access] [modifiers] enum Name [implements I1, I2] {
 *        CONSTANT_ONE, CONSTANT_TWO;      // constants come first
 *        // fields, constructors, methods
 *    }
 *
 *  RECORD
 *    [annotations] [access] [modifiers] record Name [<T>](Type a, Type b)
 *        [implements I1, I2] {
 *        // compact constructor, extra constructors, methods, static members
 *    }
 *
 *  Modifiers you may write in the [modifiers] slot:
 *    class      abstract, final, sealed, non-sealed, static (nested only)
 *    interface  abstract (redundant), sealed, non-sealed, static (nested, implicit)
 *    enum       none (static is implicit when nested)
 *    record     final (redundant); static is implicit when nested
 *
 * ============================================================================
 *  3. WHAT EACH KEYWORD MEANS
 * ============================================================================
 *
 *  ACCESS (who can see it; choose at most one)
 *    public        Visible everywhere. A public top-level type must be in a
 *                  file with the same name.
 *    (no keyword)  Package-private. Visible only inside the same package.
 *    protected     Same package plus subclasses. NESTED types only.
 *    private       Visible only inside the enclosing class. NESTED types only.
 *
 *  MODIFIERS (what the type is allowed to do)
 *    abstract      Incomplete; cannot be instantiated; may contain abstract
 *                  methods that subclasses must implement.
 *    final         Cannot be extended.
 *    sealed        Only the classes listed in "permits" may extend/implement it.
 *    non-sealed    On a child of a sealed type: reopens it so anyone can extend.
 *    static        On a nested class: no outer object is needed.
 *
 *  KIND KEYWORDS (what sort of type you are declaring)
 *    class         A normal class.
 *    interface     A contract of methods; no constructors, no instance state.
 *    enum          A fixed set of named constants.
 *    record        An immutable data carrier with generated methods.
 *
 *  CLAUSES (relationships to other types)
 *    extends       Inherit from ONE parent class. For an interface: from MANY
 *                  interfaces. Not allowed on enums and records.
 *    implements    Promise to provide the methods of one or more interfaces.
 *                  Not allowed on an interface.
 *    permits       Lists the allowed direct subtypes of a sealed type. May be
 *                  left out if they are all in the same file.
 *
 *  OTHER PARTS OF THE TEMPLATE
 *    Name          The type's name (UpperCamelCase by convention).
 *    annotations   Metadata written with @, such as @Override or
 *                  @FunctionalInterface. They go before the access modifier.
 *    <T>           Type parameter: makes the type generic, e.g. Box<T>.
 *    (Type a, ...) Record components: they become the record's private final
 *                  fields, constructor parameters, and accessors.
 *    CONSTANT_ONE  Enum constants: the fixed instances of the enum.
 *    strictfp      Old floating-point modifier. No effect since Java 17.
 *
 *  SYMBOLS USED IN THESE FILES
 *    x             Marks a line that is ILLEGAL and therefore commented out.
 *    implicit      The compiler adds the modifier for you; you need not write it.
 *    redundant     Legal to write, but the compiler already adds it.
 *    nested only   Allowed only when the type is declared inside another type.
 *    effectively final   A local variable assigned once and never changed.
 * ############################################################################
 */

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

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

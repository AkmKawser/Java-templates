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
 *  ABSTRACT CLASS
 * ============================================================================
 *
 * WHAT IT IS
 *   A class that is deliberately INCOMPLETE. It is marked "abstract", cannot be
 *   instantiated with "new", and exists to be extended. It can mix finished
 *   (concrete) methods with unfinished (abstract) ones.
 *
 * WHAT IT CAN CONTAIN
 *   - abstract methods: a signature with no body ("abstract double area();")
 *   - normal methods with bodies
 *   - fields (including instance state, which interfaces cannot have)
 *   - constructors. You cannot call them with "new", but subclass constructors
 *     call them through super(...), so they initialise the shared state.
 *
 * RULES FOR ABSTRACT METHODS
 *   - They cannot be private (a subclass could never override them).
 *   - They cannot be static (static methods are not overridden).
 *   - They cannot be final (final forbids overriding).
 *   - The first concrete subclass MUST implement all of them, or it must be
 *     declared abstract too.
 *   - A class with even one abstract method must itself be abstract. An
 *     abstract class may also have zero abstract methods.
 *
 * ACCESS MODIFIERS
 *   public / package-private at top level; all four when nested.
 *
 * OTHER MODIFIERS
 *   abstract + final       ILLEGAL: abstract needs subclasses, final forbids them
 *   abstract + sealed      LEGAL and common: a closed family of subtypes
 *   static                 nested only
 *
 * CLAUSES
 *   extends: one class (abstract or concrete).
 *   implements: many interfaces. It does NOT have to implement their methods;
 *   it can leave them for its subclasses (see Bird and Sparrow below).
 *
 * WHEN TO USE ONE
 *   Use an abstract class when subclasses share state or behaviour AND must
 *   supply some part themselves. The "template method" pattern is the classic
 *   case: the abstract class fixes the steps, subclasses fill in one step
 *   (see Template and Report below).
 *
 * ABSTRACT CLASS vs INTERFACE
 *   Abstract class: single inheritance, can hold instance fields, has constructors.
 *   Interface:      many can be implemented, no instance fields, no constructors.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Abstract classes at every access level, abstract + sealed, extending an
 *   abstract class with another abstract class, and implementing an interface
 *   partially.
 * ============================================================================
 */
// Run: java AbstractClass.java   (Java 17+)
public class AbstractClass {

    protected abstract class P1 { abstract String name(); }              // PROTECTED (nested)
    class P1Impl extends P1 { String name() { return "protected abstract inner"; } }
    private abstract class P2 { abstract String name(); }                // PRIVATE (nested)
    class P2Impl extends P2 { String name() { return "private abstract inner"; } }
    abstract static class Base { abstract String hi(); }                  // STATIC (nested)
    static class BaseImpl extends Base { String hi() { return "abstract static nested"; } }

    public static void main(String[] args) {
        // new Vehicle();  // x cannot instantiate an abstract class
        AbstractClass o = new AbstractClass();
        System.out.println(o.new P1Impl().name());
        System.out.println(o.new P2Impl().name());
        System.out.println(new BaseImpl().hi());
        System.out.println(new Circle().area());                          // PUBLIC abstract Shape
        System.out.println(new Report().render());                        // PACKAGE-PRIVATE abstract Template
        System.out.println(new Card().pay() + " / " + new Cash().pay());  // abstract + sealed
        System.out.println(new Dog().name() + " is a pet");               // EXTENDS
        System.out.println(new Sparrow().fly());                          // IMPLEMENTS
    }
}

abstract class Shape { abstract double area(); }                          // PUBLIC-style abstract class
class Circle extends Shape { double area() { return 3.14; } }
abstract class Template { String render() { return "template: " + body(); } abstract String body(); }
class Report extends Template { String body() { return "report body"; } }
abstract sealed class Payment permits Card, Cash { abstract String pay(); }
final class Card extends Payment { String pay() { return "card"; } }
non-sealed class Cash extends Payment { String pay() { return "cash"; } }
abstract class Animal { String name() { return "animal"; } }
abstract class Pet extends Animal { }                                     // EXTENDS
class Dog extends Pet { String name() { return "Dog"; } }
interface Flyer { String fly(); }
abstract class Bird implements Flyer { }                                  // IMPLEMENTS (leaves fly() open)
class Sparrow extends Bird { public String fly() { return "sparrow flies"; } }

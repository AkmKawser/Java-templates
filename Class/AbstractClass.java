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

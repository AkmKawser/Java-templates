/*
 * ============================================================================
 *  FINAL CLASS
 * ============================================================================
 *
 * WHAT IT IS
 *   A class that CANNOT be extended. Writing "class X extends FinalClass"
 *   is a compile error. The class itself works normally: it can extend one
 *   class, implement interfaces, and be instantiated.
 *
 * REAL EXAMPLES IN THE JDK
 *   String, Integer, Long, Double, Math, and System are all final.
 *
 * EFFECT ON METHODS
 *   Every method of a final class is effectively final too, because no
 *   subclass can exist to override it. You do not need to write "final" on
 *   each method.
 *
 * WHY MAKE A CLASS FINAL
 *   1. Immutability and safety: nobody can subclass it and change its behaviour
 *      (this is why String is final; a malicious subclass could break security).
 *   2. Design intent: the class was not designed for inheritance.
 *   3. Predictability: callers know the exact behaviour of the type.
 *
 * MODIFIER RULES
 *   final + abstract       ILLEGAL (abstract needs subclasses)
 *   final + sealed         ILLEGAL (sealed needs permitted subclasses)
 *   final + non-sealed     ILLEGAL
 *   final + static         legal for a nested class
 *   Access modifiers work as usual (protected/private only when nested).
 *
 * "final" ON OTHER THINGS (do not confuse them)
 *   final class   -> cannot be extended
 *   final method  -> cannot be overridden
 *   final field / variable -> can be assigned only once
 *
 * ALREADY IMPLICITLY FINAL
 *   Records are always final. Enums are final unless a constant has a body.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   final classes at every access level, and a final class that extends a
 *   normal class and implements an interface. The illegal cases are commented out.
 * ============================================================================
 */
// Run: java FinalClass.java   (Java 17+)
public class FinalClass {

    protected final class P1 { String hi() { return "protected final inner"; } }  // PROTECTED (nested)
    private final class P2 { String hi() { return "private final inner"; } }      // PRIVATE (nested)
    static final class Config { String hi() { return "static final nested"; } }   // STATIC (nested)

    public static void main(String[] args) {
        FinalClass o = new FinalClass();
        System.out.println(o.new P1().hi());
        System.out.println(o.new P2().hi());
        System.out.println(new Config().hi());
        System.out.println(new Money().show());                 // public-style final class
        System.out.println(new Helper().show());                // package-private final class
        System.out.println(new Cat().show());                   // final class that extends
        System.out.println(new Report().show());                // final class that implements
        // class Hack extends Money {}          // x cannot extend a final class
        // abstract final / sealed final        // x contradictions
    }
}

final class Money { String show() { return "Money (final)"; } }
final class Helper { String show() { return "Helper (final, package-private)"; } }
class Animal { }
final class Cat extends Animal { String show() { return "Cat extends Animal"; } }
interface Printable { }
final class Report implements Printable { String show() { return "Report implements Printable"; } }

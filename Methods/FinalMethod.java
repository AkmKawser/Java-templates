/*
 *
 * ============================================================================
 *  1. KINDS OF METHODS
 * ============================================================================
 *
 *  A method is a named block of code that can take parameters and return a
 *  value. The kinds below are classified by DIFFERENT questions, so one method
 *  can belong to several at once (for example a public static final method).
 *
 *   #  KIND                 QUESTION IT ANSWERS / MEANING
 *  --  -------------------  ---------------------------------------------------
 *   1  Instance method      Belongs to an OBJECT. Called on an object, can use
 *                           "this" and instance variables.
 *   2  Static method        Belongs to the CLASS. Called with the class name, no
 *                           "this", cannot use instance members directly.
 *   3  Abstract method      Has NO body; declared with abstract (or in an
 *                           interface). Subclasses must supply the body.
 *   4  Final method         Cannot be overridden by a subclass.
 *   5  Overloaded method    Same name, DIFFERENT parameter lists, in one class.
 *                           Chosen at compile time.
 *   6  Overridden method    A subclass REPLACES an inherited method with the
 *                           same signature. Chosen at run time.
 *   7  Concrete method      Has a body (the opposite of abstract).
 *   8  Synchronized method  Only one thread at a time may run it (per lock).
 *   9  Getter / Setter      Accessor methods that read / write a private field.
 *  10  Constructor          Special method that builds an object when you write
 *                           "new". Same name as the class, no return type.
 *
 *  How they group:
 *    belongs to          instance (1) or static (2)
 *    has a body?         abstract (3) or concrete (7)
 *    inheritance         final (4), overridden (6)
 *    signature           overloaded (5)
 *    thread safety       synchronized (8)
 *    purpose             getter / setter (9)
 *    object creation     constructor (10)
 *  Also exist: native (implemented in C/C++), default / static / private methods
 *  inside interfaces, and strictfp (no effect since Java 17).
 *
 * ============================================================================
 *  2. THE TEMPLATES
 * ============================================================================
 *  Square brackets [ ] mean optional.
 *
 *  METHOD
 *    [annotations] [access] [modifiers] [<T>] ReturnType name([parameters])
 *        [throws Exception1, Exception2] {
 *        // body
 *    }
 *
 *  ABSTRACT METHOD (no body, ends with a semicolon)
 *    [annotations] [access] abstract [<T>] ReturnType name([parameters])
 *        [throws Exception1, Exception2] ;
 *
 *  CONSTRUCTOR (no return type; the name is the class name)
 *    [annotations] [access] ClassName([parameters]) [throws Exception1] {
 *        // optional first line: this(...)  or  super(...)
 *        // body
 *    }
 *
 *  GETTER AND SETTER (a naming convention, not new syntax)
 *    [access] Type getName() { return name; }          boolean: isName()
 *    [access] void setName(Type name) { this.name = name; }
 *
 *  METHODS INSIDE AN INTERFACE
 *    ReturnType name(...) ;                       abstract (implicitly public)
 *    default ReturnType name(...) { ... }         has a body, inherited
 *    static ReturnType name(...) { ... }          called as Interface.name()
 *    private ReturnType name(...) { ... }         helper for the other methods
 *
 *  Modifiers allowed in the [modifiers] slot of a method:
 *    static  final  abstract  synchronized  native  strictfp  default(interface)
 *
 *  Illegal combinations:
 *    abstract + private | static | final | synchronized | native
 *    private + protected | public (only one access level)
 *    Constructors accept ONLY the four access levels (no static, final,
 *    abstract, synchronized, native).
 *
 * ============================================================================
 *  3. WHAT EACH KEYWORD MEANS
 * ============================================================================
 *
 *  ACCESS (who can call it; at most one)
 *    public        Callable from anywhere.
 *    (no keyword)  Package-private: callable inside the same package.
 *    protected     Same package plus subclasses.
 *    private       Callable only inside the declaring class.
 *
 *  MODIFIERS
 *    static        Belongs to the class; no object needed; no "this".
 *    final         Cannot be overridden.
 *    abstract      No body; subclasses must implement it.
 *    synchronized  Acquires a lock first: the object's lock (instance method) or
 *                  the Class object's lock (static method).
 *    native        Implemented in another language (no Java body).
 *    default       In an interface: a method with a body that implementers inherit.
 *    strictfp      Old floating-point modifier; no effect since Java 17.
 *
 *  PARTS OF A METHOD
 *    ReturnType    The type of value returned, or void for none.
 *    name          The method's name (lowerCamelCase by convention).
 *    parameters    Variables that receive the caller's arguments.
 *    signature     The name plus the parameter TYPES in order. The return type is
 *                  NOT part of the signature.
 *    throws        Lists the checked exceptions the method may throw.
 *    <T>           Type parameter: makes the method generic.
 *    return        Ends the method and (if not void) gives back a value.
 *
 *  OTHER TERMS
 *    this          The current object. this(...) calls another constructor.
 *    super         The parent. super.method() calls the parent's version;
 *                  super(...) calls the parent's constructor.
 *    @Override     Asks the compiler to check that you really override something.
 *    overload      Same name, different parameters (compile-time choice).
 *    override      Same signature in a subclass (run-time choice).
 *    covariant return   An override may return a SUBTYPE of the original type.
 *    varargs       A final parameter written Type... that takes any number of values.
 *    polymorphism  The same call runs different code depending on the real type.
 *
 *  SYMBOLS USED IN THESE FILES
 *    x             Marks a line that is ILLEGAL and therefore commented out.
 */

/*
 * ============================================================================
 *  FINAL METHOD
 * ============================================================================
 *
 * WHAT IT IS
 *   A method declared with "final". A subclass CANNOT override it. The method is
 *   still inherited and callable; it just cannot be replaced.
 *
 * WHY MAKE A METHOD FINAL
 *   1. Protect an algorithm: the parent fixes a sequence of steps that
 *      subclasses must not change (the template method pattern, see process()).
 *   2. Safety: behaviour that must stay correct, such as security checks.
 *   3. Calling it from a constructor is safe, because it cannot be overridden by
 *      a subclass that is not yet initialised.
 *
 * MODIFIER RULES
 *   final + abstract   ILLEGAL (abstract demands overriding)
 *   final + private    legal but pointless: private methods cannot be overridden
 *   final + static     legal; it also stops subclasses from hiding the method
 *   final + synchronized, public, protected, package-private: all fine
 *
 * FINAL ON OTHER THINGS (do not confuse)
 *   final class     cannot be extended (every method is then effectively final)
 *   final method    cannot be overridden
 *   final variable  can be assigned only once
 *
 * RECORDS AND ENUMS
 *   A record is implicitly final, so its methods cannot be overridden by a
 *   subclass (there can be none).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   A final method, a final method used as a template with overridable hooks,
 *   final static, the redundant private final, and the errors from trying to
 *   override or hide a final method.
 * ============================================================================
 */
// Run: java FinalMethod.java   (Java 17+)
public class FinalMethod {

    static class Parent {
        final void show() { System.out.println("Parent.show (final)"); }
        public final String id() { return "ID-1"; }
        void normal() { System.out.println("Parent.normal"); }

        final void process() {                            // TEMPLATE: fixed order, customizable steps
            start();
            System.out.println("  processing (cannot be changed)");
            finish();
        }
        void start() { System.out.println("  Parent.start"); }
        void finish() { System.out.println("  Parent.finish"); }

        static final String util() { return "Parent.util (static final)"; }
        private final void hidden() { }                   // legal but redundant
        // abstract final void bad();                     // x final + abstract
    }

    static class Child extends Parent {
        // void show() { }                                // x cannot override the final method show()
        // public String id() { return "ID-2"; }          // x cannot override the final method id()
        // static String util() { return "x"; }           // x cannot hide the final static method util()

        @Override void normal() { System.out.println("Child.normal (overridden)"); }
        @Override void start() { System.out.println("  Child.start (custom step)"); }
    }

    public static void main(String[] args) {
        Child c = new Child();
        c.show();                                         // inherited, but cannot be replaced
        System.out.println(c.id());
        c.normal();                                       // a normal method CAN be overridden
        System.out.println("process():");
        c.process();                                      // fixed template, Child's hook runs
        System.out.println(Parent.util());
    }
}

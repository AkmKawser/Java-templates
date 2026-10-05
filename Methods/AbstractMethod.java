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
 *  ABSTRACT METHOD
 * ============================================================================
 *
 * WHAT IT IS
 *   A method with a signature but NO BODY, ended by a semicolon:
 *       abstract double area();
 *   It says WHAT must be done and leaves HOW to the subclasses.
 *
 * WHERE IT CAN APPEAR
 *   - an abstract class (the class must be declared abstract)
 *   - an interface (every non-default, non-static, non-private method is
 *     implicitly public abstract)
 *   - an enum, when every constant provides a body for it
 *
 * ACCESS MODIFIERS
 *   public, protected, package-private. NOT private: a subclass could never see
 *   it, so it could never implement it.
 *
 * ILLEGAL COMBINATIONS
 *   abstract + private      (cannot be implemented)
 *   abstract + static       (static methods are not overridden)
 *   abstract + final        (final forbids the overriding abstract requires)
 *   abstract + synchronized (no body to lock)
 *   abstract + native       (native means the body exists elsewhere)
 *
 * RULES FOR SUBCLASSES
 *   - The first CONCRETE subclass must implement every inherited abstract method,
 *     or it must itself be declared abstract.
 *   - The implementation may keep or WIDEN the access (protected -> public),
 *     never narrow it.
 *   - The implementation may throw fewer or narrower checked exceptions.
 *   - The abstract class cannot be instantiated; a reference of the abstract type
 *     can hold any concrete subclass object.
 *
 * WHY USE IT
 *   It forces every subclass to supply a behaviour while letting the parent
 *   write shared code that calls it (the template method pattern, see
 *   Shape.describe below).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Abstract methods at three access levels, throws on an abstract method, a
 *   concrete method calling abstract ones, widening access when implementing,
 *   a partial implementation, interface abstract methods, and the illegal cases.
 * ============================================================================
 */
// Run: java AbstractMethod.java   (Java 17+)
public class AbstractMethod {

    static abstract class Shape {
        abstract double area();                           // PACKAGE-PRIVATE abstract
        public abstract String name();                    // PUBLIC abstract
        protected abstract String color();                // PROTECTED abstract
        abstract void risky() throws java.io.IOException; // an abstract method may declare throws
        // private abstract void a();                     // x abstract + private
        // abstract static void b();                      // x abstract + static
        // abstract final void c();                       // x abstract + final
        // abstract synchronized void d();                // x abstract + synchronized
        // abstract native void e();                      // x abstract + native
        // abstract void f() { }                          // x an abstract method cannot have a body

        public String describe() {                        // CONCRETE method that uses the abstract ones
            return name() + " (" + color() + ") area = " + area();
        }
    }

    static class Circle extends Shape {
        double area() { return 3.0 * 2 * 2; }
        public String name() { return "circle"; }
        public String color() { return "red"; }           // protected -> public: WIDER access is allowed
        void risky() { }                                  // may drop the throws clause
        // String color() { return "red"; }               // x cannot narrow protected to package-private
    }

    static abstract class Partial extends Shape {         // implements only some, so it stays abstract
        public String name() { return "partial"; }
    }
    static class Square extends Partial {                 // the first concrete class finishes the job
        double area() { return 4; }
        protected String color() { return "blue"; }
        void risky() { }
    }

    interface Greeter {
        void hello();                                     // implicitly public abstract
        String bye();
    }

    public static void main(String[] args) {
        // Shape s = new Shape();                         // x an abstract class cannot be instantiated
        Shape s = new Circle();                           // an abstract type can refer to a concrete subclass
        System.out.println(s.describe());
        System.out.println(new Square().describe());

        Greeter g = new Greeter() {                       // implementing the interface's abstract methods
            public void hello() { System.out.println("hello"); }
            public String bye() { return "bye"; }
        };
        g.hello();
        System.out.println(g.bye());
    }
}

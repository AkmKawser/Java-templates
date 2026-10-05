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
 *  OVERRIDDEN METHOD
 * ============================================================================
 *
 * WHAT IT IS
 *   A subclass method that REPLACES an inherited instance method by declaring the
 *   SAME NAME and the SAME PARAMETER TYPES. It is run-time polymorphism: the
 *   version that runs depends on the object's REAL type, not on the reference's
 *   declared type.
 *       Animal a = new Dog();
 *       a.speak();     // runs Dog.speak()
 *
 * RULES FOR A LEGAL OVERRIDE
 *   1. Same name and same parameter types (the signature).
 *   2. Return type: the same, or a SUBTYPE (covariant return).
 *   3. Access: the same or WIDER (protected -> public). Never narrower.
 *   4. throws: no new or broader CHECKED exceptions (fewer or narrower is fine).
 *   5. The parent method must be inherited and overridable:
 *        final     cannot be overridden
 *        private   not inherited, so a same-named method is a NEW method
 *        static    not overridden; a same-signature static method HIDES it
 *   6. Use @Override so the compiler reports a mistake (wrong name, wrong
 *      parameters) instead of silently creating an unrelated method.
 *
 * CALLING THE PARENT'S VERSION
 *   super.method() runs the parent's implementation from inside the override.
 *
 * WHAT YOU OFTEN OVERRIDE
 *   toString(), equals(), hashCode() from Object, abstract methods from
 *   abstract classes and interfaces, and interface default methods.
 *
 * OVERRIDING vs OVERLOADING
 *   override: subclass, same signature, chosen at RUN time.
 *   overload: same class, different parameters, chosen at COMPILE time.
 *
 * FIELDS ARE NOT OVERRIDDEN
 *   A field with the same name in a subclass HIDES the parent's field; there is no
 *   polymorphism for fields.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   A basic override, covariant return with wider access, narrower throws, super
 *   calls, static hiding, a private method that is not an override, an interface
 *   default override, and the typo that @Override catches.
 * ============================================================================
 */
// Run: java OverriddenMethod.java   (Java 17+)
public class OverriddenMethod {

    static class Animal {
        String speak() { return "..."; }
        protected Animal create() { return new Animal(); }
        void work() throws Exception { }
        @Override public String toString() { return "Animal"; }
        static String stat() { return "Animal.stat"; }
        private String priv() { return "Animal.priv"; }
        String callPriv() { return priv(); }              // always calls Animal's private method
    }

    static class Dog extends Animal {
        @Override String speak() { return "Woof"; }                    // basic override
        @Override public Dog create() { return new Dog(); }            // covariant return (Dog) + wider access (public)
        @Override void work() { }                                      // narrower throws: none
        @Override public String toString() { return "Dog"; }           // overriding an Object method
        static String stat() { return "Dog.stat"; }                    // HIDES the parent's static method
        private String priv() { return "Dog.priv"; }                   // NOT an override: the parent's is private
        String parentSpeak() { return super.speak(); }                 // call the parent's version
        // @Override String speek() { return "typo"; }                 // x @Override reports: method does not override
        // @Override private String speak() { return "x"; }            // x cannot reduce visibility
    }

    interface Walker { default String move() { return "walks"; } }
    static class Robot implements Walker {
        @Override public String move() { return "rolls"; }             // overriding an interface default method
    }

    public static void main(String[] args) {
        Animal a = new Dog();                             // declared type Animal, real type Dog
        System.out.println(a.speak());                    // Woof: the REAL type decides
        System.out.println(a + " / " + a.create());       // toString and covariant create() are Dog's
        System.out.println(((Dog) a).parentSpeak());      // super.speak()

        System.out.println(Animal.stat() + " / " + Dog.stat());   // static: hidden, not overridden
        System.out.println(a.callPriv());                 // Animal.priv: private methods are not overridden

        Walker w = new Robot();
        System.out.println("robot " + w.move());
    }
}

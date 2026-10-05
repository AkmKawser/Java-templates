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
 *  INSTANCE METHOD
 * ============================================================================
 *
 * WHAT IT IS
 *   A method declared WITHOUT static. It belongs to an object, so you call it on
 *   an object: obj.method(). Inside it, "this" is that object, and it can read
 *   and change the object's instance variables.
 *
 * WHAT IT CAN USE
 *   - this and every instance member (fields, other instance methods)
 *   - every static member as well
 *
 * ACCESS MODIFIERS (choose at most one)
 *   public / (none) / protected / private: the same four levels as for fields.
 *   private instance methods are helpers that only the class itself can call.
 *
 * OTHER MODIFIERS
 *   final         cannot be overridden (see FinalMethod)
 *   synchronized  one thread at a time per object (see SynchronizedMethod)
 *   abstract      leaves it without a body (see AbstractMethod)
 *   native        implemented outside Java
 *   static        NOT here: that makes it a static method (see StaticMethod)
 *
 * RETURN TYPE
 *   void (nothing), a primitive, a reference type, or the class itself. Returning
 *   "this" lets callers chain calls: obj.a().b().c().
 *
 * throws
 *   May declare checked exceptions; the caller must catch or declare them.
 *
 * INHERITANCE
 *   Instance methods are inherited by subclasses (unless private) and can be
 *   overridden. The version that runs depends on the object's real type.
 *
 * COMMON MISTAKES
 *   - Calling an instance method from main without creating an object.
 *   - Forgetting that two objects have independent state.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   The four access levels, return types, returning this for chaining, throws,
 *   final and synchronized, independent state per object, and access from
 *   another class.
 * ============================================================================
 */
// Run: java InstanceMethod.java   (Java 17+)
public class InstanceMethod {

    private int count = 0;
    private final String name;

    InstanceMethod(String name) { this.name = name; }

    public void incrementPublic() { count++; }            // PUBLIC
    void incrementPackage() { count++; }                  // PACKAGE-PRIVATE
    protected void incrementProtected() { count++; }      // PROTECTED
    private void incrementPrivate() { count++; }          // PRIVATE: helper, only this class can call it
    public void viaPrivate() { incrementPrivate(); }      // a public method that uses the private helper

    public int getCount() { return count; }               // returns a primitive
    public String describe() { return name + ":" + count; } // returns a reference, uses this.name implicitly
    public InstanceMethod chain() { count++; return this; } // returns this, so calls can be chained

    void risky() throws Exception {                       // declares a checked exception
        throw new Exception("boom");
    }

    public final void notOverridable() { count += 10; }   // FINAL: allowed
    public synchronized void safeIncrement() { count++; } // SYNCHRONIZED: allowed
    // public abstract void x();                          // x a method with a body cannot be abstract here
    // the keyword static would turn this into a static method

    static class Other {                                  // another class, same package
        void use(InstanceMethod m) {
            m.incrementPublic();
            m.incrementPackage();
            m.incrementProtected();
            // m.incrementPrivate();                      // x private: only InstanceMethod can call it
        }
    }

    public static void main(String[] args) {
        InstanceMethod a = new InstanceMethod("a");       // an OBJECT is required
        InstanceMethod b = new InstanceMethod("b");
        a.incrementPublic();
        a.incrementPublic();
        b.incrementPublic();
        System.out.println(a.describe() + " " + b.describe());   // independent state per object

        a.viaPrivate();
        a.chain().chain().chain();                        // chaining
        System.out.println("after private + chain: " + a.getCount());

        a.notOverridable();
        a.safeIncrement();
        System.out.println("final + synchronized: " + a.getCount());

        new Other().use(b);
        System.out.println("after Other.use: " + b.getCount());

        try {
            a.risky();
        } catch (Exception e) {
            System.out.println("caught: " + e.getMessage());
        }
        // InstanceMethod.incrementPublic();              // x non-static method cannot be called with the class name
    }
}

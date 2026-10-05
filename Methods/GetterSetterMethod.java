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
 *  GETTER AND SETTER METHODS
 * ============================================================================
 *
 * WHAT THEY ARE
 *   Ordinary instance methods that follow a naming convention to read (getter) and
 *   write (setter) a PRIVATE field. They are the standard way to apply
 *   ENCAPSULATION: hide the data, expose controlled access to it.
 *
 * NAMING CONVENTION
 *   field name        getter           setter
 *   String name       getName()        setName(String name)
 *   int age           getAge()         setAge(int age)
 *   boolean active    isActive()       setActive(boolean active)
 *   (frameworks and tools such as JavaBeans rely on these names.)
 *
 * TEMPLATE
 *   private Type name;
 *   public Type getName()          { return name; }
 *   public void setName(Type name) { this.name = name; }
 *
 * WHY NOT A PUBLIC FIELD?
 *   - A setter can VALIDATE (reject a negative balance).
 *   - You can make a field read-only (getter only) or write-only (setter only).
 *   - You can change the internal representation later without breaking callers.
 *   - You can add logging, caching, or derived values.
 *
 * VARIATIONS
 *   read-only      getter only (the field may be final)
 *   write-only     setter only (for example a password)
 *   fluent setter  returns this so calls can be chained
 *   immutable      final fields, no setters
 *   defensive copy return a copy of a mutable field so callers cannot change the
 *                  internal state through the getter
 *
 * ACCESS MODIFIERS
 *   Usually public. A setter is often protected or package-private to limit who
 *   may change the state.
 *
 * RECORDS
 *   A record generates accessors automatically, named after the component
 *   (name(), not getName()) and has no setters.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Getters and setters with validation, an is-getter for boolean, a read-only
 *   field, a write-only field, a fluent setter, a defensive copy, a limited-access
 *   setter, and a record for comparison.
 * ============================================================================
 */
// Run: java GetterSetterMethod.java   (Java 17+)
import java.util.ArrayList;
import java.util.List;

public class GetterSetterMethod {

    static class Account {
        private final String id;                          // read-only: getter only
        private String owner;
        private double balance;
        private boolean active = true;
        private String pin;                               // write-only: setter only
        private final List<String> tags = new ArrayList<>();

        Account(String id, String owner) { this.id = id; this.owner = owner; }

        public String getId() { return id; }              // READ-ONLY (no setter exists)

        public String getOwner() { return owner; }
        public void setOwner(String owner) {              // SETTER with validation
            if (owner == null || owner.isBlank()) throw new IllegalArgumentException("owner required");
            this.owner = owner;
        }

        public double getBalance() { return balance; }
        public void setBalance(double balance) {
            if (balance < 0) throw new IllegalArgumentException("balance cannot be negative");
            this.balance = balance;
        }

        public boolean isActive() { return active; }      // boolean getter uses "is"
        public void setActive(boolean active) { this.active = active; }

        public void setPin(String pin) { this.pin = pin; }            // WRITE-ONLY: no getter
        public boolean checkPin(String attempt) { return pin != null && pin.equals(attempt); }

        public Account withOwner(String owner) {          // FLUENT setter: returns this
            setOwner(owner);
            return this;
        }

        public List<String> getTags() { return List.copyOf(tags); }   // DEFENSIVE COPY
        public void addTag(String tag) { tags.add(tag); }

        protected void setBalanceInternal(double b) { this.balance = b; }  // limited-access setter
    }

    record Point(int x, int y) { }                        // record: accessors x() and y(), no setters

    public static void main(String[] args) {
        Account acc = new Account("A-1", "Ali");
        System.out.println(acc.getId() + " " + acc.getOwner() + " " + acc.getBalance() + " " + acc.isActive());

        acc.setBalance(250.5);
        acc.setActive(false);
        System.out.println(acc.getBalance() + " " + acc.isActive());

        try {
            acc.setBalance(-5);                           // the setter protects the object
        } catch (IllegalArgumentException e) {
            System.out.println("rejected: " + e.getMessage());
        }
        // acc.balance = -5;                              // x private: no way around the validation

        acc.setPin("1234");
        System.out.println("pin ok? " + acc.checkPin("1234") + ", wrong? " + acc.checkPin("0000"));

        System.out.println(acc.withOwner("Sara").getOwner());          // fluent

        acc.addTag("vip");
        List<String> copy = acc.getTags();
        try {
            copy.add("hack");                             // the copy is unmodifiable
        } catch (UnsupportedOperationException e) {
            System.out.println("tags are protected: " + acc.getTags());
        }

        Point p = new Point(3, 4);
        System.out.println(p.x() + "," + p.y() + " (record accessors)");
    }
}

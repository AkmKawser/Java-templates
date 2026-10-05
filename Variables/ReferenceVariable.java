/*
 *
 * ============================================================================
 *  1. KINDS OF VARIABLES
 * ============================================================================
 *
 *  A variable is a named storage location with a TYPE. Java has two families
 *  of types: PRIMITIVE (byte short int long float double char boolean), which
 *  hold the value itself, and REFERENCE (classes, interfaces, enums, records,
 *  arrays), which hold a reference to an object or null.
 *
 *  Where a variable is declared decides its kind:
 *    inside a class body, outside every method   -> field (instance or static)
 *    inside a method, constructor, or block      -> local variable
 *    inside the parentheses of a method header   -> parameter
 *
 *   #  KIND                MEANING
 *  --  ------------------  --------------------------------------------------
 *   1  Local variable      Declared inside a method, constructor, or block.
 *                          Lives only while that block runs. No default value.
 *   2  Instance variable   Field declared WITHOUT static. One separate copy
 *                          per object. Lives on the heap with the object.
 *   3  Static variable     Field declared WITH static (class variable). ONE
 *                          copy shared by every object of the class.
 *   4  Parameter           Variable in a method/constructor/lambda/catch
 *                          header. Receives a COPY of the caller's value.
 *   5  Reference variable  A variable whose type is a class, interface, enum,
 *                          record, or array. It holds a reference, not the
 *                          object. This is NOT a separate storage kind: a
 *                          local, instance, static, or parameter variable can
 *                          each be a reference variable.
 *
 *  Kinds 1 to 4 are classified by WHERE the variable lives. Kind 5 is
 *  classified by WHAT TYPE it has, so it overlaps with the others.
 *
 *  Default values (fields only; locals get none):
 *    byte short int long -> 0      float double -> 0.0
 *    char -> '\u0000'              boolean -> false      references -> null
 *
 * ============================================================================
 *  2. THE TEMPLATES
 * ============================================================================
 *  Square brackets [ ] mean optional.
 *
 *  FIELD (instance or static)
 *    [annotations] [access] [static] [final] [transient] [volatile]
 *        Type name [= value] [, name2 [= value2]] ;
 *
 *  LOCAL VARIABLE
 *    [final] Type name [= value] ;
 *    [final] var name = value ;              (Java 10+, type is inferred)
 *
 *  PARAMETER
 *    [final] Type name                       (inside the method's parentheses)
 *    Type... name                            (varargs, must be the LAST one)
 *
 *  REFERENCE VARIABLE
 *    ClassName name = new ClassName(args) ;  or  = null ;  or  = otherReference ;
 *
 *  Which modifiers each kind accepts:
 *    kind        access   static   final   transient   volatile
 *    local        no       no       yes      no          no
 *    instance     yes      no       yes      yes         yes
 *    static       yes      (is)     yes      yes         yes
 *    parameter    no       no       yes      no          no
 *  Illegal combination: final + volatile.
 *
 * ============================================================================
 *  3. WHAT EACH KEYWORD MEANS
 * ============================================================================
 *
 *  ACCESS (who can use the field; at most one; fields only)
 *    public        Visible everywhere.
 *    (no keyword)  Package-private: visible inside the same package.
 *    protected     Same package plus subclasses.
 *    private       Visible only inside the declaring class.
 *
 *  MODIFIERS
 *    static        The field belongs to the CLASS, not to any object.
 *    final         Can be assigned only once. For a reference variable, the
 *                  reference is fixed but the object it points to can change.
 *    transient     The field is skipped when the object is serialized.
 *    volatile      Every read/write goes to main memory, so all threads see
 *                  the latest value (visibility between threads).
 *
 *  OTHER TERMS
 *    var           Let the compiler infer the type (locals only).
 *    new           Creates an object and returns a reference to it.
 *    null          The reference that points to no object.
 *    this          The current object; used as this.name to reach a field
 *                  when a parameter or local has the same name.
 *    ...           Varargs: a parameter that accepts zero or more values.
 *    blank final   A final variable with no initial value; it must be assigned
 *                  exactly once (in a constructor, static block, or later).
 *    effectively final   A local/parameter assigned once and never changed;
 *                  lambdas and inner classes may capture it.
 *    scope         The region of code where the variable's name can be used.
 *    shadowing     A local or parameter hides a field of the same name.
 *    pass-by-value Java always passes a COPY of the variable's value. For a
 *                  reference, the copy is the reference, not the object.
 *
 *  SYMBOLS USED IN THESE FILES
 *    x             Marks a line that is ILLEGAL and therefore commented out.
 */

/*
 * ============================================================================
 *  REFERENCE VARIABLE (not a class variable)
 * ============================================================================
 *
 * WHAT IT IS
 *   A variable whose type is a class, interface, enum, record, or array. It does
 *   NOT contain the object. It contains a REFERENCE (think: an address) to an
 *   object on the heap, or null if it points at nothing.
 *
 * NOT TO BE CONFUSED WITH "CLASS VARIABLE"
 *   "Class variable" is another name for a STATIC variable (a storage kind).
 *   "Reference variable" describes the variable's TYPE. They are different
 *   ideas. A reference variable can be local, instance, static, or a parameter.
 *
 * PRIMITIVE vs REFERENCE
 *   primitive variable: holds the value itself.        int x = 5;
 *   reference variable: holds a reference to an object. Box b = new Box();
 *   Assigning a reference copies the REFERENCE, not the object, so two
 *   variables can point to the same object (aliasing).
 *
 * KEY BEHAVIOURS
 *   - Default value (as a field) is null. Using a null reference to reach a
 *     member throws NullPointerException.
 *   - "==" compares references (same object?). equals() compares contents
 *     (same value?) if the class defines it, as String does.
 *   - The reference's declared type decides what you may CALL. The object's
 *     real type decides which overridden method RUNS (polymorphism):
 *         Animal a = new Dog();   a.sound() runs Dog's version.
 *   - final reference: the variable cannot be re-pointed, but the object it
 *     points to can still be changed.
 *   - When no reference points to an object any more, the garbage collector may
 *     reclaim it.
 *
 * MODIFIERS
 *   The same as for any variable of that kind (local: final only; field: access,
 *   static, final, transient, volatile).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Aliasing, == vs equals, null and NullPointerException, polymorphism through
 *   a super type reference, a final reference, array and interface references,
 *   and reference variables in each variable kind.
 * ============================================================================
 */
// Run: java ReferenceVariable.java   (Java 17+)
import java.util.ArrayList;
import java.util.List;

public class ReferenceVariable {

    static String staticRef = "static reference";     // a STATIC reference variable
    String instanceRef = "instance reference";        // an INSTANCE reference variable
    Box defaultNull;                                  // reference field: default is null

    static class Box { int v; Box(int v) { this.v = v; } }
    static class Animal { String sound() { return "..."; } }
    static class Dog extends Animal { String sound() { return "Woof"; } void fetch() { System.out.println("fetching"); } }

    static void useParameter(Box p) {                 // a PARAMETER that is a reference variable
        p.v = 100;                                    // changes the shared object
    }

    public static void main(String[] args) {
        int primitive = 5;                            // PRIMITIVE: holds the value
        Box a = new Box(1);                           // LOCAL reference variable: holds a reference
        System.out.println("primitive = " + primitive + ", a.v = " + a.v);

        System.out.println("-- aliasing: two variables, one object");
        Box b = a;                                    // copies the REFERENCE, not the object
        b.v = 2;
        System.out.println("a.v = " + a.v + ", a == b? " + (a == b));

        System.out.println("-- == vs equals");
        Box c = new Box(2);
        System.out.println("a == c? " + (a == c) + "  (different objects, same content)");
        String s1 = new String("hi"), s2 = new String("hi");
        System.out.println("s1 == s2? " + (s1 == s2) + ", s1.equals(s2)? " + s1.equals(s2));

        System.out.println("-- null");
        Box n = null;
        try {
            System.out.println(n.v);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: n points to no object");
        }
        System.out.println("default of a reference field: " + new ReferenceVariable().defaultNull);

        System.out.println("-- polymorphism through a super type reference");
        Animal an = new Dog();                        // declared type Animal, real type Dog
        System.out.println(an.sound());               // Dog's version runs
        // an.fetch();                                // x Animal has no fetch(): the DECLARED type decides what you can call
        if (an instanceof Dog d) d.fetch();           // check the real type, then use it

        System.out.println("-- final reference");
        final Box fb = new Box(1);
        fb.v = 5;                                     // OK: the object can change
        // fb = new Box(2);                           // x the final reference cannot be re-pointed
        System.out.println("fb.v = " + fb.v);

        System.out.println("-- array and interface references");
        int[] arr = {1, 2, 3};
        int[] arr2 = arr;                             // arrays are objects: same aliasing
        arr2[0] = 9;
        System.out.println("arr[0] = " + arr[0]);
        List<String> list = new ArrayList<>();        // interface type, class object
        list.add("x");
        System.out.println(list);

        System.out.println("-- reference as parameter, static, instance");
        useParameter(a);
        System.out.println("a.v = " + a.v + ", " + staticRef + ", " + new ReferenceVariable().instanceRef);
    }
}

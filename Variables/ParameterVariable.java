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
 *  PARAMETER (formal parameter)
 * ============================================================================
 *
 * WHAT IT IS
 *   A variable declared in the header of a method, constructor, lambda, or catch
 *   clause. It receives its value from the caller. The values the caller passes
 *   are called ARGUMENTS; the variables in the header are the PARAMETERS.
 *
 * WHERE PARAMETERS APPEAR
 *   method:       void add(int a, int b)
 *   constructor:  Person(String name)
 *   lambda:       (x, y) -> x * y
 *   catch clause: catch (NumberFormatException e)
 *   varargs:      int sum(int... nums)   (zero or more arguments, must be last)
 *
 * SCOPE AND LIFETIME
 *   Scope: the whole method body. Lifetime: one call. Each call gets fresh
 *   copies. A parameter behaves like a local variable that is already
 *   initialised by the caller, so it never needs a default.
 *
 * MODIFIERS
 *   Only "final" is allowed. access modifiers, static, transient, and volatile
 *   are not. A final parameter cannot be reassigned inside the method.
 *
 * PASS BY VALUE (the most important rule)
 *   Java ALWAYS copies the argument into the parameter.
 *     primitive: the parameter is a copy of the number. Changing it does not
 *                touch the caller's variable.
 *     reference: the parameter is a copy of the REFERENCE. Both point to the
 *                same object, so changing the OBJECT's contents is visible to
 *                the caller, but pointing the parameter at a NEW object is not.
 *
 * SHADOWING
 *   A parameter with the same name as a field hides the field. Use this.name to
 *   reach the field (the standard pattern in constructors and setters).
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Method, constructor, varargs, final, lambda and catch parameters, and the
 *   three pass-by-value cases: primitive, mutate-the-object, reassign-the-parameter.
 * ============================================================================
 */
// Run: java ParameterVariable.java   (Java 17+)
import java.util.function.BiFunction;

public class ParameterVariable {

    private String name;

    ParameterVariable(String name) {                  // CONSTRUCTOR parameter
        this.name = name;                             // this.name = the field, name = the parameter
    }

    static int add(int a, int b) {                    // METHOD parameters
        return a + b;
    }

    static int sum(int... nums) {                     // VARARGS parameter: nums is an int[]
        int total = 0;
        for (int n : nums) total += n;
        return total;
    }

    static void finalParam(final int x) {             // FINAL parameter
        // x = 5;                                     // x cannot assign a value to a final parameter
        System.out.println("final parameter x = " + x);
    }

    // public int bad(public int x) { }               // x access modifiers are not allowed on parameters
    // void bad2(static int x) { }                    // x

    static void changePrimitive(int x) { x = 99; }                       // changes only the COPY
    static void mutate(StringBuilder sb) { sb.append(" world"); }        // changes the shared OBJECT
    static void reassign(StringBuilder sb) { sb = new StringBuilder("new"); } // changes only the COPY of the reference

    public static void main(String[] args) {
        System.out.println(add(2, 3));
        System.out.println(sum() + " " + sum(1) + " " + sum(1, 2, 3));   // zero, one, many arguments
        finalParam(7);
        System.out.println(new ParameterVariable("Ali").name);

        System.out.println("-- pass by value: primitive");
        int num = 5;
        changePrimitive(num);
        System.out.println("num is still " + num);

        System.out.println("-- pass by value: mutate the object");
        StringBuilder sb = new StringBuilder("hello");
        mutate(sb);
        System.out.println("sb = " + sb);               // hello world

        System.out.println("-- pass by value: reassign the parameter");
        reassign(sb);
        System.out.println("sb = " + sb);               // unchanged

        System.out.println("-- lambda parameters");
        BiFunction<Integer, Integer, Integer> times = (x, y) -> x * y;
        System.out.println(times.apply(4, 5));

        System.out.println("-- catch parameter");
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {            // e is a parameter of the catch clause
            System.out.println("caught " + e.getClass().getSimpleName());
        }
    }
}

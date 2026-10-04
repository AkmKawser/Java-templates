/*
 * ============================================================================
 *  ENUM CLASS
 * ============================================================================
 *
 * WHAT IT IS
 *   A special class with a FIXED, named set of instances called constants.
 *   Each constant (RED, GREEN, ...) is created exactly once, when the enum is
 *   loaded. Because nothing else can create instances, enums are the safe way
 *   to model a closed set of values such as days, levels, or states.
 *
 * HOW IT WORKS
 *   - Implicitly extends java.lang.Enum, so you can NOT write "extends".
 *   - Implicitly final, unless a constant has its own body (then the compiler
 *     makes a hidden subclass for that constant).
 *   - Constructors are implicitly private. You never call "new" on an enum.
 *   - Free methods: values(), valueOf(String), name(), ordinal(), compareTo().
 *   - Works directly in switch statements and with EnumSet / EnumMap.
 *
 * WHAT IT CAN CONTAIN
 *   Fields, constructors, methods, static members, nested types, and it can
 *   implement interfaces (see Size implements Describable).
 *
 * ABSTRACT METHODS IN AN ENUM
 *   You cannot write "abstract enum", but an enum may declare an abstract
 *   method as long as EVERY constant provides a body for it (see Operation).
 *   This gives each constant its own behaviour.
 *
 * MODIFIERS
 *   public / package-private at top level; all four when nested.
 *   NOT allowed: abstract, final, sealed, non-sealed (the compiler decides).
 *   static is implicit when nested, so you never write it.
 *
 * WHERE DECLARED
 *   Top-level, as a member, or local inside a method (Java 16+).
 *
 * ENUM vs CONSTANTS
 *   Plain "static final int" constants are not type-safe: any int fits. An
 *   enum is a real type, so the compiler rejects wrong values.
 *
 * WHAT THIS FILE DEMONSTRATES
 *   Nested and top-level enums, enum with an abstract method and constant
 *   bodies, an enum implementing an interface, and a local enum.
 * ============================================================================
 */
// Run: java EnumClass.java   (Java 17+)
public class EnumClass {
    protected enum A { ONE }                 // PROTECTED (nested only), implicitly static
    private enum B { TWO }                   // PRIVATE (nested only)
    enum Day { MON, TUE }                    // package-private nested, implicitly static

    public static void main(String[] args) {
        System.out.println(Level.HIGH + " " + Color.GREEN);          // top-level enums
        System.out.println(A.ONE + " " + B.TWO + " " + Day.TUE);
        for (Operation op : Operation.values())                       // abstract method + constant bodies
            System.out.println(op + " -> " + op.apply(6, 3));
        System.out.println(Size.LARGE.describe());                    // IMPLEMENTS an interface
        enum Mode { ON, OFF }                                         // LOCAL enum (Java 16+)
        System.out.println(Mode.OFF + " ordinal " + Mode.OFF.ordinal());
        // abstract/final/sealed enum, or "extends" -> x not allowed
    }
}
enum Level { LOW, HIGH }                     // PUBLIC-style top-level enum
enum Color { RED, GREEN }                    // PACKAGE-PRIVATE
enum Operation {
    ADD { int apply(int a, int b) { return a + b; } },
    SUB { int apply(int a, int b) { return a - b; } };
    abstract int apply(int a, int b);        // every constant must implement it
}
interface Describable { String describe(); }
enum Size implements Describable {
    SMALL, LARGE;
    public String describe() { return "Size " + name(); }
}

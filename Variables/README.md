# Java Variable Kinds

One runnable example for each kind of Java variable: local, instance, static (class), parameter, and reference.

Each file starts with a reference block (kinds, templates, keyword meanings), then a detailed explanation of that kind, then the runnable code. Illegal cases are commented out with an `x` and the reason.

## Memory map

![Java variable kinds memory map: who owns it (instance or static), where it is declared (local or parameter), and what it holds (reference variable)](java_variable_kinds_memory_map.png)

Read each row left to right: the question on the left, then the kinds that answer it. Rows 1 and 2 are the core kinds, and row 3 is the extra.

## Requirements

Java 17 or newer.

## Run

```
java LocalVariable.java
```

Each file is independent, so run them one at a time.

## Slime

Slime is a node-based interpreter

```java
// Create a space to store things
final Space space = new Space();

// Define the interpreter
final Interpreter interpreter = new Interpreter(space);

// Execute
interpreter.exec(scanner.nextLine());
```

### Process

The process:

```
Lexing -  Breaking down into tokens
Parsing - Parsed the tokens
Node Creator - Create a node tree
NodeReader/processor - Read the nodes and process them into result
```

### Variable

Asign a value to a variable (`_a-zA-Z`) characters are allowed.

```java
a = 'The Android Java';
```

The variable can be a number or a string.

```java
a = 70 + (10 + 20); // 100
a = 'The number is ' + a;
```

### Print

Use the `print` method to print a variable or a thing.

```java
print a; // Prints value from variable 'a' with value 'The number is 100'
print 50 + (1 + 1); // 52
```

### Basic Operators

Slime can understand `+ / - *` math operators including `braces`.

You will have to request the interpreter line by line.
<br>
`;` is a optional symbol representing the end of the statement.

<hr>

This was made to learn.
<br>
Feel free to fork and contribute back 🙂

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

### The process:

```
Lexing -  Breaking down into tokens
Parsing - Parsed the tokens
Node Creator - Create a node tree
NodeReader/processor - Read the nodes and process them into result
```

### Variables

Enter a variable name (`_a-zA-Z`) are the accepted characters and assign it an value.

```java
res = 2000; // Now variable has 2000 value
res = res + 21; // Add 20 to it
```

Inputs cannot have comments.

### Print

Print a statement using the print method.

```java
print res; // Prints 2021
print 'The year is ' + res; // Prints 'The year is 202'
```

### Operators

Basic math operators `+ - / *` and braces.

```java
a = 45 + (5 + 50); // Result 100
print a // Prints the result
```

### Exceptions

Two types of exceptions can occur.

`[CANNOT FIND SYMBOL]` The interpreter cannot find the thing (Variable/Method)
<br>
`[INVALID SYNRAX]` The syntax is invalid.


Feel free to fork and contribute to the repo 🙂

Slime is a node-based interpreter

```java
// Create a space to store things
final Space space = new Space();

// Define the interpreter
final Interpreter interpreter = new Interpreter(space);

// Execute
interpreter.exec(scanner.nextLine());
```

The process:

Lexing -  Breaking down into tokens
Parsing - Parsed the tokens
Node Creator - Create a node tree
NodeReader/processor - Read the nodes and process them into result
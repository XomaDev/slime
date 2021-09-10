## Slime

Slime is a node-based interpreter

```java
// Create a space to store things
final Space space = new Space();

// Define the slime interpreter
final Slime slime = new Slime(space);

// Execute
slime.exec(scanner.nextLine());

// Demo tests

// print result of 1 + 1 - (7 + 5)       
slime.exec("print 1 + 1 - (7 + 5)")
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

Asign a value to a variable (`a-zA-Z`) characters are allowed.

```java
a = 'The Android Java';
```

The variable can be a number or a string.

```java
// example inputs

a = 70 + (10 + 20)
a = 'The number is ' + a
```

### Print

Use the `print` method to print a variable or a thing.

```java
// prints value from variable 'a' 
// with value 'The number is 100'

print a
print 50 + (1 + 1) // 52
        
// prints 2.1        
print .1 + 2
```

### Basic Operators

Slime can understand `+`, `-`, `/` and `*` math operators including `braces`.

You will have to request the interpreter line by line.

<hr>

### Custom methods

You can also declare your methods!<br>

For that, you will need to create a class that extends `SlimeMethods`
and then do change accordingly, an example:

```java
class MyMethods extends SlimeMethods {
    private final StringJoiner joiner = new StringJoiner("\n");

    @Override
    public void print(final Object text) {
        joiner.add(String.valueOf(text));
    }

    @SuppressWarnings({"unused"})
    public void format(final Object text) {
        System.out.println(String.valueOf(text).translateEscapes());
    }

    @Override
    public String toString() {
        return joiner.toString();
    }
}
```

Declare a constructor as passing your own SlimeMethods instance as the second parameter.

```java
final Space space = new Space();
final Slime slime = new Slime(space, new MyMethods());
```

<hr>

### Dynamic operators

You can also add a dynamic operator, doing additional things!<br>
For that you have to call `setOperator` on `Slime` and pass an operator and the operator handler. This will call the `handle(Object, Object)` method.<br>
You have to perform any operator and return the result.

```java
// Dynamically adding a boolean operator `<?> is <?>`
slime.setOperator("is", new Operator() {
            @Override
            public Object handle(Object first, Object second) {
                // checks and returns if first == second
                return Objects.equals(valueOf(first), valueOf(second));
            }
        });

slime.exec("a = (70 + (10 + 20)) is 100")
slime.exec("print a"); // prints true
```
<hr>
This was made to learn.
<br>
Feel free to fork and contribute back 🙂

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
        
print a is 100 // true
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

Slime can understand `+`, `-`, `/` and `*` math operators, `is` and `or` boolean operator including `braces`.

You will have to request the interpreter line by line.

<hr>

### Custom methods

You can also declare your methods!<br>

For that, you will need to create a class that extends `SlimeMethods`
and then do change accordingly, an example:

```java
class MyMethods extends SlimeMethods {
    public void trim(final Object text) {
        System.out.println(String.valueOf(text).trim());
    }
}
```

Declare a constructor as passing your own SlimeMethods instance as the second parameter.

```java
final Space space = new Space();
final Slime slime = new Slime(space, new MyMethods());
```

<hr>

### Define

You can also define variable with your own values with slime, you just need to call `define(name, expression)` or `define(name, value)` to define a constant.

```java
slime.define("pie", valueOf(Math.PI));
slime.defineConstant("cakes", "50")
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

### Function

Yeah! You can also add functions!<br>
There are two inbuilt functions named `max` and `min`.<br>
To access this feature, you will have to use `execBlock` as replacement for `exec` command method. 

```java
// creates a variable named cakeName 
// with the value 'Chocolate Cake!'

slime.execBlock("cakeName = 'Chocolate Cake!'");

// prints 'CHOCOLATE CAKE!'
slime.execBlock("print case(cakeName, 'upper')");

// prints 'chocolate cake!'
slime.execBlock("print case(cakeName, 'lower')");

// find the max and  in the args provided
        
// prints the number 7     
slime.execBlock("print max(PI, 7)");

// prints the number 1

slime.execBlock("print min(PI, 7, 1)")
```

A complicated example to define a function with by calling the method `defineFunction(MethodName, Function)` on Slime object.

```java
slime.defineFunction("case", new Function() {
            @Override
            public Object handle(ArrayList<Object> parms) {
                if (parms.size() != 2) {
                    throw new IllegalArgumentException("Expected only two parameter!");
                }
                final String value = parms.get(0) + "", toCase = parms.get(1) + "";
                final boolean toLowerCase;

                if (toCase.equals("true") || toCase.equals("false")) {
                    toLowerCase = toCase.equals("true");
                } else if (toCase.equalsIgnoreCase("lower")) {
                    toLowerCase = true;
                } else if (toCase.equalsIgnoreCase("upper")) {
                    toLowerCase = false;
                } else {
                    throw new IllegalArgumentException("Not a valid argument '" + toCase + "'");
                }

                return toLowerCase
                        ? value.toLowerCase()
                        : value.toUpperCase();
            }
        });
```
<hr>
This was made to learn.
<br>
Feel free to fork and contribute back 🙂

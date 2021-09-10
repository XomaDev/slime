package xyz.kumaraswamy.slime;

 /**
  * A class to define methods for the slime interpreter
  * A statement starting with a method will be treated as a method call
 */

public class SlimeMethods {

     /**
      * Prints the message Object
      * @param message a value to print
      */

    @SuppressWarnings("unused")
    public void print(final Object message) {
        System.out.println(message);
    }
}

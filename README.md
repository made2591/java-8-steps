# java-8-steps

This repo contains just some examples and a few notes about what is in the video [Java 8 Lambda Expressions & Streams](https://www.youtube.com/watch?v=8pDm_kH4YKY) so, first of all, many thanks go to NewCircle Instructor Adib Saikali for his lesson. Enjoy!

### Lambda function

##### Lambda definition

- Define anonymous functions;
- Can be assigned to variables;
- Can be passed to functions;
- Can be returned from functions;

##### They are good for

- Form the basis of the functional programming paradigm;
- Make parallel programming easier;
- Write more compact code;
- Richer data structure collections;
- Develop cleaner APIs;

__Note__: concept vs. Implementation

A concept can have multiple implementations:

- Map concept is implemented by HashMap, ConcurrentHashMap, etc;
- List concept is implemented by ArrayList, LinkedList, SkipList, etc;

Lambda expressions are a _concept_, that has its own implementation flavor in different languages. What are lambda? How are implemented in Java?

###### Look at step 1

##### Lambda Lifecycle

The compiler transform the lambda expression in a static function and then call the generated function. Should be static method, which class they belong, and so on...it doesn't matter for now. Instead, we are interest in the type of the lambda. Initially they thought about a specific new function type. We try to cover the question: what is the type of a lambda function? To do that, we need first to talk about functional interface.

##### Functional interface

The functional interfaces are interface with only one method in them. They are the most common and used in Java. For instance, the Runnable, Comparable, Callable interface are a functional interface. They are so popular in lot of library, such as in Spring library: it contains TransactionCallback, RowMapper, StatementCallback, and others.
So they officially decided to call this kind of interface functional interface to formalize them inside the language. ```@FunctionalInterface``` is an optional annotation to make compiler able to produce error if more than one method is added to a functional interface. Any interface with one method is considered a functional interface by Java 8 even if was complied it with a Java 1.0 compiler: the new lambda function will work with old libraries without need to recompile.

###### Look at step 2

Back to the lambda expression, the type of the lambda expression is same as the type of the functional interface that the lambda expression is assigned to. At this point one could think about lambda as anonymous inner class: it is not.

###### Variable capture

Lambda can interact with variables defined outside the body of the lambda: using variables outside is called variable capture.

###### Look at step 3

The value of the var declared outside must be final. The compiler will handle consider this var as final (also static). The signature generated from this example will be:
```
public static void generatedName(Integer x, final int var) {
	System.out.println(x + var);
}
```

###### Look at step 4

There no ability for you to define private field inside a lambda expression: they are just the body of the signature defined in a functional interface the lambda expression will be assigned to. 
The lambda expression has always a type: this because they can only be assigned to a variable, or passed as a parameter or returned by a method: in each of these cases you need a type.

##### Lambda vs Anonymous Inner Class

So let summarize the differences between a lambda expression and an anonymous inner class:

- Inner classes can have state in the form of class level instance variables lambdas can not;
- Inner classes can have multiple methods, while lambdas only have a single method body;
- this points to the object instance for an anonymous Inner class BUT points to the enclosing object for a lambda;

So lambda are different from Anonymous inner class.

##### java.util.function.*

java.util.function package contains 43 commonly used functional interfaces:

- ```Consumer<T>``` - function that takes an argument of type T and returns void;
- ```Supplier<T>``` - function that takes no argument and returns a result of Type T;
- ```Predicate<T>``` - function that takes an argument of Type T and returns a boolean;
- ```Function<T, R>``` - function that takes an argument of Type T and returns a result of type R;
- etc...

So until now we saw _how_ the functional interface provide a way to expose the lambda expression to outside world and use them in our code. Let's move one step forward.

### Method references

Basically a lambda is a way to define an anonymous function. The question now is: what about functions I already written? Do I have to rewrite them? The short answer is no, because method references can be used to pass an existing function in a place where a lambda is expected.

###### Look at step 5

In the ```consumerOldFunction``` 











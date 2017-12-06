# Java 8 - An overview

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

### Functional interface

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

###### Look at step 5.1

#### Reference a static method
In the ```consumerOldFunction``` we are saying "ok use the ```myAlreadyWrittenFunction``` as the implementation of accept method of the functional interface consumer. Obviously, the signature of the referenced method __needs__ to match the signature of functional interface method (It will handle also overloading and generics). In this first scenario we referenced a static method, but there are four different types of method references.

###### Look at step 5.2

#### Reference a constructor
You can also use method reference to reference constructor: this is handy when working with streams. With the notation 
```Function<String, Integer> mapper2 = Integer::new;```
we are asking the compiler to create for us a function that takes as angurment a string, returns an integer and in the body of that method invoque the new constructor of the Integer and pass the string parameter to that method.

###### Look at step 5.3

#### Reference to a specific object instance method
With the notation 
```Consumer<Integer> consumer2 = System.out::println;```
we tell the compiler that the lambda body signature should match the method println and that the lambda expression should result in a call to System.out.println(x).

###### Look at step 5.4

#### Reference to a specific arbitrary object of a particular type
With the notation 
```Function<String, String> mapper2 = String::toUpperCase;```
we tell the compiler to invoke the toUpperCase method on the parameter that is passed to the lambda. So invoking:
```mapper2.apply("def")```
will call the lambda expression derived method and inside that will call the toUpperCase method on the parameter "def"

The all idea of the method reference is that if you already have the method ready you can create a lambda using method reference, and then use lambda wherever you want in your code. 

### Default methods
lambda (->), method ref, now default methods.
A natural place to use lambdas is with the Java collections framework. The collection framework is defined with interfaces such as Iterable, Collection, Map, List, Set, ... etc. Adding a new method such forEach to the Iterable interface will mean that all existing implementations of Iterable will break. All codes already compiled will not work with the new version of java. This problem is known as the __the interface evolution problem__. How can published interfaces be evolved without breaking existing implementations?

A default method on java interface has an implementation provided in the interface and is inherited by classes that implement the interface<T>  

###### Look at step 6

Can you override a default method? Yes of course, simply overriding a method. 
Furthermore, if you a default method C in an interface A, an interface B that extend interface A and override the implementation provided by interface A of the default method C, if you have an implementation of interface B without any implementation of the default method C, the B implementation will be used: then, the closest implementation in hierarchy will be used.

#### Creating a conflict
Interface A and B provide default methods with same signature. C implements A, B: compile errors - duplicate default methods implementations. How to solve the conflict? Of course with overriding. Further, you can call the implementation in the interface with notation ```A.super.doSomething()```. A default method should not be final because it doesn't make so much sense. Same level => compiler error, override mandatory.

	__Functional interface__ must have only one non-default method.

### Collections Enhancements

Java 8 uses lambda expressions and default methods to improve the exising java collections frameworks and add a lot of functions to them.

At this point we already seen example of these methods, such as the forEach - an example of internal iteration: it delegates the looping to a library function (such as forEach, as we said), and the loop body processing to a lambda expression. Further, it allows the library function we are delegating to implement the logic needed to execute the iteration on muliple cores, if desired.

Some of the new methods provided by the standard library are:

- New java.lang.Iterable methods:
	- default void forEach(Consumer<? super T> action);
	- default Spliterator<T> spliterator();
- New java.util.Collection methods:
	- default boolean removeIf(Predicate<? super E> filter);
	- default Spliterator<E> spliterator();
	- default Stream<E> stream();
	- default Stream<E> parallelStream();
- New java.util.Map methods:
	- default V getOrDefault(Object key, V defaultValue);
	- putIfAbsent(K key, V value);
	- etc;

The streams related methods mentioned above are:
- default Spliterator<T> spliterator();
- default Stream<E> stream();
- default Stream<E> parallelStream();

But what is a stream?

### Stream

Streams are a functional programming desing pattern for processing sequences of elements sequentially or in parallel. When examining java programs we always run into code along the following lines:
- Run a database query to get a list of objects;
- Iterate over the list to compute a single result;
- Iterate over the list to generate a new data structure such as another list, map, set, etc;
- Iterate over the list and etc;

What we are saying is that stream are a concept (a design pattern) in the same way lambdas are: they can be implemented in many programming languages.

###### Look at step 7

The code in step7 Create a stream instance from a source java collection, add a filter operation to the stream intermediate operations pipeline, add a map operation to the stream intermediate operations pipeline and add a terminal operation (sum) that kicks off the stream processing.

##### The stream composition

A __stream__ has three elements:
- a __source__ that the stream can pull objects from;
- a __pipeline__ of operations that will execute on the elements of the stream;
- a __terminal__ operation that pulls values down the stream;

##### The stream lifecycle
A __stream__ has the following life steps:
- a __creation__ step: streams get created from a source object such as a collection, file, or generator;
- a __configuration__ step: streams get configured with a collection of pipeline operations;
- a __execution__ step: stream terminal operation is invoked which starts pulling objects trough the operations pipeline of the stream;
- a __cleanup__ step: stream can only be used once;

__Note__: stream execution is __lazy__, that means that until you call a terminal operation the stream doesn't do anythings. It doesn't loop.

###### Look at step 8

##### Number Stream Source 
The first example shows a range from 0 to 5 starting from a LongSource from stream package in standard library. Remember Stream can be anything, what the stream does is splitting out elements.

##### Collection Stream Source 
A character stream source is called using chars() and then count().

##### File System Streams
```Files.list(Path object from Java 7)``` gives a stream to work directly on list.
```Files.walk(Path object from Java 7)``` gives a stream to work directly on list with depth first search.

###### Look at step 9

The example print out it self. The find method returns a stream, so it is possible to call forEach method on it: the forEach terminal do something to each things that it founds. The things it found are pointed by path object, we then use another stream of Files, lines, that out each lines of the file as a stream.

#### How about terminals?
There are many types of terminals for stream:

- Reduction terminal operations that return a single result (such as sum());
- Mutable reduction terminal operations that return multiple results in a container data structure;
- Search terminal operations that return a result as soon as a match is found;
- Generic terminal operations that do any kind of processing you want on each stream element;

But remember that nothing happens until the terminal operation is invoked (think about updating, and so on);

###### Look at step 10

##### Reduction terminal operations
Get min, get max => Actually, I didn't understand the min.

###### Look at step 11

##### Mutable reduction operations
Collectors are used to collect all the things that came out of a stream and collect them into a structure such as a List, a Set, etc. Actually, collectors class defines many useful collectors such as List, Set, Map, groupingBy, partitioningBy, ...etc

###### Look at step 12

##### Search terminal operation
Note the findAny: result of findAny call is unpredictable. In a sense that if stream is executed, for instance, parallel, then the first call that ends will return the result so you don't know exactly a priori which will be the returned value. It just means find any.

##### Generic terminal operation
forEach is an example of.

#### Streams pipeline rules
- Streams can process elements sequentially;
- Streams can process elements in parallel;
- Thus means that streams operations are not allowed to modify the stream source otherwise bad things happens;

##### Intermediate stream operations
There are two classes of intermediate stream operations:

- __Stateless intermediate operations__: they do not need to know the history of results from the previous steps in the pipeline or keep track of how many results it have produced or seen. Example of stateless intermediate operations are filter, map, flatMap, peek.

- __Stateful intermediate operations__: they need to know the history of results from the previous steps in the pipeline or need to keep track of how many results it have produced or seen. Example of stateless intermediate operations are distinct, limit, skip, sorted.

So, for instance you want a stream to sort elements you obviously needs to know the order of sorting. Instead, if you have a parallel operations with threads that compete with each others, you are not interested in state of each elements. Let's have a look at sample example

###### Look at step 13

###### Stateless intermediate operations
No notes about that.

###### Look at step 14

###### Stateful intermediate operations
No notes about that.














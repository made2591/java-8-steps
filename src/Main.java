import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main implements Matteo {

    private static final Main main = new Main();
    private int var = 1;
    private static int step = 0;
    private static List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

    private static void stepperStart() {
        step++;
        System.out.print("####### STEP "+step+" #######\n");
    }

    private static void stepperEnd() {
        System.out.println("Press \"ENTER\" to continue...\n");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private static void step1() {

        stepperStart();

        /*
         *  forEach is a method that accepts
         *  a function as its input and calls
         *  the function for each value in the list.
         *
         *  x -> System.out.println(x) is a lambda expression
         *  that defines an anonymous function with one
         *  parameter named x of type Integer.
         */

        integers.forEach(
                x -> System.out.println(x)
        );

        integers.forEach(x -> {
            int y = x + 10;
            System.out.println(y);
        });

        /*
         * It is possible to define multiple
         * line lambda expression. You can create local
         * variable inside lambda expression and specify
         * type for x parameters: it is not needed because
         * java compiler can do type inference.
        */

        stepperEnd();

    }

    private static void step2() {

        stepperStart();

        Consumer<Integer> consumer = x -> System.out.print(x);

        Main.integers.forEach(consumer);

        stepperEnd();

    }

    private static void step3() {

        stepperStart();

        int localVar = 10;
        integers.forEach(
                x -> System.out.println(x + localVar)
        );

        stepperEnd();

    }

    private void step4() {

        stepperStart();

        integers.forEach(
                x -> {
                    System.out.println(x + this.var);
                    if (this == main) {
                        System.out.println("Compiler has passed this of enclosing method as the first parameter of method generated from lambda expression");
                    }
                }
        );

        stepperEnd();

        integers.forEach(
                new Consumer<Integer>() {

                    private int state = 10;

                    @Override
                    public void accept(Integer x) {
                        int y = this.state + Main.this.var + x;
                        System.out.println("Anonymous class that implements Consumer interface with accept overriding: " + y);
                    }
                }
        );

        stepperEnd();

    }

    private static void myAlreadyWrittenFunction(Integer i) {
        System.out.println(i);
    }

    private static void step5() {

        stepperStart();

        Consumer<Integer> consumerLambda = x -> System.out.println(x);
        consumerLambda.accept(1);

        Consumer<Integer> consumerOldFunction = Main::myAlreadyWrittenFunction;
        consumerOldFunction.accept(1);

        stepperEnd();

        Function<String, Integer> mapper1 = x -> new Integer(x);
        System.out.println(mapper1.apply("11"));

        Function<String, Integer> mapper2 = Integer::new;
        System.out.println(mapper2.apply("12"));

        stepperEnd();

        Consumer<Integer> consumer1 = x -> System.out.println(x);
        consumer1.accept(13);

        Consumer<Integer> consumer2 = System.out::println;
        consumer1.accept(14);

        stepperEnd();

        Function<String, String> mapper3 = x -> x.toUpperCase();
        System.out.println(mapper3.apply("abc"));

        Function<String, String> mapper4 = String::toUpperCase;
        System.out.println(mapper4.apply("def"));

        stepperEnd();

    }

    private static void step6() {

        stepperStart();

        new Main().printMatteo();

        stepperEnd();

    }

    private static void step7() {

        stepperStart();

        int sumOdd = integers.stream()
                .filter(o -> o % 2 == 1)
                .mapToInt(o -> o)
                .sum();
        System.out.print(sumOdd+"\n");

        stepperEnd();

    }

    public static void main(String[] args) {

//        step1();
//        step2();
//        step3();
//        main.step4();
//        step5();
//        step6();
        step7();

    }

}

interface Matteo {

    default void printMatteo() {
        System.out.print("Matteo\n");
    }

}
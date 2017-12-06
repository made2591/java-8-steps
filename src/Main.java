import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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

    private static void step8() throws IOException {

        stepperStart();

        System.out.print("Long Stream Source\n");

        LongStream.range(0, 5).forEach(System.out::println);

        stepperEnd();

        List<String> cities = Arrays.asList("toronto",
            "ottawa", "montreal", "vancouver");
        cities.stream().forEach(System.out::println);

        stepperEnd();

        long length = "ABC".chars().count();
        Consumer<Long> printer = System.out::println;
        printer.accept(length);

        stepperEnd();

        String workDir = System.getProperty("user.dir");
        Path workDirPath = FileSystems.getDefault().getPath(workDir);

        System.out.println("Directory Listing Stream");
        Files.list(workDirPath).forEach(System.out::println);

        stepperEnd();

        System.out.println("Depth First Directory Walking Stream");
        Files.walk(workDirPath).forEach(System.out::println);

        stepperEnd();

    }

    private static void step9() throws IOException {

        stepperStart();

        String workDir = System.getProperty("user.dir");
        Path workDirPath = FileSystems.getDefault().getPath(workDir);

        String className = Main.class.getName().replace(".", "/") + ".java";

        Files.find(workDirPath, 10, (fileName, attributes) -> fileName.endsWith(className)).forEach(path -> {
            try {
                Files.lines(path).forEach(System.out::println);
            } catch (Exception e) {}
        });

        stepperEnd();

    }

    private static void step10() throws IOException {

        stepperStart();

        System.out.println(integers.stream().count());

        stepperEnd();

        Optional<Integer> result;
        result = integers.stream().min((x, y) -> x - y);
        System.out.println(result.get());

        stepperEnd();

        result = integers.stream().max(Comparator.comparingInt(x -> x));
        System.out.println(result.get());

        stepperEnd();

        Integer res = integers.stream().reduce(0, (x, y) -> x + y);
        System.out.println(res);

    }

    private static void step11() throws IOException {

        stepperStart();

        Set<Integer> result = integers.stream().collect(Collectors.toSet());
        System.out.println(result);

        stepperEnd();

        Integer[] a = integers.stream().toArray(Integer[]::new);
        Arrays.stream(a).forEach(System.out::println);

        stepperEnd();

        Integer res = integers.stream().reduce(0, (x, y) -> x + y);
        System.out.println(res);

    }

    private static void step12() throws IOException {

        stepperStart();

        Optional<Integer> result = integers.stream().findFirst();
        System.out.println(result);

        stepperEnd();

        Boolean res = integers.stream().anyMatch(x -> x == 5);
        System.out.println(res);

        stepperEnd();

        res = integers.stream().anyMatch(x -> x > 3);
        System.out.println(res);

        stepperEnd();

        Optional<Integer> anyres = integers.stream().findAny();
        System.out.println(anyres.get());

        stepperEnd();

    }

    private static void step13() throws IOException {

        stepperStart();

        integers.stream().filter(x -> x < 4).forEach(System.out::println);

        stepperEnd();

        integers = integers.stream().map(x -> x + x).collect(Collectors.toList());
        integers.forEach(System.out::println);

        stepperEnd();

        IntSummaryStatistics intSummaryStatistics = integers.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println(intSummaryStatistics);

        stepperEnd();

    }

    private static void step14() throws IOException {

        stepperStart();

        integers = Arrays.asList(1,1,2,4,5,2,3,2,5,1);
        integers.stream().distinct().forEach(System.out::println);

        stepperEnd();

        integers.stream().limit(3).forEach(System.out::println);

        stepperEnd();

        integers.stream().skip(3).forEach(System.out::println);

        stepperEnd();

        integers.stream().sorted().forEach(System.out::println);

        stepperEnd();

    }

    public static void main(String[] args) throws IOException {

//        step1();
//        step2();
//        step3();
//        main.step4();
//        step5();
//        step6();
//        step7();
//        step8();
//        step9();
//        step10();
//        step11();
//        step12();
//        step13();
        step14();
    }

}

interface Matteo {

    default void printMatteo() {
        System.out.print("Matteo\n");
    }

}
package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        Stream.of(Address.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Inspect.class))
                .forEach(method -> {
                    var returnType = method.getReturnType().getSimpleName();
                    var methodName = method.getName();
                    System.out.printf("Method %s returns a value of type %s%n", methodName, returnType);
                });
        // END
    }
}

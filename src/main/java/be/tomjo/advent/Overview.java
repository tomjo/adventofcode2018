package be.tomjo.advent;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparingInt;

public class Overview {

    public static void main(String[] args) {
        new Reflections().getSubTypesOf(Solution.class).stream()
                .sorted(comparingInt(c -> parseInt(c.getSimpleName().replace("Day",""))))
                .forEach(Overview::execute);
    }

    @SneakyThrows
    private static void execute(Class<? extends Solution> solutionClass){
        solutionClass.newInstance().run();
    }
}

package be.tomjo.advent.benchmark;

import java.util.function.Supplier;

public class BenchmarkResult<T>{
    private final T result;
    private final long time;

    public BenchmarkResult(T result, long time) {
        this.result = result;
        this.time = time;
    }

    public static <T> BenchmarkResult<T> benchmark(Supplier<T> runnable){
        long start = System.currentTimeMillis();
        T result = runnable.get();
        long end = System.currentTimeMillis();
        return new BenchmarkResult(result, end-start);
    }


    public long getTime() {
        return time;
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                ", time=" + time +
                '}';
    }
}

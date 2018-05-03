package lab3.model;

import java.util.List;

public interface Queue<T> {

    void add(T t);
    T get();
    boolean isEmpty();
    List<T> getTasks();
}

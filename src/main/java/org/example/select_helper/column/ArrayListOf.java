package org.example.select_helper.column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListOf<T> extends ArrayList<T> {
    @SafeVarargs
    public ArrayListOf(T... items) {
        super(Arrays.asList(items));
    }
    @SafeVarargs
   public static <E> List<E> of(E... elements) {
        return new ArrayListOf<>(elements);
    }
    public ArrayListOf<T> from(List<T> t) {
        super.addAll(t);
        return this;
    }
}

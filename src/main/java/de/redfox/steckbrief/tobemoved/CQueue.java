package de.redfox.steckbrief.tobemoved;

import java.util.*;

public class CQueue<T> extends LinkedList<T> {
    public T poll() {
        if (this.size() == 0)
            return null;

        T t = this.get(0);
        this.remove(0);
        return t;
    }

    public T peek() {
        return this.get(0);
    }
}

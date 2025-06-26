package delivery;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContenedorCocina {
    private int capacidad;
    private int count;

    Lock m;
    Condition c;

    public ContenedorCocina(int capacidad) {
        this.capacidad = capacidad;
        this.count = 0;
        m = new ReentrantLock();
        c = m.newCondition();
    }

    public  void put() throws InterruptedException {
        m.lock();
        try {
            while (count == capacidad) {
                c.await();
            }
            count++;
            c.signalAll();
        } finally {
            m.unlock();
        }
    }

    public  void get() throws InterruptedException {
        m.lock();
        try {
            while (count == 0) {
                c.await();
            }
            count--;
            c.signalAll();
        } finally {
            m.unlock();
        }
    }

    public  int getCount() {
        return count;
    }
}
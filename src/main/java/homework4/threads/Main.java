package homework4.threads;

public class Main {

    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        Thread t1 = new Thread(new ThreadA(monitor));
        Thread t2 = new Thread(new ThreadB(monitor));
        Thread t3 = new Thread(new ThreadC(monitor));

        t3.start();
        t1.start();
        t2.start();
    }

    static class Monitor {
        public int counter = 1;
    }
}

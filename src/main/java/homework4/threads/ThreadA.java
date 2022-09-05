package homework4.threads;


public class ThreadA implements Runnable {
    private String symbol = "A";
    private Main.Monitor monitor;

    public ThreadA(Main.Monitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 5) {
            synchronized (monitor) {
                while (monitor.counter != 1) {
                    try {
                        monitor.wait(1000);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                System.out.print(symbol);
                monitor.counter = 2;
            }
        }
    }
}

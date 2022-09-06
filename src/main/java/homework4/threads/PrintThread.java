package homework4.threads;

public class PrintThread implements Runnable {
    private String symbol;
    private Main.Monitor monitor;

    public PrintThread(String symbol, Main.Monitor monitor){
        this.monitor = monitor;
        this.symbol = symbol;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 5) {
            synchronized (monitor) {
                while (monitor.counter != 3) {
                    try {
                        monitor.wait(1000);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                System.out.print(symbol);
                monitor.counter = 1;
            }
        }
    }
}

package homework4.threads;

public  class ThreadC implements Runnable {
    private String symbol = "C";
    private Main.Monitor monitor;

    public ThreadC(Main.Monitor monitor){
        this.monitor = monitor;
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
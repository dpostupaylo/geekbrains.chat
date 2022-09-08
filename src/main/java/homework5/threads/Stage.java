package homework5.threads;

import java.util.concurrent.CountDownLatch;
import java.util.zip.CheckedOutputStream;

public abstract class Stage {

    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }
    public abstract void go(Car c, CountDownLatch countDownLatch);
}
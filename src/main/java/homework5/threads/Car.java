package homework5.threads;

import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private CountDownLatch countDownLatchPrepare;
    private CountDownLatch countDownLatchLaunch;
    private CountDownLatch countDownLatchFinish;
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CountDownLatch countDownLatchPrepare, CountDownLatch countDownLatchLaunch, CountDownLatch countDownLatchFinish) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.countDownLatchPrepare = countDownLatchPrepare;
        this.countDownLatchLaunch = countDownLatchLaunch;
        this.countDownLatchFinish = countDownLatchFinish;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            countDownLatchPrepare.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            countDownLatchPrepare.await();
        }catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }

        countDownLatchLaunch.countDown();

        for (int i = 0; i < race.getStages().size(); i++) {
            CountDownLatch countDownLatchStage = new CountDownLatch(race.getStages().size());
            race.getStages().get(i).go(this, countDownLatchStage);
        }

        if (countDownLatchFinish.getCount() == 4){
            System.out.println(name + " WINNNER!");
        }

        countDownLatchFinish.countDown();

    }
}

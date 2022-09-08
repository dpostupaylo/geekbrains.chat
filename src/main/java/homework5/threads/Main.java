package homework5.threads;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        CountDownLatch cdlPrep = new CountDownLatch(4);
        CountDownLatch cdlLainch = new CountDownLatch(4);
        CountDownLatch cdlStop = new CountDownLatch(4);

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cdlPrep, cdlLainch, cdlStop);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            cdlLainch.await();
        }catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        try {
            cdlStop.await();
        }catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

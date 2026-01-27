package example2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;
    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread>  threads = new ArrayList<>();

        threads.add(new AscendingThread(vault));
        threads.add(new DescendingThread(vault));
        threads.add(new PoliceThread());

        for (Thread t : threads) {
            t.start();
        }
    }

    private static class Vault{
        private int password;
        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess){
            try{
                Thread.sleep(5);
            } catch (InterruptedException e) {}
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread{
        protected Vault vault;

        public HackerThread(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingThread extends HackerThread{
        public AscendingThread(Vault vault){
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = 0; guess < MAX_PASSWORD; guess++){
                if(vault.isCorrectPassword(guess)){
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingThread extends HackerThread{
        public DescendingThread(Vault vault){
            super(vault);
        }

        @Override
        public void run() {
            for(int guess = MAX_PASSWORD; guess >= 0 ; guess--){
                if(vault.isCorrectPassword(guess)){
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread{
        @Override
        public void run() {
            for(int i = 10; i >=0; i--) {
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
                System.out.println(i);
            }
            System.out.println("Game over for you hackers");
            System.exit(0);
        }
    }
}
//
//Thread Creation - MultiExecutor Solution
//In this exercise we are going to implement a  MultiExecutor .
//
//The client of this class will create a list of Runnable tasks and provide that list into MultiExecutor's constructor.
//
//When the client runs the . executeAll(),  the MultiExecutor,  will execute all the given tasks.
//
//To take full advantage of our multicore CPU, we would like the MultiExecutor to execute all the tasks in parallel, by passing each task to a different thread.
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MultiExecutor {
//
//    private final List<Runnable> tasks;
//
//    /*
//     * @param tasks to executed concurrently
//     */
//    public MultiExecutor(List<Runnable> tasks) {
//        this.tasks = tasks;
//    }
//
//    /**
//     * Executes all the tasks concurrently
//     */
//    public void executeAll() {
//        List<Thread> threads = new ArrayList<>(tasks.size());
//
//        for (Runnable task : tasks) {
//            Thread thread = new Thread(task);
//            threads.add(thread);
//        }
//
//        for(Thread thread : threads) {
//            thread.start();
//        }
//    }
//}

//import java.util.List;
//
//public class MultiExecutor {
//
//    // Add any necessary member variables here
//    List<Runnable> tasks;
//    /*
//     * @param tasks to executed concurrently
//     */
//    public MultiExecutor(List<Runnable> tasks) {
//        // Complete your code here
//        this.tasks = tasks;
//    }
//
//    /**
//     * Starts and executes all the tasks concurrently
//     */
//    public void executeAll() {
//        // complete your code here
//        for(Runnable task : tasks){
//            Thread t = new Thread(task);
//            t.start();
//        }
//    }
//}

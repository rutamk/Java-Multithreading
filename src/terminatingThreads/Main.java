package terminatingThreads;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        //Blocking thread simulation
//        Thread thread = new Thread(new BlockingThread());
//        thread.start();
//        thread.interrupt();

        //LongTask Simulation

        Thread thread = new Thread(new LongComputationTask(new BigInteger("99072"),new BigInteger("1658750")));

//        thread.setDaemon(true); // daemon threads are background threads that do not prevent the application from exiting if the main thread terminates.
        // as this thread is set as daemon it is no longer blocking the main thread from terminating and so main threads terminates which in turn stops the entire application.
        thread.start();
        thread.interrupt();
    }

    private static class BlockingThread implements Runnable{
        @Override
        public void run() {
            try{
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread");
            }
        }
    }

    private static class LongComputationTask implements Runnable{
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power){
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + "=" + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power){
            BigInteger result = BigInteger.ONE;
            for(BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Prematurely interrupted calculation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}

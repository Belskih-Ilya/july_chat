package lesson3_4_multithread;

public class MainClass {
    public static volatile char currentChar = 'A';
    public static Object mon = new Object();

    public static void main(String[] args) {
        Thread printA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon) {
                    while (!(currentChar == 'A')) {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < 5; i++) {
                        if (currentChar == 'A') {
                            System.out.print('A');
                            currentChar = 'B';
                            mon.notifyAll();
                        }
                    }
                }
            }
        });

        Thread printB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon) {
                    while (!(currentChar == 'B')) {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < 5; i++) {
                        if (currentChar == 'B') {
                            System.out.print('B');
                            currentChar = 'C';
                            mon.notifyAll();
                        }
                    }
                }
            }
        });

        Thread printC = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mon) {
                    while (!(currentChar == 'C')) {
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < 5; i++) {
                        if (currentChar == 'C') {
                            System.out.print('C');
                            currentChar = 'A';
                            mon.notifyAll();
                        }
                    }
                }
            }
        });

        printA.start();
        printB.start();
        printC.start();
    }

}

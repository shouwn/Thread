package basic;

public class ThreadMake extends Thread implements Runnable{

	public static void main(String[] args) {
		Thread t1 =
				new Thread(new ThreadMake());

		Thread t2 =
				new ThreadMake();

		t1.start();
		t2.start();
	}

	@Override
	public void run() {

		ThreadMake.print();
	}

	public static void print() {
		System.out.println(Thread.currentThread().getName() + ": " + 
				Thread.currentThread().getThreadGroup());
	}
}

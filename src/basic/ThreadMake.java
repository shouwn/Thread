package basic;

public class ThreadMake implements Runnable{

	public static void main(String[] args) {
		Thread t1 =
				new Thread(new ThreadMake());
		
		Thread t2 =
				new Thread(new ThreadMake());

		t1.start();
		t2.start();
		
		print();
	}

	@Override
	public void run() {

		int i = 0;
		while(i++ < 10) {
			ThreadMake.print();
			try {
				System.exit(0);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void print() {
		System.out.println(Thread.currentThread().getName() + ": " + 
				Thread.currentThread().getThreadGroup());
	}
}

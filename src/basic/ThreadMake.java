package basic;

public class ThreadMake implements Runnable{

	public static void main(String[] args) {
		new Thread(new ThreadMake()).start();
		new Thread(new ThreadMake()).start();
		new Thread(new ThreadMake()).start();
		new Thread(new ThreadMake()).start();
		new Thread(new ThreadMake()).start();
		print();
	}

	@Override
	public void run() {

		int i = 0;
		while(i++ < 10) {
			ThreadMake.print();
			try {
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

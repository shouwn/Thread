package basic;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class ThreadUnsafe {

    public static void main(String[] args) throws InterruptedException {
        Box box = new Box();

        Runnable r = () ->
                Stream.iterate(1, n -> n + 1).limit(100000)
                        .forEach(n -> box.increase());

        Thread t1 = new Thread(r);
        Thread t2 =  new Thread(r);
        Thread t3 =  new Thread(r);

        t1.start(); t2.start(); t3.start();

        t1.join(); t2.join(); t3.join();

        System.out.println(box.getContent());
    }

}

class Box {
	private int content = 0;

	public void increase() {
		content++;
	}

	public int getContent(){
	    return this.content;
    }
}
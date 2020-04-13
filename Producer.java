
import java.util.*;

public class Producer implements Runnable{
	String name;
	boolean isProducing;
	ArrayList<Integer> productList;
	Thread t;
	
	Producer(String name,ThreadGroup g2){
		this.isProducing = true;
		this.productList = new ArrayList<Integer>();
		this.name = name;
		t = new Thread(g2,this,name);
		t.start();
	}
	
	public void run(){
		System.out.println(this.name+" has started producing..");
		for(int i=0;i<10;i++) {
			productList.add(i+1);
			System.out.println(this.name+" just produced "+(i+1)+" product..");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.print("Exception occured in SLeep()....");
			}
		}
		System.out.println(this.name+" has produced all products...");
		this.isProducing = false;	
	}
	public synchronized int consumed(int index) {
		int item = -1;
		if(this.productList.size()>index) {
			item = productList.get(index);
			productList.set(index, -1);
		}
		return item;
	}
}

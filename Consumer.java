
import java.util.*;

public class Consumer implements Runnable{
	
	String name;
	Producer prod;
	int requiredProd;
	ArrayList<Integer> consumedProd;
	Thread t;
	
	Consumer(Producer p,int requirement,String name,ThreadGroup g1){
		this.prod = p;
		this.requiredProd = requirement;
		consumedProd = new ArrayList<Integer>();
		this.name = name;
		t = new Thread(g1,this,name);
		t.start();
	}
	
	public void run() {
		while(this.prod.isProducing) {
			System.out.println(this.prod.name+ " is busy...");
			try{
				Thread.sleep(100);
			}catch(InterruptedException e) {
				System.out.println("Error while waiting by Consumer Thread.");
			}
		}
		ArrayList<Integer> tempList = this.prod.productList;
		int count=0;
		for(int i=0;i<Math.min(tempList.size(),requiredProd);i++) {
			int itemConsumed = this.prod.consumed(i);	// it is reference So it will get deleted from producer's list as well
			if(itemConsumed==-1) {
				System.out.println("The item "+i+" is unavailable for "+this.name+"...");
			}
			else {	
				this.consumedProd.add(itemConsumed);
				System.out.println(this.name+" took item number: "+itemConsumed);
				count++;
			}
		}
		this.requiredProd = this.requiredProd - count;
		System.out.println(this.name+" already consumed "+count+" products.");
	}

}

import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		ThreadGroup market = new ThreadGroup("Market");	// Super ThreadGroup of Producers and Consumers
		
		ThreadGroup producers = new ThreadGroup(market,"Producers");	// Thread Group of Producers
		Producer p = new Producer("Producer1",producers);
		
		ThreadGroup consumers = new ThreadGroup(market,"Consumers");		// Thread Group of Consumer
		Consumer c1 = new Consumer(p,2,"Consumer1",consumers);
		Consumer c2 = new Consumer(p,3,"Consumer2",consumers);
		
		int count = Thread.activeCount();		// activeCount() --> returns the estimate number of threads active this particular moment
		Thread[] groupAllThread = new Thread[count];
		count = market.enumerate(groupAllThread);	// Thread Array of all active thread in particular group and it's sub-group as well.
		System.out.println("\n\nActive Theads:\n");
		for(int i=0;i<count;i++) {
			System.out.println(groupAllThread[i].getName()+ " is Active.");
		}

		// Killing all the Threads except Main Thread
		try {
			c1.t.join();	
			c2.t.join();
		}catch(Exception e) {
			System.out.println("Error while Terminating Consumer 1 & 2 Threads.");
		}
		System.out.println("Consumer 1 thread State after Join(): "+c1.t.getState());
		System.out.println("Consumer 2 thread State after Join(): "+c1.t.getState());
		
		// Setting up the ShutDown Hook in Runtime
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				System.out.println("Shut Down Hook is Running....");
			}
		});
		
		System.out.println("Application has Terminated....");
	}
}

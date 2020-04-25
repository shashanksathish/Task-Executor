package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;

public class BlockingFifo {

		public BlockingFifo(int length) {
			not_empty = this;
			not_full = this;
			length_Buffer = length;
			buffer = new Task[length];
		}
		
		public void put(Task task) throws InterruptedException 
		{
		  // Buffer is full, wait for take
			while(true) {
				if(count == length_Buffer )
				{
					synchronized (not_full)
					{
						not_full.wait();
					}
				}
				
				synchronized(this)
				{
					if(count==length_Buffer)
						continue;
				
			    buffer[next_in] = task;
			    next_in = (next_in + 1) % length_Buffer;
			    count++;
		   
			    not_empty.notify();
		   
				}
				return;
		
		   }
		}
		public Task take() throws InterruptedException 
		{		
			
			while(true) {
			
				if(count==0)
				{
					synchronized (not_empty)
					{
						not_empty.wait();
					}
				}
			
				synchronized(this)
				{
					if(count==0)
						continue;
				
			    Task result = buffer[next_out];
			    next_out = (next_out + 1) % length_Buffer;
			    count--;
		   
		    	not_full.notify();
		    		return result;
				}
		    }
		}
		

		private Task buffer[];
		private int length_Buffer;
		private int next_in, next_out;
		private int count;
		private Object not_full, not_empty; // Monitors used for synchronization		
		
	}
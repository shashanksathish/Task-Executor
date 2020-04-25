package edu.utdallas.taskRunnerImpl;

import edu.utdallas.taskExecutor.TaskRunner;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutorImpl.BlockingFifo;
import edu.utdallas.taskExecutorImpl.TaskExecutorImpl;;

public class TaskRunnerImpl implements TaskRunner{

	@Override
	public void init() {
		bqueue = TaskExecutorImpl.bfifo_Get();
	} 
	
	@Override
	public void run() {
		while(true) {
	        // take() blocks if queue is empty
	        Task newTask;
			try {
				newTask = bqueue.take();
				newTask.execute();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			catch(Throwable t) {
				System.out.println(t.getMessage());
			}
	    }
	}
	
	private BlockingFifo bqueue;
	
}
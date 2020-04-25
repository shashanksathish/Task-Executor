package edu.utdallas.taskExecutorImpl;

import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;
import edu.utdallas.taskRunnerImpl.TaskRunnerImpl;

public class TaskExecutorImpl implements TaskExecutor
{

	public TaskExecutorImpl(int taskRunnerThreadCount) {
		tCount = taskRunnerThreadCount;
		blockingfifo_Create();
		threads_Create(tCount);
	}

	@Override
	public void addTask(Task task)
	{
		// TODO Complete the implementation
		BlockingFifo bFifo = TaskExecutorImpl.bfifo_Get();
		try {
			bFifo.put(task);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());	
		}
	}
	
	public static BlockingFifo bfifo_Get() {
		return BFifo;
	}
	
	private void threads_Create(int tCount) {
		for (int x = 0; x < tCount; x++) {
			TaskRunnerImpl taskRunner = new TaskRunnerImpl();
			taskRunner.init(); //For utilizing the same Blocking FIFO  
			Thread thread = new Thread(taskRunner);
			thread.start();
        }
	}
	
	private void blockingfifo_Create() {
		if(BFifo == null ) {
			BFifo = new BlockingFifo(100);
		}
	}	
	private int tCount;
	private static BlockingFifo BFifo;
	

}

package executor.monitoring;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

public class TestConsumer {
	private BlockingQueue<Edge> input;

	public TestConsumer(BlockingQueue<Edge> input) {
		this.input = input;
	}

	public void consume(IProgressMonitor monitor) {
		while (true) {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			Edge transition = null;
			try {
				transition = input.poll(100, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (transition != null) {
				if(transition.isFinished()){break;}
				
				if(transition.isTestCaseFinished()){
					if(transition.isFailure()){
						System.out.println("Testcase: "+transition.getTestcase()+ " failed");
					}else{
						System.out.println("Testcase: "+transition.getTestcase()+ " succeeded");
					}
					continue;
				}

				System.out.println(transition.getMethod()+" "+transition.getLineFrom()+"->"+transition.getLineTo());
			}
		}
	}

}

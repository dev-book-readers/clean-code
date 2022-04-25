import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorClientScheduler implements ClientScheduler {
  Executor executor;

  public ExecutorClientScheduler(int availableThreads) {
    executor = Executors.newFixedThreadPool(availableThreads);
  }

  public void schedule(final ClientRequestProcessor requestProcessor) {
    Runnable runnable = new Runnable() {
      public void run() {
        requestProcessor.process();
      }
    };
    executor.execute(runnable);
  }
}
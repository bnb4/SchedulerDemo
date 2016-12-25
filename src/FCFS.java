import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FCFS {

	public static List<Process> schedule(List<Process> processes) {
		
		// ID 排序
		Collections.sort(processes, Utils.getProcessIdSortInstance());				
		// 到達時間排序
		Collections.sort(processes, Utils.getProcessArrivalTimeSortInstance());
		
		List<Process> gantt = new LinkedList<>();
		Queue<Process> queue = new LinkedList<>(processes);
		Queue<Process> processing = new LinkedList<>();

		int time = 0;
		while (!processing.isEmpty() || !queue.isEmpty()) {
			
			// 檢查有沒有程序要進入
			while (queue.peek() != null && queue.peek().arrivalTime <= time) 
				processing.offer(queue.poll());
			
			Process nowProcess = processing.peek();
			gantt.add(nowProcess);
			
			if (nowProcess != null && nowProcess.pushBurst()) {
				nowProcess.finishTime = time;
				processing.poll();
			}
				
			
			time++;
		}
		
		return gantt;
	}
	
}

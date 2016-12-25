import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Priority {
	
	@SuppressWarnings("unchecked")
	public static List<Process> schedule(List<Process> processes) {
		
		// ID 排序
		Collections.sort(processes, Utils.getProcessIdSortInstance());				
		// 到達時間排序
		Collections.sort(processes, Utils.getProcessArrivalTimeSortInstance());
		
		for (Process p : processes) p.priority = p.priority * 2;
		
		List<Process> gantt = new LinkedList<>();
		Queue<Process> queue = new LinkedList<>(processes);
		Queue<Process> ready = new LinkedList<>();
		Deque<Process> processing = new LinkedList<>();
		
		int time = 0;
		while (!processing.isEmpty() || !ready.isEmpty() || !queue.isEmpty()) {
					
			// 檢查有沒有程序要進入
			while (queue.peek() != null && queue.peek().arrivalTime <= time) 
				ready.add(queue.poll());
					
			if ( processing.size() == 0 && ready.size() == 0) {
				// 沒有可處裡的事
				gantt.add(null);
			} else {
				// 有處理的事
				
				Collections.sort((List<Process>) processing, Utils.getProcessPrioritySortInstance());
				Collections.sort((List<Process>) ready, Utils.getProcessPrioritySortInstance());
				
				if (processing.size() == 0) {
					processing.addFirst(ready.poll());
					processing.peek().priority--;
				}
				
				if (ready.size() > 0) {
					int rfp = ready.peek().priority;
					int pfp = processing.peek().priority;
					
					if (rfp < pfp) processing.peek().priority++;
					
					if (rfp <= pfp) {
						processing.addFirst(ready.poll());
						processing.peek().priority--;
					}
				}

				Process nowProcess = processing.peek();
				gantt.add(nowProcess);
						
				// 是否執行完成
				if( nowProcess.pushBurst()) {
					nowProcess.finishTime = time;
					processing.poll();
				}
			}
			time++;
		}
		return gantt;
	}
}

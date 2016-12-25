import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RR {
	public static List<Process> schedule(List<Process> processes, int timeSlice) {
		
		// ID 排序
		Collections.sort(processes, Utils.getProcessIdSortInstance());				
		// 到達時間排序
		Collections.sort(processes, Utils.getProcessArrivalTimeSortInstance());
		
		List<Process> gantt = new LinkedList<>();
		Queue<Process> queue = new LinkedList<>(processes);
		Queue<Process> processing = new LinkedList<>();
		
		int time = 0, nowTimeSlice = timeSlice;
		while (!processing.isEmpty() || !queue.isEmpty()) {
			
			// 檢查有沒有程序要進入
			while (queue.peek() != null && queue.peek().arrivalTime <= time) 
				processing.add(queue.poll());
			
			if ( processing.size() == 0) {
				// 沒有可處裡的事
				gantt.add(null);
			} else {
				// 有處理的事

				// 時間用完，換人用
				if (nowTimeSlice == 0) {
					processing.offer(processing.poll());
					nowTimeSlice = timeSlice;
				}

				Process nowProcess = processing.peek();
				
				gantt.add(nowProcess);
				nowTimeSlice--;
				
				// 是否執行完成
				if( nowProcess.pushBurst()) {
					nowProcess.finishTime = time;
					processing.poll();
					nowTimeSlice = timeSlice;
				}
			}
			time++;
		}
		
		return gantt;
	}
}

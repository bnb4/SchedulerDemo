import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PSJF {
	@SuppressWarnings("unchecked")
	public static List<Process> schedule(List<Process> processes) {
		
		// ID 排序
		Collections.sort(processes, Utils.getProcessIdSortInstance());				
		// 到達時間排序
		Collections.sort(processes, Utils.getProcessArrivalTimeSortInstance());
		
		List<Process> gantt = new LinkedList<>();
		Queue<Process> queue = new LinkedList<>(processes);
		Queue<Process> ready = new LinkedList<>();
		List<Process> processing = new ArrayList<>();
		
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
				Collections.sort(processing, Utils.getProcessCpuBurstRemainingSortInstance());
				Collections.sort((List<Process>) ready, Utils.getProcessCpuBurstRemainingSortInstance());
				
				if (processing.size() == 0) {
					processing.add(ready.poll());
				} else {
					if (ready.size() > 0 &&
							processing.get(0).getRemainingBurst() >= ready.peek().cpuBurst) {
						processing.add(0, ready.poll());
					}
				}
				
				Process nowProcess = processing.get(0);
				
				// 紀錄執行的程序
				gantt.add(nowProcess);
				
				// 是否執行完成
				if( nowProcess.pushBurst()) {
					nowProcess.finishTime = time;
					processing.remove(0);
				}
			}
			time++;
		}
		
		return gantt;
	}
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NPSJF {

	public static List<Process> schedule(List<Process> processes) {
		
		// ID 排序
		Collections.sort(processes, Utils.getProcessIdSortInstance());				
		// 到達時間排序
		Collections.sort(processes, Utils.getProcessArrivalTimeSortInstance());
		
		List<Process> gantt = new LinkedList<>();
		Queue<Process> queue = new LinkedList<>(processes);
		List<Process> processing = new ArrayList<>();
		
		Process p = null;
		
		int time = 0;
		while (p != null || !processing.isEmpty() || !queue.isEmpty()) {
			
			// 檢查有沒有程序要進入
			while (queue.peek() != null && queue.peek().arrivalTime <= time) 
				processing.add(queue.poll());
			
			if ( p == null && processing.size() == 0) {
				// 沒有可處裡的事
				gantt.add(null);
			} else {
				// 有處理的事
				// 若優先執行是空的，找一個最小的來執行
				if ( p == null ) {
					Collections.sort(processing, Utils.getProcessCpuBurstRemainingSortInstance());
					p = processing.remove(0);
				}
				
				// 紀錄執行的程序
				gantt.add(p);
				
				// 是否執行完成
				if( p.pushBurst()) {
					p.finishTime = time;
					p = null;
				}
			}
			time++;
		}
		
		return gantt;
	}
}

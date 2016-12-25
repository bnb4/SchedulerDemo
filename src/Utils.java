import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

	private static ProcessIdSort processIdSort;
	public static ProcessIdSort getProcessIdSortInstance() {
		return processIdSort == null ? 
				processIdSort = new ProcessIdSort() : processIdSort;}
	
	public static class ProcessIdSort implements Comparator<Process> {

		private ProcessIdSort() {}
		
		@Override
		public int compare(Process a, Process b) {
			if ( a == null ) return 1;
			if ( b == null ) return -1;
			return a.id - b.id;
		}
	}
	
	private static ProcessArrivalTimeSort processArrivalTimeSort;
	public static ProcessArrivalTimeSort getProcessArrivalTimeSortInstance() {
		return processArrivalTimeSort == null ? 
				processArrivalTimeSort = new ProcessArrivalTimeSort() : processArrivalTimeSort;}
	public static class ProcessArrivalTimeSort implements Comparator<Process> {

		private ProcessArrivalTimeSort() {}
		
		@Override
		public int compare(Process a, Process b) {
			return a.arrivalTime - b.arrivalTime;
		}
	}
	
	private static ProcessCpuBurstRemainingSort processCpuBurstRemainingSort;
	public static ProcessCpuBurstRemainingSort getProcessCpuBurstRemainingSortInstance() {
		return processCpuBurstRemainingSort == null ? 
				processCpuBurstRemainingSort = new ProcessCpuBurstRemainingSort() : processCpuBurstRemainingSort;}
	public static class ProcessCpuBurstRemainingSort implements Comparator<Process> {

		private ProcessCpuBurstRemainingSort() {}
		
		@Override
		public int compare(Process a, Process b) {
			return a.getRemainingBurst() - b.getRemainingBurst();
		}
	}
	
	private static ProcessPrioritySort processPrioritySort;
	public static ProcessPrioritySort getProcessPrioritySortInstance() {
		return processPrioritySort == null ? 
				processPrioritySort = new ProcessPrioritySort() : processPrioritySort;}
	public static class ProcessPrioritySort implements Comparator<Process> {

		private ProcessPrioritySort() {}
		
		@Override
		public int compare(Process a, Process b) {
			return a.priority - b.priority;
		}
	}
	
	public static class ResultData {
		private final Map<MethodType, List<Process>> data = new HashMap<>();
		
		public final void addResult(MethodType method, List<Process> data) {
			this.data.put(method, data);
		}
		
		public final List<Process> getResult(MethodType method) {
			return this.data.get(method);
		}
	}
	
}

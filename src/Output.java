import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Output {
	
	private PrintWriter file = null;
	
	public void close() {
		if (file != null) file.close();
	}
	
	public boolean setFile(String path) {
		if (file != null) file.close();
		return newFile(path);
	}
	
	private boolean newFile(String path) {
		try {
			file = new PrintWriter(path, "UTF-8");
			return true;
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("輸出檔案路經開啟時發生錯誤!");
			return false;
		}
	}
	
	public void println(String data) {
		System.out.println(data);
		if (file != null) file.println(data);
	}

	public boolean printResult(MethodType method, Utils.ResultData result) {
		switch (method) {
		case FCFS: 		return false;
		case RR: 		return false;
		case PSJF: 		return false;
		case NPSJF: 	return false;
		case PRIORITY: 	return false;
		case ALL: 		return printALL(result);
		default: 		return false;
		}
	}
	
	private boolean printALL(Utils.ResultData result) {
		if (result == null) return false;
		
		List<Process> FCFS = result.getResult(MethodType.FCFS);
		List<Process> RR = result.getResult(MethodType.RR);
		List<Process> PSJF = result.getResult(MethodType.PSJF);
		List<Process> NPSJF = result.getResult(MethodType.NPSJF);
		List<Process> Priority = result.getResult(MethodType.PRIORITY);
		
		List<Process> sortedFCFS = getSortedSet(FCFS);
		List<Process> sortedRR = getSortedSet(RR);
		List<Process> sortedPSJF = getSortedSet(PSJF);
		List<Process> sortedNPSJF = getSortedSet(NPSJF);
		List<Process> sortedPriority = getSortedSet(Priority);
		
		println("==    FCFS==");
		println(getGanttString(FCFS));
		println("==      RR==");
		println(getGanttString(RR));
		println("==    PSJF==");
		println(getGanttString(PSJF));
		println("==Non-PSJF==");
		println(getGanttString(NPSJF));
		println("== Priority==");
		println(getGanttString(Priority));
		println("===========================================================\r\n");
		println("Waiting Time");
		println("ID\tFCFS\tRR\tPSJF\tNPSJF\tPriority");
		println("===========================================================");
		println(getAllWaitingTime(sortedFCFS, sortedRR, sortedPSJF, sortedNPSJF, sortedPriority));
		println("===========================================================\r\n");
		println("Turnaround Time");
		println("ID\tFCFS\tRR\tPSJF\tNPSJF\tPriority");
		println("===========================================================");
		println(getAllTurnaroundTime(sortedFCFS, sortedRR, sortedPSJF, sortedNPSJF, sortedPriority));
		println("===========================================================");
	
		return true;
	}
	
	private String getGanttString(List<Process> gantt) {
		if (gantt == null) return "";
		
		StringBuilder sb = new StringBuilder();
		Iterator<Process> iterator = gantt.iterator();
		
		while (iterator.hasNext()) {
			Process process = iterator.next();

			if (process == null) {
				// 當下沒有程序
				sb.append('-');
			} else {
				int id = process.id;
				if (id > 9) sb.append(((char) (id - 10 + 'A') ));
				else sb.append(String.valueOf(id));
			}
		}
		return sb.toString();
	}
	
	private List<Process> getSortedSet(List<Process> list) {
		if (list == null) return null;
		ArrayList<Process> l = new ArrayList<>(new HashSet<>(list));
		Collections.sort(l, Utils.getProcessIdSortInstance());
		return l;
	}
	
	private String getAllWaitingTime(List<Process> FCFS, List<Process> RR,
			List<Process> PSJF, List<Process> NPSJF, List<Process> Priority) {
		
		StringBuilder sb = new StringBuilder();
		
		int count = FCFS.size();
		
		for (int i = 0; i < count; i++) {
			Process p = FCFS.get(i);
			if (p == null) continue;
			int id = p.id;
			
			sb.append(id);sb.append('\t');
			sb.append(FCFS==null?"9999":FCFS.get(i).getWaitingTime());sb.append('\t');
			sb.append(RR==null?"9999":RR.get(i).getWaitingTime());sb.append('\t');
			sb.append(PSJF==null?"9999":PSJF.get(i).getWaitingTime());sb.append('\t');
			sb.append(NPSJF==null?"9999":NPSJF.get(i).getWaitingTime());sb.append('\t');
			sb.append(Priority==null?"9999":Priority.get(i).getWaitingTime());sb.append('\t');
			sb.append("\r\n");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	
	private String getAllTurnaroundTime(List<Process> FCFS, List<Process> RR,
			List<Process> PSJF, List<Process> NPSJF, List<Process> Priority) {
		
		StringBuilder sb = new StringBuilder();
		
		int count = FCFS.size();
		
		for (int i = 0; i < count; i++) {
			Process p = FCFS.get(i);
			if (p == null) continue;
			int id = p.id;
			
			sb.append(id);sb.append('\t');
			sb.append(FCFS==null?"9999":FCFS.get(i).getThrnaroundTime());sb.append('\t');
			sb.append(RR==null?"9999":RR.get(i).getThrnaroundTime());sb.append('\t');
			sb.append(PSJF==null?"9999":PSJF.get(i).getThrnaroundTime());sb.append('\t');
			sb.append(NPSJF==null?"9999":NPSJF.get(i).getThrnaroundTime());sb.append('\t');
			sb.append(Priority==null?"9999":Priority.get(i).getThrnaroundTime());sb.append('\t');
			sb.append("\r\n");
		}
		sb.delete(sb.length()-2, sb.length());
		
		return sb.toString();
	}
}

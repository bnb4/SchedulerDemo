
public class Process implements Cloneable{
	public int id;
	public int cpuBurst;
	public int arrivalTime;
	public int priority;
	
	public int finishTime = -1;
	private int clacedBurst = 0;
	
	public Process(int id, int cpuBurst, int arrivalTime, int priority) {
		this.id = id;
		this.cpuBurst = cpuBurst;
		this.arrivalTime = arrivalTime;
		this.priority = priority;
	}
	
	/**
	 * 往前執行一個slice 
	 * @return 是否執行完畢
	 */
	public boolean pushBurst() {
		this.clacedBurst++;
		if (getRemainingBurst() == 0) return true;
		else return false;
	}
	
	public int getBurst() {
		return this.clacedBurst;
	}
	
	public int getRemainingBurst() {
		return this.cpuBurst - getBurst();
	}
	
	public int getThrnaroundTime() {
		if (finishTime == -1) return -1;
		return finishTime - arrivalTime + 1;
	}
	
	public int getWaitingTime() {
		int thT = getThrnaroundTime();
		if (thT == -1) return -1;
		return thT - cpuBurst;
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}

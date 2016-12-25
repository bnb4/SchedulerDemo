public enum MethodType {
	FCFS(1),RR(2),PSJF(3),NPSJF(4),PRIORITY(5),ALL(6);
	public static MethodType fromInteger(int x) {
		switch(x) {
	        case 1:return FCFS;
	        case 2:return RR;
	        case 3:return PSJF;
	        case 4:return NPSJF;
	        case 5:return PRIORITY;
	        case 6:return ALL;
	        default: return null;
        }
	}
	private final int value;
    private MethodType(int value) { this.value = value;} 
    public int value() { return this.value; }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileParser {
	
	public static class ReturnData {

		public final MethodType method;
		public final int timeSlice;
		
		private final List<Process> list = new ArrayList<>();
		
		public ReturnData(MethodType method, int timeSlice) {
			this.method = method;
			this.timeSlice = timeSlice;
		}
		
		public void add(Process p) {list.add(p);}
		
		public List<Process> getData() {
			int size = list.size();
			List<Process> l = new ArrayList<>(size);
			for (int i = 0; i < size; i++) l.add(i, (Process) list.get(i).clone());
			return l;
		}
	}
	
	private String path;
	
	public FileParser(String path) {
		this.path = path;
	}
	
	public ReturnData get() {

		if (path == null || path.length() == 0) return null;
		
		try (Scanner scn = new Scanner(new File(path))){
			ReturnData returnData = new ReturnData( MethodType.fromInteger(scn.nextInt()), scn.nextInt());
			
			scn.nextLine(); scn.nextLine();
			
			while (scn.hasNext()) 
				returnData.add(new Process(scn.nextInt(),scn.nextInt(),scn.nextInt(),scn.nextInt()));
			
			return returnData;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("檔案格式錯誤!");
		}
		
		return null;
	}
	
}

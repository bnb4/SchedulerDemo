import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		Output output = new Output();
		
		@SuppressWarnings("resource")
		Scanner scn = new Scanner(System.in);
		
		// input file
		System.out.print("Input file:");
		String inputFile = scn.nextLine();
		FileParser.ReturnData returnData = 
				new FileParser(inputFile.equals("")?"input1.txt":inputFile).get();
		
		// output file
		System.out.print("Output file:");
		String outputFile = scn.nextLine();
		output.setFile(outputFile.equals("") ? "output1.txt" : outputFile);

		// Start calculation
		Utils.ResultData resultData = new Utils.ResultData();
		resultData.addResult(MethodType.FCFS, FCFS.schedule(returnData.getData()));
		resultData.addResult(MethodType.RR, RR.schedule(returnData.getData(), returnData.timeSlice));
		resultData.addResult(MethodType.PSJF, PSJF.schedule(returnData.getData()));
		resultData.addResult(MethodType.NPSJF, NPSJF.schedule(returnData.getData()));
		resultData.addResult(MethodType.PRIORITY, Priority.schedule(returnData.getData()));
		
		output.printResult(returnData.method, resultData);
		output.close();
	}
}

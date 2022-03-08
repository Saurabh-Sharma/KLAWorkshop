package kla.workshop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

public class Execute{
	
	
	public void execute(Map<String, Object> workflow) {
		Map.Entry<String, Object> workflowMap = (Map.Entry<String, Object>)workflow.entrySet().iterator().next();
		
		// Setting the log message
		String logStr = workflowMap.getKey();
		
		// Adding log to the log_file
		System.out.println(getTime()+";"+logStr+" Entry");
		
		// Getting flow information
		Map<String, Object> map = (Map<String, Object>)workflowMap.getValue();
		
		if(map.get("Type").toString().equals("Flow")) {
			if(map.get("Execution").toString().equals("Sequential")) {
				executeSequentialFlow((Map<String, Object>)map.get("Activities"), logStr);
			}
		}
		System.out.print(getTime()+";"+logStr+" Exit");
	}
	
	// Sequential Flow Execution
	private void executeSequentialFlow(Map<String, Object> workflowMap, String logStr) {
		for(Map.Entry<String, Object> valueObj: workflowMap.entrySet()) {
			// Creating the log for flow
			String currLogStr = valueObj.getKey();
			
			// Getting the flow information
			Map<String, Object> map = (Map<String, Object>)valueObj.getValue();
			String type = map.get("Type").toString(); 
			
			// Checking the Type for Task or Flow
			if(type.equals("Task")) {
				String function = (String)map.get("Function");
				if(function.equals("TimeFunction")) {
					Map<String, String> inputs = (Map<String, String>)map.get("Inputs");
					String functionInput = inputs.get("FunctionInput");
					String executionTimeString = inputs.get("ExecutionTime");
					int executionTime = Integer.parseInt(executionTimeString);
					
					// Printing the entry log
					System.out.println(getTime()+";"+logStr+"."+currLogStr+" Entry");
					
					// Calling timeFunction with specified execution time
					try {
						timeFunction(functionInput, executionTime, logStr+"."+currLogStr);
					}catch(Exception exc) {
		
					}
					
					// Printing the exit log
					System.out.println(getTime()+";"+logStr+"."+currLogStr+" Exit");
				}
			}
			else if(type.equals("Flow")) {
				String execution = (String)map.get("Execution");
				
				// Checking the inner flow info
				if(execution.equals("Sequential")) {
					// If flow is sequential then recursively calling flow
					System.out.println(getTime()+";"+logStr+"."+currLogStr+" Entry");
					executeSequentialFlow((Map<String, Object>)map.get("Activities"), logStr+"."+currLogStr);
					System.out.println(getTime()+";"+logStr+"."+currLogStr+" Exit");
				}
			}
		}
	}
	
	private void executeConcurrentFlow(Map<String, Object> workflowMap, String logStr) {
		for(Map.Entry<String, Object> valueObj: workflowMap.entrySet()) {
			// Creating the log for flow
			String currLogStr = valueObj.getKey();
			
			// Creating thread 
			Thread t = new Thread();
			t.run();
			// Getting the flow information
			Map<String, Object> map = (Map<String, Object>)valueObj.getValue();
			String type = map.get("Type").toString(); 
			
			// Checking the Type for Task or Flow
			if(type.equals("Task")) {
				String function = (String)map.get("Function");
				if(function.equals("TimeFunction")) {
					Map<String, String> inputs = (Map<String, String>)map.get("Inputs");
					String functionInput = inputs.get("FunctionInput");
					String executionTimeString = inputs.get("ExecutionTime");
					int executionTime = Integer.parseInt(executionTimeString);
					
					// Printing the entry log
					System.out.println(getTime()+";"+logStr+"."+currLogStr+" Entry");
					
						// Calling timeFunction with specified execution time
						try {
							timeFunction(functionInput, executionTime, logStr+"."+currLogStr);
						}catch(Exception exc) {
		
					}
					
					// Printing the exit log
					System.out.println(getTime()+";"+logStr+"."+currLogStr+" Exit");
				}
			}
			else if(type.equals("Flow")) {
				String execution = (String)map.get("Execution");
				
				// Checking the inner flow info
				if(execution.equals("Sequential")) {
					// If flow is sequential then recursively calling flow
					System.out.println(getTime()+";"+logStr+"."+currLogStr+" Entry");
					executeSequentialFlow((Map<String, Object>)map.get("Activities"), logStr+"."+currLogStr);
					System.out.println(getTime()+";"+logStr+"."+currLogStr+" Exit");
				}
			}
		}
	}

	private void timeFunction(String functionInput, int executionTime, String logStr) throws InterruptedException {
		System.out.print(getTime()+";"+logStr+" Executing TimeFunction ");
		System.out.println("("+functionInput+", "+executionTime+")");
		
		// Making Main thread sleep for executionTime seconds
		Thread.sleep(executionTime*1000);
	}
	
	public String getTime() {
		StringBuilder time = new StringBuilder(LocalDateTime.now().toString());
		int idx = time.indexOf("T");
		time.setCharAt(idx, ' ');
		time = new StringBuilder(time.substring(0, 20));
		time.append("000000");
		return time.toString();
	}
}

class MyRunnable implements Runnable {
	Map<String, Object> tasks;
	
	MyRunnable() {
		tasks = new LinkedHashMap<String, Object>();
	}
	
	
	
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

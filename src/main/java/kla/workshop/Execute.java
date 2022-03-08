package kla.workshop;

import java.util.Map;
import java.time.LocalDateTime;

public class Execute {
	
	
	public void execute(Map<String, Object> workflow) {
		Map.Entry<String, Object> workflowMap = (Map.Entry<String, Object>)workflow.entrySet().iterator().next();
		String logStr = workflowMap.getKey();
		System.out.println(getTime()+";"+logStr+" Entry");
		Map<String, Object> map = (Map<String, Object>)workflowMap.getValue();
		if(map.get("Type").toString().equals("Flow")) {
			if(map.get("Execution").toString().equals("Sequential")) {
				executeSequentialFlow((Map<String, Object>)map.get("Activities"), logStr);
			}
		}
		System.out.println(getTime()+" "+logStr+" Exit");
	}
	
	public void executeSequentialFlow(Map<String, Object> workflowMap, String logStr) {
		for(Map.Entry<String, Object> valueObj: workflowMap.entrySet()) {
			String currLogStr = valueObj.getKey();
			Map<String, Object> map = (Map<String, Object>)valueObj.getValue();
			String type = map.get("Type").toString(); 
			if(type.equals("Task")) {
				String function = (String)map.get("Function");
				if(function.equals("TimeFunction")) {
					Map<String, String> inputs = (Map<String, String>)map.get("Inputs");
					String functionInput = inputs.get("FunctionInput");
					String executionTimeString = inputs.get("ExecutionTime");
					int executionTime = Integer.parseInt(executionTimeString);
					System.out.println(getTime()+" "+logStr+"."+currLogStr+" Entry");
					timeFunction(functionInput, executionTime, logStr+"."+currLogStr);
					System.out.println(getTime()+" "+logStr+"."+currLogStr+" Exit");
				}
			}
			else if(type.equals("Flow")) {
				String execution = (String)map.get("Execution");
				if(execution.equals("Sequential")) {
					System.out.println(getTime()+" "+logStr+"."+currLogStr+" Entry");
					executeSequentialFlow((Map<String, Object>)map.get("Activities"), logStr+"."+currLogStr);
					System.out.println(getTime()+" "+logStr+"."+currLogStr+" Exit");
				}
			}
		}
	}

	private void timeFunction(String functionInput, int executionTime, String logStr) {
		System.out.println(getTime()+" "+logStr+" Executing TimeFunction");
		System.out.println("("+functionInput+", "+executionTime+")");
	}
	
	public String getTime() {
		StringBuilder time = new StringBuilder(LocalDateTime.now().toString());
		int idx = time.indexOf("T");
		time.setCharAt(idx, ' ');
		return time.toString();
	}
}

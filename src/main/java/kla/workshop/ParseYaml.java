package kla.workshop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ParseYaml {
	public static void main(String[] args) throws Exception{
		InputStream inputStream = new FileInputStream("src/main/resources/Milestone1A.yaml");
		System.setOut(new PrintStream(new FileOutputStream("src/main/resources/Milestone1A_Log.txt")));
		Yaml yaml = new Yaml();
		Map<String, Object> workflowMap = yaml.load(inputStream);
		Execute execute = new Execute();
		execute.execute(workflowMap);
	}
}

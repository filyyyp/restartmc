package sk.ujcik.restartmc.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class LinuxService {


	public String runCommand(String command, String folder) throws IOException {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("sh", "-c", command);
		if (folder != null) {
			builder.directory(new File(folder));
		}

		Process process = builder.start();

		BufferedReader reader =
			new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while ( (line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(System.getProperty("line.separator"));
		}

		return stringBuilder.toString();
	}
}

package sk.ujcik.restartmc.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MinecraftController {

	@GetMapping("/minecraftLog")
	public String getMinecraftLog() throws IOException, InterruptedException {
		boolean isWindows = System.getProperty("os.name")
			.toLowerCase().startsWith("windows");
		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
			builder.command("cmd.exe", "/c", "type trace.txt");
		} else {
			builder.command("sh", "-c", "tail -50 log-gt.txt");
		}
		builder.directory(new File("/home/minecraft"));

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

	@PostMapping("/restart")
	public String restartServer() throws Exception {
		boolean isWindows = System.getProperty("os.name")
			.toLowerCase().startsWith("windows");
		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
			throw new Exception("Can not run on windows!");
		} else {
			builder.command("sh", "-c", "service minecraft-gt restart");
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



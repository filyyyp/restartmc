package sk.ujcik.restartmc.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.ujcik.restartmc.service.LinuxService;

@RestController
public class MinecraftController {
	private final String MINECRAFT_FOLDER = "/home/minecraft";
	private final String MINECRAFT_GT_FOLDER = "/home/minecraft/gtnewhorizon";

	private final LinuxService linuxService;

	public MinecraftController(LinuxService linuxService) {
		this.linuxService = linuxService;
	}

	@GetMapping("/minecraftLog")
	public String getMinecraftLog() throws IOException {
		return linuxService.runCommand("tail -50 log-gt.txt", MINECRAFT_FOLDER);
	}

	@PostMapping("/restart")
	public String restartServer() throws IOException {
		return linuxService.runCommand("service minecraft-gt restart", null);
	}

	@GetMapping("/status")
	public String getServerStatus() throws IOException {
		return linuxService.runCommand("mcstatus localhost status", null);
	}
}



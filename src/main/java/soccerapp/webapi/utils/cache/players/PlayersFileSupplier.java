package soccerapp.webapi.utils.cache.players;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class PlayersFileSupplier implements Function<String, String> {

	private static final String PREFIX = "src/main/resources/cache/teams/";
	private final Function<String, String> dataSrc;

	public PlayersFileSupplier(Function<String, String> dataSrc) {
		this.dataSrc = dataSrc;
	}

	@Override
	public String apply(String path) {
		String html = readFromDisk(path.substring(1));
		if (html != null)
			return html;

		html = dataSrc.apply(path);
		writeToDisk(path.substring(1), html);
		return html;
	}

	private String readFromDisk(String id) {
		StringBuilder out = new StringBuilder();
		String fileName = "Team_" + id + "_Players";
		File file = new File(PREFIX + fileName + ".html");
		String absolutePath = file.getAbsolutePath();
		Path path = Paths.get(absolutePath);
		if (Files.isReadable(path)) {
			try {
				List<String> lines = Files.readAllLines(path);
				lines.forEach(out::append);
				return out.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void writeToDisk(String id, String text) {
		String fileName = "Team_" + id + "_Players";
		File file = new File(PREFIX + fileName + ".html");
		String absolutePath = file.getAbsolutePath();
		Path path = Paths.get(absolutePath);
		try {
			if(!Files.isReadable(path))
				Files.createFile(path);
			Files.write(path, Collections.singletonList(text), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package soccerapp.webapi.utils.cache.leaguesList;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class LeaguesListFileSupplier implements Supplier<String> {

	private static final String PREFIX = "src/main/resources/cache/";
	private static final String FILE_NAME = "leaguesList.html";
	private final Supplier<String> dataSrc;

	public LeaguesListFileSupplier(Supplier<String> dataSrc) {
		this.dataSrc = dataSrc;
	}

	@Override
	public String get() {
		String html = readFromDisk();
		if(html != null)
			return html;

		html = dataSrc.get();
		writeToDisk(html);
		return html;
	}

	private String readFromDisk(){
		StringBuilder out = new StringBuilder();

		File file = new File(PREFIX + FILE_NAME);
		String absolutePath = file.getAbsolutePath();
		Path path = Paths.get(absolutePath);
		if(Files.isReadable(path)){
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

	private void writeToDisk(String text){
		File file = new File(PREFIX + FILE_NAME);
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

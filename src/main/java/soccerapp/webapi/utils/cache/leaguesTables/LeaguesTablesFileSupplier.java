package soccerapp.webapi.utils.cache.leaguesTables;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class LeaguesTablesFileSupplier implements Function<Integer, String> {

	private static final String PREFIX = "src/main/resources/cache/leaguesTables/";
	private final Function<Integer, String> dataSrc;

	public LeaguesTablesFileSupplier(Function<Integer, String> dataSrc) {
		this.dataSrc = dataSrc;
	}

	@Override
	public String apply(Integer id) {
		String html = readFromDisk(id);
		if(html != null)
			return html;

		html = dataSrc.apply(id);
		writeToDisk(id, html);
		return html;
	}

	private String readFromDisk(int id){
		StringBuilder out = new StringBuilder();
		String fileName = "League_Table_" + id;
		File file = new File(PREFIX + fileName + ".html");
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

	private void writeToDisk(int id, String text){
		String fileName = "League_Table_" + id;
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

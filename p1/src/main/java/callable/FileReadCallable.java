package callable;

import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.P1Util;

public class FileReadCallable implements Callable<String> {

	private Logger logger = LoggerFactory.getLogger(FileReadCallable.class);

	private List<String> filters;

	private String filename;

	public FileReadCallable(String filename, List<String> filters) {
		this.filename = filename;
		this.filters = filters;
	}

	public String call() {

		try (BufferedReader reader = new BufferedReader(new FileReader(
				new File(filename)))) {
			String line;
			while ((line = reader.readLine()) != null) {

				boolean filtertarget = false;
				for (String filter : filters) {
					Pattern p = Pattern.compile(P1Util.FILTER_BASE.replace(
							"$1", filter));
					Matcher filterMatcher = p.matcher(line);
					if (filterMatcher.find()) {
						filtertarget = true;
						break;
					}
				}

				if (filtertarget == false) {

					String url = P1Util.parseUrl(line);
					if (url.equals("")) {
						continue;
					}

					String ip = P1Util.parseIp(line);
					if (ip.equals("")) {
						continue;
					}

					String date = P1Util.parseTime(line);
					if (date.equals("")) {
						continue;
					}

					File file = new File(ip);
					if (!file.exists()) {
						file.createNewFile();
					}

					try (BufferedWriter writer = new BufferedWriter(
							new FileWriter(file))) {
						writer.write(ip + "," + url + "," + date);
					}
				}
			}
		} catch (IOException e) {
			logger.error("error", e);
		}
		return "";
	}
}

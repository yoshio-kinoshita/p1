package callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.P1Util;
import entity.AccessLog;

public class LogAnalisysCallable implements Callable<List<AccessLog>> {

	private List<String> lines;
	private List<String> filters;

	public LogAnalisysCallable(List<String> lines, List<String> filters) {
		this.lines = lines;
		this.filters = filters;
	}

	@Override
	public List<AccessLog> call() throws Exception {

		List<AccessLog> loglist = new ArrayList<>();
		for (String line : lines) {
			boolean filtertarget = false;
			for (String filter : filters) {
				Pattern p = Pattern.compile(P1Util.FILTER_BASE.replace("$1",
						filter));
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

				String date = P1Util.parseTime2String(line);
				if (date.equals("")) {
					continue;
				}

				AccessLog log = new AccessLog();
				log.setIp(ip);
				log.setUrl(url);
				log.setAccessDate(date);

				loglist.add(log);
			}
		}
		return loglist;
	}

}

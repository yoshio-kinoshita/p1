import java.util.List;

public interface LogParser {

	public List<Object[]> parser(List<String> logfiles, List<String> filters);

}

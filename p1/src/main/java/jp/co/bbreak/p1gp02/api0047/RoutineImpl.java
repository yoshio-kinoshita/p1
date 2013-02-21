package jp.co.bbreak.p1gp02.api0047;

import java.io.File;
import java.util.List;

import jp.co.bbreak.p1gp02.application.Result;
import jp.co.bbreak.p1gp02.application.Routine;

public class RoutineImpl implements Routine{

	@Override
	public List<Result> analyze(List<File> files, List<String> filters)
			throws Exception {
		return Job.start(files, filters);
	}

	@Override
	public String getUserName() {
		return "木下喜雄";
	}

}

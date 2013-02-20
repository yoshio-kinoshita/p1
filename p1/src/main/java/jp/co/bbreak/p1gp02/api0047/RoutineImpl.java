package jp.co.bbreak.p1gp02.api0047;

import java.io.File;
import java.util.List;

import jp.co.bbreak.p1gp02.application.Result;
import jp.co.bbreak.p1gp02.application.Routine;

public class RoutineImpl implements Routine {

	@Override
	public List<Result> analyze(List<File> arg0, List<String> arg1)
			throws Exception {
		return Job.start(arg0, arg1);
	}

	@Override
	public String getUserName() {
		return "0047";
	}

}

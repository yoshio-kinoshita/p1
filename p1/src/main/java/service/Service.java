package service;

import java.util.List;

import entity.AccessLog;
import entity.Result;

public interface Service {

	public List<Result> createResult(List<AccessLog> accessLog);

}

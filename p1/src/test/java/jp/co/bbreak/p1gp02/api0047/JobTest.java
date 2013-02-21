package jp.co.bbreak.p1gp02.api0047;

import java.util.Arrays;

import org.junit.Test;

public class JobTest {

	@Test
	public void array() {
		int i=1;
		System.out.println(Arrays.binarySearch(P1Util.RESULT_INDEX, i));
		
		i=2;
		System.out.println(Arrays.binarySearch(P1Util.RESULT_INDEX, i));
		
		i=20;
		System.out.println(Arrays.binarySearch(P1Util.RESULT_INDEX, i));
		
	}

}

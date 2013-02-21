import java.io.*;

class MyTimer {

	private final long start;

	public MyTimer() {
		start = System.currentTimeMillis();
	}

	public long getElapsed() {
		return System.currentTimeMillis() - start;
	}

}

public class IODemo4 {

	static final int N = 1000000;
	static final String TESTFILE = "src/test/resources/p1gp2_log2.txt";
	static int cnt1, cnt2;

	static void read1() throws IOException {
		// readLine() を使ってテキストファイルから読み込み
		MyTimer mt = new MyTimer();
		FileReader fr = new FileReader(TESTFILE);
		BufferedReader br = new BufferedReader(fr);

		cnt1 = 0;
		while (br.readLine() != null) {
			cnt1++;
		}
		br.close();
		System.out.println("read1 " + mt.getElapsed());
	}

	static void read2() throws IOException {

		// バッファ使ってテキストファイルを読み込み、

		// 個々の文字を取得

		MyTimer mt = new MyTimer();
		FileReader fr = new FileReader(TESTFILE);
		BufferedReader br = new BufferedReader(fr);
		cnt2 = 0;
		int prev = -1;
		final int BUFSIZE = 1024 * 8;
		char cbuf[] = new char[BUFSIZE];
		int currpos = 0;
		int maxpos = 0;
		
		StringBuilder sb = new StringBuilder();
		while (true) {
			if (currpos == maxpos) {
				maxpos = br.read(cbuf, 0, BUFSIZE);
				if (maxpos == -1) {
					break;
				}
				currpos = 0;
			}
			
			int c = cbuf[currpos++];
			sb.append(cbuf[currpos++]);
			if (c == '\r' || (c == '\n' && prev != '\r')) {
				System.out.println(sb.toString());
				cnt2++;
			}
			prev = c;
		}
		br.close();
		System.out.println("read2 " + mt.getElapsed());

	}

	public static void main(String args[])

	throws IOException {

		// テストファイルの書き出し

		// 1 回目のテストファイルの読み込み

		read1();
		read2();

		// sanity check

		if (cnt1 != cnt2) {
			System.out.println("cnt1/cnt2 mismatch");
		}

		// 2 回目のテストファイルの読み込み

		read1();
		read2();

	}

}
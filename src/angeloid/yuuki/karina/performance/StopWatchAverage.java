package angeloid.yuuki.karina.performance;

public class StopWatchAverage {
	long  startTime;
	long  elapsedTime = 0;
	double  totalElapsedTime;
	long  runCount = 0;
	String  currentName;
	boolean threadFlag = false;


	/**
	 * 개체의 이름을 정의하면서 StopWatchAverage의 속성과 시간을 정합니다.
	 */
	public StopWatchAverage() {
		currentName ="";
		startTime = System.nanoTime();
	}

	/**
	 * Thread를 사용할지 안할지 결정합니다.
	 * @ param threadFlag Thread를 사용하는가?
	 */
	public StopWatchAverage(boolean threadFlag) {
		changeMessage("",true, true);
	}

	/**
	 * 메세제를 표시합니다.
	 * @ param message 메세지를 전달받아 표시합니다.
	 */
	public StopWatchAverage(String message) {
		changeMessage(message, false, true);
	}

	/**
	 * メッセージThreadを使用するかどうかを一緒に指定する作成
	 * @ param messageさらに明示するメッセージ
	 * @ param threadFlag Threadを使用するかどうか
	 */
	public StopWatchAverage(String message, boolean threadFlag) {
		changeMessage(message, threadFlag, true);
	}

	/**
	 * StopWatchの時間データを初期化する。
	 */
	public void reset() {
		startTime = System.nanoTime();
		elapsedTime = 0;
		totalElapsedTime = 0;
		runCount = 0;
	}

	/**
	 * 時間の計測を開始する。
	 */
	public void start() {
		startTime = System.nanoTime();
		elapsedTime = 0;
	}

	/**
	 * StopWatchを停止し、応答時間の結果を
	 * ArrayListに入れる. 
	 */
	public void stop() {
		elapsedTime = System.nanoTime() - startTime;
		totalElapsedTime += elapsedTime;
		runCount++;
	}

	/**
	 * メッセージを指定します。
	 * @ param messageさらに明示するメッセージ
	 * @ param threadFlag Threadを使用するかどうか
	 * @ param resetFlagオブジェクトの作成時にStopWatchリケトかどうか
	 */
	private void changeMessage(String message, boolean threadFlag, boolean resetFlag) {
		String threadName = "";
		this.threadFlag = threadFlag;

		if (threadFlag){
			threadName = " ThreadName= "+ Thread.currentThread().getName();
		}

		currentName = "[" + message + threadName + "]";
		if (resetFlag){
			reset();
		}
	}

	/**
     * StopWatchを停止し、最後に（あるいは現在まで）
     *実行された時間を返します。
     *@ return最後に実行されたミリ秒
	 */
	public double getElapsedMS() {
		if (elapsedTime == 0){
			stop();
		}
		return elapsedTime / 1000000.0;
	}

	/**
     * StopWatchを停止し、最後に（あるいは現在まで）
     *実行された時間を返します。
     *@ return最後に実行されたナノ秒
	 */
	public double getElapsedNano() {
		if (elapsedTime == 0) stop();

		return elapsedTime;
	}


	/** 
     *現在までに収集された回数、全実行時間の合計、
     *平均実行時間をミリ秒単位で返してくれる。
     *(non-Javadoc）
     *@ see java.lang.Object＃toString（）
     *@ return実行回数全体実行時間、平均実行時間
	 */
	public String toString() {
		if (elapsedTime == 0) stop();
		double elapsedAverage = totalElapsedTime / runCount;

		return currentName + "Run Count : " + runCount
				+ " , Total : " + totalElapsedTime / 1000000.0
				+ " ms, Average : " + elapsedAverage / 1000000.0
				+ " ms";
	}
}
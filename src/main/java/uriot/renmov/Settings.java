package uriot.renmov;

import java.io.Serializable;

public class Settings implements Serializable {
	Boolean logToFile;
	Boolean roundToSecond;
	String language;
	String lastDir1;
	String lastDir2;

	public Settings(Boolean logToFile, Boolean roundToSecond, String language, String lastDir1, String lastDir2) {
		this.logToFile = logToFile;
		this.roundToSecond = roundToSecond;
		this.language = language;
		this.lastDir1 = lastDir1;
		this.lastDir2 = lastDir2;
	}

	@Override
	public String toString() {
		return "Settings{" + "logToFile=" + logToFile + ", roundToSecond=" + roundToSecond + ", language=" + language + ", lastDir1=" + lastDir1 + ", lastDir2=" + lastDir2 + '}';
	}
	
}

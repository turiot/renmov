package uriot.renmov;

import java.time.Instant;

public class Criteria {
	public Instant date;
	public long    size;
	private final long millis;

	public Criteria(Instant date, long size, boolean roundToSecond) {
		this.date = date;
		this.size = size;
	    this.millis = roundToSecond ? date.toEpochMilli() / 1000L : date.toEpochMilli();
	}

	@Override
	public int hashCode() {
		var hash = 7;
		hash = 71 * hash + (int) (this.size ^ (this.size >>> 32));
		hash = 71 * hash + (int) (this.millis ^ (this.millis >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		var other = (Criteria) obj;
		if (this.size != other.size) {
			return false;
		}
		return other.millis == millis;
	}

	@Override
	public String toString() {
		return "Criteria{" + "date=" + date + ", size=" + size + '}';
	}
	
}

package seedutils.util;

public class Pair<A, B> {

	private final A a;
	private final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A getFirst() {
		return this.a;
	}

	public B getSecond() {
		return this.b;
	}

}

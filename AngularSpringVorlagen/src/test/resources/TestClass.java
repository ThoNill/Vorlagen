
import java.util.*;

public class TestClass {
	private int a = 1;
	private List<Integer> l;
	
	public TestClass() {
	}
	
	@Deprecated
	public void add() {
		l.stream().filter(x -> x < 10);
		int a =  (1 + 3) * 2;
	}

}

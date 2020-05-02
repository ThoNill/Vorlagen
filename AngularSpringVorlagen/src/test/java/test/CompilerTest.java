package test;

import java.io.File;

import org.junit.Test;
import org.nill.vorlagen.compiler.Compiler;

public class CompilerTest {

	public CompilerTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void versionen() {
		new Compiler().versions();
	}

	@Test
	public void compilieren() {
		try {
			final File source = new File(CompilerTest.class.getResource("/TestClass.java").toURI());
			new Compiler().compile(source);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

package org.nill.vorlagen.annotations;

import java.io.File;
import java.util.function.UnaryOperator;

import javax.annotation.processing.SupportedAnnotationTypes;

@SupportedAnnotationTypes("org.nill.vorlagen.annotations1.Modell")
public class TestModelProzessor extends ModellProzessor {

	public TestModelProzessor() {
		super(new File("src/main/resources/javavorlagen/single"), 
				new File("src/main/resources/javavorlagen/multi"),
				new File("./target/javatest"),UnaryOperator.identity());
	}

}

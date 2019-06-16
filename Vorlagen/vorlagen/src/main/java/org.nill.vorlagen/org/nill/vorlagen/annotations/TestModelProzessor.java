package org.nill.vorlagen.annotations;

import java.util.function.UnaryOperator;

import javax.annotation.processing.SupportedAnnotationTypes;

@SupportedAnnotationTypes("org.nill.vorlagen.annotations1.Modell")
public class TestModelProzessor extends ModellProzessor {

	public TestModelProzessor() {
		super("src/main/resources/javavorlagen/single", 
				"src/main/resources/javavorlagen/multi",
				"./target/javatest",UnaryOperator.identity());
	}

}

package org.nill.vorlagen.compiler1;

import java.io.File;
import java.util.function.UnaryOperator;

import javax.annotation.processing.SupportedAnnotationTypes;

import org.nill.vorlagen.annotations.ModellProzessor;

@SupportedAnnotationTypes("org.nill.vorlagen.annotations1.Modell")
public class TestModelProzessor extends ModellProzessor {

	public TestModelProzessor() {
		super(new File("src/main/resources/javavorlagen/single"), 
				new File("src/main/resources/javavorlagen/multi"),
				new File("./target/javatest"),UnaryOperator.identity());
	}

}

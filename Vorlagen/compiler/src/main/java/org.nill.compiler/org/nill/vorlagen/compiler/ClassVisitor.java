package org.nill.vorlagen.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import org.nill.vorlagen.compiler.model.DreiSichten;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.compiler.util.RuntimeCompilerException;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.PackageTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

public class ClassVisitor extends TreePathScanner<Object, Trees> {
	static Logger logger = Logger.getLogger(ClassVisitor.class.getSimpleName());

	private ConverterVerzeichnis converterVerzeichnis;
	private ObjectModell data = new ObjectModell();

	public ObjectModell getData() {
		return data;
	}

	public ClassVisitor(Class<?> clazz, Elements elements,ConverterVerzeichnis converterVerzeichnis) {
		
		this.data.setClazz(clazz);
		this.data.setElements(elements);
		this.converterVerzeichnis = converterVerzeichnis;
	}

	@Override
	public Object visitPackage(PackageTree node, Trees p) {
		logger.log(Level.INFO, "start visitPackage {0}",node);
		data.setPackageElement(data.getElements().getPackageElement(node.getPackageName().toString()));
		return super.visitPackage(node, p);
	}

	@Override
	public Object visitImport(ImportTree node, Trees p) {
		logger.log(Level.INFO, "start visitImport {0}",node);
		data.getImportsTrees().add(node);
		return super.visitImport(node, p);
	}

	@Override
	public Object visitVariable(VariableTree node, Trees p) {
		logger.log(Level.INFO, "start visitVariable {0}",node);
		Element elem = p.getElement(this.getCurrentPath());
		logger.log(Level.INFO,"Eement: {0}",elem);
		if (elem != null && elem.getKind().equals(ElementKind.FIELD)) {
			try {
				logger.log(Level.INFO,"declared Field: " + elem.getSimpleName().toString());
				logger.log(Level.INFO,"Class: {0}",data.getClazz().getSimpleName());
				Field f = data.getClazz().getDeclaredField(elem.getSimpleName().toString());
				logger.log(Level.INFO,"Field: {0}",f);	
				data.getFieldSichten()
						.add(new DreiSichten(elem, node,
								f,
								p.getDocComment(this.getCurrentPath()),converterVerzeichnis.get(f.getType())));
			} catch (Exception e) {
				throw new RuntimeCompilerException("Fehler in visitVariable element: " +elem.toString(),e);
			}

		}
		return super.visitVariable(node, p);
	}

	@Override
	public Object visitClass(ClassTree node, Trees p) {
		logger.log(Level.INFO, "start visitClass {0}",node);
		Element elem = p.getElement(this.getCurrentPath());
		if (elem != null && node.getSimpleName().contentEquals(data.getClazz().getSimpleName())) {
			data.setClassSicht(new DreiSichten(elem, node, data.getClazz(), p.getDocComment(this.getCurrentPath())));
		}
		return super.visitClass(node, p);
	}

	@Override
	public Object visitMethod(MethodTree node, Trees p) {
		logger.log(Level.INFO, "start visitMethod {0}",node);
		Element elem = p.getElement(this.getCurrentPath());
		if (elem != null && elem.getKind().equals(ElementKind.METHOD)) {
			try {
				Method m = searchMethod((ExecutableElement) elem);
				data.getMethodSichten().add(new DreiSichten(elem, node, m, p.getDocComment(this.getCurrentPath())));
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Fehler in visitMethod");
			}
		}
		return super.visitMethod(node, p);
	}

	private Method searchMethod(ExecutableElement elem)  {
		for (Method m : data.getClazz().getMethods()) {
			if (elem.getSimpleName().contentEquals(m.getName())) {
				List<? extends VariableElement> parameter = elem.getParameters();
				Parameter[] aparameter = m.getParameters();
				if (aparameter.length == parameter.size()) {
					boolean sindGleich = true;
					for (int i = 0; i < parameter.size() && sindGleich; i++) {
						String tparameter = parameter.get(i).asType().toString();
						String mparameter = aparameter[i].getType().getTypeName();
						sindGleich = sindGleich(tparameter, mparameter);
					}
					if (sindGleich) {
						return m;
					}
				}
			}
		}
		return null;
	}

	private boolean sindGleich(String tparameter, String mparameter) {
		return mparameter.equals(entferneSpitzeKlammern(tparameter));
	}

	private String entferneSpitzeKlammern(String mparameter) {
		StringBuilder buffer = new StringBuilder();
		int anzahlOffeneSpitzeLinkeKlammern = 0;
		for (char c : mparameter.toCharArray()) {
			switch (c) {
			case '<':
				anzahlOffeneSpitzeLinkeKlammern++;
				break;
			case '>':
				anzahlOffeneSpitzeLinkeKlammern--;
				break;
			default:
				if (anzahlOffeneSpitzeLinkeKlammern == 0) {
					buffer.append(c);
				}
				break;

			}
		}
		return buffer.toString();
	}
}

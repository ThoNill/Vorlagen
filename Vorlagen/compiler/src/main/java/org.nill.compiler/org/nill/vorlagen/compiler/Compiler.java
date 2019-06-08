package org.nill.vorlagen.compiler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.nill.vorlagen.interfaces.ConverterVerzeichnis;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTaskImpl;;

public class Compiler {

	public Compiler() {
		// TODO Auto-generated constructor stub
	}

	public void versions() {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		for (final SourceVersion version : compiler.getSourceVersions()) {
			System.out.println(version);
		}
	}

	public void compile(File source) throws IOException {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		final StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, null, null);
		final Iterable<? extends JavaFileObject> sources = manager.getJavaFileObjectsFromFiles(Arrays.asList(source));
		final CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sources);
		task.call();
		for (final Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			System.out.format("%s, line %d in %s", diagnostic.getMessage(null), diagnostic.getLineNumber(),
					diagnostic.getSource().getName());
		}
		// Trees trees = Trees.instance(task);
		manager.close();
	}

	public ObjectModell analyse(File sourceDir,Class clazz,ConverterVerzeichnis converter) throws IOException {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		final StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, null, null);
		
		File source = new File(sourceDir,clazz.getCanonicalName().replace(".","/") +".java");
		
		final Iterable<? extends JavaFileObject> sources = manager.getJavaFileObjectsFromFiles(Arrays.asList(source));
		final CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sources);
		
		
		JavacTaskImpl javaTask = (JavacTaskImpl) task;

		Iterable<? extends CompilationUnitTree> asts = javaTask.parse();
		javaTask.analyze();

		Elements elements = javaTask.getElements();
		/*
		for (ModuleElement modul : elements.getAllModuleElements()) {
			//System.out.println("Modul: " + modul.toString());
			elements.getDocComment(modul);
			for (Element p : modul.getEnclosedElements()) {
				if (p instanceof PackageElement) {
				//	System.out.println("Package: " + p.toString());
					for (Element c : p.getEnclosedElements()) {
						if (c instanceof TypeElement) {
							for (Element m : c.getEnclosedElements()) {
					//			System.out.println("Type: " + c.toString());
							}

						}
						elements.getDocComment(p);
					}
				}
			}
			Types types = javaTask.getTypes();
		*/
//	    types.isAssignable(t1, t2);
//	    types.isSameType(t1, t2)
			// javax.lang.model.type

			ClassVisitor myVisitor = new ClassVisitor(clazz,elements,converter);
			for (CompilationUnitTree ast : asts) {
				//elements.getAllMembers(ast);
				
			//	System.out.println(ast.toString());
				myVisitor.scan(ast,Trees.instance(task));
			}

			for (final Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
				System.out.format("%s, line %d in %s", diagnostic.getMessage(null), diagnostic.getLineNumber(),
						diagnostic.getSource().getName());
			}
			// Trees trees = Trees.instance(task);
			manager.close();
			return myVisitor.getData();
		//}
	}
}

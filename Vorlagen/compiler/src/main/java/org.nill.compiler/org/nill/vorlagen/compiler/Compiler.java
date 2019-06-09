package org.nill.vorlagen.compiler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;

import org.nill.vorlagen.compiler.model.ObjectModell;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTaskImpl;

public class Compiler {
	static Logger logger = Logger.getLogger(Compiler.class.getSimpleName());

	public Compiler() {
		super();
	}

	public void versions() {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		for (final SourceVersion version : compiler.getSourceVersions()) {
			logger.log(Level.INFO, () -> version.toString());
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
			logger.log(Level.INFO, String.format("%s, line %d in %s", diagnostic.getMessage(null),
					diagnostic.getLineNumber(), diagnostic.getSource().getName()));
		}
		manager.close();
	}

	public ObjectModell analyse(File sourceDir, Class clazz, ConverterVerzeichnis converter) throws IOException {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		final StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, null, null);

		File source = new File(sourceDir, clazz.getCanonicalName().replace(".", "/") + ".java");

		final Iterable<? extends JavaFileObject> sources = manager.getJavaFileObjectsFromFiles(Arrays.asList(source));
		final CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sources);

		JavacTaskImpl javaTask = (JavacTaskImpl) task;

		Iterable<? extends CompilationUnitTree> asts = javaTask.parse();
		javaTask.analyze();

		Elements elements = javaTask.getElements();

		ClassVisitor myVisitor = new ClassVisitor(clazz, elements, converter);
		for (CompilationUnitTree ast : asts) {
			myVisitor.scan(ast, Trees.instance(task));
		}

		for (final Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			logger.log(Level.INFO, String.format("%s, line %d in %s", diagnostic.getMessage(null),
					diagnostic.getLineNumber(), diagnostic.getSource().getName()));
		}
		manager.close();
		return myVisitor.getData();
	}
}

package org.nill.vorlagen.compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.compiler.util.RuntimeCompilerException;

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
		List<String> opts = Arrays.asList("-source","1.8", "-target","1.8","-encoding","UTF8");
		final CompilationTask task = compiler.getTask(null, manager, diagnostics, opts, null, sources);
		task.call();
		diagnoseAusgeben(diagnostics);
		manager.close();
	}

	public ObjectModell analyse(String sourceDir, Class clazz, ConverterVerzeichnis converter) throws IOException {
		return analyse(sourceDir,clazz,converter,Collections.emptyList(),new ArrayList<String>());
	}
	
	public ObjectModell analyse(String sourceDir, Class<?> clazz, ConverterVerzeichnis converter,List<String> sourcePaths,List<String> optsCompiler) throws IOException {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		final StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, null, null);
		logger.log(Level.INFO, "Manager " + manager.getClass().getCanonicalName());
		File source = getFileFromClass(sourceDir, clazz);
		logger.log(Level.INFO, "Sourcedatei " + source.getAbsolutePath());
		final Iterable<? extends JavaFileObject> sources = manager.getJavaFileObjectsFromFiles(Arrays.asList(source));
		
		optsCompiler.add("-encoding");
		optsCompiler.add("UTF8");
		
		if (!sourcePaths.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			boolean first=true;
			for(String path : sourcePaths) {
				if (!first) {
					sb.append(";");
				}
				first = false;
				sb.append(path);
			}
			String sourcepath = sb.toString();
			optsCompiler.add("-sourcepath");
			optsCompiler.add(sourcepath);
		}
		
		final CompilationTask task = compiler.getTask(null, manager, diagnostics, optsCompiler, null, sources);

		JavacTaskImpl javaTask = (JavacTaskImpl) task;

		Iterable<? extends CompilationUnitTree> asts = javaTask.parse();
		javaTask.analyze();

		diagnoseAusgeben(diagnostics);

		Elements elements = javaTask.getElements();
		ClassVisitor myVisitor = new ClassVisitor(clazz, elements, converter);
		for (CompilationUnitTree ast : asts) {
			myVisitor.scan(ast, Trees.instance(task));
		}

		manager.close();
		return myVisitor.getData();
	}

	private void diagnoseAusgeben(final DiagnosticCollector<JavaFileObject> diagnostics) {
		if (diagnostics.getDiagnostics() != null) {
			for (final Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
				logger.log(Level.INFO, String.format("%s, line %d in %s", diagnostic.getMessage(null),
						diagnostic.getLineNumber(), (diagnostic.getSource()==null) ? "nn" : diagnostic.getSource().getName()));
			}
		}
	}

	private File getFileFromClass(String sourceDir, Class<?> clazz) {
		if (!(new File(sourceDir).exists())) {
			throw new RuntimeCompilerException("Datei " + sourceDir + " did not exist");
		}
		return new File(sourceDir, clazz.getCanonicalName().replace(".", "/") + ".java");
	}


}

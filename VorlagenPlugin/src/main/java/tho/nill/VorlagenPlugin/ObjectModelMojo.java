package tho.nill.VorlagenPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.nill.vorlagen.compiler.ConverterVerzeichnis;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.object.ModellGenerator;

/**
 * Generate Entities, Interfaces from a Class
 * 
 */

@Mojo(name = "generateFromObjectModel", 
      defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ObjectModelMojo extends MojoWithFileChecks {

	public ObjectModelMojo() {
		super();
	}
	
	@Parameter(property = "doNothing", defaultValue = "false")
	private boolean doNothing;

	@Parameter(property = "modellDir", required = true)
	private String modellDir;

	@Parameter(property = "templateDir", required = true)
	private String templateDir;

	@Parameter(property = "outputDir", required = true)
	private String outputDir;

	@Parameter(property = "modelClasses", required = true)
	private String[] modelClasses;

	@Parameter(property = "converterClass", required = true)
	private String converterClass;

	@Parameter(property = "connectionClass", required = true)
	private String connectionClass;

	@Parameter(defaultValue = "${settings.localRepository}", property = "localRepository", required = false)
	private String localRepository;

	@Parameter(defaultValue = "0.0.1-SNAPSHOT", property = "vorlagenVersion", required = false)
	private String vorlagenVersion;

	@Parameter(property = "sourceJarPaths", alias = "sourceJarPath", required = false)
	private String[] sourceJarPaths;

	@Parameter(property = "source", required = false, defaultValue = "1.8")
	private String source;

	@Parameter(property = "target", required = false, defaultValue = "1.8")
	private String target;

	public void execute() throws MojoExecutionException {
		if (doNothing) {
			getLog().info("i do nothing");
			return;
		}
		
		try {
			getLog().info("modellDir: " + modellDir);
			getLog().info("templateDir: " + templateDir);
			getLog().info("outputDir: " + outputDir);
			getLog().info("converterClass: " + converterClass);
			getLog().info("connectionClass: " + connectionClass);
			getLog().info("localRepository: " + localRepository);
			getLog().info("vorlagenVersion: " + vorlagenVersion);

			ConverterVerzeichnis converter = createConverterVerzeichnis();

			ObjectModell connector = createConnector();

			ModellGenerator g = new ModellGenerator(templateDir, outputDir,
					new ArrayList(Arrays.asList("-source", source, "-target", target)));

			g.addSourcePath(modellDir);
			g.addSourcePath(localRepository + "/tho/nill/Vorlagen-compiler/" + vorlagenVersion + "/Vorlagen-compiler-"
					+ vorlagenVersion + "-sources.jar");
			g.addSourcePath(localRepository + "/tho/nill/Vorlagen-vorlagen/" + vorlagenVersion + "/Vorlagen-vorlagen-"
					+ vorlagenVersion + "-sources.jar");

			if (sourceJarPaths != null && sourceJarPaths.length > 0) {
				for (String path : sourceJarPaths) {
					checkJar(path);
					g.addSourcePath(path);
				}
			}
			if (modelClasses != null && modelClasses.length > 0) {
				for (String modelClass : modelClasses) {
					g.erzeugeAusgabe(modelClass, converter, connector, modellDir);
				}
			} else {
				throw new MojoExecutionException("modelClasses need not be empty");
			}
		} catch (Exception e) {
			throw new MojoExecutionException("Intern exception", e);
		}

	}

	private Object createObject(String className) throws  
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class<?> clazz = loader.loadClass(className);
		return clazz.getConstructor().newInstance();
	}

	private ConverterVerzeichnis createConverterVerzeichnis() throws   ReflectiveOperationException, MojoExecutionException {
		Object oconverter = createObject(this.converterClass);
		if (!(oconverter instanceof ConverterVerzeichnis)) {
			throw new MojoExecutionException(
					"converter " + connectionClass + " is not an " + ConverterVerzeichnis.class.getSimpleName());
		}
		return (ConverterVerzeichnis) oconverter;
	}

	private ObjectModell createConnector() throws   ReflectiveOperationException, MojoExecutionException {
		Object oconnector = createObject(this.connectionClass);
		if (!(oconnector instanceof ObjectModell)) {
			throw new MojoExecutionException(
					"connector " + connectionClass + " is not an " + ObjectModell.class.getSimpleName());
		}
		return (ObjectModell) oconnector;
	}

}

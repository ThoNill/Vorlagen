package tho.nill.VorlagenPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.nill.vorlagen.compiler.model.ObjectModell;
import org.nill.vorlagen.freeform.st.FreeformSTGenerator;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;

/**
 * Generate Entities, Interfaces from a Class
 * 
 */

@Mojo(name = "generateFromFreeformModel", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class FreeformMojo extends MojoWithFileChecks {

	public FreeformMojo() {
		super();
	}

	@Parameter(property = "doNothing", defaultValue = "false")
	private boolean doNothing;

	@Parameter(property = "templateDir", required = true)
	private String templateDir;

	@Parameter(property = "outputDir", required = true)
	private String outputDir;

	@Parameter(property = "modelClasses", required = true)
	private String[] modelClasses;

	@Parameter(property = "adapterClass", required = false)
	private String adapterClass;

	public void execute() throws MojoExecutionException {
		if (doNothing) {
			getLog().info("i do nothing because doNothing = true");
			return;
		}

		try {
			getLog().info("templateDir: " + templateDir);
			getLog().info("outputDir: " + outputDir);


			Object adapter = null;
			if (adapterClass != null) {
				adapter = createObject(adapterClass);
				if (!(adapter instanceof ObjectModelAdaptor)) {
					throw new MojoExecutionException(adapterClass + " is not an ObjectModelAdaptor");
				}
			}

			if (modelClasses != null && modelClasses.length > 0) {
				List<Object> nichtIterable = new ArrayList<>();
				for (String modelClass : modelClasses) {
					instanzenAusgebenOderSammeln(adapter, nichtIterable, modelClass);
				}
				ausgabeDerNichtIterablenInstanzen(adapter, nichtIterable);
			} else {
				throw new MojoExecutionException("modelClasses must not be empty");
			}
		} catch (Exception e) {
			throw new MojoExecutionException("Intern exception", e);
		}

	}

	private void instanzenAusgebenOderSammeln(Object adapter, List<Object> objects, String modelClass)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException,
			InstantiationException, IllegalArgumentException, SecurityException, MojoExecutionException {
		Object o = createObject(modelClass);
		if (o instanceof Iterable) {
			ausgabeBeiIterable(adapter, modelClass, o);
		} else {
			objects.add(createObject(modelClass));
		}
	}

	private void ausgabeDerNichtIterablenInstanzen(Object adapter, List<Object> objects) {
		if (!objects.isEmpty()) {
			new FreeformSTGenerator(objects, templateDir, outputDir, objects.get(0).getClass(),
					(ObjectModelAdaptor) adapter).erzeugeAusgabe();
		}
	}

	private void ausgabeBeiIterable(Object adapter, String modelClass, Object o) {
		Iterable<?> iterable = (Iterable<?>) o;
		Iterator<?> iterator = iterable.iterator();
		if (iterator.hasNext()) {
			Object exampleObject = iterable.iterator().next();
			new FreeformSTGenerator(iterable, templateDir, outputDir, exampleObject.getClass(),
					(ObjectModelAdaptor) adapter).erzeugeAusgabe();
		} else {
			getLog().info("Empty List " + modelClass + " nothig generated");

		}
	}

	private Object createObject(String className)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException,
			InstantiationException, IllegalArgumentException, SecurityException, MojoExecutionException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class<?> clazz = loader.loadClass(className);
		if (ObjectModell.class.isAssignableFrom(clazz)) {
			throw new MojoExecutionException("class " + className + " is a ObjectModell, please use generateFromObjectModel ");
		}
		return clazz.getConstructor().newInstance();
	}

}

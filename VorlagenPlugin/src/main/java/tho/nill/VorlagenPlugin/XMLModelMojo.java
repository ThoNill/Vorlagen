package tho.nill.VorlagenPlugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.nill.vorlagen.xml.st.DocumentSTGenerator;

@Mojo(name = "generateFromXMLModel", defaultPhase = LifecyclePhase.GENERATE_SOURCES)

/**
 * Generate files from a XML Description
 * 
 */

public class XMLModelMojo extends MojoWithFileChecks{

	public XMLModelMojo() {
		super();
	}

	@Parameter(property = "outputDir", required = true)
	private String outputDir;

	@Parameter( property = "modellDir", required = true)
	private String modellDir;

	@Parameter( property = "templateDir", required = true)
	private String templateDir;

	@Parameter( property = "packageName", required = true)
	private String packageName;

	@Parameter( property = "defaultClass", required = true)
	private String defaultClass;

	public void execute() throws MojoExecutionException {
		try {
			getLog().info("modellDir: " + modellDir);
			getLog().info("templateDir: " + templateDir);
			getLog().info("outputDir: " + outputDir);
			getLog().info("packageName: " + packageName);
			getLog().info("defaultClass: " + defaultClass);
			
			
			DocumentSTGenerator a = new DocumentSTGenerator(this.modellDir, this.templateDir, this.outputDir,
					this.packageName, this.defaultClass);
			a.erzeugeAusgabe();
		} catch (Exception e) {
			throw new MojoExecutionException("Intern exception", e);
		}

	}

}

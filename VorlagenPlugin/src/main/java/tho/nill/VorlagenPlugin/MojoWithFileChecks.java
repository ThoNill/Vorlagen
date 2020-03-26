package tho.nill.VorlagenPlugin;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

public abstract class MojoWithFileChecks extends AbstractMojo {

	public MojoWithFileChecks() {
		super();
	}

	protected void checkDirectory(String dirName) throws MojoExecutionException {
		File d = new File(dirName);
		if (!d.exists()) {
			throw new MojoExecutionException("File does not exist: " + dirName);
		}
	
		if (!d.isDirectory()) {
			throw new MojoExecutionException("Not a directory " + dirName);
		}
	}

	protected void checkJar(String fileName) throws MojoExecutionException {
		File d = new File(fileName);
		if (!d.exists()) {
			throw new MojoExecutionException("File does not exist: " + fileName);
		}
	
		if (!d.isFile()) {
			throw new MojoExecutionException("Not a file " + fileName);
		}
		
		if(!fileName.endsWith("jar")) {
			throw new MojoExecutionException("Not a jar file " + fileName);
		}
	}

}
package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.MojoRule;
import org.codehaus.plexus.PlexusTestCase;
import org.junit.Rule;
import org.junit.Test;

import tho.nill.VorlagenPlugin.ObjectModelMojo;

public class PluginTest extends AbstractMojoTestCase {
	
	@Rule
	public MojoRule rule = new MojoRule();

	/**
	 * @throws Exception if any
	 */
	@Test
	public void testSomething() throws Exception {
		doIntegrationTest( "Vorlagen:generateFromXMLModel@id1");
		doIntegrationTest( "Vorlagen:generateFromObjectModel@id2");
		doIntegrationTest("Vorlagen:generateFromObjectModel@id3");
	
	}

	private void doIntegrationTest(String mojoName) throws IOException, VerificationException {
		File testDir = ResourceExtractor.simpleExtractResources(getClass(), "/test");

		Verifier verifier = new Verifier(testDir.getAbsolutePath() + "/..", true);
		verifier.setMavenDebug(true);
		verifier.executeGoal(mojoName);

		List<String> lines = verifier.loadFile(verifier.getBasedir(), verifier.getLogFileName(), false);
		for (String line : lines) {
			System.out.println(line);
		}

		verifier.verifyErrorFreeLog();
		verifier.resetStreams();
	}

}
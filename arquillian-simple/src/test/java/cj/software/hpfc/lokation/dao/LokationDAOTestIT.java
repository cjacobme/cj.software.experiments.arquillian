package cj.software.hpfc.lokation.dao;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cj.software.cassandra.SessionProducer;

@RunWith(Arquillian.class)
public class LokationDAOTestIT
{
	private static Logger logger = LogManager.getFormatterLogger();

	@Deployment
	public static Archive<?> createDeployment()
	{
		PomEquippedResolveStage lPomFile = Maven.resolver().loadPomFromFile("pom.xml");
		File[] lFiles = lPomFile
				.importRuntimeAndTestDependencies()
				.resolve()
				.withTransitivity()
				.asFile();

		Archive<?> lResult = ShrinkWrap
				.create(WebArchive.class, "LokationDAOTestIT.war")
				.addClasses(LokationDAO.class)
				.addPackage(SessionProducer.class.getPackage())
				.addAsLibraries(lFiles)
				.addAsResource("log4j2-console.xml", "log4j2.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		logger.info(lResult.toString(true));
		return lResult;
	}

	@Inject
	private LokationDAO lokationDAO;

	@Test
	public void readLokationen()
	{
		List<String> lLokationsnamen = this.lokationDAO.readLokationen();
		logger.info("found %d lokationen", lLokationsnamen != null ? lLokationsnamen.size() : -1);
		Assert.assertEquals(
				Arrays.asList("Essen", "Irgendwo", "Montaione", "Nordkap", "Nordpol"),
				lLokationsnamen);
	}

}

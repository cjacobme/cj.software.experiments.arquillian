package cj.software.experiments.arquillian.service;

import java.io.File;

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

@RunWith(Arquillian.class)
public class LoginBeanTestIT
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
				.create(WebArchive.class, "LoginBeanTestIT.war")
				.addClasses(LoginBean.class)
				.addAsLibraries(lFiles)
				.addAsResource("log4j2-console.xml", "log4j2.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		logger.info(lResult.toString(true));
		return lResult;
	}

	@Inject
	private LoginBean loginBean;

	@Test
	public void loginMary()
	{
		this.assertLogin("Mary");
	}

	@Test
	public void loginBen()
	{
		this.assertLogin("Ben");
	}

	private void assertLogin(String pName)
	{
		String lActual = this.loginBean.login(pName);
		String lExpected = "Hello " + pName;
		Assert.assertEquals(lExpected, lActual);
	}
}

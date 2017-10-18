package cj.software.experiments.arquillian.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginBean
{
	private Logger logger = LogManager.getFormatterLogger();

	public String login(String pName)
	{
		this.logger.info("login requested for \"%s\"", pName);
		return "Hello " + pName;
	}
}

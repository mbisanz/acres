package com.prodyna.pac.acres;

import java.io.File;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.prodyna.pac.acres.rest.AcresApplication;
import com.prodyna.pac.acres.rest.exceptionhandling.AcresExceptionHandler;

@ArquillianSuiteDeployment
public class Deployments {

	@Deployment
	@OverProtocol("Servlet 3.0")
	public static WebArchive createDeployment() {
		WebArchive war = ShrinkWrap.create(WebArchive.class, "acres-test.war");
		war.addPackages(true, "com.prodyna.pac.acres");
		war.addClass(AcresApplication.class);
		war.addPackage(AcresExceptionHandler.class.getPackage());
		war.as(ExplodedImporter.class).importDirectory("../acres-rest/src/main/webapp");
		war.delete("WEB-INF/beans.xml");
		war.delete("WEB-INF/jboss-web.xml");
		war.addAsWebInfResource(new File("src/test/webapp/WEB-INF/beans.xml"), "beans.xml");
		war.addAsWebInfResource(new File("src/test/webapp/WEB-INF/jboss-web.xml"), "jboss-web.xml");
		war.addAsWebInfResource(new File("src/test/webapp/WEB-INF/ejb-jar.xml"), "ejb-jar.xml");
		war.addAsResource("META-INF/persistence.xml");
		System.out.println(war.toString(true));
		return war;
	}
}

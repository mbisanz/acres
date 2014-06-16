package com.prodyna.pac.acres;

import java.io.File;

import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

@ArquillianSuiteDeployment
public class Deployments {

	@Deployment
	@OverProtocol("Servlet 3.0")
	public static WebArchive createDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "acres-test.war");
		wa.addPackages(true, "com.prodyna.pac.acres");
		wa.addAsResource("META-INF/persistence.xml");
		wa.addAsWebInfResource(new File("src/test/webapp/WEB-INF/beans.xml"), "beans.xml");
		System.out.println(wa.toString(true));
		return wa;
	}
}

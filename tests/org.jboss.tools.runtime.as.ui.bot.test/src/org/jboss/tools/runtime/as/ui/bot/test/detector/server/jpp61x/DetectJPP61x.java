package org.jboss.tools.runtime.as.ui.bot.test.detector.server.jpp61x;

import java.util.Arrays;
import java.util.List;

import org.jboss.reddeer.requirements.jre.JRERequirement.JRE;
import org.jboss.tools.runtime.as.ui.bot.test.RuntimeProperties;
import org.jboss.tools.runtime.as.ui.bot.test.entity.Runtime;
import org.jboss.tools.runtime.as.ui.bot.test.template.DetectRuntimeTemplate;

@JRE(value=1.7, cleanup=true)
public class DetectJPP61x extends DetectRuntimeTemplate {

	public static final String SERVER_ID = "jboss-jpp-6.1.x";
	
	public static final String SERVER_NAME = "JBoss Portal 6.1";
	
	@Override
	protected String getPathID() {
		return SERVER_ID;
	}

	@Override
	protected List<Runtime> getExpectedRuntimes() {
		Runtime server = new Runtime();
		server.setName(SERVER_NAME);
		server.setType("JPP");
		server.setVersion("6.1");
		server.setLocation(RuntimeProperties.getInstance().getRuntimePath(getPathID()));
		return Arrays.asList(server);
	}
}

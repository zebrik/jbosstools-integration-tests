package org.jboss.ide.eclipse.as.ui.bot.test.as42;

import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerReqState;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerReqType;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement.Server;
import org.jboss.ide.eclipse.as.ui.bot.test.template.DeployJSPProjectTemplate;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;

/**
 * @see DeployJSPProjectTemplate
 * @author Lucia Jelinkova
 *
 */
@Server(state=ServerReqState.RUNNING, type=ServerReqType.AS, version="4.2")
public class DeployJSPProjectAS42Server extends DeployJSPProjectTemplate {

	@InjectRequirement
	protected ServerRequirement requirement;
	
	@Override
	protected String getServerName() {
		return requirement.getServerNameLabelText();
	} 
	
	@Override
	protected String getConsoleMessage() {
		return "deploy, ctxPath=/" + PROJECT_NAME;
	}
}

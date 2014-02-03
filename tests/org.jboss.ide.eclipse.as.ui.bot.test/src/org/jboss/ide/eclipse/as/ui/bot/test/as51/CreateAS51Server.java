package org.jboss.ide.eclipse.as.ui.bot.test.as51;

import java.util.List;

import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerReqState;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerReqType;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement.Server;
import org.jboss.ide.eclipse.as.ui.bot.test.template.CreateServerTemplate;
import org.jboss.reddeer.junit.requirement.inject.InjectRequirement;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.tools.ui.bot.ext.entity.XMLConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;

/**
*
* @see CreateServerTemplate
* @author Lucia Jelinkova
*
*/
@CleanWorkspace
@Server(state=ServerReqState.PRESENT, type=ServerReqType.AS, version="5.1")
public class CreateAS51Server extends CreateServerTemplate {

	@InjectRequirement
	protected ServerRequirement requirement;
	
	@Override
	protected String getServerName() {
		return requirement.getServerNameLabelText();
	} 
	
	@Override
	protected void assertEditorPorts() {
		assertThat("8080", is(editor.getWebPort()));
		assertThat("1099", is(editor.getJNDIPort()));		
	}

	@Override
	protected void assertViewPorts(List<XMLConfiguration> configurations) {
		for (XMLConfiguration config : configurations){
			assertValueIsNumber(config);
		}
	}

	private void assertValueIsNumber(XMLConfiguration config){
		try {
			Integer.parseInt(config.getValue());
		} catch (NumberFormatException e){
			fail(config + " does not a numeric value");
		}
	}
}

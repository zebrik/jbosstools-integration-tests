package org.jboss.tools.cdi.bot.test.validation.cdi11;

import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerReqType;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement.JBossServer;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaEEPerspective;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.requirements.server.ServerReqState;
import org.jboss.tools.cdi.bot.test.validation.template.CDIValidatorTemplate;
import org.jboss.tools.cdi.reddeer.common.model.ui.editor.EditorPartWrapper;
import org.junit.Before;

@JBossServer(state = ServerReqState.PRESENT, type = ServerReqType.WILDFLY10x, cleanup=false)
@OpenPerspective(JavaEEPerspective.class)
public class CDIValidatorTestCDI11 extends CDIValidatorTemplate{
	
	@Before
	public void changeDiscoveryMode(){
		EditorPartWrapper beansEditor = beansXMLHelper.openBeansXml(PROJECT_NAME);
		beansEditor.activateTreePage();
		beansEditor.selectBeanDiscoveryMode("all");
		beansEditor.save();
		beansEditor.close();
	}

}

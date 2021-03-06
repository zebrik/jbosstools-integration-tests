/*******************************************************************************
 * Copyright (c) 2007-2016 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.ui.bot.test.editor.tags;

import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;

/**
 * Tests Rich Faces Panel Tag behavior
 * 
 * @author vlado pakan
 *
 */
public class PanelTagTest extends AbstractTagTest {
	private static final String HEADER_VALUE = "!-*Header Value";
	private static final String BODY_VALUE = "!-*Body Value";

	@Override
	protected void initTestPage() {
		initTestPage(TestPageType.XHTML,
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\"\n"
						+ "  xmlns:ui=\"http://java.sun.com/jsf/facelets\"\n"
						+ "  xmlns:f=\"http://java.sun.com/jsf/core\"\n"
						+ "  xmlns:rich=\"http://richfaces.org/rich\"\n"
						+ "  xmlns:h=\"http://java.sun.com/jsf/html\">\n" + "  <head>\n" + "  </head>\n" + "  <body>\n"
						+ "    <f:view>\n" + "      <rich:panel>\n" + "        <f:facet name=\"header\">\n"
						+ "          <h:outputText value=\"" + PanelTagTest.HEADER_VALUE + "\"/>\n"
						+ "        </f:facet>\n" + "        <h:outputText value=\"" + PanelTagTest.BODY_VALUE + "\"/>\n"
						+ "      </rich:panel>\n" + "    </f:view>\n" + "  </body>\n" + "</html>");
	}

	@Override
	protected void verifyTag() {
		assertVisualEditorContains(getVisualEditor(), "DIV", new String[] { "class" },
				new String[] { "dr-pnl rich-panel" }, getTestPageFileName());
		assertVisualEditorContains(getVisualEditor(), "DIV", new String[] { "class" },
				new String[] { "dr-pnl-b rich-panel-body" }, getTestPageFileName());
		assertVisualEditorContainsNodeWithValue(getVisualEditor(), PanelTagTest.HEADER_VALUE, getTestPageFileName());
		assertVisualEditorContainsNodeWithValue(getVisualEditor(), PanelTagTest.BODY_VALUE, getTestPageFileName());
		// check tag selection
		getVisualEditor().selectDomNode(getVisualEditor().getDomNodeByTagName("DIV", 4), 0);
		AbstractWait.sleep(TimePeriod.getCustom(3));
		String selectedText = getSourceEditor().getSelectedText();
		String hasToStartWith = "<rich:panel>";
		assertTrue("Selected text in Source Pane has to start with '" + hasToStartWith + "'" + "\nbut it is '"
				+ selectedText + "'", selectedText.trim().startsWith(hasToStartWith));
		String hasEndWith = "</rich:panel>";
		assertTrue("Selected text in Source Pane has to end with '" + hasEndWith + "'" + "\nbut it is '" + selectedText
				+ "'", selectedText.trim().endsWith(hasEndWith));
	}

}

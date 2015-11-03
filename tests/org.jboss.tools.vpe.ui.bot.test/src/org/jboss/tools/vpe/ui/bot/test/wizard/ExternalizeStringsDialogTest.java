/*******************************************************************************
 * Copyright (c) 2007-2010 Exadel, Inc. and Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Exadel, Inc. and Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.ui.bot.test.wizard;

import java.awt.event.KeyEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.Position;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCheckBox;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotRadio;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.jboss.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.jboss.reddeer.swt.keyboard.KeyboardFactory;
import org.jboss.reddeer.workbench.impl.editor.TextEditor;
import org.jboss.tools.vpe.ui.bot.test.VPEAutoTestCase;
import org.jboss.tools.jst.web.ui.internal.editor.messages.JstUIMessages;
import org.jboss.tools.ui.bot.ext.SWTJBTExt;
import org.jboss.tools.ui.bot.ext.SWTTestExt;
import org.jboss.tools.ui.bot.ext.Timing;
import org.jboss.tools.ui.bot.ext.helper.KeyboardHelper;
import org.jboss.tools.ui.bot.test.WidgetVariables;

public class ExternalizeStringsDialogTest extends VPEAutoTestCase {

	private final String ENABLED_TEST_TEXT = "<html>Externalize Text</html>"; //$NON-NLS-1$
	private final String TOOL_TIP = (SWTJBTExt.isRunningOnMacOs() ? "Externalize selected string..." //$NON-NLS-1$
			: "Externalize selected string...");
	private final String FOLDER_TEXT_LABEL = "Enter or select the parent folder:"; //$NON-NLS-1$
	private final String INCORRECT_TABLE_VALUE = "Table value is incorrect"; //$NON-NLS-1$
	private final String TOOLBAR_ICON_ENABLED = "Toolbar button should be enabled"; //$NON-NLS-1$
	private final String TOOLBAR_ICON_DISABLED = "Toolbar button should be disabled"; //$NON-NLS-1$
	private final String CANNOT_FIND_PROPERTY_VALUE = "Cannot find 'Property Value' text field"; //$NON-NLS-1$
	private final String CANNOT_FIND_RADIO_BUTTON = "Cannot find radio button with name: "; //$NON-NLS-1$
	private final String COMPLEX_TEXT = "!! HELLO ~ Input User, Name.Page ?" //$NON-NLS-1$
			+ " \r\n and some more text \r\n" //$NON-NLS-1$
			+ "@ \\# vc \\$ % yy^ &*(ghg ) _l-kk+mmm\\/fdg\\ " //$NON-NLS-1$
			+ "\t ;.df:,ee {df}df[ty]"; //$NON-NLS-1$
	private final String COMPLEX_KEY_RESULT = "HELLO_Input_User_Name_Page_and" + //$NON-NLS-1$
			"_some_more_text_vc_yy_ghg_l_kk_mmm_fdg_df_ee_df_df_ty"; //$NON-NLS-1$
	private final String COMPLEX_VALUE_RESULT = "\\r\\n!! HELLO ~ Input User, Name.Page ? \\r\\n and some more text " + //$NON-NLS-1$
			"\\r\\n@ \\# vc \\$ % yy^ &*(ghg ) _l-kk+mmm\\/fdg\\ \\t ;.df:,ee {df}df[ty]\\r\\n\\t\\t";; //$NON-NLS-1$

	private boolean isUnusedDialogOpened = false;

	public ExternalizeStringsDialogTest() {
		super();
	}

	@Override
	protected void closeUnuseDialogs() {
		try {
			SWTBotShell dlgShell = bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE);
			dlgShell.setFocus();
			dlgShell.close();
		} catch (WidgetNotFoundException e) {
			e.printStackTrace();
		} finally {
			isUnusedDialogOpened = false;
		}

	}

	@Override
	protected boolean isUnuseDialogOpened() {
		return isUnusedDialogOpened;
	}

	public void testExternalizeStringsDialog() throws Throwable {
		isUnusedDialogOpened = false;
		/*
		 * Open simple html file in order to get the VPE toolbar
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME).getProjectItem("WebContent", "pages", TEST_PAGE)
				.open();
		TextEditor editor = new TextEditor(TEST_PAGE);
		editor.activate();
		/*
		 * Select some text
		 */
		editor.selectText("User");
		/*
		 * Get toolbar button
		 */
		SWTBotToolbarButton tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitForToolbarButtonEnabled(tbButton, Timing.time5S());
		tbButton.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * Check properties key and value fields
		 */
		SWTBotText defKeyText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_KEY,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull("Cannot find 'Property Key' text field", defKeyText); //$NON-NLS-1$
		assertText("User", defKeyText); //$NON-NLS-1$
		SWTBotText defValueText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_VALUE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defValueText);
		assertText("User", defValueText); //$NON-NLS-1$
		SWTBotCheckBox checkBox = bot.checkBox();
		assertNotNull(
				"Cannot find checkbox '" //$NON-NLS-1$
						+ JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_NEW_FILE + "'", //$NON-NLS-1$
				checkBox);
		/*
		 * Check that "Next" button is disabled
		 */
		assertFalse("Checkbox should be unchecked.", //$NON-NLS-1$
				checkBox.isChecked());
		assertFalse("Next button should be disabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		/*
		 * Select existed resource bundle
		 */
		SWTBotCombo combo = bot.comboBox();
		combo.setSelection(0);
		assertText("demo.Messages", combo); //$NON-NLS-1$
		/*
		 * Check table results
		 */
		SWTBotTable table = bot.table();
		assertNotNull("Table should exist", table); //$NON-NLS-1$
		/*
		 * The list should be sorted in the alphabetical order.
		 */
		assertEquals(INCORRECT_TABLE_VALUE, "header", table.cell(0, 0)); //$NON-NLS-1$
		assertEquals(INCORRECT_TABLE_VALUE, "Hello Demo Application", table.cell(0, 1)); //$NON-NLS-1$
		assertEquals(INCORRECT_TABLE_VALUE, "hello_message", table.cell(1, 0)); //$NON-NLS-1$
		assertEquals(INCORRECT_TABLE_VALUE, "Hello", table.cell(1, 1)); //$NON-NLS-1$
		assertEquals(INCORRECT_TABLE_VALUE, "prompt_message", table.cell(2, 0)); //$NON-NLS-1$
		assertEquals(INCORRECT_TABLE_VALUE, "Name:", table.cell(2, 1)); //$NON-NLS-1$
		/*
		 * Press OK and replace the text in the editor
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		/*
		 * Check replaced text
		 */
		assertEquals("Replaced text is incorrect", editor.getPositionOfText("#{Message.User}"), 1);
		editor.close();
		/*
		 * Check that properties file has been updated
		 */
		SWTBotEditor editor2 = SWTTestExt.eclipse.openFile(JBT_TEST_PROJECT_NAME, "JavaSource", "demo", //$NON-NLS-1$ //$NON-NLS-2$
				"Messages.properties"); //$NON-NLS-1$
		editor2.bot().cTabItem("Source").activate();
		editor2.toTextEditor().selectLine(3);
		String line = editor2.toTextEditor().getSelection();
		assertEquals("'Messages.properties' was updated incorrectly", "User=User", line); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testExternalizingTheSameTextAgain() throws Throwable {
		isUnusedDialogOpened = false;
		/*
		 * Open simple html file in order to get the VPE toolbar
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME).getProjectItem("WebContent", "pages", TEST_PAGE)
				.open();
		TextEditor editor = new TextEditor(TEST_PAGE);
		editor.activate();
		/*
		 * Select some text
		 */
		editor.selectText("User");
		assertEquals("Replaced text is incorrect", "User", editor.getSelectedText()); //$NON-NLS-1$ //$NON-NLS-2$
		/*
		 * Get toolbar button
		 */
		SWTBotToolbarButton tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitForToolbarButtonEnabled(tbButton, Timing.time5S());
		tbButton.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;

		/*
		 * Check the generated property key. It should be as is. The dialog
		 * should use the stored key-pair value. OK button should be enabled. No
		 * modifications to the properties file should be made.
		 */
		SWTBotText defKeyText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_KEY,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull("Cannot find 'Property Key' text field", defKeyText); //$NON-NLS-1$
		assertText("User", defKeyText); //$NON-NLS-1$

		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		editor.close();
		/*
		 * Check that properties file hasn't been modified.
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME)
				.getProjectItem("JavaSource", "demo", "Messages.properties").open();

		TextEditor editor2 = new TextEditor("Messages.properties");
		editor2.selectLine(3);
		String line = editor2.getSelectedText();
		assertEquals("'Messages.properties' was updated incorrectly", "User=User", line);
		/*
		 * Change the property value to the new one, let say 'User1'. And
		 * externalize the same string again.
		 */
		new DefaultCTabItem("Source").activate();
		editor2.activate();
		editor2.setText(editor2.getText() + "1");
		editor2.save();
		editor2.close();
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME).getProjectItem("WebContent", "pages", TEST_PAGE)
				.open();
		editor = new TextEditor(TEST_PAGE);
		editor.activate();
		assertEquals("Replaced text is incorrect", editor.getPositionOfText("User"), 1);
		/*
		 * Get toolbar button
		 */
		util.waitForToolbarButtonWithTooltipIsFound(TOOL_TIP, Timing.time3S());
		tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitForToolbarButtonEnabled(tbButton, Timing.time5S());
		tbButton.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * The value has been changed and now unique. But the same key is in the
		 * bundle. Thus the dialog should suggest "User_1" as a key value.
		 */
		defKeyText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_KEY,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull("Cannot find 'Property Key' text field", defKeyText); //$NON-NLS-1$
		util.waitForNonIgnoredJobs(true, Timing.time3S());
		assertText("User_1", defKeyText); //$NON-NLS-1$
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		/*
		 * Change the key to validate duplicate key situation
		 */
		defKeyText.setText("User"); //$NON-NLS-1$
		assertTrue("(OK) button should be disabled.", //$NON-NLS-1$
				!bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		defKeyText.setText("User_1"); //$NON-NLS-1$
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		editor.close();
		/*
		 * Check that the new key has been added.
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME)
				.getProjectItem("JavaSource", "demo", "Messages.properties").open();
		editor2 = new TextEditor("Messages.properties");
		editor2.selectLine(4);
		line = editor2.getSelectedText();
		assertEquals("'Messages.properties' was updated incorrectly", "User_1=User", line); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testCompoundKeyWithDot() throws Throwable {
		isUnusedDialogOpened = false;
		/*
		 * Open simple html file in order to get the VPE toolbar
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME).getProjectItem("WebContent", "pages", TEST_PAGE)
				.open();
		TextEditor editor = new TextEditor(TEST_PAGE);
		editor.activate();

		/*
		 * Select some text
		 */
		editor.selectText("User");
		/*
		 * Get toolbar button
		 */
		SWTBotToolbarButton tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitForToolbarButtonEnabled(tbButton, Timing.time5S());
		tbButton.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * Check properties key and value fields
		 */
		SWTBotText defKeyText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_KEY,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull("Cannot find 'Property Key' text field", defKeyText); //$NON-NLS-1$
		util.waitForNonIgnoredJobs(true, Timing.time3S());
		assertText("User_1", defKeyText); //$NON-NLS-1$
		defKeyText.setText("user.compoundKey"); //$NON-NLS-1$
		SWTBotText defValueText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_VALUE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defValueText);
		assertText("User", defValueText); //$NON-NLS-1$
		SWTBotCheckBox checkBox = bot.checkBox();
		assertNotNull(
				"Cannot find checkbox '" //$NON-NLS-1$
						+ JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_NEW_FILE + "'", //$NON-NLS-1$
				checkBox);
		/*
		 * Check that "Next" button is disabled
		 */
		assertFalse("Checkbox should be unchecked.", //$NON-NLS-1$
				checkBox.isChecked());
		assertFalse("Next button should be disabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		/*
		 * Select existed resource bundle
		 */
		SWTBotCombo combo = bot.comboBox();
		combo.setSelection(0);
		assertText("demo.Messages", combo); //$NON-NLS-1$
		util.waitForAll();
		/*
		 * Press OK and replace the text in the editor
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		/*
		 * Check replaced text
		 */
		assertEquals("Replaced text is incorrect", editor.getPositionOfText("#{Message['user.compoundKey']}"),1);
		editor.close();
		/*
		 * Check that properties file has been updated
		 */
		SWTBotEditor editor2 = SWTTestExt.eclipse.openFile(JBT_TEST_PROJECT_NAME, "JavaSource", "demo", //$NON-NLS-1$ //$NON-NLS-2$
				"Messages.properties"); //$NON-NLS-1$
		/*
		 * Select the 4th line. In the 3rd line should be results from the
		 * previous test "User=User". If previous test failed -- it could crash
		 * this one as well.
		 */
		editor2.bot().cTabItem("Source").activate();
		editor2.toTextEditor().selectLine(5);
		String line = editor2.toTextEditor().getSelection();
		assertEquals("'Messages.properties' was updated incorrectly", "user.compoundKey=User", line); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testExternalizeStringsDialogInXhtml() throws Throwable {
		isUnusedDialogOpened = false;
		/*
		 * Open simple html file in order to get the VPE toolbar
		 */
		SWTTestExt.packageExplorer.getProject(FACELETS_TEST_PROJECT_NAME)
			.getProjectItem("WebContent", "pages",FACELETS_TEST_PAGE).open();
		
		TextEditor editor = new TextEditor(FACELETS_TEST_PAGE);
		editor.activate();
		/*
		 * Select some text
		 */
		editor.selectText("User");
		/*
		 * Get toolbar button
		 */
		util.waitForToolbarButtonWithTooltipIsFound(TOOL_TIP, Timing.time3S());
		SWTBotToolbarButton tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitForToolbarButtonEnabled(tbButton, Timing.time5S());
		tbButton.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;

		/*
		 * Check properties key and value fields
		 */
		SWTBotText defKeyText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_KEY,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull("Cannot find 'Property Key' text field", defKeyText); //$NON-NLS-1$
		assertText("User", defKeyText); //$NON-NLS-1$
		SWTBotText defValueText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_VALUE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defValueText);
		assertText("User", defValueText); //$NON-NLS-1$
		SWTBotCheckBox checkBox = bot.checkBox();
		assertNotNull(
				"Cannot find checkbox '" //$NON-NLS-1$
						+ JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_NEW_FILE + "'", //$NON-NLS-1$
				checkBox);
		/*
		 * Check that "Next" button is disabled
		 */
		assertFalse("Checkbox should be unchecked.", //$NON-NLS-1$
				checkBox.isChecked());
		assertFalse("Next button should be disabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		/*
		 * Select existed resource bundle
		 */
		SWTBotCombo combo = bot.comboBox();
		combo.setSelection(0);
		assertText("resources", combo); //$NON-NLS-1$
		/*
		 * Check table results
		 */
		SWTBotTable table = bot.table();
		assertNotNull("Table should exist", table); //$NON-NLS-1$
		/*
		 * The list should be sorted in the alphabetical order.
		 */
		assertEquals(INCORRECT_TABLE_VALUE, "greeting", table.cell(0, 0)); //$NON-NLS-1$
		assertEquals(INCORRECT_TABLE_VALUE, "Hello", table.cell(0, 1)); //$NON-NLS-1$
		assertEquals(INCORRECT_TABLE_VALUE, "prompt", table.cell(1, 0)); //$NON-NLS-1$
		assertEquals(INCORRECT_TABLE_VALUE, "Your Name:", table.cell(1, 1)); //$NON-NLS-1$
		/*
		 * Press OK and replace the text in the editor
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		/*
		 * Check replaced text
		 */
		assertEquals("Replaced text is incorrect", editor.getPositionOfText("Input #{msg.User} Name"), 1);
		/*
		 * Check that properties file has been updated
		 */
		SWTTestExt.packageExplorer.getProject(FACELETS_TEST_PROJECT_NAME)
			.getProjectItem("JavaSource","resources.properties").open();
		TextEditor editor2 = new TextEditor("resources.properties");
		editor2.selectLine(3);
		String line = editor2.getSelectedText();
		assertEquals("'resources.properties' was updated incorrectly", "User=User", line); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testNewFileInExternalizeStringsDialog() throws Throwable {
		createNewBundleToLastPage(JBT_TEST_PROJECT_NAME + "/WebContent/pages", //$NON-NLS-1$
			"externalize.properties"); //$NON-NLS-1$
		TextEditor editor = new TextEditor();
		/*
		 * 'OK' button should be enabled. Press it.
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		/*
		 * Check that the text was replaced
		 */
		assertEquals("Replaced text is incorrect", editor.getPositionOfText("#{.Input}"), 1);
		/*
		 * Check that properties file has been created
		 */
		SWTBotEditor editor2 = SWTTestExt.eclipse.openFile(JBT_TEST_PROJECT_NAME, "WebContent", "pages", //$NON-NLS-1$ //$NON-NLS-2$
				"externalize.properties"); //$NON-NLS-1$
		editor2.toTextEditor().selectLine(0);
		String line = editor2.toTextEditor().getSelection();
		assertEquals("Created file is incorrect", "Input=Input", line); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testEmptySelectionInExternalizeStringsDialog() throws Throwable {
		isUnusedDialogOpened = false;
		/*
		 * Open simple html file in order to get the VPE toolbar
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME)
			.getProjectItem("WebContent", "pages",TEST_PAGE).open();
		TextEditor editor = new TextEditor(TEST_PAGE);
		editor.activate();
		/*
		 * Select some text
		 */
		editor.selectText(" ",11); // <h:messages style - select space
		/*
		 * There is an exception caused by the fact that line delimiter was
		 * selected. But for this test it's ok, so just ignore this exception.
		 */
		setException(null);
		util.waitForToolbarButtonWithTooltipIsFound(TOOL_TIP, Timing.time3S());
		SWTBotToolbarButton tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitWhileToolbarButtonisDisabled(tbButton, Timing.time5S());
		/*
		 * Check that the toolbar button is disabled
		 */
		assertFalse("Toolbar button should be disabled", tbButton.isEnabled());
		/*
		 * Select some text
		 */
		editor.setCursorPosition(21, 50);
		/*
		 * Send key press event to fire VPE listeners
		 */
		editor.activate();
		KeyboardFactory.getKeyboard().invokeKeyCombination(SWT.ARROW_RIGHT);
		/*
		 * Activate the dialog
		 */
		tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitForToolbarButtonEnabled(tbButton, Timing.time5S());
		tbButton.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * Check that the property value text is auto completed.
		 */
		SWTBotText defValueText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_VALUE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defValueText);
		assertText("Say Hello!", defValueText); //$NON-NLS-1$
		/*
		 * Close the dialog
		 */
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).close();
		isUnusedDialogOpened = false;
		/*
		 * Type some text outside the tag
		 */
		editor.activate();
		editor.insertText(13, 0,COMPLEX_TEXT);
		/*
		 * Select nothing and call the dialog -- the whole text should be
		 * selected.
		 */
		editor.setCursorPosition(13, 3);
		/*
		 * Activate the dialog
		 */
		util.waitForToolbarButtonWithTooltipIsFound(TOOL_TIP, Timing.time3S());
		SWTBotToolbarButton tbExternalizeStrings = (bot.toolbarButtonWithTooltip(TOOL_TIP));
		util.waitForToolbarButtonEnabled(tbExternalizeStrings, Timing.time3S());
		tbExternalizeStrings.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * Check that the property key and value text
		 */
		SWTBotText defKeyText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_KEY,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defKeyText);
		assertText(COMPLEX_KEY_RESULT, defKeyText);
		defValueText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_VALUE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defValueText);
		assertText(COMPLEX_VALUE_RESULT, defValueText);
		/*
		 * Close the dialog
		 */
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).close();
		isUnusedDialogOpened = false;
		/*
		 * Check selection in the attribute's value
		 */
		editor.save();
		editor.setCursorPosition(19, 109);
		KeyboardHelper.typeKeyCodeUsingAWT(KeyEvent.VK_RIGHT);
		util.waitForAll();
		/*
		 * Activate the dialog
		 */
		bot.toolbarButtonWithTooltip(TOOL_TIP).click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * Check that the property value text is empty
		 */
		defValueText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_VALUE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defValueText);
		assertText("true", defValueText); //$NON-NLS-1$
		/*
		 * Close the dialog
		 */
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).close();
		isUnusedDialogOpened = false;
	}

	public void testNewFileCreationWithoutAnyExistedBundles() throws Throwable {
		isUnusedDialogOpened = false;
		/*
		 * Open hello.jsp file
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME)
			.getProjectItem("WebContent", "pages", "hello.jsp").open();
		
		TextEditor editor = new TextEditor("hello.jsp");
		
		editor.activate();
		/*
		 * Select some text
		 */
		editor.selectLine(3);
		editor.insertText(3,0,"Plain text"); //$NON-NLS-1$
		editor.setCursorPosition(3, 3);
		editor.save();
		/*
		 * Activate the dialog
		 */
		util.waitForToolbarButtonWithTooltipIsFound(TOOL_TIP, Timing.time3S());
		assertTrue(TOOLBAR_ICON_ENABLED, bot.toolbarButtonWithTooltip(TOOL_TIP).isEnabled());
		bot.toolbarButtonWithTooltip(TOOL_TIP).click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * Check that the property key and value text are auto completed.
		 */
		SWTBotText defKeyText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_KEY,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defKeyText);
		assertText("Plain_text", defKeyText); //$NON-NLS-1$
		SWTBotText defValueText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_VALUE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defValueText);
		assertText("\\r\\n\\r\\nPlain text\\r\\n\\r\\n", defValueText); //$NON-NLS-1$
		/*
		 * Check that checkbox for the new file is selected
		 */
		assertTrue(bot.checkBox(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_NEW_FILE).isChecked());
		/*
		 * 'Next>' button should. Press it.
		 */
		assertTrue("(Next>) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		bot.button(WidgetVariables.NEXT_BUTTON).click();
		/*
		 * Check the folder name
		 */
		SWTBotText folderText = bot.textWithLabel(FOLDER_TEXT_LABEL);
		assertNotNull("'" + FOLDER_TEXT_LABEL + "' text field is not found", folderText); //$NON-NLS-1$ //$NON-NLS-2$
		assertText(JBT_TEST_PROJECT_NAME + "/JavaSource", folderText); //$NON-NLS-1$
		/*
		 * Check the file name
		 */
		SWTBotText fileName = bot.textWithLabel("File name:"); //$NON-NLS-1$
		assertNotNull("'File Name:' text field is not found", fileName); //$NON-NLS-1$
		assertText("hello.properties", fileName); //$NON-NLS-1$
		/*
		 * 'Next>' button should be enabled. Press it.
		 */
		assertTrue("(Next>) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		bot.button(WidgetVariables.NEXT_BUTTON).click();
		/*
		 * Check that 'manually by user' radiobutton is selected
		 */
		SWTBotRadio rb = bot.radioInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_USER_DEFINED,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_SAVE_RESOURCE_BUNDLE);
		assertNotNull(CANNOT_FIND_RADIO_BUTTON + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_USER_DEFINED, rb);
		assertTrue(
				"(" + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_USER_DEFINED + ") " + //$NON-NLS-1$ //$NON-NLS-2$
						"radio button should be enabled.", //$NON-NLS-1$
				rb.isSelected());
		/*
		 * Create new file
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		/*
		 * Check the file content
		 */
		editor.close();
		SWTBotEditor editor2 = SWTTestExt.eclipse.openFile(JBT_TEST_PROJECT_NAME, "JavaSource", //$NON-NLS-1$
				"hello.properties"); //$NON-NLS-1$
		editor2.bot().cTabItem("Source").activate();
		editor2.toTextEditor().selectLine(0);
		String line = editor2.toTextEditor().getSelection();
		assertEquals("Created file is incorrect", "Plain_text=\\r\\n\\r\\nPlain\\ text\\r\\n\\r\\n", line); //$NON-NLS-1$ //$NON-NLS-2$
		/*
		 * Reopen the page, and check that the new file for existed properties
		 * file won't be created. Open hello.jsp file once again.
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME)
			.getProjectItem("WebContent", "pages", "hello.jsp").open();
		editor = new TextEditor("hello.jsp");
		editor.activate();
		/*
		 * Select some text
		 */
		editor.selectLine(3);
		editor.insertText(3,0,"Plain text"); //$NON-NLS-1$
		editor.setCursorPosition(3, 3);
		editor.save();
		/*
		 * Activate the dialog
		 */
		assertTrue(TOOLBAR_ICON_ENABLED, bot.toolbarButtonWithTooltip(TOOL_TIP).isEnabled());
		bot.toolbarButtonWithTooltip(TOOL_TIP).click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * Check that the property key and value text are auto completed.
		 */
		defKeyText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_KEY,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defKeyText);
		assertText("Plain_text", defKeyText); //$NON-NLS-1$
		defValueText = bot.textWithLabelInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPERTIES_VALUE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_PROPS_STRINGS_GROUP);
		assertNotNull(CANNOT_FIND_PROPERTY_VALUE, defValueText);
		assertText("\\r\\n\\r\\nPlain text\\r\\n\\r\\n", defValueText); //$NON-NLS-1$
		/*
		 * Check that checkbox for the new file is selected
		 */
		assertTrue(bot.checkBox(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_NEW_FILE).isChecked());
		/*
		 * 'Next>' button should. Press it.
		 */
		assertTrue("(Next>) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		bot.button(WidgetVariables.NEXT_BUTTON).click();
		/*
		 * Check the folder name
		 */
		folderText = bot.textWithLabel(FOLDER_TEXT_LABEL);
		assertNotNull("'" + FOLDER_TEXT_LABEL + "' text field is not found", folderText); //$NON-NLS-1$ //$NON-NLS-2$
		assertText(JBT_TEST_PROJECT_NAME + "/JavaSource", folderText); //$NON-NLS-1$
		/*
		 * Check the file name
		 */
		fileName = bot.textWithLabel("File name:"); //$NON-NLS-1$
		assertNotNull("'File Name:' text field is not found", fileName); //$NON-NLS-1$
		assertText("hello.properties", fileName); //$NON-NLS-1$
		/*
		 * 'Next>' button should be disabled.
		 */
		assertFalse("(Next>) button should be disabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		/*
		 * Create new file
		 */
		assertFalse("(OK) button should be disabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).close();
		isUnusedDialogOpened = false;
	}

	public void testNewBundleRegisteringInFacesConfig() throws Throwable {
		createNewBundleToLastPage("", ""); //$NON-NLS-1$ //$NON-NLS-2$
		/*
		 * Select 'in the faces-config.xml file' radio button
		 */
		SWTBotRadio rb = bot.radioInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_FACES_CONFIG,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_SAVE_RESOURCE_BUNDLE);
		assertNotNull(CANNOT_FIND_RADIO_BUTTON + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_FACES_CONFIG, rb);
		rb.setFocus();
		rb.click();
		assertTrue(
				"(" + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_FACES_CONFIG + ") " + //$NON-NLS-1$ //$NON-NLS-2$
						"radio button should be enabled.", //$NON-NLS-1$
				rb.isSelected());

		/*
		 * 'OK' button should be enabled. Press it.
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		bot.sleep(Timing.time2S());
		/*
		 * Check that the text was replaced
		 */
		TextEditor editor = new TextEditor();
		assertEquals("Replaced text is incorrect", editor.getPositionOfText("#{inputUserName.Input}"), 1); //$NON-NLS-1$ //$NON-NLS-2$
		/*
		 * Check that the resource-bundle has been added to the faces-config
		 */
		SWTBotEditor ed = bot.editorByTitle("faces-config.xml"); //$NON-NLS-1$
		assertNotNull("faces-config.xml should be opened in the editor", ed); //$NON-NLS-1$
		ed.setFocus();
		bot.cTabItem("Source").activate(); //$NON-NLS-1$

		ed.toTextEditor().selectLine(23);
		assertEquals("24 line in not: <resource-bundle>", //$NON-NLS-1$
				"  <resource-bundle>", //$NON-NLS-1$
				ed.toTextEditor().getSelection());
		ed.toTextEditor().selectLine(24);
		assertEquals("25 line in not: <base-name>inputUserName</base-name>", //$NON-NLS-1$
				"   <base-name>inputUserName</base-name>", //$NON-NLS-1$
				ed.toTextEditor().getSelection());
		ed.toTextEditor().selectLine(25);
		assertEquals("26 line in not: <var>inputUserName</var>", //$NON-NLS-1$
				"   <var>inputUserName</var>", //$NON-NLS-1$
				ed.toTextEditor().getSelection());
		ed.toTextEditor().selectLine(26);
		assertEquals("27 line in not: </resource-bundle>", //$NON-NLS-1$
				"  </resource-bundle>", //$NON-NLS-1$
				ed.toTextEditor().getSelection());
	}

	public void testNewBundleRegisteringViaLoadBundleTag() throws Throwable {
		createNewBundleToLastPage("", "inputUserName2");
		/*
		 * Select 'via <f:loadBundle> tag on the current page' radio button
		 */
		SWTBotRadio rb = bot.radioInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_SAVE_RESOURCE_BUNDLE);
		assertNotNull(CANNOT_FIND_RADIO_BUTTON + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE, rb);
		rb.setFocus();
		rb.click();
		assertTrue(
				"(" + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE + ") " + //$NON-NLS-1$ //$NON-NLS-2$
						"radio button should be enabled.", //$NON-NLS-1$
				rb.isSelected());

		/*
		 * 'OK' button should be enabled. Press it.
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		/*
		 * Check that the text was replaced
		 */
		TextEditor editor = new TextEditor();
		editor.activate();
		editor.selectLine(7);
		assertEquals("Replaced text is incorrect", //$NON-NLS-1$
				"    	<title><f:loadBundle var=\"inputUserName\" basename=\"inputUserName2\" />#{inputUserName.Input} User Name Page</title>", //$NON-NLS-1$
				editor.getSelectedText());
	}

	public void testTaglibRegistrationInJSP() throws Throwable {
		/*
		 * Open simple html file in order to get the VPE toolbar
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME)
			.getProjectItem("WebContent", "pages", TEST_PAGE).open();
		
		TextEditor editor = new TextEditor(TEST_PAGE);
		editor.activate();
		editor.selectLine(0);
		assertEquals("JSF Core taglib should present on page", //$NON-NLS-1$
				"<%@ taglib uri=\"http://java.sun.com/jsf/core\" prefix=\"f\" %>", //$NON-NLS-1$
				editor.getSelectedText());
		/*
		 * Rewrite the taglib
		 */
		KeyboardFactory.getKeyboard().type(" ");
		editor.save();
		/*
		 * Make sure that taglib doesn't present
		 */
		assertTrue("JSF Core taglib should be removed", //$NON-NLS-1$
				(editor.getText()
						.indexOf("<%@ taglib uri=\"http://java.sun.com/jsf/core\" prefix=\"f\" %>") == -1)); //$NON-NLS-1$
		createNewBundleToLastPage("", "inputUserName4"); //$NON-NLS-1$ //$NON-NLS-2$
		/*
		 * Select 'via <f:loadBundle> tag on the current page' radio button
		 */
		SWTBotRadio rb = bot.radioInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_SAVE_RESOURCE_BUNDLE);
		assertNotNull(CANNOT_FIND_RADIO_BUTTON + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE, rb);
		rb.setFocus();
		rb.click();
		assertTrue(
				"(" + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE + ") " + //$NON-NLS-1$ //$NON-NLS-2$
						"radio button should be enabled.", //$NON-NLS-1$
				rb.isSelected());

		/*
		 * 'OK' button should be enabled. Press it.
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		/*
		 * Check that the text was replaced
		 */
		editor.activate();
		editor.save();
		editor.selectLine(1);
		assertEquals("https://issues.jboss.org/browse/JBIDE-17920\nTaglig insertion failed!", //$NON-NLS-1$
				"<%@ taglib uri=\"http://java.sun.com/jsf/core\" prefix=\"f\"%>", //$NON-NLS-1$
				editor.getSelectedText());
	}

	public void testTaglibRegistrationInXHTML() throws Throwable {
		isUnusedDialogOpened = false;
		/*
		 * Open simple html file in order to get the VPE toolbar
		 */
		SWTTestExt.packageExplorer.getProject(FACELETS_TEST_PROJECT_NAME)
			.getProjectItem("WebContent", "pages", FACELETS_TEST_PAGE).open();
		TextEditor editor = new TextEditor(FACELETS_TEST_PAGE);		
		editor.activate();;
		editor.selectLine(4);
		assertEquals("JSF Core taglib should present on page", //$NON-NLS-1$
				"      xmlns:f=\"http://java.sun.com/jsf/core\"", //$NON-NLS-1$
				editor.getSelectedText());
		/*
		 * Rewrite the taglib
		 */
		KeyboardFactory.getKeyboard().type(" ");
		editor.save();
		/*
		 * Make sure that taglib doesn't present
		 */
		assertTrue("JSF Core taglib should be removed", //$NON-NLS-1$
				(editor.getText().indexOf("xmlns:f=\"http://java.sun.com/jsf/core\"") == -1)); //$NON-NLS-1$
		editor.selectText("User");
		/*
		 * Get toolbar button
		 */
		util.waitForToolbarButtonWithTooltipIsFound(TOOL_TIP, Timing.time3S());
		SWTBotToolbarButton tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitForToolbarButtonEnabled(tbButton, Timing.time5S());
		tbButton.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		SWTBotCheckBox checkBox = bot.checkBox();
		assertNotNull(
				"Cannot find checkbox '" //$NON-NLS-1$
						+ JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_NEW_FILE + "'", //$NON-NLS-1$
				checkBox);
		checkBox.select();
		assertTrue("Checkbox should be checked.", //$NON-NLS-1$
				checkBox.isChecked());
		assertTrue("Next button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		bot.button(WidgetVariables.NEXT_BUTTON).click();
		/*
		 * 'Next>' button should be enabled. Press it.
		 */
		assertTrue("(Next>) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		bot.button(WidgetVariables.NEXT_BUTTON).click();
		/*
		 * Select 'via <f:loadBundle> tag on the current page' radio button
		 */
		SWTBotRadio rb = bot.radioInGroup(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE,
				JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_SAVE_RESOURCE_BUNDLE);
		assertNotNull(CANNOT_FIND_RADIO_BUTTON + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE, rb);
		rb.setFocus();
		rb.click();
		assertTrue(
				"(" + JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_LOAD_BUNDLE + ") " + //$NON-NLS-1$ //$NON-NLS-2$
						"radio button should be enabled.", //$NON-NLS-1$
				rb.isSelected());

		/*
		 * 'OK' button should be enabled. Press it.
		 */
		assertTrue("(OK) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.OK_BUTTON).isEnabled());
		bot.button(WidgetVariables.OK_BUTTON).click();
		isUnusedDialogOpened = false;
		/*
		 * Check that the text was replaced
		 */
		editor.activate();
		editor.selectLine(6);
		assertEquals("https://issues.jboss.org/browse/JBIDE-17920\nTaglig insertion failed!", //$NON-NLS-1$
				"      xmlns:f=\"http://java.sun.com/jsf/core\">", //$NON-NLS-1$
				editor.getSelectedText());
	}

	public void testToolBarIconEnableState() throws Throwable {
		isUnusedDialogOpened = false;
		SWTTestExt.packageExplorer.getProject(FACELETS_TEST_PROJECT_NAME)
			.getProjectItem("WebContent", "pages", FACELETS_TEST_PAGE).open();
		TextEditor editor = new TextEditor(FACELETS_TEST_PAGE);
		editor.activate();
		editor.setText(ENABLED_TEST_TEXT);
		navigateToAndTestIcon(editor, new Position(0, 1), false);
		navigateToAndTestIcon(editor, new Position(0, 3), false);
		navigateToAndTestIcon(editor, new Position(0, 16), true);
		navigateToAndTestIcon(editor, new Position(0, 3), false);
	}

	private void navigateToAndTestIcon(TextEditor editor, Position pos, boolean enabled) {
		/*
		 * Select some text
		 */
		editor.setCursorPosition(pos.line, pos.column);
		bot.sleep(Timing.time1S());
		/*
		 * Send key press event to fire VPE listeners
		 */
		KeyboardHelper.typeKeyCodeUsingAWT(KeyEvent.VK_RIGHT);
		bot.sleep(Timing.time1S());
		/*
		 * Get toolbar button
		 */
		util.waitForToolbarButtonWithTooltipIsFound(TOOL_TIP, Timing.time3S());
		SWTBotToolbarButton tbExternalizeStrings = (bot.toolbarButtonWithTooltip(TOOL_TIP));
		if (enabled) {
			util.waitForToolbarButtonEnabled(tbExternalizeStrings, Timing.time3S());
		} else {
			util.waitWhileToolbarButtonisDisabled(tbExternalizeStrings, Timing.time3S());
		}
		assertEquals(enabled ? TOOLBAR_ICON_ENABLED : TOOLBAR_ICON_DISABLED, enabled, tbExternalizeStrings.isEnabled());
	}

	/**
	 * Creates the new bundle till last page.
	 *
	 * @param folderPath
	 *            the folder path
	 * @param fileName
	 *            the file name
	 * @return the swt bot editor
	 */
	private void createNewBundleToLastPage(String folderPath, String fileName) {
		isUnusedDialogOpened = false;
		/*
		 * Open simple html file in order to get the VPE toolbar
		 */
		SWTTestExt.packageExplorer.getProject(JBT_TEST_PROJECT_NAME)
			.getProjectItem("WebContent", "pages", TEST_PAGE);
		TextEditor editor = new TextEditor(TEST_PAGE);
		editor.activate();
		/*
		 * Select some text
		 */
		editor.selectText("Input");
		/*
		 * Get toolbar button
		 */
		util.waitForToolbarButtonWithTooltipIsFound(TOOL_TIP, Timing.time3S());
		SWTBotToolbarButton tbButton = bot.toolbarButtonWithTooltip(TOOL_TIP);
		util.waitForToolbarButtonEnabled(tbButton, Timing.time5S());
		tbButton.click();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).setFocus();
		bot.shell(JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_TITLE).activate();
		isUnusedDialogOpened = true;
		/*
		 * Enable next page and check it
		 */
		SWTBotCheckBox checkBox = bot.checkBox();
		assertNotNull(
				"Cannot find checkbox '" //$NON-NLS-1$
						+ JstUIMessages.EXTERNALIZE_STRINGS_DIALOG_NEW_FILE + "'", //$NON-NLS-1$
				checkBox);
		checkBox.select();
		assertTrue("Checkbox should be checked.", //$NON-NLS-1$
				checkBox.isChecked());
		assertTrue("Next button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		bot.button(WidgetVariables.NEXT_BUTTON).click();
		/*
		 * Create new file
		 */
		SWTBotText folderText = bot.textWithLabel(FOLDER_TEXT_LABEL);
		assertNotNull("'" + FOLDER_TEXT_LABEL + "' text field is not found", folderText); //$NON-NLS-1$ //$NON-NLS-2$
		if ((null != folderPath) && (folderPath.length() > 0)) {
			folderText.setText(folderPath);
		}
		SWTBotText fileNameField = bot.textWithLabel("File name:"); //$NON-NLS-1$
		assertNotNull("'File Name:' text field is not found", fileNameField); //$NON-NLS-1$
		if ((null != fileName) && (fileName.length() > 0)) {
			fileNameField.setText(fileName);
		}
		/*
		 * 'Next>' button should be enabled. Press it.
		 */
		assertTrue("(Next>) button should be enabled.", //$NON-NLS-1$
				bot.button(WidgetVariables.NEXT_BUTTON).isEnabled());
		bot.button(WidgetVariables.NEXT_BUTTON).click();
	}

}
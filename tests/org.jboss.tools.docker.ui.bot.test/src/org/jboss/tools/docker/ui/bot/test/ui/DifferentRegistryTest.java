/*******************************************************************************
 * Copyright (c) 2016 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.docker.ui.bot.test.ui;

import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.equinox.security.ui.StoragePreferencePage;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.tools.docker.ui.bot.test.AbstractDockerBotTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author jkopriva
 *
 */

public class DifferentRegistryTest extends AbstractDockerBotTest {
	private String serverAddress = "registry.access.redhat.com";
	private String email = "test@test.com";
	private String userName = "test";
	private String password = "password";
	private String imageName = "rhel:latest";

	@Before
	public void before() {
		openDockerPerspective();
		createConnection();
	}

	@Test
	public void testDifferentRegistry() {
		ConsoleView cview = new ConsoleView();
		cview.open();
		try {
			cview.clearConsole();
		} catch (CoreLayerException ex) {
			// there's not clear console button, since nothing run before
		}
		setUpRegister(this.serverAddress, this.email, this.userName, this.password);
		setSecureStorage(this.password);
		pullImage(this.userName+ "@" +this.serverAddress, this.imageName);
		new WaitWhile(new JobIsRunning());
		assertTrue("Image is not deployed!", imageIsDeployed(this.imageName));
	}

	public static void disableSecureStorage() {
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		StoragePreferencePage storagePage = new StoragePreferencePage();
		preferenceDialog.open();

		preferenceDialog.select(storagePage);
		for (TableItem item : storagePage.getMasterPasswordProviders()) {
			item.setChecked(true);
		}
		storagePage.apply();
		preferenceDialog.ok();
	}

	@After
	public void after() {
		deleteImage(this.imageName);
		deleteConnection();
		deleteRegister(serverAddress);
	}

}

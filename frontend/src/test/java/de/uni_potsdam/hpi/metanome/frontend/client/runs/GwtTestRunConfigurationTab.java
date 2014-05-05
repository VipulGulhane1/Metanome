/*
 * Copyright 2014 by the Metanome project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.uni_potsdam.hpi.metanome.frontend.client.runs;

import com.google.gwt.junit.client.GWTTestCase;
import de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSpecification;
import de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSpecificationCsvFile;
import de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSpecificationString;
import de.uni_potsdam.hpi.metanome.frontend.client.BasePage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the algorithm specific pages (tabs)
 */
public class GwtTestRunConfigurationTab extends GWTTestCase {

    private BasePage page;

    @Test
    public void testConstruction() {
        //Execute
        RunConfigurationPage runConfigPage = new RunConfigurationPage(page);

        //Check - should contain the jarChooser and a Label for pre-selected data source (possibly empty)
        assertEquals(2, runConfigPage.getWidgetCount());
        assertTrue(runConfigPage.getJarChooser() instanceof JarChooser);
    }

    @Test
    public void testAddParameterTable() {
        //Setup
        RunConfigurationPage runConfigPage = new RunConfigurationPage(page);
        List<ConfigurationSpecification> paramList = new ArrayList<ConfigurationSpecification>();
        int widgetCount = runConfigPage.getWidgetCount();

        //Execute
        runConfigPage.addParameterTable(paramList);

        //Check
        assertEquals(widgetCount + 1, runConfigPage.getWidgetCount());
    }

    @Test
    public void testAddAlgorithms() {
        //Setup
        RunConfigurationPage runConfigPage = new RunConfigurationPage(page);
        int noOfAlgorithms = runConfigPage.getJarChooser().getListItemCount();

        //Execute
        runConfigPage.addAlgorithms("Additional 1", "Additional 2");

        //Check
        assertEquals(noOfAlgorithms + 2, runConfigPage.getJarChooser().getListItemCount());
    }

    @Test
    public void testSelectAlgorithm() {
        //Setup
        RunConfigurationPage runConfigPage = new RunConfigurationPage(page);
        String algoName = setUpJarChooser(runConfigPage);

        //Execute
        runConfigPage.selectAlgorithm(algoName);

        //Check
        assertEquals(algoName, runConfigPage.getCurrentlySelectedAlgorithm());
    }


    @Test
    public void testForwardParamters() {
        //Setup
        RunConfigurationPage runConfigPage = new RunConfigurationPage(page);
        List<ConfigurationSpecification> paramList = new ArrayList<ConfigurationSpecification>();
        paramList.add(new ConfigurationSpecificationString("someString"));
        paramList.add(new ConfigurationSpecificationCsvFile("theDataSource"));

        //Execute
        runConfigPage.jarChooser.forwardParameters(paramList);

        //Check
        assertNotNull(runConfigPage.parameterTable);
    }

    protected String setUpJarChooser(RunConfigurationPage runConfigPage) {
        String algoName = "somethingRandom";
        runConfigPage.addAlgorithms("Additional 1", algoName, "Additional 2");
        assertEquals("--", runConfigPage.getCurrentlySelectedAlgorithm());
        return algoName;
    }

    @Override
    public String getModuleName() {
        return "de.uni_potsdam.hpi.metanome.frontend.Metanome";
    }

}
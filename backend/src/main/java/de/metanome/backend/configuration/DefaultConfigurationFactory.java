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

package de.metanome.backend.configuration;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.configuration.ConfigurationFactory;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementBoolean;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementDatabaseConnection;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementFileInput;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementInteger;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementListBox;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementRelationalInput;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementString;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.input.FileInputGenerator;
import de.metanome.backend.input.DefaultRelationalInputGeneratorInitializer;
import de.metanome.backend.input.csv.DefaultFileInputGenerator;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Converts given {@link de.metanome.algorithm_integration.configuration.ConfigurationRequirement}s to the {@link de.metanome.algorithm_integration.configuration.ConfigurationValue}s.
 *
 * @author Jakob Zwiener
 */
public class DefaultConfigurationFactory extends ConfigurationFactory {

  /**
   * Converts a {@link de.metanome.algorithm_integration.configuration.ConfigurationRequirementFileInput}
   * to a {@link de.metanome.algorithm_integration.input.FileInputGenerator}.
   *
   * @param requirement the file input requirement
   * @return the created file input generator
   */
  @Deprecated
  private static FileInputGenerator[] createFileInputGenerators(
      ConfigurationRequirementFileInput requirement) throws AlgorithmConfigurationException {

    DefaultFileInputGenerator[]
        defaultFileInputGenerators =
        new DefaultFileInputGenerator[requirement.getSettings().length];

    int i = 0;
    for (ConfigurationSettingFileInput setting : requirement.getSettings()) {
      try {
        if (setting.isAdvanced()) {
          defaultFileInputGenerators[i] =
              new DefaultFileInputGenerator(new File(setting.getFileName()),
                                   setting.getSeparatorChar(),
                                   setting.getQuoteChar(),
                                   setting.getEscapeChar(),
                                   setting.getSkipLines(),
                                   setting.isStrictQuotes(),
                                   setting.isIgnoreLeadingWhiteSpace(),
                                   setting.hasHeader(),
                                   setting.isSkipDifferingLines());
        } else {
          defaultFileInputGenerators[i] =
              new DefaultFileInputGenerator(new File(setting.getFileName()));
        }
      } catch (FileNotFoundException e) {
        throw new AlgorithmConfigurationException("Could not find CSV file.");
      }
      i++;
    }

    return defaultFileInputGenerators;
  }

  /**
   * TODO docs
   */
  public ConfigurationValueBoolean build(ConfigurationRequirementBoolean requirement) {
    return new ConfigurationValueBoolean(requirement);
  }

  /**
   * TODO docs
   */
  public ConfigurationValueInteger build(ConfigurationRequirementInteger requirement) {
    return new ConfigurationValueInteger(requirement);
  }

  /**
   * TODO docs
   */
  public ConfigurationValueListBox build(ConfigurationRequirementListBox requirement) {
    return new ConfigurationValueListBox(requirement);
  }

  // TODO add ConfigurationValueDatabaseConnection

  /**
   * TODO docs
   */
  public ConfigurationValueRelationalInputGenerator build(
      ConfigurationRequirementRelationalInput requirement)
      throws FileNotFoundException, AlgorithmConfigurationException {
    DefaultRelationalInputGeneratorInitializer inputGeneratorInitializer = new DefaultRelationalInputGeneratorInitializer(requirement);
    return inputGeneratorInitializer.getConfigurationValue();
  }

  /**
   * TODO docs
   */
  public ConfigurationValueSqlInputGenerator build(
      ConfigurationRequirementDatabaseConnection requirement) {
    try {
      return new ConfigurationValueSqlInputGenerator(requirement);
    } catch (AlgorithmConfigurationException e) {
      e.printStackTrace();
      //TODO handle exception
    }
    return null;
  }

  /**
   * TODO docs
   */
  public ConfigurationValueString build(ConfigurationRequirementString requirement) {
    return new ConfigurationValueString(requirement);
  }

}

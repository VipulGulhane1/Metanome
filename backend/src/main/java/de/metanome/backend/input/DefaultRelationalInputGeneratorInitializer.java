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

package de.metanome.backend.input;


import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementRelationalInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingRelationalInput;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingTableInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.input.RelationalInputGeneratorInitializer;
import de.metanome.backend.configuration.ConfigurationValueRelationalInputGenerator;
import de.metanome.backend.input.csv.DefaultFileInputGenerator;
import de.metanome.backend.input.sql.DefaultTableInputGenerator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DefaultRelationalInputGeneratorInitializer implements RelationalInputGeneratorInitializer {

  List<RelationalInputGenerator> generatorList = new ArrayList<>();
  String identifier;

  public DefaultRelationalInputGeneratorInitializer(
      ConfigurationRequirementRelationalInput requirementRelationalInput)
      throws FileNotFoundException, AlgorithmConfigurationException {
    this.identifier = requirementRelationalInput.getIdentifier();

    for (ConfigurationSettingRelationalInput setting : requirementRelationalInput.getSettings()) {
      setting.generate(this);
    }
  }

  public void initialize(ConfigurationSettingFileInput setting)
      throws FileNotFoundException {
    generatorList.add(new DefaultFileInputGenerator(setting));
  }

  public void initialize(ConfigurationSettingTableInput setting)
      throws AlgorithmConfigurationException {
    generatorList.add(new DefaultTableInputGenerator(setting));
  }

  public ConfigurationValueRelationalInputGenerator getConfigurationValue() {
    return new ConfigurationValueRelationalInputGenerator(identifier,
                                                          generatorList.toArray(
                                                              new RelationalInputGenerator[generatorList
                                                                  .size()]));
  }
}

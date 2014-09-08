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

package de.uni_potsdam.hpi.metanome.example_sql_profiling_algorithm;

import de.uni_potsdam.hpi.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.uni_potsdam.hpi.metanome.algorithm_integration.AlgorithmExecutionException;
import de.uni_potsdam.hpi.metanome.algorithm_integration.algorithm_types.SqlInputParameterAlgorithm;
import de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSpecification;
import de.uni_potsdam.hpi.metanome.algorithm_integration.configuration.ConfigurationSpecificationSqlIterator;
import de.uni_potsdam.hpi.metanome.algorithm_integration.input.SqlInputGenerator;

import java.util.LinkedList;
import java.util.List;


/**
 * TODO docs
 *
 * @author Jakob Zwiener
 */
public class SqlProfilingAlgorithm implements SqlInputParameterAlgorithm {

  public static final String SQL_IDENTIFIER = "sql identifier";

  protected SqlInputGenerator inputGenerator;

  @Override
  public void setSqlInputConfigurationValue(String identifier, SqlInputGenerator... values)
      throws AlgorithmConfigurationException {

    if (identifier.equals(SQL_IDENTIFIER)) {
      inputGenerator = values[0];
    }
  }

  @Override
  public List<ConfigurationSpecification> getConfigurationRequirements() {
    List<ConfigurationSpecification> configurationRequirements = new LinkedList<>();

    configurationRequirements.add(new ConfigurationSpecificationSqlIterator(SQL_IDENTIFIER));

    return configurationRequirements;
  }

  @Override
  public void execute() throws AlgorithmExecutionException {

  }
}
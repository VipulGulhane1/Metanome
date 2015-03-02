/*
 * Copyright 2015 by the Metanome project
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

package de.metanome.backend.helper;

import de.metanome.algorithm_integration.results.BasicStatistic;
import de.metanome.algorithm_integration.results.ConditionalUniqueColumnCombination;
import de.metanome.algorithm_integration.results.FunctionalDependency;
import de.metanome.algorithm_integration.results.InclusionDependency;
import de.metanome.algorithm_integration.results.OrderDependency;
import de.metanome.algorithm_integration.results.Result;
import de.metanome.algorithm_integration.results.UniqueColumnCombination;
import de.metanome.backend.results_db.ResultType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultReader {

  public static List<Result> readResultsFromFile(String fileName, String type) throws IOException {
    List<Result> results = new ArrayList<>();

    File resultFile = new File(fileName);

    BufferedReader br = new BufferedReader(new FileReader(resultFile));
    String line;
    while((line = br.readLine()) != null) {
      results.add(convertLineToResult(line, type));
    }

    return results;
  }

  private static Result convertLineToResult(String line, String type) {
    if (type.equals(ResultType.CUCC.getName())) {
      return ConditionalUniqueColumnCombination.fromString(line);

    } else if (type.equals(ResultType.OD.getName())) {
      return OrderDependency.fromString(line);

    } else if (type.equals(ResultType.IND.getName())) {
      return InclusionDependency.fromString(line);

    } else if (type.equals(ResultType.FD.getName())) {
      return FunctionalDependency.fromString(line);

    } else if (type.equals(ResultType.UCC.getName())) {
      return UniqueColumnCombination.fromString(line);

    } else if (type.equals(ResultType.STAT.getName())) {
      return BasicStatistic.fromString(line);

    }

    return null;
  }

}

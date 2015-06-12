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

package de.metanome.backend.result_postprocessing.result_ranking;

import de.metanome.backend.result_postprocessing.helper.TableInformation;
import de.metanome.backend.result_postprocessing.results.OrderDependencyResult;

import java.util.List;
import java.util.Map;

/**
 * Calculates the rankings for order dependency results.
 */
public class OrderDependencyRanking extends Ranking {

  protected List<OrderDependencyResult> results;

  public OrderDependencyRanking(List<OrderDependencyResult> results,
                                Map<String, TableInformation> tableInformationMap) {
    super(tableInformationMap);
    this.results = results;
  }

  @Override
  public void calculateDataIndependentRankings() {
    for (OrderDependencyResult result : this.results) {
      calculateColumnRatios(result);
      calculateGeneralCoverage(result);
    }
  }

  @Override
  public void calculateDataDependentRankings() {
    for (OrderDependencyResult result : this.results) {
      calculateColumnRatios(result);
      calculateGeneralCoverage(result);
    }
  }

  /**
   * Calculates the ratio of the lhs/rhs column count and the column count of the
   * corresponding table.
   *
   * @param result the result
   */
  protected void calculateColumnRatios(OrderDependencyResult result) {
    Integer lhsColumnCount = result.getLhs().getColumnIdentifiers().size();
    Integer rhsColumnCount = result.getRhs().getColumnIdentifiers().size();

    Integer
        lhsTableColumnCount =
        this.tableInformationMap.get(result.getLhsTableName()).getColumnCount();
    Integer
        rhsTableColumnCount =
        this.tableInformationMap.get(result.getRhsTableName()).getColumnCount();

    result.setLhsColumnRatio((float) lhsColumnCount / lhsTableColumnCount);
    result.setRhsColumnRatio((float) rhsColumnCount / rhsTableColumnCount);
  }


  /**
   * Calculates the relation between the total number of columns of the result and the number of
   * columns of tables, which are involved.
   *
   * @param result the result
   */
  protected void calculateGeneralCoverage(OrderDependencyResult result) {
    Integer referencedColumnCount = result.getLhs().getColumnIdentifiers().size();
    Integer dependantColumnCount = result.getRhs().getColumnIdentifiers().size();

    int tableCount;
    if (result.getRhsTableName().equals(result.getLhsTableName())) {
      tableCount = this.tableInformationMap.get(result.getRhsTableName()).getColumnCount();
    } else {
      tableCount = this.tableInformationMap.get(result.getRhsTableName()).getColumnCount() +
                   this.tableInformationMap.get(result.getLhsTableName()).getColumnCount();
    }

    result.setGeneralCoverage(((float) referencedColumnCount + dependantColumnCount) / tableCount);
  }


}

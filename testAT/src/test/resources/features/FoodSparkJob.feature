Feature: Feature for FoodSparkJob
  Scenario: Run a service process
    Given The id of the process as 'FoodSparkJob'
    And The env 'INPUT_PATH' targeting the resource file 'data/input/csv/t_kdit_food/food.csv'
    And The env 'INPUT_PATH' targeting the resource file 'data/input/csv/t_kdit_foodie/foodie.csv'
    And The env 'INPUT_SCHEMA_PATH' targeting the resource file 'schema/t_kdit_food.input.schema'
    And The env 'INPUT_SCHEMA_PATH' targeting the resource file 'schema/t_kdit_foodie.input.schema'
    And The env 'OUTPUT_PATH' targeting the target file 'output'
    And The env 'OUTPUT_SCHEMA_PATH' targeting the resource file 'schema/t_kdit_report1.output.schema'
    And A config file with the contents:
      """
      FoodSparkJob {
  input {
    t_kdit_food {
      type = "csv"
      delimiter = "|"
      schema {
        path = "src/test/resources/schema/t_kdit_food.input.schema"
      }
      paths: ["src/test/resources/data/input/csv/t_kdit_food/food.csv"]
    }
    t_kdit_foodie {
      type = "csv"
      delimiter = "|"
      schema {
        path = "src/test/resources/schema/t_kdit_foodie.input.schema"
      }
      paths = ["src/test/resources/data/input/csv/t_kdit_foodie/foodie.csv"]
    }
    parameters {
      message = ${?MESSAGE}
    }
  }
  output {
    report1 {
      path = "src/test/resources/data/output/t_kdit_report1"
      schema {
        path = "src/test/resources/schema/t_kdit_report1.output.schema"
      }
      partitions = ["ROW_NUMBER"]
      mode = overwrite
      type = parquet
      options {
        includeMetadataFields = true
        includeDeletedFields = true
        partitionsOverwriteMode = dynamic
        coalesce = 1
      }
    }
  }
}
      """
    When Executing the Launcher
    Then The exit code should be 0

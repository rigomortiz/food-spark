
# Dataproc Spark Scala Job - FoodSparkJob

*This readme was generated by the project archetype, remove the next section and fill it with documentation* 

## Getting started with the new project
### First Compilation

Make a clean install in the root directory of the project

```bash
mvn clean install
```

### First Execution with the SparkLauncher of dataproc-sdk libraries

Go to your IDE run configurations window and set the following configuration:
 * Main class: `com.datio.example.food.FoodSparkJobLauncher`
 * VM options: `-Dspark.master=local[*]`
 * Program arguments should be a valid path to a configuration file: `dataproc-spark-job-impl/src/main/resources/config/application.conf`
 * Working directory should point to the root path of the project: `/home/example/workspace/food`
 * Use classpath of the main implementation module => `dataproc-spark-job-impl`
 * Set the location of the local input and output files as environment variables:
   * INPUT_SCHEMA_PATH = "dataproc-spark-job-impl/src/main/resources/schema/inputSchema.json"
   * OUTPUT_SCHEMA_PATH = "dataproc-spark-job-impl/src/main/resources/schema/outputSchema.json"
   * INPUT_PATH = "dataproc-spark-job-impl/src/main/resources/data/olympics.csv"
   * OUTPUT_PATH = "dataproc-spark-job-impl/target/data/result"

You will also need to enable the maven profile `run-local`.

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class SparkTest {

    public static void main(String... args){

        System.setProperty("hadoop.home.dir", "/usr/local/hadoop");

        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("wordcount");
        sparkConf.setMaster("yarn-client");
        sparkConf.set("spark.yarn.am.memory","1024m");
        sparkConf.setJars(new String[] { "/home/tony/Development/HadoopTest/target/SparkApp.jar"});
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        // Load our input data.
        JavaRDD<String> input = sc.textFile("hdfs://localhost:9000/big.txt");
        JavaPairRDD<String, Integer> counts = input
                .flatMap(s -> Arrays.asList(s.split(" ")).iterator())
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((a, b) -> a + b);
        System.out.println("-----------" + counts.collect() + "-----------");
    }
}

package gtl.modeling.src.decisionTree;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/30 0030.
 */
public class PreprocessWithRF {
    private RandomForestModel model;
    public RandomForestModel getModel(){return model;}
    public void run(){
        SparkConf sparkConf = new SparkConf()
                .setMaster("local")
                .setAppName("PreprocessingWithRDD");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        String datapath = "C:\\train.txt";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), datapath).toJavaRDD();
        int numClasses = 3;
        Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
        int numTrees = 8; // Use more in practice.
        String featureSubsetStrategy = "auto"; // Let the algorithm choose.
        String impurity = "gini";
        int maxDepth = 10;
        int maxBins = 32;
        int seed = 12345;
        model = RandomForest.trainClassifier(data, numClasses,
                categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
                seed);
    }
}

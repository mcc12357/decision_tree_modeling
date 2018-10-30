package gtl.modeling.src.decisionTree;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;
import scala.Tuple4;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/10 0010.
 */
public class PreprocessingWithRDD {
    private DecisionTreeModel model;
    public DecisionTreeModel getModel(){return model;}
    //private RandomForestModel model;
    //public RandomForestModel getModel(){return model;}
    public void run(){
        SparkConf sparkConf = new SparkConf()
                .setMaster("local")
                .setAppName("PreprocessingWithRDD");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        String datapath = "C:\\train.txt";
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(jsc.sc(), datapath).toJavaRDD();
        //decision tree algorithm

        int numClasses = 3;
        Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
        String impurity = "gini";
        int maxDepth = 10;
        int maxBins = 32;
        model = DecisionTree.trainClassifier(data, numClasses,
                categoricalFeaturesInfo, impurity, maxDepth, maxBins);

    }
}

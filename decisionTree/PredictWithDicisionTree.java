package gtl.modeling.src.decisionTree;

import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import gtl.modeling.src.preprocess.*;

/**
 * Created by Administrator on 2018/4/10 0010.
 */
public class PredictWithDicisionTree {
    public static void main(String[] args){
        try {
            long start=System.currentTimeMillis(); //获取开始时间
            PreprocessingWithRDD t = new PreprocessingWithRDD();
            t.run();
            DecisionTreeModel TrainedTree = t.getModel();
            //RandomForestModel TrainedTree = t.getModel();
            long end=System.currentTimeMillis(); //获取结束时间
            System.out.println("程序运行时间： "+(end-start)+"ms");

            double maxX = 504469.182, maxY = 4528505.923, maxZ = 1111.9, minX = 495560.744, minY = 4512757.568, minZ = 910.5;
            double posX = minX, posY = minY, posZ = minZ;
            double stepX = 200, stepY = 200,stepZ = 2;
            //double projectedstepX = 50 / (maxX - minX) * stepX,projectedstepY = 50 / (maxY - minY) * stepY,projectedstepZ = 50 / (maxZ - minZ) * stepZ;
            //double projectedPosX = 0,projectedPosY = 0,projectedPosZ = 0;
            //System.out.println(TrainedTree.predict(Vectors.dense(5026.899 ,45272.274 ,943.4)));
            //System.out.println(projectedstepY);
            String filepath = "C:\\decisiontreeresult.txt";// train.txt file path
            File file = new File(filepath);
            OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file), "gbk");
            BufferedWriter bufw = new BufferedWriter(fw);
            bufw.write("result");
            bufw.newLine();
            bufw.write('4');
            bufw.newLine();
            bufw.write('X');
            bufw.newLine();
            bufw.write('Y');
            bufw.newLine();
            bufw.write('Z');
            bufw.newLine();
            bufw.write("Sedtype");
            bufw.newLine();
            while (posX <= maxX + stepX) {
                posY = minY;
                //projectedPosY = 0;
                while (posY <= maxY + stepY) {
                    posZ = minZ;
                    //projectedPosZ = 0;
                    while (posZ <= maxZ + stepZ) {
                        double result = TrainedTree.predict(Vectors.dense(posX, posY, posZ));
                        Premain tmp = new Premain();
                        String x_str = tmp.DoubleToString(posX);
                        //String x_str = tmp.DoubleToString(projectedPosX);
                        bufw.write(x_str + ' ');
                        String y_str = tmp.DoubleToString(posY);
                        //String y_str = tmp.DoubleToString(projectedPosY);
                        bufw.write(y_str + ' ');
                        String z_str = tmp.DoubleToString(posZ);
                        //String z_str = tmp.DoubleToString(projectedPosZ);
                        bufw.write(z_str + ' ');
                        String result_str = tmp.DoubleToString(result);
                        bufw.write(result_str);
                        bufw.newLine();
                        posZ += stepZ;
                        //projectedPosZ += projectedstepZ;
                    }
                    posY += stepY;
                    //projectedPosY += projectedstepY;
                }
                posX += stepX;
                //projectedPosX += projectedstepX;
            }
            bufw.close();
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

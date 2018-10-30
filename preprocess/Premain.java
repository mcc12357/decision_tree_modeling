package gtl.modeling.src.preprocess;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.NumberFormat;

import static com.google.common.primitives.Doubles.max;
import static com.google.common.primitives.Doubles.min;

public class Premain {
    private ArrayList<Borehole> boreholes;//contain borehole list

    private static HashMap<String, String> map1;//3 type hashmap

    //private double maxX = 0,maxY = 0,maxZ = 0,minX = 10000000,minY = 10000000,minZ = 100000;

    /*
    //获取x,y,z方向的最值
    public double getMaxX(){return maxX;}
    public double getMinX(){return minX;}
    public double getMaxY(){return maxY;}
    public double getMinY(){return minY;}
    public double getMaxZ(){return maxZ;}
    public double getMinZ(){return minZ;}
    */

    public Premain() {
        boreholes = new ArrayList<Borehole>();
        map1 = new HashMap<>();
        map1.put("冲洪积","0");
        map1.put("湖积","1");
        map1.put("基岩","2");
    }

    public void setBoreholes(ArrayList<Borehole> boreholes) {
        this.boreholes = boreholes;
    }

    public ArrayList<Borehole> getBoreholes() {
        return boreholes;
    }

    //read data from .csv file
    public void readfile() {
        try{
            //read lithology data from .csv file
            String filepath = "D:\\devs\\studies\\scala\\HelloWorld\\src\\gtl\\modeling\\data\\original\\boreholes\\boreholes_lithology_Z";//the path of directory which contains borehole lithology files(.csv)
            File file = new File(filepath);
            if(!file.isDirectory()) {
                System.out.println("wrong filepath");
            }
            else {
                String[] filelist = file.list();
                //System.out.println(filelist.length);
                for (int i = 0; i < filelist.length; i++) {
                    InputStreamReader fr = new InputStreamReader(new FileInputStream(filepath + "\\" + filelist[i]),"gbk");
                    //FileReader fr = new FileReader(filepath + "\\" + filelist[i]);
                    BufferedReader bufr = new BufferedReader(fr);
                    String line = bufr.readLine();
                    Borehole bo = new Borehole();
                    bo.setName(line.split(",")[1]);//in csv file data is divided by ","
                    bufr.readLine();
                    bufr.readLine();//not use two line data
                    line = null;
                    ArrayList<Lithology> lis = new ArrayList<Lithology>();
                    String flag = "";
                    while ((line = bufr.readLine()) != null) {
                        String item[] = line.split(",");
                        Lithology li;
                        if(!"".equals(item[0])) {
                            li = new Lithology(item[0], Double.parseDouble(item[1]), Double.parseDouble(item[2]), item[3]);
                            flag = item[0];
                        }
                        else {
                            li = new Lithology(flag, Double.parseDouble(item[1]), Double.parseDouble(item[2]), item[3]);
                        }
                        lis.add(li);
                    }
                    //System.out.println(lis.size());
                    bo.setLithologies(lis);
                    boreholes.add(bo);
                    bufr.close();
                    fr.close();
                }
            }
            //read position data from .csv file
            String filepath2 = "D:\\devs\\data\\DEMO\\DEMO\\boreholes\\boreholes_position.csv";// position file path
            File file2 = new File(filepath2);
            InputStreamReader fr2 = new InputStreamReader(new FileInputStream(file2),"gbk");
            //FileReader fr2 = new FileReader(file2);
            BufferedReader bufr2 = new BufferedReader(fr2);
            bufr2.readLine();
            String line = null;
            while ((line = bufr2.readLine()) != null) {
                String item[] = line.split(",");
                int flag1 = 0;//whether borehole name is exsit
                for (int i = 0; i < boreholes.size(); i++) {
                    if (item[0].equals(boreholes.get(i).getName())) {//find same name
                        flag1 = 1;
                        boreholes.get(i).setX(Double.parseDouble(item[1]));
                        boreholes.get(i).setY(Double.parseDouble(item[2]));
                        boreholes.get(i).setZ(Double.parseDouble(item[3]));
                    }
                }
                if (flag1 == 0) {
                    System.out.println("wrong name");
                }
            }
            bufr2.close();
            fr2.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //write point data in text file; divided 3 type
    public void writefile() {
        try {
            double maxX = 0,maxY = 0,maxZ = 0,minX = 10000000,minY = 10000000,minZ = 100000;
            String filepath = "C:\\train.txt";// train.txt file path
            //String filepath = "C:\\origin.txt";
            File file = new File(filepath);
            OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file),"gbk");
            BufferedWriter bufw = new BufferedWriter(fw);
            for (int i = 0; i < boreholes.size(); i++) {
                //double z = boreholes.get(i).getZ();
                //bufw.write(boreholes.get(i).getName());
                for (int j = 0; j < boreholes.get(i).getLithologies().size(); j++) {
                    Lithology tmp = boreholes.get(i).getLithologies().get(j);
                    if (tmp.getSedtype().equals("冲洪积")) bufw.write('0');
                    else if (tmp.getSedtype().equals("湖积")) bufw.write('1');
                    else if (tmp.getSedtype().equals("基岩")) bufw.write('2');
                    bufw.write(' ');

                    double x = boreholes.get(i).getX();
                    maxX = max(maxX, x);
                    minX = min(minX, x);
                    String x_str = DoubleToString(x);
                    bufw.write("1:" + x_str + ' ');
                    //bufw.write(x_str + ' ');

                    double y = boreholes.get(i).getY();
                    maxY = max(maxY, y);
                    minY = min(minY, y);
                    String y_str = DoubleToString(y);
                    bufw.write("2:" + y_str + ' ');
                    //bufw.write( y_str + ' ');
                    ;
                    //bufw.write(boreholes.get(i).getY() + ',');
                    //bufw.write(boreholes.get(i).getZ() + ',');
                    double z = boreholes.get(i).getZ() - tmp.getUnderheight();
                    maxZ = max(maxZ, z);
                    minZ = min(minZ, z);
                    String z_str = DoubleToString(z);
                    bufw.write("3:" + z_str + ' ');
                    //bufw.write(z_str + ' ');
                    bufw.newLine();
                }
            }

            bufw.close();
            fw.close();

            //计算x,y,z方向的最大最小值
            String filepath_ = "C:\\extreme.txt";// train.txt file path
            File file_ = new File(filepath_);
            OutputStreamWriter fw_ = new OutputStreamWriter(new FileOutputStream(file_),"gbk");
            BufferedWriter bufw_ = new BufferedWriter(fw_);
            String maxX_str = DoubleToString(maxX);
            String minX_str = DoubleToString(minX);
            String maxY_str = DoubleToString(maxY);
            String minY_str = DoubleToString(minY);
            String maxZ_str = DoubleToString(maxZ);
            String minZ_str = DoubleToString(minZ);
            bufw_.write("maxX=" + maxX_str);
            bufw_.newLine();
            bufw_.write("minX=" + minX_str);
            bufw_.newLine();
            bufw_.write("maxY=" + maxY_str);
            bufw_.newLine();
            bufw_.write("minY=" + minY_str);
            bufw_.newLine();
            bufw_.write("maxZ=" + maxZ_str);
            bufw_.newLine();
            bufw_.write("minZ=" + minZ_str);
            bufw_.newLine();
            bufw_.close();
            fw_.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String DoubleToString(double p){
        Double p_obj = new Double(p);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        String p_str = nf.format(p_obj);
        return p_str;
    }
    public static void main(String[] args) {
        Premain test = new Premain();
        test.readfile();
        test.writefile();

    }
}

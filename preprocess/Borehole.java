package gtl.modeling.src.preprocess;

import java.util.ArrayList;

public class Borehole {
    private String name;//名字
    private double X;//X坐标
    private double Y;//Y坐标
    private double Z;//Z坐标,即钻孔地面标高
    private ArrayList<Lithology> lithologies;//岩性信息

    public Borehole() {

    }

    public void setLithologies(ArrayList<Lithology> lithologies) {
        this.lithologies = lithologies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.X = x;
    }

    public void setY(double y) {
        this.Y = y;
    }

    public void setZ(double z) {
        this.Z = z;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getZ() {
        return Z;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Lithology> getLithologies() {
        return lithologies;
    }
}

package gtl.modeling.src.preprocess;

public class Lithology {
    private String sedtype;//沉积类型
    private double underheight;//层底埋深
    private double heigh;//厚度
    private String lithotype;//岩性
    public Lithology() {

    }

    public Lithology(String sedtype, double underheight, double heigh, String lithotype) {
        this.sedtype = sedtype;
        this.underheight = underheight;
        this.heigh = heigh;
        this.lithotype = lithotype;
    }

    public void setHeigh(double heigh) {
        this.heigh = heigh;
    }

    public void setLithotype(String lithotype) {
        this.lithotype = lithotype;
    }

    public void setSedtype(String sedtype) {
        this.sedtype = sedtype;
    }

    public void setUnderheight(double underheight) {
        this.underheight = underheight;
    }

    public double getHeigh() {
        return heigh;
    }

    public double getUnderheight() {
        return underheight;
    }

    public String getLithotype() {
        return lithotype;
    }

    public String getSedtype() {
        return sedtype;
    }
}

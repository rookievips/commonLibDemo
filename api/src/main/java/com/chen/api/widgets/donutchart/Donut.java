package com.chen.api.widgets.donutchart;

public class Donut {

    private String ratio;//所占比例
    private String name;//名称
    private float degree;//所占圆环的度数，圆环360度
    private int color;//颜色

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getRatioFloat() {
        try{
            if (ratio != null && !"".equals(ratio)){
                return Float.valueOf(ratio) * 100;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0.01f;
    }

    public String getRatioStr() {

        try{
            if (ratio != null && !"".equals(ratio)){
                return (int)(Float.valueOf(ratio) * 100) + "%";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

}

package seoultech.se.tetris.component.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Data {
    protected String level;
    protected String color_weak;
    protected String display;
    protected String left,right,rotate,harddrop,pause,down;
    Object setting;
    BufferedWriter writer;
    JSONObject js;

    public Data() throws IOException {
        try {
            setting = new JSONParser().parse(new FileReader("src/main/java/seoultech/se/tetris/component/model/setting.json"));
            js = (JSONObject) setting;

            level = (String) js.get("Level");
            color_weak = (String) js.get("Color_weak");
            display = (String) js.get("Display");
            left = (String) js.get("left");
            right = (String)js.get("right");
            rotate = (String)js.get("rotate");
            harddrop = (String)js.get("hardDrop");
            pause =  (String)js.get("pause");
            down = (String)js.get("down");

        }
        catch (ParseException | IOException e){
            System.out.println(e);
        }
    }

    public void setLevel(String lv) throws IOException {
        level = lv;
        js.put("Level", "" + lv + "");
        //System.out.println("Set Level : " + lv);
    }

    public void setColor_weak(String colorweak) throws IOException {
        color_weak = colorweak;
        js.put("Color_weak","" + colorweak + "");

    }

    public void setDisplay(String dp) throws IOException {
        display = dp;
        js.put("Display","" + dp + "");
        System.out.println("Set Display : " + dp);

    }

    public void setLeft(int code) throws IOException {
        left = convertString(code);
        js.put("left","" + code + "");

    }

    public void setRight(int code) throws IOException {
        right = convertString(code);
        js.put("right","" + code + "");

    }

    public void setRotate(int code) throws IOException {
        rotate = convertString(code);
        js.put("rotate", "" + code + "");

    }

    public void setHarddrop(int code) throws IOException {
        harddrop = convertString(code);
        js.put("hardDrop","" + code + "");

    }

    public void setPause(int code) throws IOException {
        pause = convertString(code);
        js.put("pause","" + code + "");
    }

    public void setDown(int code) throws IOException {
        down = convertString(code);
        js.put("down","" + code + "");
    }

    public String getLevel(){ return level;}
    public String getColor_weak(){ return color_weak;}
    public String getDisplay(){ return display;}
    public int getLeft(){ return convertInt(left);}
    public int getRight(){ return convertInt(right);}
    public int getRotate(){ return convertInt(rotate);}
    public int getHarddrop(){ return convertInt(harddrop);}
    public int getPause(){ return convertInt(pause);}
    public int getDown(){return convertInt(down);}

    public void resetting() throws IOException {
        js.put("Level",js.get("ori_Level"));
        js.put("Color_weak",js.get("ori_Color_weak"));
        js.put("Display",js.get("ori_Display"));
        js.put("left",js.get("ori_left"));
        js.put("right",js.get("ori_right"));
        js.put("rotate",js.get("ori_rotate"));
        js.put("hardDrop",js.get("ori_hardDrop"));
        js.put("pause",js.get("ori_pause"));
        js.put("down",js.get("ori_down"));
        System.out.println(js);

        saveSetting();

//        writer = new BufferedWriter(new FileWriter("src/main/java/seoultech/se/tetris/component/model/setting.json"));
//        writer.write(js.toJSONString());
//        writer.close();
    }

    private int convertInt(String code) {
        return Integer.parseInt(code);
    }

    private String convertString(int code){
        return Integer.toString(code);
    }

    public void printjs() {
        System.out.println(js);
    }

    public void saveSetting() throws IOException {
        writer = new BufferedWriter(new FileWriter("src/main/java/seoultech/se/tetris/component/model/setting.json"));
        writer.write(js.toJSONString());
        writer.close();
    }

}
package model.igc;

/**
 * Created by kenneth on 18/05/2018.
 */
public class Glider {
    private int glider_id;
    private String glider_no;

    public Glider(int glider_id, String glider_no) {
        this.glider_id = glider_id;
        this.glider_no = glider_no;
    }

    public int getGlider_id() {
        return glider_id;
    }

    public void setGlider_id(int glider_id) {
        this.glider_id = glider_id;
    }

    public String getGlider_no() {
        return glider_no;
    }

    public void setGlider_no(String glider_no) {
        this.glider_no = glider_no;
    }
}

package model.igc;

/**
 * Created by kenneth on 18/05/2018.
 */
public class Glider {
    private GliderID glider_id;
    private GliderNumber glider_no;

    public Glider(int glider_id, String glider_no) {
        this.glider_id = new GliderID(glider_id);
        this.glider_no = new GliderNumber(glider_no);
    }
    public Glider(String glider_no)
    {
        this.glider_id = null;
        this.glider_no = new GliderNumber(glider_no);
    }

    public int getGlider_id() {
        return glider_id.getGliderID();
    }

    public void setGlider_id(int glider_id) {
        this.glider_id.setGliderID(glider_id);
    }

    public String getGlider_no() {
        return glider_no.getGliderNumber();
    }

    public void setGlider_no(String glider_no) {
        this.glider_no.setGliderNumber(glider_no);
    }
}

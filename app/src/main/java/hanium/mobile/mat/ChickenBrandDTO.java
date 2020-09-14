package hanium.mobile.mat;

import java.io.Serializable;

public class ChickenBrandDTO implements Serializable {

    private long _id;
    private String bname;
    private byte[] image;

    public ChickenBrandDTO(long _id, String bname, byte[] image) {
        this._id = _id;
        this.bname = bname;
        this.image = image;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

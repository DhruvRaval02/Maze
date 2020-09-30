import java.awt.Polygon;

public class PolyObj{
    private Polygon p;
    private Boolean black;

    public PolyObj(Polygon p, Boolean black){
        this.p = p;
        this.black = black;
    }

    public Polygon getPolygon(){
        return p;
    }
    public Boolean isBlack(){
        return black;
    }
}
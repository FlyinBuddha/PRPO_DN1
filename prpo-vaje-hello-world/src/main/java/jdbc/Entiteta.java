package jdbc;
import java.io.Serializable;

public abstract class Entiteta implements Serializable{

    private int id;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}

package aviee.footballscout.pojo;

import java.io.Serializable;

public class Pair implements Serializable {

    private static final long serialVersionUID = 1L;

    String formation;
    String position;

    public Pair(String position, String formation) {
        this.formation = formation;
        this.position = position;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "formation='" + formation + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

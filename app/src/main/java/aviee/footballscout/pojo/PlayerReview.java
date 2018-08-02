package aviee.footballscout.pojo;

public class PlayerReview {

    String name;
    String club;
    String file;

    public PlayerReview() {
    }

    public PlayerReview(String name, String club, String file) {
        this.name = name;
        this.club = club;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}

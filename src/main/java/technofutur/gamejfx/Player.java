package technofutur.gamejfx;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.io.File;

public class Player implements Serializable{
    private String pseudo;
    private int win;
    private int loose;
    private String profilePic;

    public Player() { }

    public Player(String pseudo, int win, int loose, String profilePic) {
        this.pseudo = pseudo;
        this.win = win;
        this.loose = loose;
        this.profilePic = profilePic;
    }

    public static Player playerFromJson(String save) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(save), Player.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getProfilePic() { return profilePic; }

    public String getPseudo() { return pseudo; }

    public int getLoose() { return loose; }

    public int getWin() { return win; }

    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public void setWin(int win) { this.win = win; }

    public void setLoose(int loose) { this.loose = loose; }

    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }
}

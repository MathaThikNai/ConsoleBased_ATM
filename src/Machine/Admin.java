package Machine;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean login(String username, String password){
        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }
        return false;
    }


}

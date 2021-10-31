package jdbc;

import jdbc.Entiteta;

public class Uporabnik extends Entiteta {
    private String name;
    private String surname;
    private String username;
    private int id;

    public Uporabnik(String ime, String priimek, String username){
        this.name = ime;
        this.surname = priimek;
        this.username = username;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() { return username; }

    public void setUsername(String username){ this.username = username; }
    /* @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public int getId(){
        return id;
    }*/

}

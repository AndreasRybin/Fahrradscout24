package hska.fahrradscout24;

public class Advertisement {

    int code;
    String name, email, address;

    /* Setters */
    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /* Getters */
    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }
}
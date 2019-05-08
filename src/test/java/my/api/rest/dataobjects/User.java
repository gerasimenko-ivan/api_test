package my.api.rest.dataobjects;

public class User {
    public String name;
    public String email;
    public String password;

    public String name1;

    public String hamster;

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }


    public User setName1(String name1) {
        this.name1 = name1;
        return this;
    }


    public User setHamster(String hamster) {
        this.hamster = hamster;
        return this;
    }

    @Override
    public String toString() {
        if (name == null && email == null && password == null) {
            String objInfo = "{";
            if (name1 != null) {
                objInfo += "name1: '" + name1 + "', ";
            }

            if (hamster != null) {
                objInfo += "hamster: '" + hamster + "', ";
            }

            if (objInfo.length() > 2) {
                objInfo = objInfo.substring(0, objInfo.length()-2);
            }
            objInfo += "}";
            return objInfo;
        }
        return "{name: '" + name + "', email: '" + email + "', password: '" + password + "'}";
    }
}

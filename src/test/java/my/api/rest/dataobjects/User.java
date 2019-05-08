package my.api.rest.dataobjects;

public class User {
    public String name;
    public String email;
    public String password;

    public String surname1;
    public String name1;
    public String fathername1;

    public String hobby;
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


    public User setSurname1(String surname1) {
        this.surname1 = surname1;
        return this;
    }
    public User setName1(String name1) {
        this.name1 = name1;
        return this;
    }
    public User setFathername1(String fathername1) {
        this.fathername1 = fathername1;
        return this;
    }


    public User setHobby(String hobby) {
        this.hobby = hobby;
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
            if (surname1 != null) {
                objInfo += "surname1: '" + surname1 + "', ";
            }
            if (name1 != null) {
                objInfo += "name1: '" + name1 + "', ";
            }
            if (fathername1 != null) {
                objInfo += "fathername1: '" + fathername1 + "', ";
            }

            if (hobby != null) {
                objInfo += "hobby: '" + hobby + "', ";
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

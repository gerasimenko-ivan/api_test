package my.api.rest.dataobjects;

public class User {
    public String name;
    public String email;
    public String password;

    public String surname1;
    public String name1;
    public String fathername1;

    public String hobby;
    public String cat;
    public String dog;
    public String parrot;
    public String cavy;
    public String hamster;
    public String squirrel;
    public String phone;
    public String adres;
    public String gender;
    public String birthday;
    public String date_start;
    public String inn;

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
    public User setCat(String cat) {
        this.cat = cat;
        return this;
    }
    public User setDog(String dog) {
        this.dog = dog;
        return this;
    }
    public User setParrot(String parrot) {
        this.parrot = parrot;
        return this;
    }
    public User setCavy(String cavy) {
        this.cavy = cavy;
        return this;
    }
    public User setHamster(String hamster) {
        this.hamster = hamster;
        return this;
    }
    public User setSquirrel(String squirrel) {
        this.squirrel = squirrel;
        return this;
    }
    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public User setAdres(String adres) {
        this.adres = adres;
        return this;
    }
    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }
    public User setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }
    public User setDate_start(String date_start) {
        this.date_start = date_start;
        return this;
    }
    public User setInn(String inn) {
        this.inn = inn;
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
            if (cat != null) {
                objInfo += "cat: '" + cat + "', ";
            }
            if (dog != null) {
                objInfo += "dog: '" + dog + "', ";
            }
            if (parrot != null) {
                objInfo += "parrot: '" + parrot + "', ";
            }
            if (cavy != null) {
                objInfo += "cavy: '" + cavy + "', ";
            }
            if (hamster != null) {
                objInfo += "hamster: '" + hamster + "', ";
            }
            if (squirrel != null) {
                objInfo += "squirrel: '" + squirrel + "', ";
            }
            if (phone != null) {
                objInfo += "phone: '" + phone + "', ";
            }
            if (adres != null) {
                objInfo += "adres: '" + adres + "', ";
            }
            if (gender != null) {
                objInfo += "gender: '" + gender + "', ";
            }
            if (birthday != null) {
                objInfo += "birthday: '" + birthday + "', ";
            }
            if (date_start != null) {
                objInfo += "date_start: '" + date_start + "', ";
            }
            if (inn != null) {
                objInfo += "inn: '" + inn + "', ";
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

package my.api.rest.dataobjects;

public class Company {
    public String company_name;
    public String company_type;
    public String[] company_users;
    public String email_owner;

    public Company setCompany_name(String company_name) {
        this.company_name = company_name;
        return this;
    }
    public Company setCompany_type(String company_type) {
        this.company_type = company_type;
        return this;
    }
    public Company setCompany_users(String[] company_users) {
        this.company_users = company_users;
        return this;
    }
    public Company setEmail_owner(String email_owner) {
        this.email_owner = email_owner;
        return this;
    }

    @Override
    public String toString() {
        return "{company_name: '" + company_name + "'; company_type: '" + company_type + "'; company_users: '" + company_users + "'; email_owner: '" + email_owner +"'}" ;
    }
}

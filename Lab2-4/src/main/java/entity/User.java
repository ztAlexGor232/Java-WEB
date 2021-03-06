package entity;

import java.io.Serializable;

public class User implements Identifiable, Serializable {

    private static final long serialVersionUID = 5113246202178236722L;
    private Integer id;
    private boolean admin;
    private String email;
    private String password;
    private String firstName;
    private String secondName;

    public User() {

    }

    public User(boolean admin, String email, String password, String firstName, String secondName) {
        this.admin = admin;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public User(Integer id, boolean admin, String email, String password, String firstName, String secondName) {
        this(admin, email, password, firstName, secondName);
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return (id != null) ? id.equals(other.id) : other.id == null;
    }

    @Override
    public int hashCode() {
        return ((id != null) ? id.hashCode() : 0) +
                Boolean.hashCode(admin) +
                ((email != null) ? email.hashCode() : 0) +
                ((password != null) ? password.hashCode() : 0) +
                ((firstName != null) ? firstName.hashCode() : 0) +
                ((secondName != null) ? secondName.hashCode() : 0);
    }

}

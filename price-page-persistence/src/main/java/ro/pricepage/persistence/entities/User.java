package ro.pricepage.persistence.entities;

import javax.persistence.*;

/**
 * User: radutoev
 * Date: 11.11.2012
 * Time: 18:42
 */
@Entity
@Table(name = "admins")
public class User
{
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role;

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false, length = 50)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "email", nullable = false, length = 50)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "role", nullable = false, length = 10)
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }
}

package crudboot.model;

import javax.persistence.*;

// security phase update :: >>>
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
// security phase update :: <<<

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")                                                                //  << spring boot phase update
    private String firstName;                                                                   //  << spring boot phase update

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")                                                                       //  << spring boot phase update
    private Integer age;                                                                        //  << spring boot phase update

    @Column(name = "email", unique = true)                                                      //  << spring boot phase update
    private String email;                                                                       //  << spring boot phase update

    // security phase update :: >>>
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String firstName, String lastName, String email, Integer age, String password, Set<Role> roles) {   // << spring boot phase update
        this.firstName = firstName;                                                                                 // << spring boot phase update
        this.lastName = lastName;
        this.email = email;                                                                                         // << spring boot phase update
        this.age = age;                                                                                             // << spring boot phase update
        this.password = password;
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }
    // security phase update :: <<<

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // spring boot phase update :: >>>
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRolesInString() {
        List<String> rolesList = new ArrayList<>();
        for (Role role : roles) {
            rolesList.add(role.getRole());
        }
        return String.join(" ", rolesList);
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
    // spring boot phase update :: <<<

    // spring security phase update :: >>>
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // UserDetails implementation :: >>>

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    // security phase update :: <<<

}

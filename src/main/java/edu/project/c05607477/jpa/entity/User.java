package edu.project.c05607477.jpa.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "User")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PIN_CODE")
    private Integer pinCode;

    @JoinTable(name = "User_ACCOUNTS",
            joinColumns = {
                    @JoinColumn(name = "User_USER_ID", referencedColumnName = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "account_ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
            })
    @ManyToMany
    private Collection<Account> accounts;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Collection<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }
}

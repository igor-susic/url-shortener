package com.demo.urlshortener.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ACCOUNTS")
public class Account implements Serializable {

    @Id
    private String accountId;

    @Column(length = 60)
    private String password;

    @ManyToOne(targetEntity =  Role.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public Account() {}

    public Account(String id, String password, Role role) {
        this.accountId = id;
        this.password = password;
        this.role = role;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

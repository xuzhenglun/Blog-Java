package com.reficul.model;

import java.io.Serializable;

/**
 * Created by xuzl on 16-2-19.
 */
public class User implements Serializable {
    public long Id;
    public String Name;
    public String PasswdHash;

    public long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getPasswdHash() {
        return PasswdHash;
    }

    public void setId(long id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPasswdHash(String passwdHash) {
        PasswdHash = passwdHash;
    }
}

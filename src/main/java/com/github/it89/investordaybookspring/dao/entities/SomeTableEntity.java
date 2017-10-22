package com.github.it89.investordaybookspring.dao.entities;

import javax.persistence.*;

@Entity
@Table(name="SomeTable")
public class SomeTableEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="ticker")
    private int code;

    @Column(name="caption")
    private String caption;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "SomeTableEntity{" +
                "id=" + id +
                ", ticker=" + code +
                ", caption='" + caption + '\'' +
                '}';
    }
}

package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("UserInfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    private String name;
    @Past
    private Date date;

    private String password;
    private String ssn;

    @OneToMany(mappedBy = "user")
    @JoinColumn(name = "id")
    private List<Post> posts;

    public User(Integer id, String name, Date date, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.password = password;
        this.ssn = ssn;
    }
}

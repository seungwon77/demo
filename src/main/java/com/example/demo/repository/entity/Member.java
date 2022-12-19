package com.example.demo.repository.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("member")
public class Member {
    private long memNo;
    private String memId;
    private String passwd;
    private String email;
    private String cellNo;
    private Date createDt;
    private Date updateDt;
}

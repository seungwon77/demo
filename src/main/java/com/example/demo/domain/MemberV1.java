package com.example.demo.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberV1 {
    private long memberNo;
    private String memberId;
    //    @Pattern(regexp = "^(?:[A-Z]()|[a-z]()|[0-9]()|[!@#$%^*()_+~`={}\\[\\]|\\:;\"',.?/-]())+$(?:\1\2\3|\1\2\4|\1\3\4|\2\3\4)")
    private String password;
    private String email;
    private String cellNo;
}

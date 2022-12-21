package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberV1 {
    @ApiModelProperty(notes = "회원번호", example = "1")
    private Long memberNo;

    @ApiModelProperty(notes = "회원ID", example = "memberid1", required = true)
    private String memberId;

    @ApiModelProperty(notes = "비밀번호", example = "password@@", required = true)
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(notes = "이메일", example = "test@test.com", required = true)
    private String email;

    @ApiModelProperty(notes = "휴대폰번호", example = "010-1234-1234", required = true)
    private String cellNo;
}

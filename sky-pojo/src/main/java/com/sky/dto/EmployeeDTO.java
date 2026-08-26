package com.sky.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    private String sex;

    private String idNumber;

}

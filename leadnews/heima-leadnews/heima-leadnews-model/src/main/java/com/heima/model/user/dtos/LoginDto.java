package com.heima.model.user.dtos;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {

    /**
     * 電話番号
     */
    @ApiModelProperty(value = "電話番号",required = true)
    private String phone;

    /**
     * パスワード
     */
    @ApiModelProperty(value = "パスワード",required = true)
    private String password;
}

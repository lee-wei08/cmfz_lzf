package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "姓名")
    private String username;
    @Excel(name = "密码")
    private String password;
    private String salt;// 盐
    @Excel(name = "昵称")
    private String nickname;
    @Excel(name = "电话")
    private String phone;// 电话
    @Excel(name = "省份")
    private String province;// 省
    @Excel(name = "城市")
    private String city;// 市
    @Excel(name = "签名")
    private String sign;// 签名
    @Excel(name = "照片", type = 2)
    private String photo;// 照片
    @Excel(name = "性别")
    private String sex;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", format = "yyyy-MM-dd")
    private Date createDate;
    private String starId;
}

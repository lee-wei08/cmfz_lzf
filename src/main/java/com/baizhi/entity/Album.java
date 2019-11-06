package com.baizhi.entity;

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
@Table(name = "t_album")
public class Album implements Serializable {

    @Id
    private String id;
    private String name;
    private String cover;
    private Integer count;// 专辑数量
    private String brief;//简介
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;// 创建时间
    private String starId;
    private Star star;

}

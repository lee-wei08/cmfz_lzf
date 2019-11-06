package com.baizhi.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Count implements Serializable {


    private String month;
    private String count;

}

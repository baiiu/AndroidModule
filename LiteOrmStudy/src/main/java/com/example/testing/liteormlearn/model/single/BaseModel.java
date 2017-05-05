package com.example.testing.liteormlearn.model.single;

import com.litesuits.orm.db.annotation.Check;
import com.litesuits.orm.db.annotation.Ignore;

import java.io.Serializable;

/**
 * @author MaTianyu
 * @date 14-7-22
 */
public class BaseModel implements Serializable {

  //BaseModel中配置全局字段
  @Check("bm NOT NULL") public String bm = "全都有";

  @Ignore private String ignore = " 这个属性不会出现在数据库里的。因为被标记了ignore";

  @Override public String toString() {
    return "BaseModel{" +
        "bm='" + bm + '\'' +
        '}';
  }
}

package com.example.testing.liteormlearn.model.me;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * author: baiiu
 * date: on 16/5/18 17:36
 * description:
 */

@Table("ModelA") public class ModelA {
  @PrimaryKey(AssignType.BY_MYSELF) public String id;
  @Column("name") public String name;
  @Column("age") public int age;

  @Ignore public String sky;

  public ModelA() {
  }

  public ModelA(String id, String name, int age, String sky) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.sky = sky;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getSky() {
    return sky;
  }

  public void setSky(String sky) {
    this.sky = sky;
  }
}

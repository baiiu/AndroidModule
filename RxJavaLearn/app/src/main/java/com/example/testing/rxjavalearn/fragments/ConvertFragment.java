package com.example.testing.rxjavalearn.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.testing.rxjavalearn.util.LogUtil;
import com.example.testing.rxjavalearn.bean.Course;
import com.example.testing.rxjavalearn.bean.Student;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * author: baiiu
 * date: on 16/5/20 15:56
 * description: 变换
 */
public class ConvertFragment extends Fragment {

  private Student[] students;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    List<Course> list = new ArrayList<>();
    list.add(new Course("course1"));
    list.add(new Course("course2"));
    Student student1 = new Student("studeng1", list);
    Student student2 = new Student("studeng2", list);
    Student student3 = new Student("studeng3", list);

    students = new Student[] { student1, student2, student3 };
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    //printStudentName();
    //printAllCourseMap();
    printAllCourseFlatMap();
  }

  private void printAllCourseFlatMap() {
    Observable.from(students)

        .flatMap(new Func1<Student, Observable<Course>>() {
          @Override public Observable<Course> call(Student student) {
            return Observable.from(student.courseList);
          }
        })

        .subscribe(new Action1<Course>() {
          @Override public void call(Course course) {
            LogUtil.d(course.name);
          }
        });
  }

  private void printAllCourseMap() {
    Observable.from(students).map(new Func1<Student, List<Course>>() {
      @Override public List<Course> call(Student student) {
        return student.courseList;
      }
    }).subscribe(new Action1<List<Course>>() {
      @Override public void call(List<Course> courses) {
        for (Course c : courses) {
          LogUtil.d(c.name);
        }
      }
    });
  }

  private void printStudentName() {

    Observable.from(students)
        //map操作符,转化事件对象
        .map(new Func1<Student, String>() {
          @Override public String call(Student student) {
            return student.name;
          }
        })

        //订阅
        .subscribe(new Action1<String>() {
          @Override public void call(String name) {
            LogUtil.d(name);
          }
        });
  }
}

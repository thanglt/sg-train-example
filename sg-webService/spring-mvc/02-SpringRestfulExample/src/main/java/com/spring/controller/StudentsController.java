package com.spring.controller;
 
import com.spring.datasource.ClassroomOutputType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.spring.datasource.Classroom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eggsy - eggsy_at_eggsylife.co.uk
 *
 */
@Controller
public class StudentsController {
 
	@RequestMapping(method=RequestMethod.GET,value="/students")
	public ModelAndView showStudentsPage() {
		Classroom classroom = new Classroom("Class One");
        classroom.setRemark("this is a test remark");
		ModelAndView mav = new ModelAndView("studentsView");
		mav.addObject("classRoom", classroom);
		return mav;
	}

    @RequestMapping(method=RequestMethod.GET,value="/classes")
	public ModelAndView showStudentsPage2() {
		Classroom classroom1 = new Classroom("Class One");
        classroom1.setRemark("this is a test remark");

        Classroom classroom2 = new Classroom("Class Two");
        classroom2.setRemark("this is a test remark 222");

        ArrayList list = new ArrayList();
        list.add(classroom1);
        list.add(classroom2);
		ModelAndView mav = new ModelAndView("classesView");
		mav.addObject("classroomOutput", new ClassroomOutputType(list));
		return mav;
	}

    @RequestMapping(method=RequestMethod.GET,value="/classesAll")
	public ModelAndView showStudentsPage3() {
		Classroom classroom1 = new Classroom("Class One");
        classroom1.setRemark("this is a test remark");

        Classroom classroom2 = new Classroom("Class Two");
        classroom2.setRemark("this is a test remark 222");

        List list = new ArrayList();
        list.add(classroom1);
        list.add(classroom2);
		ModelAndView mav = new ModelAndView("classesAllView");
		mav.addObject("classesAll", list);
		return mav;
	}


}
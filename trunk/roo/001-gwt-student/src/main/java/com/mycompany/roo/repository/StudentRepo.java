package com.mycompany.roo.repository;

import com.mycompany.roo.domain.Student;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Student.class)
public interface StudentRepo {
}

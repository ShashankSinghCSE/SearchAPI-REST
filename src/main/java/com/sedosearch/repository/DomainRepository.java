package com.sedosearch.repository;

import org.springframework.data.repository.CrudRepository;
import com.sedosearch.entity.Domain;

public interface DomainRepository extends CrudRepository<Domain, Long> {

}

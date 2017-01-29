package com.gaurav.elasticsearch.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gaurav.model.Driver;

/**
 * @author gauravSingh
 *
 */
public interface DriverIndexRepo extends ElasticsearchRepository<Driver, Integer> {

}

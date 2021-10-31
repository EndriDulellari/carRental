package com.rental.luca.generic;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public abstract class GenericService {


    protected Query generateQuery(Map<String, String> filters) {
        Query query = new Query();
        List<Criteria> criteria = checkFilter(filters);
        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        return query;
    }


    private List<Criteria> checkFilter(Map<String, String> filters) {
        List<Criteria> criteria = new ArrayList<>();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String filterName = entry.getKey();
            String filterValue = entry.getValue();
            if (filterValue != null && !filterValue.isEmpty())
                criteria.add(Criteria.where(filterName).is(filterValue));
        }
        return criteria;
    }
}

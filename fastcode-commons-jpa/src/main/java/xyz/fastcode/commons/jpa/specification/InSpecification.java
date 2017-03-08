package xyz.fastcode.commons.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class InSpecification<T> extends SingleSpecification<T> {

    private final String propName;
    private final Collection propValue;

    public InSpecification(String propName, Collection propValue) {
        this.propName = propName;
        this.propValue = propValue;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return getFieldPath(root, query, propName).in(propValue);
    }
}

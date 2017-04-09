package xyz.dreamcoder.minicommons.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LikeSpecification<T> extends SingleSpecification<T> {

    private final String propName;
    private final Object propValue;

    public LikeSpecification(String propName, Object propValue) {
        this.propName = propName;
        this.propValue = propValue;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.like(
                getFieldPath(root, query, propName), "%" + propValue.toString() + "%");
    }
}

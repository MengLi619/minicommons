package xyz.fastcode.commons.jpa.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public abstract class SingleSpecification<T> implements Specification<T> {

    private static final String JoinSeparator = ".";

    @SuppressWarnings("UnusedParameters")
    public <X, Y> Path<Y> getFieldPath(Root<X> root, CriteriaQuery<?> query, String propName) {

        Join<?, ?> join = null;
        int joinSeparatorIndex;

        while ((joinSeparatorIndex = propName.indexOf(JoinSeparator)) != -1) {

            String joinName = propName.substring(0, joinSeparatorIndex);
            propName = propName.substring(joinSeparatorIndex + 1);

            join = join == null ? rootJoin(root, joinName) : innerJoin(join, joinName);
        }

        return join == null ? root.get(propName) : join.get(propName);
    }

    private <X> Join<X, ?> rootJoin(Root<X> root, String joinName) {

        for (Join<X, ?> join : root.getJoins()) {
            if (join.getAttribute().getName().equals(joinName)) {
                return join;
            }
        }

        return root.join(joinName);
    }

    private <Y> Join<Y, ?> innerJoin(Join<?, Y> join, String joinName) {

        for (Join<Y, ?> innerJoin : join.getJoins()) {

            if (innerJoin.getAttribute().getName().equals(joinName)) {
                return innerJoin;
            }
        }

        return join.join(joinName);
    }
}

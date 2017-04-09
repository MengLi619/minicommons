package xyz.dreamcoder.minicommons.jpa.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class Specifications<T> implements Specification<T> {

    private final Specification<T> spec;

    public Specifications() {
        this(null);
    }

    public Specifications(Specification<T> spec) {
        this.spec = spec;
    }

    public static <T> Specifications<T> where(Specification<T> spec) {
        return new Specifications<>(spec);
    }

    public static <T> Specifications<T> and(List<Specification<T>> specs) {
        return new Specifications<>(new ComposedSpecification<>(ComposedType.AND, specs));
    }

    @SafeVarargs
    public static <T> Specification<T> and(Specification<T>... specs) {
        return new Specifications<>(new ComposedSpecification<>(ComposedType.AND, Arrays.asList(specs)));
    }

    public static <T> Specifications<T> or(List<Specification<T>> specs) {
        return new Specifications<>(new ComposedSpecification<>(ComposedType.OR, specs));
    }

    @SafeVarargs
    public static <T> Specification<T> or(Specification<T>... specs) {
        return new Specifications<>(new ComposedSpecification<>(ComposedType.OR, Arrays.asList(specs)));
    }

    public Specifications<T> and(Specification<T> other) {
        return new Specifications<>(new ComposedSpecification<>(ComposedType.AND, Arrays.asList(spec, other)));
    }

    public Specifications<T> or(Specification<T> other) {
        return new Specifications<>(new ComposedSpecification<>(ComposedType.OR, Arrays.asList(spec, other)));
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return spec == null ? null : spec.toPredicate(root, query, builder);
    }

    public static class ComposedSpecification<T> implements Specification<T> {

        private final ComposedType composedType;
        private final List<Specification<T>> specs;

        public ComposedSpecification(ComposedType composedType, List<Specification<T>> specs) {
            this.composedType = composedType;
            this.specs = specs;
        }

        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

            return composedType.combine(builder, specs.stream()
                    .filter(x -> x != null)
                    .map(x -> x.toPredicate(root, query, builder))
                    .toArray(Predicate[]::new));
        }
    }

    public enum ComposedType {
        AND {
            @Override
            public Predicate combine(CriteriaBuilder builder, Predicate... predicates) {
                return builder.and(predicates);
            }
        },
        OR {
            @Override
            public Predicate combine(CriteriaBuilder builder, Predicate... predicates) {
                return builder.or(predicates);
            }
        };

        abstract Predicate combine(CriteriaBuilder builder, Predicate... predicates);
    }
}



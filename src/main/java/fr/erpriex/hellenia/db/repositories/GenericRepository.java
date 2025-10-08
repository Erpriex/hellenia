package fr.erpriex.hellenia.db.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Optional;

public class GenericRepository<T, ID extends Serializable> {
    private final Class<T> type;
    private final SessionFactory sf;

    public GenericRepository(Class<T> type, SessionFactory sf) {
        this.type = type;
        this.sf = sf;
    }

    public T save(T entity) {
        try (Session s = sf.openSession()) {
            var tx = s.beginTransaction();
            s.persist(entity);
            tx.commit();
            return entity;
        }
    }

    public Optional<T> findById(ID id) {
        try (Session s = sf.openSession()) {
            return Optional.ofNullable(s.get(type, id));
        }
    }

    public T update(T entity) {
        try (Session s = sf.openSession()) {
            var tx = s.beginTransaction();
            T merged = (T) s.merge(entity);
            tx.commit();
            return merged;
        }
    }

    public void deleteById(ID id) {
        try (Session s = sf.openSession()) {
            var tx = s.beginTransaction();
            T ref = s.get(type, id);
            if (ref != null) s.remove(ref);
            tx.commit();
        }
    }
}

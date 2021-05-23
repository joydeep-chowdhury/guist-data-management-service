package joydeep.poc.guist.persistor.daos;

import java.util.List;

public interface PersistenceContract<T> {
    public void persist(T t);

    public List<T> retrieveAll();

    public List<T> retrieveAllByPage(int pageNo,int pageSize);
}

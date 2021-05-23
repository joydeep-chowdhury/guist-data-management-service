package joydeep.poc.guist.guist.orchestrator.services;

import java.util.List;

public interface PersistorClientService<T> {

    List<T> fetchAll();
    List<T> fetchByPage(int pageNo,int pageSize);
}

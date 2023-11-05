package service.external;

import domain.Conta;

public interface ContaEvent {
    public  enum  EventType {CREATED,UPDATED,DELETED};
    void dispatch(Conta conta,EventType type) throws Exception;
}

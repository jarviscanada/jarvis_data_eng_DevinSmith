package ca.jrvs.apps.trading;

import java.util.List;
import java.util.Optional;

import org.apache.http.client.HttpClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketDataDao extends JpaRepository<IexQuoteDom, String> {

    Optional<IexQuoteDom> findById(String ticker);

    List<IexQuoteDom> findAllById(Iterable<String> tickers);

    Optional<String> executeHttpGet(String url);

    HttpClient getHttpClient();

    boolean existsById(String s);

    List<IexQuoteDom> findAll();

    long count();

    void deleteById(String s);

    void delete(IexQuoteDom entity);

    void deleteAll(Iterable<? extends IexQuoteDom> entities);

    void deleteAll();

    IexQuoteDom save(IexQuoteDom entity);

    List<IexQuoteDom> saveAll(List<IexQuoteDom> entities);

}
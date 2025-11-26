package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Repositories;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Mappers.ReceiptDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ReceiptRepositoryAdapter implements ReceiptRepositoryProvider {
    MongoRepository<ReceiptDocument,String> mongoRepository;
    @Override
    public ReceiptRepositoryResponse save(Receipt receipt) {
        ReceiptDocument receiptDocument = ReceiptDocumentMapper.toReceiptDocument(receipt);
        this.save(receiptDocument);

        return ;
    }

    @Override
    public <S extends ReceiptDocument> S insert(S entity) {
        return mongoRepository.insert(entity);
    }

    @Override
    public <S extends ReceiptDocument> List<S> insert(Iterable<S> entities) {
        return mongoRepository.insert(entities);
    }

    @Override
    public <S extends ReceiptDocument> Optional<S> findOne(Example<S> example) {
        return mongoRepository.findOne(example);
    }

    @Override
    public <S extends ReceiptDocument> List<S> findAll(Example<S> example) {
        return mongoRepository.findAll(example);
    }

    @Override
    public <S extends ReceiptDocument> List<S> findAll(Example<S> example, Sort sort) {
        return mongoRepository.findAll(example,sort);
    }

    @Override
    public <S extends ReceiptDocument> Page<S> findAll(Example<S> example, Pageable pageable) {
        return mongoRepository.findAll(example, pageable);
    }

    @Override
    public <S extends ReceiptDocument> long count(Example<S> example) {
        return mongoRepository.count(example);
    }

    @Override
    public <S extends ReceiptDocument> boolean exists(Example<S> example) {
        return mongoRepository.exists(example);
    }

    @Override
    public <S extends ReceiptDocument, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return mongoRepository.findBy(example, queryFunction);
    }

    @Override
    public <S extends ReceiptDocument> S save(S entity) {
        return mongoRepository.save(entity);
    }

    @Override
    public <S extends ReceiptDocument> List<S> saveAll(Iterable<S> entities) {
        return mongoRepository.saveAll(entities);
    }

    @Override
    public Optional<ReceiptDocument> findById(String s) {
        return mongoRepository.findById(s);
    }

    @Override
    public boolean existsById(String s) {
        return mongoRepository.existsById(s);
    }

    @Override
    public List<ReceiptDocument> findAll() {
        return mongoRepository.findAll();
    }

    @Override
    public List<ReceiptDocument> findAllById(Iterable<String> strings) {
        return mongoRepository.findAllById(strings);
    }

    @Override
    public long count() {
        return mongoRepository.count();
    }

    @Override
    public void deleteById(String s) {
        mongoRepository.deleteById(s);
    }

    @Override
    public void delete(ReceiptDocument entity) {
        mongoRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        mongoRepository.deleteAllById(strings);
    }

    @Override
    public void deleteAll(Iterable<? extends ReceiptDocument> entities) {
        mongoRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        mongoRepository.deleteAll();
    }

    @Override
    public List<ReceiptDocument> findAll(Sort sort) {
        return mongoRepository.findAll(sort);
    }

    @Override
    public Page<ReceiptDocument> findAll(Pageable pageable) {
        return mongoRepository.findAll(pageable);
    }
}

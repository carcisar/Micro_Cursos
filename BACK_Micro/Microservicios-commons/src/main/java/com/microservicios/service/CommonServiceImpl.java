package com.microservicios.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CommonServiceImpl<E, R extends JpaRepository<E, Long>> implements CommonService<E> {
    
    @Autowired
    protected R repository;

    @Override
    @Transactional
    public Iterable<E> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public Optional<E> findById(Long id) {
        // Llama directamente al repositorio
        return repository.findById(id);
    }

    @Override
    @Transactional
    public E save(E entity) {
        // Llama directamente al repositorio
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Llama directamente al repositorio
        repository.deleteById(id);
    }
}

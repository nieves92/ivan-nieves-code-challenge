package com.clip.challenge.builder.impl;

import com.clip.challenge.builder.Builder;
import com.clip.challenge.dto.TransactionDTO;
import com.clip.challenge.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionBuilderImpl implements Builder<Transaction, TransactionDTO> {

    @Autowired
    ModelMapper modelMapper;

    public Transaction buildEntity(TransactionDTO transactionDTO) {
        return this.modelMapper.map(transactionDTO, Transaction.class);
    }

    @Override
    public TransactionDTO buildDto(Transaction entity) {
        return this.modelMapper.map(entity, TransactionDTO.class);
    }
}

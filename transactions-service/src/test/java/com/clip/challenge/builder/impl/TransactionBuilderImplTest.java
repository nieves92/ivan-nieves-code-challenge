package com.clip.challenge.builder.impl;

import com.clip.challenge.dto.TransactionDTO;
import com.clip.challenge.entity.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class TransactionBuilderImplTest {

    @InjectMocks
    private TransactionBuilderImpl transactionBuilder;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TransactionDTO transactionDTO;
    @Mock
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildEntityTest() {
        Mockito.when(this.modelMapper.map(ArgumentMatchers.eq(this.transactionDTO), ArgumentMatchers.eq(Transaction.class)))
                .thenReturn(this.transaction);

        Assertions.assertNotNull(this.transactionBuilder.buildEntity(this.transactionDTO));
    }

    @Test
    public void buildDtoTest() {
        Mockito.when(this.modelMapper.map(ArgumentMatchers.eq(this.transaction), ArgumentMatchers.eq(TransactionDTO.class)))
                .thenReturn(this.transactionDTO);

        Assertions.assertNotNull(this.transactionBuilder.buildDto(this.transaction));
    }

}

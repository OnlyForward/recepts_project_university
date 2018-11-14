package com.example.a123.recepts_project_university.model;

import com.example.a123.recepts_project_university.db.model.Receipt;
import com.example.a123.recepts_project_university.db.model.User;

import java.util.List;

public interface DbHelper {


    Long insertUser(final User user);

    List<User> getAllUsers();

    List<Receipt> getAllReceipts();

    Boolean isReceiptsEmpty();

    Long saveReceipts(Receipt receipt);

    Boolean saveReceiptsList(List<Receipt> receiptsList);

}

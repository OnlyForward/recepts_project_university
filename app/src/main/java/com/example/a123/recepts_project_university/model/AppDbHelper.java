package com.example.a123.recepts_project_university.model;

import com.example.a123.recepts_project_university.db.model.DaoMaster;
import com.example.a123.recepts_project_university.db.model.DaoSession;
import com.example.a123.recepts_project_university.db.model.Receipt;
import com.example.a123.recepts_project_university.db.model.User;
import  com.example.a123.recepts_project_university.db.model.StepToReceipts;

import java.util.List;

import javax.inject.Inject;

public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;


    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Long insertUser(final User user) {
        return mDaoSession.getUserDao().insert(user);
    }

    @Override
    public List<User> getAllUsers() {
        return mDaoSession.getUserDao().loadAll();

    }

    @Override
    public List<Receipt> getAllReceipts() {
        return mDaoSession.getReceiptDao().loadAll();

    }

    @Override
    public Boolean isReceiptsEmpty() {
        return !(mDaoSession.getReceiptDao().count() > 0);
    }

    @Override
    public Long saveReceipts(final Receipt receipt) {
        return mDaoSession.getReceiptDao().insert(receipt);
    }

    @Override
    public Boolean saveReceiptsList(final List<Receipt> receiptList) {
        mDaoSession.getReceiptDao().insertInTx(receiptList);
        return true;
    }

    public Receipt getReceipt(Long Key){
        return mDaoSession.getReceiptDao().load(Key);
    }

    public Boolean saveSteps(StepToReceipts stepToReceipts){
        mDaoSession.getStepToReceiptsDao().insert(stepToReceipts);
        return true;
    }

    public List<StepToReceipts> getSteps(Long key){
        return mDaoSession.getStepToReceiptsDao()._queryReceipt_MListStep(key);
    }

    public void deleteReceipt(Long key, long key1){
         mDaoSession.getReceiptDao().deleteByKey(key);
         mDaoSession.getStepToReceiptsDao().deleteByKey(key1);
    }

}

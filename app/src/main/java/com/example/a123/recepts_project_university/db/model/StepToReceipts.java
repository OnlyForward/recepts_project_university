package com.example.a123.recepts_project_university.db.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "StepToReceipts",active = true)
public class StepToReceipts {

    @Id
    private Long id_step;

    @Property(nameInDb = "id_receipts_steps")
    @NotNull
    private Long id_receipts_step;

    @Property(nameInDb = "step_number")
    private int step;

    @Property(nameInDb = "image_to_step")
    private String imageToStep;

    @Property(nameInDb = "description_to_step")
    private String ImageDesciption;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1387081919)
    private transient StepToReceiptsDao myDao;

    @Keep()
    public StepToReceipts(Long id_receipts, int step, String imageToStep,
            String ImageDesciption) {
        this.id_receipts_step = id_receipts;
        this.step = step;
        this.imageToStep = imageToStep;
        this.ImageDesciption = ImageDesciption;
    }

    @Generated(hash = 1704405326)
    public StepToReceipts() {
    }

    @Generated(hash = 1883482760)
    public StepToReceipts(Long id_step, @NotNull Long id_receipts_step, int step,
            String imageToStep, String ImageDesciption) {
        this.id_step = id_step;
        this.id_receipts_step = id_receipts_step;
        this.step = step;
        this.imageToStep = imageToStep;
        this.ImageDesciption = ImageDesciption;
    }

    public Long getId_receipts() {
        return this.id_receipts_step;
    }

    public void setId_receipts(Long id_receipts) {
        this.id_receipts_step = id_receipts;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getImageToStep() {
        return this.imageToStep;
    }

    public void setImageToStep(String imageToStep) {
        this.imageToStep = imageToStep;
    }

    public String getImageDesciption() {
        return this.ImageDesciption;
    }

    public void setImageDesciption(String ImageDesciption) {
        this.ImageDesciption = ImageDesciption;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public Long getId_receipts_step() {
        return this.id_receipts_step;
    }

    public void setId_receipts_step(Long id_receipts_step) {
        this.id_receipts_step = id_receipts_step;
    }



    public Long getId_step() {
        return this.id_step;
    }

    public void setId_step(Long id_step) {
        this.id_step = id_step;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 435369309)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getStepToReceiptsDao() : null;
    }

}

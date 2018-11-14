package com.example.a123.recepts_project_university.db.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

@Entity(active = true, nameInDb = "Receipts")
public class Receipt {

    @Id
    private Long id_receipts;


    private Long userId;

    @ToOne(joinProperty = "userId")
    private User mUser;

    @NotNull
    @Property(nameInDb = "description")
    private String Description;

    @NotNull
    @Property(nameInDb = "title")
    private String Title;

    @NotNull
    @Property(nameInDb = "icon")
    private String Icon;

    @NotNull
    @Property(nameInDb = "image_main")
    private String ImageMain;


    @NotNull
    @Property(nameInDb = "ingredients")
    private String Ingredients;

    @ToMany(joinProperties = {@JoinProperty(name = "id_receipts", referencedName = "id_receipts_step")})
    private List<StepToReceipts> mListStep;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    /** Used for active entity operations. */
    @Generated(hash = 1424773800)
    private transient ReceiptDao myDao;


    @Keep()
    public Receipt(Long id_receipts, Long userId, @NotNull String Description, @NotNull String Title,
            @NotNull String Icon, @NotNull String ImageMain, @NotNull String Ingredients) {
        this.id_receipts = id_receipts;
        this.userId = userId;
        this.Description = Description;
        this.Title = Title;
        this.Icon = Icon;
        this.ImageMain = ImageMain;
        this.Ingredients = Ingredients;
    }

    @Generated(hash = 723313403)
    public Receipt() {
    }

    @Keep()
    private transient Long mUser__resolvedKey;

    public List<StepToReceipts> getListStep() {
        return mListStep;
    }

    public void setListStep(List<StepToReceipts> listStep) {
        mListStep = listStep;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public Long getId_receipts() {
        return this.id_receipts;
    }

    public void setId_receipts(Long id_receipts) {
        this.id_receipts = id_receipts;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImageMain() {
        return this.ImageMain;
    }

    public void setImageMain(String ImageMain) {
        this.ImageMain = ImageMain;
    }

    /** To-one relationship, resolved on first access. */
    @Keep()
    public User getMUser() {
        Long __key = this.userId;
        if (mUser__resolvedKey == null || !mUser__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User mUserNew = targetDao.load(__key);
            synchronized (this) {
                mUser = mUserNew;
                mUser__resolvedKey = __key;
            }
        }
        return mUser;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 929680949)
    public void setMUser(User mUser) {
        synchronized (this) {
            this.mUser = mUser;
            userId = mUser == null ? null : mUser.getUserId();
            mUser__resolvedKey = userId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 53973398)
    public List<StepToReceipts> getMListStep() {
        if (mListStep == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StepToReceiptsDao targetDao = daoSession.getStepToReceiptsDao();
            List<StepToReceipts> mListStepNew = targetDao
                    ._queryReceipt_MListStep(id_receipts);
            synchronized (this) {
                if (mListStep == null) {
                    mListStep = mListStepNew;
                }
            }
        }
        return mListStep;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1141418610)
    public synchronized void resetMListStep() {
        mListStep = null;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 858617663)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getReceiptDao() : null;
    }



}

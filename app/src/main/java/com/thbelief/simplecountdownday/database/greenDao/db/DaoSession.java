package com.thbelief.simplecountdownday.database.greenDao.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.thbelief.simplecountdownday.model.DataModel;

import com.thbelief.simplecountdownday.database.greenDao.db.DataModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dataModelDaoConfig;

    private final DataModelDao dataModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dataModelDaoConfig = daoConfigMap.get(DataModelDao.class).clone();
        dataModelDaoConfig.initIdentityScope(type);

        dataModelDao = new DataModelDao(dataModelDaoConfig, this);

        registerDao(DataModel.class, dataModelDao);
    }
    
    public void clear() {
        dataModelDaoConfig.clearIdentityScope();
    }

    public DataModelDao getDataModelDao() {
        return dataModelDao;
    }

}
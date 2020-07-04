package net.thumbtack.school.hospital.impl;

import net.thumbtack.school.hospital.database.dao.intrface.CommonDao;
import net.thumbtack.school.hospital.error.ServerException;

import org.junit.jupiter.api.BeforeEach;

public abstract class TestBase {

    private CommonDao commonDao;

    TestBase(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    @BeforeEach
    public void clearDB() throws ServerException {
        commonDao.clearDB();
    }
}

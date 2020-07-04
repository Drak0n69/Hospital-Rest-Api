package net.thumbtack.school.hospital.debug;

import net.thumbtack.school.hospital.database.dao.intrface.CommonDao;
import net.thumbtack.school.hospital.error.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebugService {

    private CommonDao commonDao;

    @Autowired
    public DebugService(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    public void clearDataBase() throws ServerException {
        commonDao.clearDB();
    }
}

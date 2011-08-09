package mytest.server.service;

import mytest.server.dao.ComputerDao;
import mytest.server.entity.Computer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ComputerServiceImpl implements ComputerService {

    @Resource
    private ComputerDao computerDao;

    public Computer getById(String id) {
        return computerDao.getById(id);
    }

    public List<Computer> getAll() {
        return computerDao.getAll();
    }

    public Computer save(Computer computer) {
        return computerDao.save(computer);
    }

    public Computer update(Computer computer) {
        return computerDao.update(computer);
    }

    public void delete(Computer computer) {
        computerDao.delete(computer);
    }
}

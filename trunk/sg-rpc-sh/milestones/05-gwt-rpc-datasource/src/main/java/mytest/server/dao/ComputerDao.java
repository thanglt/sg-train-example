package mytest.server.dao;

import mytest.server.entity.Computer;
import java.util.List;

public interface ComputerDao {

    Computer getById(String id);

    List<Computer> getAll();

    Computer save(Computer computer);

    Computer update(Computer computer);

    void delete(Computer computer);
}

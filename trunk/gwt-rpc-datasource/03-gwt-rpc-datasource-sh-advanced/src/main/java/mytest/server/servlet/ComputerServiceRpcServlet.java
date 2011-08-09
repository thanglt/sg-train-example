package mytest.server.servlet;

import mytest.client.service.ComputerServiceRPC;
import mytest.server.entity.Computer;
import mytest.server.service.ComputerService;
import mytest.shared.ComputerDto;
import mytest.shared.lib.GenericGwtRpcList;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

@Service("computerServiceRpcServlet")
public class ComputerServiceRpcServlet implements ComputerServiceRPC {

    @Resource
    private ComputerService computerService;

    public List<ComputerDto> fetch(Integer startRow, Integer endRow, final String sortBy, Map<String, String> filterCriteria) {
        GenericGwtRpcList<ComputerDto> outList = new GenericGwtRpcList<ComputerDto>();
        List<Computer> computerList = computerService.getAll();
        List<ComputerDto> dtoList = new ArrayList<ComputerDto>();

        for(Computer computer : computerList){
            dtoList.add(DozerBeanMapperSingletonWrapper.getInstance().map(computer , ComputerDto.class));
        }

        if (filterCriteria != null && filterCriteria.size() > 0) {
            List<ComputerDto> filteredList = new ArrayList<ComputerDto>();
            if (filterCriteria.containsKey("type")) {
                String pattern = ".*" + filterCriteria.get("type") + ".*";
                for (ComputerDto yourDataObject : dtoList) {
                    if (Pattern.matches(pattern, yourDataObject.getType())) {
                        filteredList.add(yourDataObject);
                    }
                }
            }
            if (filterCriteria.containsKey("code")) {
                String pattern = ".*" + filterCriteria.get("code") + ".*";
                for (ComputerDto yourDataObject : dtoList) {
                    if (Pattern.matches(pattern, yourDataObject.getCode())) {
                        filteredList.add(yourDataObject);
                    }
                }
            }
            dtoList = filteredList;
        }

        Comparator<ComputerDto> comparator = new Comparator<ComputerDto>() {
            public int compare(ComputerDto o1, ComputerDto o2) {
                if ("type".equals(sortBy)) {
                    return (o1.getType().compareTo(o2.getType()));
                }
                if ("code".equals(sortBy)) {
                    return (o1.getCode().compareTo(o2.getCode()));
                }
                if ("-type".equals(sortBy)) {
                    return (o2.getType().compareTo(o1.getType()));
                }
                if ("-code".equals(sortBy)) {
                    return (o2.getCode().compareTo(o1.getCode()));
                }
                return 0;
            }
        };
        Collections.sort(dtoList, comparator);

        // if startRow and endRow are not null, the client wishes paged fetching.
        if (startRow != null && endRow != null) {
            // extract the requested part
            for (int i = startRow; (i < endRow && i < dtoList.size()); i++) {
                outList.add(dtoList.get(i));
            }
        } else {
            outList.addAll(dtoList);
        }
        outList.setTotalRows(dtoList.size());

        return outList;
    }

    @Transactional
    public ComputerDto add(ComputerDto data) {
        Computer computer = DozerBeanMapperSingletonWrapper.getInstance().map(data, Computer.class);
        computerService.save(computer);
        return data;
    }

    @Transactional
    public ComputerDto update(ComputerDto data) {
        Computer computer = DozerBeanMapperSingletonWrapper.getInstance().map(data, Computer.class);
        computerService.update(computer);
        return data;
    }

    @Transactional
    public void remove(ComputerDto data) {
        Computer computer = DozerBeanMapperSingletonWrapper.getInstance().map(data, Computer.class);
        computerService.delete(computer);
    }
}

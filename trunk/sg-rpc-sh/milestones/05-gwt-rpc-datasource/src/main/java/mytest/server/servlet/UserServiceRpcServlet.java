package mytest.server.servlet;

import mytest.client.service.UserServiceRPC;
import mytest.server.entity.User;
import mytest.server.service.UserService;
import mytest.shared.UserDto;
import mytest.shared.lib.GenericGwtRpcList;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

@Service("userServiceRpcServlet")
public class UserServiceRpcServlet implements UserServiceRPC {

    @Resource
    private UserService userService;

    @Transactional
    public UserDto add(UserDto data) {
        User user = DozerBeanMapperSingletonWrapper.getInstance().map(data, User.class);
        userService.save(user);
        return data;
    }


    @Transactional
    public void remove(UserDto data) {
        User user = DozerBeanMapperSingletonWrapper.getInstance().map(data, User.class);
        userService.delete(user);
    }

    @Transactional
    public UserDto update(UserDto data) {
        User user = DozerBeanMapperSingletonWrapper.getInstance().map(data, User.class);
        userService.update(user);
        return data;
    }

    private UserDto getById(String id) {
        User user = userService.getById(id);
        return DozerBeanMapperSingletonWrapper.getInstance().map(user, UserDto.class);
    }

    public List<UserDto> fetch(Integer startRow, Integer endRow, final String sortBy, Map<String, String> filterCriteria) {

        GenericGwtRpcList<UserDto> outList = new GenericGwtRpcList<UserDto>();


        // we copy the original list, cause we do not want to alter it.
//        List<UserDto> out = new ArrayList<UserDto>(items);
        List<User> userList = userService.getAll();
        List<UserDto> dtoList = new ArrayList<UserDto>();

        for (User user : userList) {
            dtoList.add(DozerBeanMapperSingletonWrapper.getInstance().map(user, UserDto.class));
        }

        // to implement filtering, retrieve the filter criterias from filterCriteria and build your query accordingly.
        // for the sake of argument, we filter the list:
        if (filterCriteria != null && filterCriteria.size() > 0) {

            List<UserDto> filteredList = new ArrayList<UserDto>();

            if (filterCriteria.containsKey("name")) {
                String pattern = ".*" + filterCriteria.get("name") + ".*";
                for (UserDto yourDataObject : dtoList) {
                    if (Pattern.matches(pattern, yourDataObject.getName())) {
                        filteredList.add(yourDataObject);
                    }
                }
            }

            if (filterCriteria.containsKey("location")) {
                String pattern = ".*" + filterCriteria.get("location") + ".*";
                for (UserDto yourDataObject : dtoList) {
                    if (Pattern.matches(pattern, yourDataObject.getLocation())) {
                        filteredList.add(yourDataObject);
                    }
                }
            }

            dtoList = filteredList;
        }

        // to implement sorting, use the transfered sortBy String. It contains the field name to sort,
        // preceded by a '-' if the sorting is to be descending.
        // of course, the following example is slow as hell, but if you use a database, you should be quite fast...
        Comparator<UserDto> comparator = new Comparator<UserDto>() {

            public int compare(UserDto o1, UserDto o2) {
                if ("name".equals(sortBy)) {
                    return (o1.getName().compareTo(o2.getName()));
                }
                if ("location".equals(sortBy)) {
                    return (o1.getLocation().compareTo(o2.getLocation()));
                }
                if ("-name".equals(sortBy)) {
                    return (o2.getName().compareTo(o1.getName()));
                }
                if ("-location".equals(sortBy)) {
                    return (o2.getLocation().compareTo(o1.getLocation()));
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

        // important to allow proper paging, we set the total counts of rows.
        outList.setTotalRows(dtoList.size());

        return outList;

    }

}
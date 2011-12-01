package com.mycompany.gwt.sales.server.servlet;

import com.mycompany.gwt.common.shared.GenericGwtRpcList;
import com.mycompany.gwt.sales.client.dto.RoleDto;
import com.mycompany.gwt.sales.client.service.RoleServiceRPC;
import com.mycompany.java.common.entity.Role;
import com.mycompany.java.common.service.RoleService;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("roleServiceRPCServlet")
public class RoleServiceRPCServlet implements RoleServiceRPC {

    private static final Logger log = Logger.getLogger(RoleServiceRPCServlet.class);

    @Resource
    private Mapper mapper;

    @Resource
    private RoleService roleService;

    @Override
    @Transactional
    public List<RoleDto> fetch(Integer startRow, Integer endRow, String sortBy, Map<String, String> filterCriteria) {
        GenericGwtRpcList<RoleDto> outList = new GenericGwtRpcList<RoleDto>();
        List<Role> roleList = roleService.getAll();
        List<RoleDto> dtoList = new ArrayList<RoleDto>();

        for (Role role : roleList) {
            dtoList.add(mapper.map(role, RoleDto.class));
        }

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

    @Override
    @Transactional
    public RoleDto add(RoleDto data) {
        Role role = mapper.map(data, Role.class);
        role.setProjectId("TEST");
        roleService.save(role);
        return mapper.map(role, RoleDto.class);
    }

    @Override
    @Transactional
    public RoleDto update(RoleDto data) {
        Role role = mapper.map(data, Role.class);
        roleService.update(role);
        return mapper.map(role, RoleDto.class);
    }

    @Override
    @Transactional
    public void remove(RoleDto data) {
        Role role = mapper.map(data, Role.class);
        roleService.delete(role);
    }

}

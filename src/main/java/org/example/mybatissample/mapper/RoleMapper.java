package org.example.mybatissample.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.mybatissample.model.Role;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

    int save(Role role);

    int update(Role role);

    int delete(Long id);
}

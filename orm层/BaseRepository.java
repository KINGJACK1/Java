package com.sn.activity.vote.core.model.base;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @Auther: Jack
 * @Date: 2019/1/8 13:14
 * @Description:
 */
public interface BaseRepository<T> {
    //增加数据
    public T save(T entity);

    //删除数据
    int delete(T entity);

    //修改数据
    public T update(T entity);

    //根据主键查询数据
    public T findById(Long id);


}

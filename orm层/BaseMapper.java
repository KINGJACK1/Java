package com.sn.activity.vote.core.infrastructure.model.mapper.base;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.sn.activity.vote.core.model.base.BaseRepository;
import lombok.val;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @Auther: Jack
 * @Date: 2019/1/8 14:45
 * @Description:
 */
@Repository
public class BaseMapper<T> {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 增加
     *
     * @author : ZhangXing
     * @date : 2019/1/8 16:01
     * @param:[entity]
     * @return:T
     */
    @Transactional
    public T save(T entity) {
        this.entityManager.persist(entity);
        return entity;
    }

    /**
     * 修改
     *
     * @author : ZhangXing
     * @date : 2019/1/8 16:01
     * @param:[entity]
     * @return:T
     */
    @Transactional
    public T update(T entity) {
        return this.entityManager.merge(entity);
    }

    /**
     * 根据主键查询bean
     *
     * @author : ZhangXing
     * @date : 2019/1/8 16:00
     * @param:[id]
     * @return:T
     */
    public T findById(Class<T> clazz, Long id) {
        return this.entityManager.find(clazz, id);
    }

    /**
     * 单字段条件查询
     *
     * @author : ZhangXing
     * @date : 2019/1/8 15:57
     * @param:[tablename, deleted, filed, obj]
     * @return:java.lang.Object
     */
    public T findOneBysql(String tablename, Integer deleted, String filed, Object obj) {

        StringBuilder sql = new StringBuilder("FROM " + tablename + " WHERE deleted=? ");
        Object entity = null;
        try {
            Query query = getQuery(deleted, filed, obj, sql);
            entity = query.getSingleResult();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } catch (NonUniqueResultException e) {
            throw new RuntimeException("返回数据封装异常，请检查方法的返回值与请求参数");
        } finally {
            entityManager.close();
        }
        return (T) entity;
    }

    private Query getQuery(Integer deleted, String filed, Object obj, StringBuilder sql) {
        Query query;
        if (StringUtils.isEmpty(filed)) {
            System.out.println("==================" + sql);
            query = entityManager.createQuery(sql.toString());
            query.setParameter(1, deleted);
        } else {
            StringBuilder append = sql.append(" AND " + filed + "=?");
            System.out.println("==================" + append);
            query = entityManager.createQuery(append.toString());
            query.setParameter(1, deleted);
            query.setParameter(2, obj);
        }
        return query;
    }

    /**
     * 单字段查询返回list
     *
     * @author : ZhangXing
     * @date : 2019/1/8 15:56
     * @param:[tablename, deleted, filed, obj]
     * @return:java.util.List<T>
     */
    public List<T> findListBysql(String tablename, Integer deleted, String filed, Object obj) {

        StringBuilder sql = new StringBuilder("FROM " + tablename + " WHERE deleted=?");
        List<T> list;
        try {
            Query query = getQuery(deleted, filed, obj, sql);
            list = query.getResultList();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } catch (NonUniqueResultException e) {
            throw new RuntimeException("返回数据封装异常，请检查方法的返回值与请求参数");
        } finally {
            entityManager.close();
        }
        return list;
    }

    /**
     * 通过多个字段返回列表
     *
     * @author : ZhangXing
     * @date : 2019/1/8 15:42
     * @param:[tablename, map]
     * @return:java.util.List<T>
     */
    public List<T> findByMoreFiled(String tablename, HashMap<String, Object> map) {

        StringBuilder sql = new StringBuilder("FROM " + tablename);
        List<T> listRe;
        try {
            Query query = getQueryByMap(map, sql);
            listRe = query.getResultList();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } finally {
            entityManager.close();
        }
        return listRe;
    }

    /**
     * 匹配多字段返回分页列表
     *
     * @author : ZhangXing
     * @date : 2019/1/9 13:15
     * @param:
     * @return:
     */
    public List<T> findByMoreFiledpages(String tablename, HashMap<String, Object> map, int start, int pageNumber) {

        StringBuilder sql = new StringBuilder("FROM " + tablename);
        List<T> listRe;
        try {
            Query query = getQueryByMap(map, sql);
            query.setFirstResult((start - 1) * pageNumber);
            query.setMaxResults(pageNumber);
            listRe = query.getResultList();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } finally {
            entityManager.close();
        }
        return listRe;
    }

    private Query getQueryByMap(HashMap<String, Object> map, StringBuilder sql) {
        String str = sql.toString();
        Set<String> set = map.keySet();
        List<String> list = new ArrayList<>(set);
        if (list.size() != 0) {
            sql.append(" WHERE ");
            for (String filed : list) {
                sql.append(filed + "=? AND ");
            }
            str = sql.substring(0, sql.length() - 4);
        }
        Query query = entityManager.createQuery(str);
        for (int i = 0; i < list.size(); i++) {
            query.setParameter(i + 1, map.get(list.get(i)));
        }
        return query;
    }

    public List<T> findByPages(String tablename, Integer deleted, String filed, Object obj, int start, int pageNumer) {
        StringBuilder sql = new StringBuilder("FROM " + tablename + " WHERE deleted=? ");
        List<T> list = null;
        try {
            Query query = getQuery(deleted, filed, obj, sql);
            query.setFirstResult((start - 1) * pageNumer);
            query.setMaxResults(pageNumer);
            list = query.getResultList();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } finally {
            entityManager.close();
        }
        return list;
    }

    @Transactional
    public int updateMoreFiled(String tablename, HashMap<String, Object> map, HashMap<String, Object> where) {
        StringBuilder sql = new StringBuilder("UPDATE " + tablename + "  SET ");
        Set<String> set = map.keySet();
        List<String> list = new ArrayList<>(set);
        for (int i = 0; i < list.size(); i++) {
            if (map.get(list.get(i)).getClass().getTypeName() == "java.lang.String") {
                sql.append(list.get(i) + "='" + map.get(list.get(i)) + "' , ");
            } else {
                sql.append(list.get(i) + "=" + map.get(list.get(i)) + " , ");
            }
        }
        StringBuilder substring = new StringBuilder(sql.substring(0, sql.length() - 2));
        int resurlt = 0;
        try {
            Query query = getQueryByMap(where, substring);
            ;
            resurlt = query.executeUpdate();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } finally {
            entityManager.close();
        }
        return resurlt;
    }

    @Transactional
    public int delete(T entity) {
        int num = 0;
        try {
            entityManager.remove(entityManager.merge(entity));
            num = 1;
        } catch (Exception e) {
            throw new RuntimeException("删除执行异常，请检查是否有外键或操作权限");
        } finally {
            entityManager.close();
        }
        return num;
    }

    public long findCount(String tablename, HashMap<String, Object> map) {
        long count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM " + tablename);
        try {
            Query query = getQueryByMap(map, sql);
            count = (long) query.getSingleResult();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } finally {
            entityManager.close();
        }
        return count;
    }

    public List<T> nativeSelect(String sql, List array, Class clazz) {
        List<T> list;
        try {
            Query nativeQuery = entityManager.createNativeQuery(sql, clazz);
            if (array != null && array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    nativeQuery.setParameter(i + 1, array.get(i));
                }
            }
            list = (List<T>) nativeQuery.getResultList();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } catch (Exception e) {
            throw new RuntimeException("SQL语句执行异常，请检查SQL语句语法是否正确");
        } finally {
            entityManager.close();
        }
        return list;
    }

    @Transactional
    public int nativeUpdate(String sql, List array) {
        int ret = 0;
        try {
            Query nativeQuery = entityManager.createNativeQuery(sql);
            if (array != null && array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    nativeQuery.setParameter(i + 1, array.get(i));
                }
            }
            ret = nativeQuery.executeUpdate();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("参数转换异常，请检查字段和参数类型是否匹配是否");
        } catch (Exception e) {
            throw new RuntimeException("SQL语句执行异常，请检查SQL语句语法是否正确");
        } finally {
            entityManager.close();
        }
        return ret;
    }
}

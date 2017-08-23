package com.me.comment.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.me.comment.bean.Page;


/**
 * BaseDao的MyBatis实现
 * @author g
 *
 * @param <T>
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
    
    @Resource(name="sqlSessionTemplate")
    private SqlSession session;
    private final String path = "mapper.";
    private Class type;
    
    public BaseDaoImpl(){
        this.type = this.getDAOClass(); 
    }
    
    @SuppressWarnings("all")
    private Class getDAOClass(){
         Class clazz = (Class)((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
         return clazz;
    }
    
    private String getMethodPath(String methodType){
        return path + type.getSimpleName() + "Dao." + methodType;
    }
    
    public void save(T obj) {
        session.insert(getMethodPath("save"), obj);
    }

    public void delete(T obj) {
        session.delete(getMethodPath("delete"), obj);
    }

    public void update(T obj) {
        session.update(getMethodPath("update"), obj);
    }

    public T get(Integer id) {
        return session.selectOne(getMethodPath("get"),id);
    }

}

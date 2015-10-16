package com.pm.help.dao;

/**
 * @ author pengsong
 * @ 功能：
 * @ 时间 2015年8月14日
 * @ 版权：北京网景信息技术有限公司
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import com.pm.mobile_case.entity.Mobile;
@Component
public class CommDao<T, ID extends Serializable> {
	

	@Resource(name = "sqlSession")
	protected SqlSessionTemplate sqlSession;
	
	
	/* 插入数据 */
	public Integer save(String sqlName, Object obj) throws Exception {
		Integer i = sqlSession.insert(sqlName, obj);
		//sqlSession.commit();
		//sqlSession.close();
		return i;
	}

	/* 更新数据 */
	public Integer update(String sqlName, Object obj) throws IOException {
		Integer i = sqlSession.update(sqlName, obj);
		//sqlSession.commit();
		//sqlSession.close();
		return i;
	}

	/* 删除数据 */
	public Integer deleteById(String sqlName, Object obj) throws IOException {
		Integer i = sqlSession.delete(sqlName, obj);
		//sqlSession.commit();
		//sqlSession.close();
		return i;
	}

	/* 根据条件，查找一条数据 */
	public Object findOne(String sqlName, Object obj) throws IOException {
		Object ob = sqlSession.selectOne(sqlName, obj);
		//sqlSession.commit();
		//sqlSession.close();
		return ob;
	}

	/* 根据条件，查找多条数据 */
	public List<T> findList(String sqlName, Object obj) throws IOException {
		List<T> list = sqlSession.selectList(sqlName, obj);
		//sqlSession.commit();
		//sqlSession.close();
		return list;
	}
	public List<T> findList(String sqlName, Object obj,RowBounds rb) throws IOException {
		List<T> list = sqlSession.selectList(sqlName, obj,rb);
		//sqlSession.commit();
		//sqlSession.close();
		return list;
	}
}

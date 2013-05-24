/**
 * 
 */
package com.cwa.nagrand.db;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.cwa.nagrand.db.arena.ArenaDAO;

/**
 * @author zero
 *
 */
public enum DBSessionManager
{
	INSTANCE;
	
	SqlSessionFactory factory;
	
	private DBSessionManager()
	{
		try {
			Reader reader = Resources.getResourceAsReader("config/db/db.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
			factory.getConfiguration().addMapper(ArenaDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<?> selectList(String statement, Object... params)
	{
		SqlSession session = null;
		try {
			session = getSession();
			if (params.length > 0)
				return session.selectList(statement, params[0]);
			else
				return session.selectList(statement);
		} finally {
			if (null != session) session.close();
		}
	}
	
	public Object select(String statement, Object... param)
	{
		SqlSession session = null;
		try {
			session = getSession();
			return session.selectOne(statement, param);
		} finally {
			if (null != session) session.close();
		}
	}
	
	public void insert(String statement, Object param)
	{
		SqlSession session = null;
		try {
			session = getSession();
			session.insert(statement, param);
		} finally {
			if (null != session) session.close();
		}
	}
	
	public void update(String statement, Object param)
	{
		SqlSession session = null;
		try {
			session = getSession();
			session.update(statement, param);
		} finally {
			if (null != session) session.close();
		}
	}
	
	public SqlSession getSession()
	{
		return factory.openSession();
	}
}

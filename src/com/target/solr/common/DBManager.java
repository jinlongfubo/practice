package com.target.solr.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;
/**
 * 1.建立数据库连接
 * 2.简单的CRUD单表操作
 * @author jinlong
 *
 */
public class DBManager {
	
	//1.静态加载
	private static DBManager dbManager;
	protected DBManager() {
		
	}
	public static DBManager getInstance(){
		if(dbManager==null){
			dbManager= new DBManager();
		}
		return dbManager;
	}
	
	/**
	 * 获得数据库连接
	 * @throws Exception 
	 */
	private Connection getConnection() throws Exception{
		
		//1.加载数据库驱动
		Class.forName("com.mysql.jdbc.Driver");
		//2.创建数据库连接
		Connection connection = DriverManager.getConnection(Config.getConnectionUrl(),
									Config.getConnectionUsername(),
									Config.getConnectionPassword()
								);
		
		return connection;
		
	}
	/**
	 * 获得预处理对象
	 * @throws Exception 
	 */
	
	private PreparedStatement getPreparedStatement(Connection connection,String sql) throws Exception{
		
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		return prepareStatement;
	}
	
	/**
	 * 查询数据库,这里运用断连接
	 * @throws Exception 
	 */
	
	public RowSet query(String sql) throws Exception  {
		Connection connection = getConnection();
		
		try{
			 //创建数据缓存对象
		  	CachedRowSet crs = new CachedRowSetImpl();
		  	//填充数据集
		  	crs.setPassword(Config.getConnectionPassword());
		  	crs.setUsername(Config.getConnectionUsername());
		  	crs.setUrl(Config.getConnectionUrl());
		  	crs.setCommand(sql);
		  	crs.execute();
		  	//关闭数据集及数据库连接
		  	crs.release();	
	  		//返回数据集
	  		return crs;
		}catch (Exception exception){
			throw new Exception("获取CachedRowSet时出错。:" + exception.toString() +
                    "/n sql:" +
                    sql);
		}finally{
			connection.close();
		}
	}
	/**
	 * 执行数据修改
	 * @throws Exception 
	 */
	public boolean execute(String sql) throws Exception{
		//获得数据连接
		Connection connection = getConnection();
		//获得预处理
		PreparedStatement preparedStatement = getPreparedStatement(connection, sql);
		//查询数据库
		boolean result = false;
		try {
			result = preparedStatement.execute(sql);
		} catch (Exception e) {
			connection.rollback();
		}
		//提交数据库
		connection.commit();
		//关闭数据集及数据库连接
		preparedStatement.close();
		connection.close();
		
		return result;
	}
 
}

package com.target.solr.common;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 索引通用类，包含一些索引常量和通用配置
 * @author jinlong
 *
 */
public class IndexCommon {

	private static IndexWriter indexWriter;
	private static IndexReader indexReader;
	private static IndexSearcher indexSearcher;
	private static Analyzer analyzer;	
	
	/**
	 * 初始化，清除索引库引用状态
	 */
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				if(indexWriter!=null){
					try {
						indexWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(indexReader!=null){
					try {
						indexReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 获取分词器
	 * @throws Exception 
	 */
	public static Analyzer getAnalyzer(){
		return analyzer==null?analyzer = new IKAnalyzer():analyzer;
	}
	
	/**
	 * 获取indexWriter对象
	 * @throws Exception 
	 */
	public static IndexWriter getIndexWriter(){
		if(indexWriter!=null){
			return indexWriter;
		}
		
		try{
			Directory directory = FSDirectory.open(new File(Config.getIndexPath()));
			IndexWriter indexWriter = new IndexWriter(directory,new IndexWriterConfig(Version.LATEST,getAnalyzer()));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return indexWriter;
	}
	
	/**
	 * 获取indexReader对象
	 */
	public static IndexReader getIndexReader(){
		if(indexReader!=null){
			return indexReader;
		}
		
		try{
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(Config.getIndexPath())));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return indexReader;
	}
	
	/**
	 * 获取indexSearcher
	 * @return
	 */
	public static IndexSearcher getIndexSearcher(){
		if(indexSearcher!=null){
			return indexSearcher;
		}
		indexSearcher = new IndexSearcher(getIndexReader());
		return indexSearcher;
	}
	/**
	 * 设置分词器
	 */
	public void setAnalyzer(Analyzer analyzer){
		IndexCommon.analyzer = analyzer;
		//更改解析器后重新初始化indexReader 和indexWriter
		if(indexWriter!=null){
			try {
				indexWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(indexReader!=null){
			try {
				indexReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 设置
	 */
	
}

package com.mark.demo.security.base;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


public class Sort implements Serializable
{
	
	//
	private static final long	serialVersionUID	= 3742701348738615700L;

	private static String DESC = "desc";
	
	private static String ASC = "asc";
	
	/**
	 * 字段
	 */
	private String[] fileds;
	
	private Short sorting;
	
	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 排序的枚举
	 */
	private SortEnum sort;
	
	public Sort(){}
	
	
	
	public Sort(String alias, String sort, String filed){
		setFiled(new String[]{filed});
		this.alias = alias;
		setSort(sort);
	}
	
	public Sort( String sort, String filed){
		setFiled(new String[]{filed});
		setSort(sort);
	}
	
	public Sort(SortEnum sort, String filed){
		setFiled(new String[]{filed});
		this.sort = sort;
	}
	
	public Sort(String alias,  SortEnum sort, String filed){
		setFiled(new String[]{filed});
		this.sort = sort;
		this.alias = alias;
	}
	
	public Sort(String alias,  String sort, String... fileds){
		setFiled(fileds);
		this.alias = alias;
		setSort(sort);
	}
	
	public Sort(String sort, String ... fileds){
		setFiled(fileds);
		setSort(sort);
	}
	
	public Sort( SortEnum sort, String... fileds){
		setFiled(fileds);
		this.sort = sort;
	}
	
	public Sort(String alias,  SortEnum sort, String... fileds){
		setFiled(fileds);
		this.sort = sort;
		this.alias = alias;
	}
	
	
	public String[] getFileds()
	{
		return fileds;
	}
	
	public String getFiled(){
		if(ArrayUtils.isNotEmpty(fileds)){
			return fileds[0];
		}
		return null;
	}

	public void setSort(String sort){
		if(DESC.equalsIgnoreCase(sort)){
			this.sort = SortEnum.DESC;
		}else if(ASC.equalsIgnoreCase(sort)){
			this.sort = SortEnum.ASC;
		}
	}
	

	public void setFiled(String[] fileds)
	{
		this.fileds = fileds;
	}


	public String getAlias()
	{
		return alias;
	}


	public void setAlias(String alias)
	{
		this.alias = alias;
	}


	public SortEnum getSort()
	{
		return sort;
	}

	public void setSort(SortEnum sort)
	{
		this.sort = sort;
	}
	

	@Override
	public String toString()
	{
		return toString(true);
	}

	/**
	 * 是否需要order by 字符串添加
	 * @param isOrderBy
	 * @return
	 */
	public String toString(boolean isOrderBy){
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(alias)){
			if(isOrderBy){
				sb.append(" order by " + alias + ".");
			}else{
				sb.append(" " + alias + ".");
			}
		}else{
			if(isOrderBy){
				sb.append(" order by " );
			}
		}
		for (int i = 0; i < fileds.length; i++)
		{
			sb.append(fileds[i] + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		
		switch (this.sort)
		{
		case ASC:
			sb.append(" asc");
			break;
		case DESC:
			sb.append(" desc");
			break;	
		default:
			break;
		}
		return sb.toString();
	}



	public Short getSorting()
	{
		return sorting;
	}



	public void setSorting(Short sorting)
	{
		this.sorting = sorting;
	}


	public enum SortEnum{
		/**
		 * 升序
		 */
		ASC,
		/**
		 * 降序
		 */
		DESC
	}
	
	
}

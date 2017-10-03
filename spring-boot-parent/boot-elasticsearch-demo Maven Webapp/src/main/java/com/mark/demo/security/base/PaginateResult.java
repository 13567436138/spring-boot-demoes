package com.mark.demo.security.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.mark.demo.security.utils.PaginateUtils;


public class PaginateResult<T> implements Serializable
{
    private static final long serialVersionUID = 3612116988761318827L;
    
    public PaginateResult()
    {
        super();
    }
    
    public PaginateResult(Pagination page, List<T> list)
    {
        this.page = page;
        this.rows = list;
    }
    
    private Pagination          page;
    
    private List<T>             rows;
    
    private Long 				total;
    
    private Map<String, Object> count;
    
    private Object              obj;
    
    public Object getObj()
    {
        return obj;
    }
    
    public void setObj(Object obj)
    {
        this.obj = obj;
    }
    
    public Map<String, Object> getCount()
    {
        return count;
    }
    
    public void setCount(Map<String, Object> count)
    {
        this.count = count;
    }
    
    public Pagination getPage()
    {
        return page;
    }
    
    public void setPage(Pagination page)
    {
        this.page = page;
    }
    
   
    
    public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	/**
     * 集合式分页
     * @param result
     * @param page
     * @return
     */
    public static <E> PaginateResult<E> toPaginateResult(Collection<E> coll, Pagination page){
        page = PaginateUtils.getPagination(page.getPageSize(), page.getCurrentPage(), coll.size());
        List<E> result = Lists.newLinkedList(coll);
        Integer currentPage = page.getCurrentPage();
        Integer form = (currentPage - 1) * page.getPageSize();              //1: 0   2:20  0
        Integer to = (currentPage * page.getPageSize());                    //1:20   2:40  20
        if( !page.getHasNextPage() ){                                       //最后一页的时候
            to = result.size();                     
        }
        return new PaginateResult<E>(page, result.subList( form , to ));
    }
}

package kr.green.spring.pagination;

public class Criteria {
	private int page;//현재 페이지
	private int perPageNum;// 한 페이지 당 컨텐츠 갯수
	
	// source -> using
	public Criteria() {
		this.page = page=1;
		this.perPageNum = perPageNum=1;
	}

	// source -> get/set
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		if(page<=0) 
			this.page=1;
		else
		this.page = page;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {
		if(perPageNum < 1)
			this.perPageNum = 1;//한 페이지에 최소 1
		/*
		 * if(perPageNum < 10)
			this.perPageNum = 10;한 페이지에 최소 10를 의미*/
		this.perPageNum = perPageNum;
	}

	// source -> toString
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
	
	// 쿼리문에서 limit에 사용되는 인턱스를 계산하는 getter
	public int getPageStart() {
		return (page - 1) * perPageNum;
	}
}

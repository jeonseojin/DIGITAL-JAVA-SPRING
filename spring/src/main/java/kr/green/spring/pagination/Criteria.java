package kr.green.spring.pagination;

public class Criteria {
	private int page;//현재 페이지
	private int perPageNum;// 한 페이지 당 컨텐츠 갯수
	private String search;
	private int type;
	
	// source -> using
	public Criteria() {
		page = 1;
		perPageNum = 3;
		// 검색을 위해서 추가 기본 검색어는 입력되어 있지 않고 타입은 전체
		search="";
		type=0;
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
// 검색 추가 get/set
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getType() {
		return type;
	}
// (수정) 0~3까지 있기 때문에 다른 숫자는 예외처리를 위해서 코드를 추가
	public void setType(int type) {
		if(type<0 || type >3) {
			this.type=0;
		}else
		this.type = type;
	}

	// source -> toString
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", search=" + search + ", type=" + type + "]";
	}
	
	// 쿼리문에서 limit에 사용되는 인턱스를 계산하는 getter
	public int getPageStart() {
		return (page - 1) * perPageNum;
	}


}

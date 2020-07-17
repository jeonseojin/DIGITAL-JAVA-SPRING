package kr.green.springtest.pagination;

public class Criteria {
	private int page;
	private int perPageNum;
	private int type;// 검색타입 int or string으로 가능 
	private String search;//검색어
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {// 페이지가 0보다 작거나 클때 1페이지로 지정
		if(page<=0)
			this.page = 1;
		else
			this.page = page;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	public void setPerPageNum(int perPageNum) {// 최대 게시물이 10개까지 보이도록 설정
		if(perPageNum < 10)
			this.perPageNum = 10;
		else
			this.perPageNum = perPageNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		/*if(type < 0 || type > 3)
			this.type = 1;
		else
			this.type = type;
			아래의 한줄로 사용 가능*/
		this.type=(type <0 || type > 3) ? 1: type;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	// 
	public int getStartPage() {
		return (page - 1) * perPageNum;
	}
	
	// 내용확인을 위한 투스트링
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", type=" + type + ", search=" + search + "]";
	}
	
	//생성자
	public Criteria() {
		page = 1;
		perPageNum = 10;
		search = "";
//		type = 0; int는 기본 초기값이 0이기 때문에 생략가능하다.
	}
}

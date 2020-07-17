package kr.green.spring.pagination;

public class PageMaker {
	private int totalCount;		// 전체 컨텐츠의 수(실제 페이지네이션에서는 적용되는 것이 없지만 마지막페이지를 표시하기 위해서 필요함)
	private int startPage;		// 실제(현재) 시작페이지
	private int endPage;		// 마지막 페이지
	private boolean prev;		// 이전버튼 활성화 여부
	private boolean next;		// 다음버튼 활성화 여부
	private int displayPageNum=3;	// 한번에 보여줄 수 있는 페이지의 갯수(페이지네이션에서 보여주는 페이지의 갯수)
	private Criteria criteria;	// 현재 페이지 정보
	
	public void calcData() {/*멤버변수를 계산하는것*/
		/* starPage와 endPage는 현재 페이지 정보인 criteria와 displayPageNum을 이용하여 계산
		 * displayPageNum이 10이고 현재 페이지가 3페이지면 startPage = 1, endPage = 10이 되도록 계산 */
		endPage = (int) (Math.ceil(criteria.getPage()/(double) displayPageNum)*displayPageNum);
		
		startPage = (endPage - displayPageNum)+1;
		/* 총 콘텐츠 갯수를 이용하여 마지막 페이지 번호를 계산 */
		int tempEndPage = (int)(Math.ceil(totalCount/(double)criteria.getPerPageNum()));
		
		/* 현재 페이지에 계산된 현재 페이지메이커의 마지막 페이지 번호와 실제 마지막 페이지 번호를 비교하여
		 * 작은 값이 마지막 페이지 번호가 됨 */
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		/* 현재 페이지메이커에 시작페이지가 1페이지면 prev가 없어야 함 */
		prev = startPage == 1 ? false : true;
		/* 현재 페이지메이커에 마지막 페이지에 컨텐츠의 마지막이 포함되어 있으면 next가 없어야 함 */
		next = endPage * criteria.getPerPageNum() >= totalCount ? false:true;
	}

	public int getLastEndPage() {
		return (int)(Math.ceil(totalCount/(double)criteria.getPerPageNum()));
		//실제 현재페이지와 상관없는 진짜 마지막 페이지
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {// get or set를 만든 후 calcDate();설정
		this.totalCount = totalCount;
		calcData();
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	
}

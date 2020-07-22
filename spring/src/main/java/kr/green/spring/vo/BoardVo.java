package kr.green.spring.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardVo {
//멤버 변수 생성 SQL에서 생성한 보드 객체와 동일하게 생성
	private int num;
	private String writer;
	private String title;
	private String content;
	private Date registerDate;
	private char isDel;
	private int views;
	private Date delDate;
	private int like;
	private String file;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegisterDate() {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String to = transFormat.format(registerDate);
		return to;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	// 변경한 내용
	/* jsp의 name과 동일할 경우 호출하기 때문에 setxxxx을 호출하기 때문에 변경 */
	public void setRegisterDate(String date) {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			registerDate = transFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public char getIsDel() {
		return isDel;
	}
	public void setIsDel(char isDel) {
		this.isDel = isDel;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "BoardVo [num=" + num + ", writer=" + writer + ", title=" + title + ", content=" + content
				+ ", registerDate=" + registerDate + ", isDel=" + isDel + ", views=" + views + ", delDate=" + delDate
				+ ", like=" + like + ", file=" + file + "]";
	}

	// 게시판에서 업로드된 파일명이 경로와 함께 뜨기 때문에 실제 파일명을 알기 위해서 "_" 뒤에 오는 제목 부분을 불러오는 코드
	public String getOrifile() {
		int index = file.indexOf("_");
		return file.substring(index+1);
	}
	
	
}

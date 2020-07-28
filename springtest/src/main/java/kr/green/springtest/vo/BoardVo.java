package kr.green.springtest.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardVo {

	private int num;
	// 기본키는 null값이 들어갈 수 없기 때문에 integer로 할 필요가 없음 
	private String title;
	private String content;
	private String writer;
	private Date registerDate;//java.util.Date로 연결
	private Date delDate;
	private char isDel;
	private int views;
	private int up;
	private String file;
	
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getRegisterDate() {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return transFormat.format(registerDate);
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public void setRegisterDate(String registerDate) {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.registerDate = transFormat.parse(registerDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
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
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "BoardVo [num=" + num + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", registerDate=" + registerDate + ", delDate=" + delDate + ", isDel=" + isDel + ", views=" + views
				+ ", up=" + up + ", file=" + file + "]";
	}
	/* DB에 저장된 file 이름은 /년도/월/일/uuid_파일명.확장자 로 되어있는데
	 * 사용자는 파일명.확장자만 보여줘야하기 때문에 getOriFile을 통해 원본 파일명을 알려준다.
	 * */
	public String getOriFile() {
		int index = file.indexOf("_");
		return file.substring(index+1);
	}

	
	
}

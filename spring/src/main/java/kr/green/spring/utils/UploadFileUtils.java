package kr.green.spring.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;



public class UploadFileUtils {
	public static String uploadFile(String uploadPath, String originalName, byte[] 	
			fileData)throws Exception{
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() +"_" + originalName;
		String savedPath = calcPath(uploadPath);
		
		/* C:\Users\Administrator\Desktop\새 폴더\2020\07\22\ uuid_test.jpg
		 * new File을 통해 이름을 가진 빈 파일이 생성이 된다.*/
		File target = new File(uploadPath + savedPath, savedName);
		
		//파일복사
		FileCopyUtils.copy(fileData, target);
		
		//폴더 경로를 \가 아닌 /로 바꾸는 코드
		String uploadFileName = makeIcon(uploadPath, savedPath, savedName);
		return uploadFileName;
	}
	
	private static String calcPath(String uploadPath) {
		//업로드 날짜 정보를 생성
		Calendar cal = Calendar.getInstance();
		
		//년도 경로를 생성 : \2020
		String yearPath = File.separator+cal.get(Calendar.YEAR);
		
		//년도와 월을 합친 월 경로를 생성 : \2020\07
		String monthPath = yearPath + File.separator 
            + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		
		//년도와 월 일을 합친 일 경로를 생성 : \2020\07\22
		String datePath = monthPath + File.separator 
            + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		//업로드할 폴더를 생성하는 과정
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;
 
	}
	/* 예를 들어 2020.07.22의 폴더가 존재하고 다음날 폴더를 생성할 경우 
	 * 2020 -> 폴더가 있어서 생성하지 않고 넘어감
	 * 07	-> 동일한 폴더가 있기 때문에 생성하지 않고 넘어감
	 * 23	-> 동일한 폴더가 없기 때문에 생성
	 * 즉 단계별로 폴더를 생성하기 때문에 파일의 경로를 계속 생성하는 것이 아니라 한곳에서 관리하기 위해서 
	 * 이미 생성된 폴더는 생성하지 않고 경로를 따라 들어가서 생성되어 있지 않는 폴더들만 생성함*/
	private static void makeDir(String uploadPath, String... paths/*배열*/) {
		//data를 기준으로 파일(폴더)을 생성
		if(new File(uploadPath+paths[paths.length-1]).exists())
			return;
		for(String path : paths) {// 경로가 있으면 다음을 진행
			File dirPath = new File(uploadPath + path);
			if( !dirPath.exists())
				dirPath.mkdir(); 
			//일까지 만들어 질때까지 진행
		}
	}
	private static String makeIcon(String uploadPath, String path, String fileName)
        	throws Exception{
		String iconName = uploadPath + path + File.separator + fileName;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

}
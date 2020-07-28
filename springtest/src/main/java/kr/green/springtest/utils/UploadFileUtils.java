package kr.green.springtest.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	/* 서버에 파일을 업로드 하고(파일 업로드 시 폴더가 없으면 폴더를 생성)
	 * 업로드된 파일명(서버경로 제외)을 알려주는 메서드
	 * */
	public static String uploadFile(String uploadPath, String originalName, byte[] 	
			fileData)throws Exception{
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() +"_" + originalName;
		String savedPath = calcPath(uploadPath);
		File target = new File(uploadPath + savedPath, savedName);
		FileCopyUtils.copy(fileData, target);
		String uploadFileName = makeIcon(uploadPath, savedPath, savedName);
		return uploadFileName;
	}
	/* 서버 경로에 업로드 날짜를 기준으로 폴더를 체크해야 날짜 경로를 
	 * 알려주는 메서드(폴더가 없으면 생성)
	 * 리턴값이 /년도/월/일이 반환
	 * */
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.separator+cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator 
            + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		String datePath = monthPath + File.separator 
            + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;
 
	}
	//uploadPath 기준으로 path 폴더가 없으면 만드는 메서드
	private static void makeDir(String uploadPath, String... paths) {
		if(new File(paths[paths.length-1]).exists())
			return;
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			if( !dirPath.exists())
				dirPath.mkdir();
		}
	}
	//	날짜경로와 파일명을 합친 문자열을 반환
	//	/년도/월/일/uuid_파일명.확장자
	private static String makeIcon(String uploadPath, String path, String fileName)
        	throws Exception{
		String iconName = uploadPath + path + File.separator + fileName;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
}
package Engine;

import java.io.File;
import java.io.IOException;
public class UserAccount {
	/*在和.project同一目录下创建users文件夹（如果存在则不创建）
	在users下创建用户名文件夹
	在用户名文件夹下创建info.txt文件
	第一行 为 密码，第二行 为 已有的棋局个数
	*/
	public UserAccount() throws IOException{
		File directory = new File("");//当前目录
		File file = new File(directory.getCanonicalPath()+"\\users");
		
		if (!file.exists() && !file.isDirectory()){
			file.mkdir();
		}
	}
	//创建成功则为true，否则false
	//若用户名存在则为false；
	//否则为true，并创建文件夹和info.txt
	public boolean Register(String account,String password){
		
		return false;
	}
	//登陆成功则为true，否则false
	//没有与用户名对应的文件夹名，或者密码错误 均返回false
	public boolean Login(String account,String password){
		
		return false;
	}
}

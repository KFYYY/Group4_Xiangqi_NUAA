package Engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class UserAccount {
	/*在和.project同一目录下创建users文件夹（如果存在则不创建）
	在users下创建用户名文件夹
	在用户名文件夹下创建password.txt文件和info.txt
 	password.txt内只有一行且第一行为 密码，
 	info.txt内只有一行且第一行为 已有的棋局个数
	账号密码必须是数字，字母或者下划线
	*/
	public UserAccount() throws IOException{
		File directory = new File("");//当前目录
		File file = new File(directory.getCanonicalPath()+"\\users");
		
		if (!file.exists() && !file.isDirectory()){
			file.mkdir();
		}
	}
	//判断账号或密码是否符合要求
	public boolean CheckInput(String Checked_Object)
	{
		String object = Checked_Object.toLowerCase();		//字母强制转换为小写，便于判断
		int object_Len = object.length();
		int object_loc;//object_flag = 1;
		for (object_loc = 0; object_loc<object_Len ; object_loc++)
		{
			char temp = object.charAt(object_loc);
			if (!(('0'<=temp && temp<='9')||('a'<=temp && temp<='z')||(temp == '_')))
			{
				return false;
			}
		}
		return true;
	}
	//在给定目录下新建指定文件名文件
	public static File CreatFile(File userfile,String filename)
	{
		File file = new File(userfile,filename);
		if(!file.exists())
		{
			try {
				file.createNewFile();	
				return file;
			}
			catch (IOException e) {
					e.printStackTrace();	}
		}
		return file;
	}
	//初始化给定文件，仅限一行数据 的 初始化
	public void InitializeFile(File filename,String data) throws IOException
	{
		 FileWriter fileWritter = new FileWriter(filename);
         BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
         bufferWritter.write(data);
         bufferWritter.close();
	}
	//创建失败，若用户名存在则为0；若账号或密码中有数字，字母和下划线以外的字符则注册失败，返回-1
	//创建成功，创建文件夹；创建info.txt，第一行写入初始棋局数0；创建password.txt，第一行写入密码；返回1
	public int Register(String account,String password) throws IOException{
		
		//先判断账号或密码中是否有数字，字母和下划线以外的字符，若有，直接返回-1
		if (!CheckInput(account) || !(CheckInput(password))) return -1;
		//尝试创建用户文件夹
		File directory = new File("");		//获取根目录
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		if (!userfile.exists())
		{
			userfile.mkdir();
			File infoFile = CreatFile(userfile,"info.txt");
			//System.out.println("666"+infoFile+"666");
			File passwordFile = CreatFile(userfile,"password.txt");
			InitializeFile(infoFile,"0");
			InitializeFile(passwordFile,password);
			return 1;
		}
		else
		{
			return 0;
		}
	}
	//判断登录时用户输入的密码是否正确(前提该用户存在且密码存在)
	public boolean CheckPassword(File userfile,String password)
	{
		File passwordFile = new File (userfile+"\\password.txt");
        BufferedReader reader = null;
        String StoredPassword = null;
        try {
           
        	FileReader fileReader = new FileReader(passwordFile);
            reader = new BufferedReader(fileReader);
            StoredPassword = reader.readLine();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        return StoredPassword.equals(password); 
	}
	//登录失败，若账号或密码中有数字，字母和下划线以外的字符返回-2
	//登录失败，没有与用户名对应的文件夹名，即该用户不存在，返回-1
	//登录失败，对应用户的密码输入错误，返回0
	//登陆成功，直接返回1
	public int Login(String account,String password) throws IOException
	{
		final int Input_Illogical = -2;  
		final int Invalid_Username = -1;  
		final int Password_Wrong = 0;  
		final int Log_In_Successed = 1; 
		if (!CheckInput(account) || !(CheckInput(password)))	
			return Input_Illogical;
		
		File directory = new File("");		//获取根目录
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		
		if (!userfile.exists())	return Invalid_Username;
		else	if (!CheckPassword(userfile,password)) return Password_Wrong; 
		else	return Log_In_Successed;
	}
	//读取info.txt中保存的棋局数
	public static String getInformation(File infofile) throws IOException
	{
		BufferedReader reader = null;
	    try {
	    	FileReader fileReader = new FileReader(infofile);
	        reader = new BufferedReader(fileReader); 
	        } 
	    catch (IOException e) {
	        e.printStackTrace();
	        } 
	    return reader.readLine();
	}
	//move为n*4的矩阵,矩阵行和列均从0开始编号
	//新增棋谱，棋谱的格式为每行四个数，前两个代表移动的原始坐标，后两个代表移动的最终坐标
	//四个数字间均以空格隔开
	//读取users\当前用户名(account)\info.txt的数字得到已经保存的局数（比如说5），新建6.txt,
	//向6.txt中读入move
	public static boolean createNewManual(String account,int n,int[][] move) throws IOException
	{
		File directory = new File("");		//获取根目录
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		
		File infofile = new File(userfile+"\\info.txt");
		String information = getInformation(infofile);
		
		Integer x = new Integer(information);x++;
		information = x.toString();
		
		File currentFile = CreatFile(userfile,information+".txt");
		FileWriter fileWritter = new FileWriter(currentFile);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        
        int movement,coordinate;						//步数及坐标
        for (movement = 0; movement < n; movement++)
        {
        	for (coordinate = 0;coordinate < 4; coordinate++)
        		bufferWritter.write(""+move[movement][coordinate]+" ");
        	bufferWritter.write("\n");
        }
        bufferWritter.close();
        
        FileWriter fileWritter2 = new FileWriter(infofile);
        BufferedWriter bufferWritter2 = new BufferedWriter(fileWritter2);
        bufferWritter2.write(information+"\n");
        bufferWritter2.close();
		return true;
	}
	//传入account,输出有多少个棋局个数
	public static int getManualNumber(String account) throws IOException
	{
		File directory = new File("");		//获取根目录
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		
		File infofile = new File(userfile+"\\info.txt");
		String information = getInformation(infofile);
		
		return Integer.parseInt(information);
	}
	//传入account和 文件名，从文件名中读出所有move数据
	//保证成立
	public static int[][] getManualMove(String account,String number) throws IOException
	{	
		File directory = new File("");		//获取根目录
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		
		File movementFile = new File(userfile+"\\"+number+".txt");
		FileReader fileReader = new FileReader(movementFile);
        BufferedReader reader = new BufferedReader(fileReader);
        
        String line = null;
        String[] tempArray=new String[10];
        int n = 0;
        
        //System.out.println(getManualMoveNumber(account,number));
        int [][] move = new int [getManualMoveNumber(account,number)][4] ;
   		while((line = reader.readLine()) != null)
   		{
   			tempArray = line.split(" ");  			//以空格为界将每行4个数据分割存入tempArray
   			
   			for (int coordinate = 0;coordinate < 4; coordinate++)
   				move[n][coordinate]=Integer.parseInt(tempArray[coordinate]);
   			n++;
   		}
   		//int n = tempArray.length/4;
   		
   		//System.out.println(tempArray.length+" "+n);
        reader.close();
   		return move;
	}
	//传入account 和 文件名，从文件名中读出所有数据的 组数totLine ，4个一行，一行为一组
	//保证成立
	public static int getManualMoveNumber(String account,String number) throws IOException{
		
		File directory = new File("");		//获取根目录
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		
		File movementFile = new File(userfile+"\\"+number+".txt");
		FileReader fileReader = new FileReader(movementFile);
        BufferedReader reader = new BufferedReader(fileReader);
        
        
        int totLine = 0;
        String line = reader.readLine();
       
   		while(line != null)
   		{
   			totLine++;
   			line = reader.readLine();			
   		} 
   		
   		reader.close();
		return totLine ;
	}
}

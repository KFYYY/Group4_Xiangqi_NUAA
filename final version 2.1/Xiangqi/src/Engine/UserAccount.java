package Engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class UserAccount {
	/*�ں�.projectͬһĿ¼�´���users�ļ��У���������򲻴�����
	��users�´����û����ļ���
	���û����ļ����´���password.txt�ļ���info.txt
 	password.txt��ֻ��һ���ҵ�һ��Ϊ ���룬
 	info.txt��ֻ��һ���ҵ�һ��Ϊ ���е���ָ���
	�˺�������������֣���ĸ�����»���
	*/
	public UserAccount() throws IOException{
		File directory = new File("");//��ǰĿ¼
		File file = new File(directory.getCanonicalPath()+"\\users");
		
		if (!file.exists() && !file.isDirectory()){
			file.mkdir();
		}
	}
	//�ж��˺Ż������Ƿ����Ҫ��
	public boolean CheckInput(String Checked_Object)
	{
		String object = Checked_Object.toLowerCase();		//��ĸǿ��ת��ΪСд�������ж�
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
	//�ڸ���Ŀ¼���½�ָ���ļ����ļ�
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
	//��ʼ�������ļ�������һ������ �� ��ʼ��
	public void InitializeFile(File filename,String data) throws IOException
	{
		 FileWriter fileWritter = new FileWriter(filename);
         BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
         bufferWritter.write(data);
         bufferWritter.close();
	}
	//����ʧ�ܣ����û���������Ϊ0�����˺Ż������������֣���ĸ���»���������ַ���ע��ʧ�ܣ�����-1
	//�����ɹ��������ļ��У�����info.txt����һ��д���ʼ�����0������password.txt����һ��д�����룻����1
	public int Register(String account,String password) throws IOException{
		
		//���ж��˺Ż��������Ƿ������֣���ĸ���»���������ַ������У�ֱ�ӷ���-1
		if (!CheckInput(account) || !(CheckInput(password))) return -1;
		//���Դ����û��ļ���
		File directory = new File("");		//��ȡ��Ŀ¼
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
	//�жϵ�¼ʱ�û�����������Ƿ���ȷ(ǰ����û��������������)
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
	//��¼ʧ�ܣ����˺Ż������������֣���ĸ���»���������ַ�����-2
	//��¼ʧ�ܣ�û�����û�����Ӧ���ļ������������û������ڣ�����-1
	//��¼ʧ�ܣ���Ӧ�û�������������󣬷���0
	//��½�ɹ���ֱ�ӷ���1
	public int Login(String account,String password) throws IOException
	{
		final int Input_Illogical = -2;  
		final int Invalid_Username = -1;  
		final int Password_Wrong = 0;  
		final int Log_In_Successed = 1; 
		if (!CheckInput(account) || !(CheckInput(password)))	
			return Input_Illogical;
		
		File directory = new File("");		//��ȡ��Ŀ¼
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		
		if (!userfile.exists())	return Invalid_Username;
		else	if (!CheckPassword(userfile,password)) return Password_Wrong; 
		else	return Log_In_Successed;
	}
	//��ȡinfo.txt�б���������
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
	//moveΪn*4�ľ���,�����к��о���0��ʼ���
	//�������ף����׵ĸ�ʽΪÿ���ĸ�����ǰ���������ƶ���ԭʼ���꣬�����������ƶ�����������
	//�ĸ����ּ���Կո����
	//��ȡusers\��ǰ�û���(account)\info.txt�����ֵõ��Ѿ�����ľ���������˵5�����½�6.txt,
	//��6.txt�ж���move
	public static boolean createNewManual(String account,int n,int[][] move) throws IOException
	{
		File directory = new File("");		//��ȡ��Ŀ¼
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		
		File infofile = new File(userfile+"\\info.txt");
		String information = getInformation(infofile);
		
		Integer x = new Integer(information);x++;
		information = x.toString();
		
		File currentFile = CreatFile(userfile,information+".txt");
		FileWriter fileWritter = new FileWriter(currentFile);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        
        int movement,coordinate;						//����������
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
	//����account,����ж��ٸ���ָ���
	public static int getManualNumber(String account) throws IOException
	{
		File directory = new File("");		//��ȡ��Ŀ¼
		File userfile = new File(directory.getCanonicalPath()+"\\users\\"+account);
		
		File infofile = new File(userfile+"\\info.txt");
		String information = getInformation(infofile);
		
		return Integer.parseInt(information);
	}
	//����account�� �ļ��������ļ����ж�������move����
	//��֤����
	public static int[][] getManualMove(String account,String number) throws IOException
	{	
		File directory = new File("");		//��ȡ��Ŀ¼
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
   			tempArray = line.split(" ");  			//�Կո�Ϊ�罫ÿ��4�����ݷָ����tempArray
   			
   			for (int coordinate = 0;coordinate < 4; coordinate++)
   				move[n][coordinate]=Integer.parseInt(tempArray[coordinate]);
   			n++;
   		}
   		//int n = tempArray.length/4;
   		
   		//System.out.println(tempArray.length+" "+n);
        reader.close();
   		return move;
	}
	//����account �� �ļ��������ļ����ж����������ݵ� ����totLine ��4��һ�У�һ��Ϊһ��
	//��֤����
	public static int getManualMoveNumber(String account,String number) throws IOException{
		
		File directory = new File("");		//��ȡ��Ŀ¼
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

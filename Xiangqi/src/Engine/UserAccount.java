package Engine;

import java.io.File;
import java.io.IOException;
public class UserAccount {
	/*�ں�.projectͬһĿ¼�´���users�ļ��У���������򲻴�����
	��users�´����û����ļ���
	���û����ļ����´���info.txt�ļ�
	��һ�� Ϊ ���룬�ڶ��� Ϊ ���е���ָ���
	*/
	public UserAccount() throws IOException{
		File directory = new File("");//��ǰĿ¼
		File file = new File(directory.getCanonicalPath()+"\\users");
		
		if (!file.exists() && !file.isDirectory()){
			file.mkdir();
		}
	}
	//�����ɹ���Ϊtrue������false
	//���û���������Ϊfalse��
	//����Ϊtrue���������ļ��к�info.txt
	public boolean Register(String account,String password){
		
		return false;
	}
	//��½�ɹ���Ϊtrue������false
	//û�����û�����Ӧ���ļ������������������ ������false
	public boolean Login(String account,String password){
		
		return false;
	}
}

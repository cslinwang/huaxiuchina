package com.huaxiuchina.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.huaxiuchina.dao.DaydealDao;
import com.huaxiuchina.model.Daydeal;
import com.huaxiuchina.model.User;
import com.huaxiuchina.util.DaydealCheck;
import com.huaxiuchina.util.GetDate;
import com.huaxiuchina.util.GpCheck;
import com.huaxiuchina.util.XLSReader;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DaydealUploadAction extends ActionSupport implements
		ModelDriven<User> {

	private User user = new User();

	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}

	// �ϴ��ļ����·��
	private final static String UPLOADDIR = "/upload";
	// �ϴ��ļ�����
	private List<File> file;
	// �ϴ��ļ�������
	private List<String> fileFileName;
	// �ϴ��ļ��������ͼ���
	private List<String> fileContentType;

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Map session = ActionContext.getContext().getSession();
	public Map request = (Map) ActionContext.getContext().get("request");

	public String execute() throws Exception {
		System.out.println("123123"+user.getType());
		for (int i = 0; i < file.size(); i++) {
			// ѭ���ϴ�ÿ���ļ�
			uploadFile(i);
		}
		DaydealDao dao = new DaydealDao();
		session.put("daydeallist",
				dao.selectAll(user.getName(), new GetDate().getDate()));
		return "daydealUploadSuccess";
	}

	// ִ���ϴ�����
	private void uploadFile(int i) throws FileNotFoundException, IOException {
		try {
			InputStream in = new FileInputStream(file.get(i));
			String dir = ServletActionContext.getRequest().getRealPath(
					UPLOADDIR);
			File fileLocation = new File(dir);
			// �˴�Ҳ������Ӧ�ø�Ŀ¼�ֶ�����Ŀ���ϴ�Ŀ¼
			if (!fileLocation.exists()) {
				boolean isCreated = fileLocation.mkdir();
				if (!isCreated) {
					// Ŀ���ϴ�Ŀ¼����ʧ��,������������,�����׳��Զ����쳣��,һ��Ӧ�ò���������������
					return;
				}
			}
			String fileName = this.getFileFileName().get(i);
			File uploadFile = new File(dir, fileName);
			// ��ȡ������
			// System.out.println(user.getType().toString()+ user.getName());
			OutputStream out = new FileOutputStream(uploadFile);
			byte[] buffer = new byte[1024 * 1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
			System.out.println("123"+user.getName());
			new DaydealCheck().isType(uploadFile.toString(), user.getType()
					.toString(), user.getName());
			
		} catch (FileNotFoundException ex) {
			System.out.println("1�ϴ�ʧ��!");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("2�ϴ�ʧ��!");
			ex.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
/*
 * package com.huaxiuchina.action;
 * 
 * import java.io.File; import java.io.FileInputStream; import
 * java.io.FileNotFoundException; import java.io.FileOutputStream; import
 * java.io.IOException; import java.io.InputStream; import java.io.OutputStream;
 * import java.util.List;
 * 
 * import org.apache.struts2.ServletActionContext;
 * 
 * import com.huaxiuchina.util.GpCheck; import com.huaxiuchina.util.XLSReader;
 * import com.opensymphony.xwork2.ActionSupport;
 * 
 * public class DaydealUploadAction extends ActionSupport {
 * 
 * //�ϴ��ļ����·�� private final static String UPLOADDIR = "/upload"; //�ϴ��ļ�����
 * private List<File> file; //�ϴ��ļ������� private List<String> fileFileName;
 * //�ϴ��ļ��������ͼ��� private List<String> fileContentType; public List<File>
 * getFile() { return file; }
 * 
 * public void setFile(List<File> file) { this.file = file; }
 * 
 * public List<String> getFileFileName() { return fileFileName; }
 * 
 * public void setFileFileName(List<String> fileFileName) { this.fileFileName =
 * fileFileName; }
 * 
 * public List<String> getFileContentType() { return fileContentType; }
 * 
 * public void setFileContentType(List<String> fileContentType) {
 * this.fileContentType = fileContentType; }
 * 
 * public String execute() throws Exception {
 * System.out.println(this.getFileFileName().get(0)); for (int i = 0; i <
 * file.size(); i++) { //ѭ���ϴ�ÿ���ļ� uploadFile(i); }
 * 
 * List list = new XLSReader().readExcelData("/upload/111.xls");
 * System.out.println(list); return "daydealUploadSuccess"; }
 * 
 * //ִ���ϴ����� private void uploadFile(int i) throws FileNotFoundException,
 * IOException { try { InputStream in = new FileInputStream(file.get(i)); String
 * dir = ServletActionContext.getRequest().getRealPath(UPLOADDIR); File
 * fileLocation = new File(dir); //�˴�Ҳ������Ӧ�ø�Ŀ¼�ֶ�����Ŀ���ϴ�Ŀ¼
 * if(!fileLocation.exists()){ boolean isCreated = fileLocation.mkdir();
 * if(!isCreated) { //Ŀ���ϴ�Ŀ¼����ʧ��,������������,�����׳��Զ����쳣��,һ��Ӧ�ò��������������� return; } }
 * String fileName=this.getFileFileName().get(i); File uploadFile = new
 * File(dir, fileName); OutputStream out = new FileOutputStream(uploadFile);
 * byte[] buffer = new byte[1024 * 1024]; int length; while ((length =
 * in.read(buffer)) > 0) { out.write(buffer, 0, length); } in.close();
 * out.close(); } catch (FileNotFoundException ex) {
 * System.out.println("�ϴ�ʧ��!"); ex.printStackTrace(); } catch (IOException ex) {
 * System.out.println("�ϴ�ʧ��!"); ex.printStackTrace(); } } }
 */
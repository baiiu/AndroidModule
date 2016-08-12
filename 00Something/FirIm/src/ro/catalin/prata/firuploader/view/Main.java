package ro.catalin.prata.firuploader.view;

import ro.catalin.prata.firuploader.Model.Binary;
import ro.catalin.prata.firuploader.Model.Document;
import ro.catalin.prata.firuploader.provider.UploadService;

/**
 * Created with IntelliJ IDEA. User: will Date: 14/12/15 Time: 下午7:39 To change
 * this template use File | Settings | File Templates.
 * 
 * updateBuildVersionFields(); performUploadValidation();
 */
public class Main {
	public static Main m;
	private String apkAbsolutePath = "/Users/baiiu/Desktop/app-debug.apk";
	private String changeLogTa = "test";
	private String apiToken = "357bb8ca2872492a448651d85d626e04";

	private UploadService uploadService;
	public Binary binary;
	public ro.catalin.prata.firuploader.Model.Document document;

	private Main() {
		uploadService = new UploadService();
		binary = new Binary();
		document = new Document();

		updateBuildVersionFields();
		performUploadValidation();
	}

	public static void main(String[] args) {
		System.out.println("哈哈哈");
		m = new Main();
	}

	public static Main getInstance() {
		if (m == null) {
			m = new Main();
		}
		return m;
	}

	/**
	 * Updates the build version(code and name) fields
	 */
	public void updateBuildVersionFields() {
		// update the code and name text fields manifest build version code and
		// name values
		if (apkAbsolutePath != null) {
			binary.initPath(apkAbsolutePath);
		}
	}

	/**
	 * Performs validation before uploading the build to test flight, if
	 * everything is in order, the build is sent
	 */
	public void performUploadValidation() {
		if (apiToken == null) {
			System.out.println("请设置fir.im的api token , api token 不合法");
		} else if (apkAbsolutePath == null || apkAbsolutePath.length() < 3) {
			System.out.println("工程没有发现apk文件请单击设置来设置apk路径, apk文件不合法");
		} else if (binary.name == null) {
			binary.name = "apk";
		} else {
			uploadBuild();
		}
	}

	/**
	 * Uploads the build to test flight, it updates also the UI
	 */
	public void uploadBuild() {
		binary.initPath(apkAbsolutePath);
		// upload the build
		uploadService.sendBuild(null, apkAbsolutePath, apiToken, binary, changeLogTa);
	}

	public void setTips(String content) {
		System.out.println(content);
	}

	public void setTest(String text) {
		System.out.println(text);
	}

}

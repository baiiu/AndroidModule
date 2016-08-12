package ro.catalin.prata.firuploader.provider;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import ro.catalin.prata.firuploader.Model.Binary;
import ro.catalin.prata.firuploader.Model.CustomMultiPartEntity;
import ro.catalin.prata.firuploader.utils.SearchFile;
import ro.catalin.prata.firuploader.utils.UploadToRio;
import ro.catalin.prata.firuploader.utils.Utils;
import ro.catalin.prata.firuploader.view.Main;

/**
 * 上传服务
 */
public class UploadService implements CustomMultiPartEntity.ProgressListener {

	/**
	 * Used to notify the status of the upload action
	 */
	public CustomMultiPartEntity iconMultipartEntity;
	public CustomMultiPartEntity multipartEntity;
	public HttpPost post;

	/**
	 * 向FIR上传文件
	 * 
	 * @param url
	 * @param filePath
	 * @param apiToken
	 * @param binary
	 * @param appChanglog
	 */
	public void sendBuild(final String url, final String filePath, final String apiToken, final Binary binary,
			final String appChanglog) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Main.getInstance().setTest("开始上传....");
				UploadToRio uploadToRio = new UploadToRio(binary.bundleId, apiToken, binary.name, binary.versionName,
						binary.versionCode, appChanglog);

				String url = uploadToRio.uploadTicket.binaryUploadUrl;
				try {
					HttpClient client;
					client = new DefaultHttpClient();

					post = new HttpPost(url);

					/*****************************************
					 * upload icon
					 ***********************************************/
					SearchFile searchFile = new SearchFile(filePath);
					try {
						if (!binary.icon.isEmpty()) {
							InputStreamBody iconToUpload = searchFile.query(binary.icon);
							iconMultipartEntity = new CustomMultiPartEntity(UploadService.this);
							// set the api token
							iconMultipartEntity.addPart("key", new StringBody(uploadToRio.uploadTicket.iconKey));
							iconMultipartEntity.addPart("token", new StringBody(uploadToRio.uploadTicket.iconToken));
							iconMultipartEntity.addPart("file", iconToUpload);

							post.setEntity(iconMultipartEntity);

							// POST the build
							HttpResponse iconResponse = client.execute(post);
							HttpEntity iconEntity = iconResponse.getEntity();
							String iconResponseString = EntityUtils.toString(iconEntity, "UTF-8");
							System.out.println(iconResponseString);

							JSONObject iconJsonObject = new JSONObject(iconResponseString);

							if (iconResponse.getStatusLine().getStatusCode() == 200) {
								// send success upload status
								Main.getInstance().setTips("Icon upload success");
							} else {
								return;
							}

						}
						Main.getInstance().setTest("上传icon完成....");
						searchFile.zipFile.close();
					} catch (Exception e) {
						Utils.postErrorNoticeTOSlack(e);
					}

					/*****************************************
					 * upload file
					 ***********************************************/
					// get the apk file
					File fileToUpload = new File(filePath);

					multipartEntity = new CustomMultiPartEntity(UploadService.this);
					// set the api token
					multipartEntity.addPart("key", new StringBody(uploadToRio.uploadTicket.binaryKey));
					multipartEntity.addPart("token", new StringBody(uploadToRio.uploadTicket.binaryToken));
					multipartEntity.addPart("file", new FileBody(fileToUpload));
					multipartEntity.addPart("x:name", new StringBody(uploadToRio.appName, Charset.forName("UTF-8")));
					multipartEntity.addPart("x:version",
							new StringBody(uploadToRio.versionName, Charset.forName("UTF-8")));
					multipartEntity.addPart("x:build", new StringBody(uploadToRio.versionCode));
					multipartEntity.addPart("x:changelog",
							new StringBody(uploadToRio.changeLog, Charset.forName("UTF-8")));

					post.setEntity(multipartEntity);

					// POST the build
					HttpResponse response = client.execute(post);
					HttpEntity entity = response.getEntity();
					String responseString = EntityUtils.toString(entity, "UTF-8");
					JSONObject jsonObject = new JSONObject(responseString);

					if (response.getStatusLine().getStatusCode() == 200) {

					} else {

					}
					Main.getInstance().setTest("上传file完成....");

				} catch (Exception e) {
					// Ups! error occurred
					e.printStackTrace();
					Main.getInstance().setTest("e" + e.getMessage());
					Utils.postErrorNoticeTOSlack(e);
				}

			}
		}).start();

	}

	public void iconUpload() {

	}

	@Override
	public void transferred(long num) {

	}

}

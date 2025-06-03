package maven_build;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.Assert;

import com.company.project.util.I18nJavaFileGenerator;
import com.github.rapid.common.util.ArgsUtil;

/**
 * Build.java run on maven build, 
 * config by pom.xml plugin:exec-maven-plugin,
 * you can generate some file.
 */
public class Build {
	
	public static String BUILD_INFO_XML_FILE = "src/main/resources/build_info.xml";
	public static String I18N_OUTPUT_KEYS_JAVA_FILE = "src/main/java/generated/I18nKeys.java";
	private static String I18N_INPUT_TEMPLATE_FILE = "/i18n_key_java_template.ftl";
	String project_basedir;
	String project_version;
	Map<String, String> params;
	
	public static void main(String[] args) throws Exception {
		Build build = new Build(args);
		build.generateAll();
		
	}
	
	public Build(String[] args) {
		params = ArgsUtil.fromArgs(args);
		project_basedir = params.get("project_basedir");
		project_version = params.get("project_version");
		Assert.hasText(project_basedir,"project_basedir must be not blank");
		Assert.hasText(project_version,"project_version must be not blank");
	}
	
	
	public void generateAll() throws Exception {
		generateBuildInfoFile();
		new I18nJavaFileGenerator(project_basedir,I18N_INPUT_TEMPLATE_FILE,I18N_OUTPUT_KEYS_JAVA_FILE).generateI18nJavaFile();
	}

	private void generateBuildInfoFile() throws Exception {
		System.out.println("---------------- Build.java run on maven compile ------------------------");
		System.out.println("project_basedir:" + project_basedir);
		System.out.println("args:" + params);
		
		Properties buildInfo = generateBuildInfo(project_version);
		storeSaveInfoFile(project_basedir, buildInfo);
	}

	private static void storeSaveInfoFile(String project_basedir, Properties buildInfo) throws Exception {
		File buildInfoFile = new File(project_basedir, BUILD_INFO_XML_FILE);
		System.out.println("storeSaveInfoFile() buildInfoFile:"+buildInfoFile);
		FileOutputStream writer = new FileOutputStream(buildInfoFile);
		buildInfo.storeToXML(writer, "# generate by "+Build.class.getName()+".java on maven build, config by pom.xml plugin:exec-maven-plugin");
		writer.close();
	}

	private static Properties generateBuildInfo(String projectVersion) throws Exception {
		Date now = new Date();
		String buildTime = DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss");
		Properties buildInfo = new Properties();
		buildInfo.put("buildVersion", ""+projectVersion);
		buildInfo.put("buildTime", buildTime);
		buildInfo.put("buildTimestmap", ""+now.getTime());
		buildInfo.put("buildUser", "" + System.getProperty("user.name"));
		buildInfo.put("buildHostname", "" + InetAddress.getLocalHost().getHostName());
		System.out.println("buildInfo:" + buildInfo);

		return buildInfo;
	}

}

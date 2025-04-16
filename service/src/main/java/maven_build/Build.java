package maven_build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.github.rapid.common.util.ArgsUtil;
import com.github.rapid.common.util.PropertiesUtil;
import com.github.rapid.common.util.ResourceUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Build.java run on maven build, 
 * config by pom.xml plugin:exec-maven-plugin,
 * you can generate some file.
 */
public class Build {
	
	public static String BUILD_INFO_XML_FILE = "src/main/resources/build_info.xml";
	public static String I18N_KEYS_JAVA_FILE = "src/main/java/generated/I18nKeys.java";
	
	String project_basedir;
	String project_version;
	Map<String, String> params;
	
	public static void main(String[] args) throws Exception {
		Build build = new Build(args);
		build.generateBuildInfoFile(args);
		build.generateI18nFile();
	}
	
	public Build(String[] args) {
		params = ArgsUtil.fromArgs(args);
		project_basedir = params.get("project_basedir");
		project_version = params.get("project_version");
	}

	private void generateI18nFile() throws Exception {
		
		Properties i18nEnMap = PropertiesUtil.loadProperties(ResourceUtil.getResourceAsText("/ValidationMessages.properties"));
		Properties i18nZhCNMap = PropertiesUtil.loadProperties(ResourceUtil.getResourceAsText("/ValidationMessages_zh_CN.properties"));
		
//		System.out.println("i18nZhCNMap:"+i18nZhCNMap);
		
		Configuration cfg = new Configuration();
		String templateStr = ResourceUtil.getResourceAsText("/i18n_key_java_template.ftl");
		Template template = new Template("i18n_key_java_template",new StringReader(templateStr),cfg);
		Map model = new HashMap();
		model.put("i18nEnMap", i18nEnMap);
		model.put("i18nZhCNMap", i18nZhCNMap);
		String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		
		File i18nFile = new File(project_basedir,I18N_KEYS_JAVA_FILE);
		System.out.println("generatei18nFile() file:"+i18nFile);
		FileUtils.writeStringToFile(i18nFile, content,"UTF-8");
	}

	private void generateBuildInfoFile(String[] args) throws Exception {
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
		buildInfo.put("buildTimestmap", now.getTime());
		buildInfo.put("buildUser", "" + System.getProperty("user.name"));
		buildInfo.put("buildHostname", "" + InetAddress.getLocalHost().getHostName());
		System.out.println("buildInfo:" + buildInfo);

		return buildInfo;
	}

}

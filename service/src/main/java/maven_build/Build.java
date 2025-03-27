package maven_build;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.time.DateFormatUtils;
import com.github.rapid.common.util.ArgsUtil;

/**
 * Build.java run on maven build you can generate some file
 */
public class Build {

	public static void main(String[] args) throws Exception {
		Map<String, String> params = ArgsUtil.fromArgs(args);
		String project_basedir = params.get("project_basedir");
		System.out.println("---------------- Build.java run on maven compile ------------------------");
		System.out.println("project_basedir:" + project_basedir);
		System.out.println("args:" + params);

		Properties buildInfo = generateBuildInfo();
		storeSaveInfoFile(project_basedir, buildInfo);
	}

	private static void storeSaveInfoFile(String project_basedir, Properties buildInfo) throws Exception {
		File buildInfoFile = new File(project_basedir, "src/main/resources/build_info.xml");
		FileOutputStream writer = new FileOutputStream(buildInfoFile);
		buildInfo.storeToXML(writer, "# generate by Build.java on maven build");
		writer.close();
	}

	private static Properties generateBuildInfo() throws Exception {
		String buildTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		Properties buildInfo = new Properties();
		buildInfo.put("buildTime", buildTime);
		buildInfo.put("buildUser", "" + System.getenv("USERNAME"));
		buildInfo.put("buildHostname", "" + InetAddress.getLocalHost().getHostName());
		System.out.println("buildInfo:" + buildInfo);

		return buildInfo;
	}

}

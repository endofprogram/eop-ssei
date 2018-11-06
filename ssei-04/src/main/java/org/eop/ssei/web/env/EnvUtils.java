package org.eop.ssei.web.env;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * <p>根据操作系统进行Profile自动配置
 * <p>主要是为了减少开发人员的配置
 * <p>由于本框架只用于活动开发，不复杂
 * <p>规定Windows和Mac为开发环境，其它是生产环境
 * <p>程序会自动去应用符合环境的配置文件
 * <p>开发人员无需进行任何配置
 * @author lixinjie
 * @since 2018-08-08
 */
public class EnvUtils {
	
	public static final String PROFILE_DEV = "dev";
	public static final String PROFILE_TEST = "test";
	public static final String PROFILE_PROD = "prod";
	public static final String PROFILE_ACTIVE = getProfile();
	
	public static final String LOG4J_CONFIGURATIONFILE = "log4j.configurationFile";
	
	public static String getProfile() {
		if (isDev()) {
			return PROFILE_DEV;
		}
		if (isTest()) {
			return PROFILE_TEST;
		}
		return PROFILE_PROD;
	}
	
	public static boolean isDev() {
		if (!System.getProperties().containsKey(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME)) {
			return isWindows() || isMac();
		}
		return PROFILE_DEV.equals(System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));
	}
	
	public static boolean isTest() {
		return PROFILE_TEST.equals(System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}
	
	public static boolean isMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
	
	/**
	 * 此种方式在web环境下暂时不管用，
	 * 无法找到一个足够靠前的地方来调用它，
	 * 即在调用它时log4j2已经初始化过了
	 */
	public static void setLog4j2ConfigurationFile() {
		if (!System.getProperties().containsKey(LOG4J_CONFIGURATIONFILE)) {
			System.setProperty(LOG4J_CONFIGURATIONFILE, "log4j2/log4j2-" + PROFILE_ACTIVE + ".xml");
		}
	}
	
	/**通过编程方式重新指定log4j2.xml配置文件*/
	public static void setLog4j2ConfigurationFileProgrammatic() {
		if (!System.getProperties().containsKey(LOG4J_CONFIGURATIONFILE)) {
			LoggerContext context = LoggerContext.getContext(true);
			try {
				Resource log4j2xml = new ClassPathResource("log4j2/" + PROFILE_ACTIVE + "/log4j2.xml");
				if (!log4j2xml.exists()) {
					log4j2xml = new ClassPathResource("log4j2/log4j2.xml");
				}
				if (log4j2xml.exists()) {
					context.setConfigLocation(log4j2xml.getURI());
				}
			} catch (IOException e) {
				//ignore
			}
		}
	}
	
	/**激活Profile*/
	public static void activeProfile(ConfigurableEnvironment environment) {
		MapPropertySource mapPropertySource = (MapPropertySource)environment.getPropertySources().get(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME);
		Map<String, Object> systemProperties = mapPropertySource.getSource();
		if (!systemProperties.containsKey(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME)) {
			systemProperties.put(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, PROFILE_ACTIVE);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(PROFILE_ACTIVE);
	}
}
